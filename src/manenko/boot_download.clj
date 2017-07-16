(ns manenko.boot-download
  {:boot/export-tasks true}
  (:require [boot.core       :as boot]
            [boot.util       :refer [dbug info]]
            [clojure.java.io :as io])
  (:import java.net.URI
           java.nio.file.Paths))


(defn ^:private get-path
  [url]
  (.getPath (URI. url)))


(defn ^:private get-file-name
  [url]
  (let [path (Paths/get (get-path url)
                        (make-array String 0))]
    (str (.getFileName path))))


(defn get-download-url
  "Gets a url the given `TmpFile` was downloaded from."
  [tmpfile]
  (get tmpfile ::downloaded-from))


(defn get-all-downloaded-files
  "Gets a seq of files (as `TmpFile` objects) downloaded by the `download-file`
  task."
  [fileset]
  (let [all-files (boot/ls fileset)]
    (boot/by-meta [#(contains? % ::downloaded-from)] all-files)))


(defn get-all-files-downloaded-from
  "Gets a seq of files (as `TmpFile` objects) downloaded from the
  given url by the `download-file` task.

  It could happen that `fileset` has a few files downloaded from the
  same url. That's why this function returns a seq instead of a single
  object (or `nil`). The sequence will be empty, if there is no files
  downloaded from the given url."
  [fileset url]
  (let [all-files (boot/ls fileset)]
    (boot/by-meta [#(= (get % ::downloaded-from) url)])))


(boot/deftask download-file
  "Downloads a single file from the given url.

The task downloads the file and adds it to the fileset as an asset.

If the output file path is not set then the task will get the file
name from the url and store the file under that name in the fileset
root directory.

The task will fail if the output path is not specified and the url has
parameters (i.e. http://example.org/file?p=foo&q=bar)."
  [u url         VAL str "The location of the remote file."
   o output-file VAL str "The location used to save the file. Optional."]
  (let [tmp (boot/tmp-dir!)]
    (boot/with-pre-wrap fileset
      (info "Downloading %s to %s...\n" url output-file)
      (let [file-path (or output-file (get-file-name url))
            file      (io/file tmp file-path)]
        (io/make-parents file)
        (with-open [in  (io/input-stream url)
                    out (io/output-stream file)]
          (io/copy in out))
        (let [fileset' (boot/add-asset fileset tmp :meta {::downloaded-from url})]
          (boot/commit! fileset'))))))
