=============
boot-download
=============

|clojars|  |license|

`Boot`_ task to download a file from a given url.

-----
Tasks
-----

.. code-block:: clojure

   (download-file [u url URL str])

Downloads a single file from the given url and adds it to the fileset
as an asset.
                
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

TBD

~~~~
REPL
~~~~

~~~~~~~
Console
~~~~~~~

While the task is not designed to be usable from a console you still can invoke it:

.. code-block:: text

    boot download-file --url https://raw.githubusercontent.com/manenko/boot-download/master/README.rst


----
TODO
----

* Add an optional parameter :code:`--output-path` to the
  :code:`download-file` task. If the output path is :code:`nil` then
  the task will try to get the name of the file from the url and
  download it to the root.
                
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
