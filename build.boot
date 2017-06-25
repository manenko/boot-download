(set-env!
 :source-paths #{"src"}
 :dependencies '[[org.clojure/clojure "1.8.0"  :scope "provided"]
                 [adzerk/bootlaces    "0.1.13" :scope "test"]])

(require '[adzerk.bootlaces :refer :all])

(def +project+ 'manenko/boot-download)
(def +version+ "0.1.0-SNAPSHOT")

(bootlaces! +version+)

(task-options!
 pom {:project     +project+
      :version     +version+
      :description "Boot task for downloading remote files via HTTP(S)."
      :url         "https://github.com/manenko/boot-download/"
      :scm         {:url "https://github.com/manenko/boot-download/"}
      :license     {"EPL" "http://www.eclipse.org/legal/epl-v10.html"}})
