(ns manenko.boot-download
  {:boot/export-tasks true}
  (:require [boot.core :as boot]
            [boot.util :refer [info]]
            [clojure.java.io :as io])
  (:import java.net.URI
           java.nio.file.Paths))

(defn ^:private add-file-to-meta
  [meta url file]
  (assoc-in meta [:metadata ::downloaded-files url] file))


(defn ^:private get-path
  [url]
  (.getPath (URI. url)))


(defn get-file-name
  [url]
  (let [path (Paths/get (get-path url)
                        (make-array String 0))]
    (str (.getFileName path))))


(boot/deftask download-file
  "Downloads a single file from a remote HTTP(S) server.

It puts a path to the downloaded file to a fileset's metadata
under the :manenko.boot-download/downloaded-files key. The value of this key is a
map of {url, path}, where both url and path are strings."
  [u url URL str "The location of the remote file."]
  (let [tmp (boot/cache-dir! ::tools)]
    (boot/with-pre-wrap fileset
      (let [file-name (get-file-name url)
            file      (io/file tmp file-name)]        
        (with-open [in  (io/input-stream url)
                    out (io/output-stream file)]
          (io/copy in out))
        (let [old-meta    (meta fileset)
              new-meta    (add-file-to-meta old-meta url (str file))
              new-fileset (with-meta fileset new-meta)]
          (boot/commit! new-fileset))))))

