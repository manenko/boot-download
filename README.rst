=============
boot-download
=============

|clojars|  |license|

`Boot`_ task to download a file from the given url.


-----
Tasks
-----

.. code-block:: clojure

   (download-file [u url         VAL str "The location of the remote file."
                   o output-file VAL str "The location used to save the file. Optional."])

Downloads a single file from the given url and adds it to the fileset
as an asset.

If the output file path is not set then the task will get the file
name from the url and store the file under that name in the fileset
root directory.

    .. caution::
       The task will fail if the :code:`output-path` is not specified AND the
       url has parameters (i.e. :code:`http://example.org/file?p=foo&q=bar`).


---------
Functions
---------

~~~~~~~~~~~~~~~~~~~~~~~~
get-all-downloaded-files
~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: clojure

   (get-all-downloaded-files [fileset])

Gets a seq of files (as :code:`TmpFile` objects) downloaded by the
:code:`download-file` task.


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
get-all-files-downloaded-from
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: clojure

   (get-all-files-downloaded-from [fileset url])

Gets a seq of files (as :code:`TmpFile` objects) downloaded from the
given url by the :code:`download-file` task.

It could happen that the :code:`fileset` has a few files downloaded
from the same url. That's why this function returns a seq instead of a
single object (or :code:`nil`). The sequence will be empty, if there
is no files downloaded from the given url.

You can use this function to get :code:`TmpFile` of the downloaded
file, when you don't know its path, i.e. you didn't provide
:code:`output-file` option.


~~~~~~~~~~~~~~~~
get-download-url
~~~~~~~~~~~~~~~~

.. code-block:: clojure

   (get-download-url [tmpfile])

Gets a url the given :code:`TmpFile` was downloaded from.


-----
Usage
-----

~~~~~~~~~~
build.boot
~~~~~~~~~~

.. code-block:: clojure

   (deftask download-electron
     []
     (let [root "https://github.com/electron/electron/releases/download"]
       (comp
        (download-file :url         (str root "/v1.7.4/electron-v1.7.4-linux-x64.zip")
                       :output-path "downloads/electron-v1.7.4-linux-x64.zip")
        (extract-from-zip :archive    "downloads/electron-v1.7.4-linux-x64.zip"
                          :output-dir "extracted/electron/v1.7.4/linux-x64")
        (target))))


~~~~~~~
Console
~~~~~~~

Download a file and save it in target directory:

.. code-block:: text

    boot download-file --url https://raw.githubusercontent.com/manenko/boot-download/master/README.rst --output-file downloads/doc/boot-download.rst target


-------
License
-------

Copyright © 2017 Oleksandr Manenko.

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.

.. _Boot: https://github.com/boot-clj/boot

.. |clojars| image:: https://img.shields.io/clojars/v/manenko/boot-download.svg
    :alt: Clojars
    :scale: 100%
    :target: https://clojars.org/manenko/boot-download

.. |license| image:: https://img.shields.io/badge/License-EPL%201.0-red.svg
    :alt: License: EPL-1.0
    :scale: 100%
    :target: https://opensource.org/licenses/EPL-1.0
