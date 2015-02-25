Pyleus v\ |version|
===================

Pyleus is a Python 2.6+ layer built on top of `Apache Storm`_ for building Storm topologies in idiomatic Python.

* Pyleus is available on PyPI: https://pypi.python.org/pypi/pyleus
* The source is hosted on GitHub: https://github.com/Yelp/pyleus

.. warning::

   Pyleus is **NOT** compatible with Python 3 (yet).

Quick Install
-------------

Install in a virtualenv:

.. code-block:: none

   $ virtualenv my_venv
   $ source my_venv/bin/activate
   $ pip install pyleus

Quick Start
-----------

First, you will need to download and extract Storm 0.9.2-incubating—the current release—from https://storm.apache.org/downloads.html.

Then create a config file ``~/.pyleus.conf`` so Pyleus can find the ``storm`` command:

.. code-block:: ini

   [storm]
   storm_cmd_path: /path/to/apache-storm-0.9.2-incubating/bin/storm

Build an example topology:

.. code-block:: none

   $ git clone https://github.com/Yelp/pyleus.git
   $ pyleus build pyleus/examples/exclamation_topology/pyleus_topology.yaml

And run it locally:

.. code-block:: none

   $ pyleus local exclamation_topology.jar

From another shell, you can run ``$ tail -F /tmp/exclamation_bolt.log`` to see the results in real-time.

Press ``C-C`` to exit.

You can submit it to a Storm cluster too:

.. code-block:: none

   $ pyleus submit -n NIMBUS_HOST exclamation_topology.jar

But since this example writes to ``/tmp``, you might not find it very interesting.

Documentation
-------------

.. toctree::
   :maxdepth: 2

   tutorial
   reliability
   grouping
   options
   parallelism
   tick
   external
   logging
   yaml
   install
   cli
   configuration
   contributing

API Documentation
-----------------

.. toctree::
   :maxdepth: 4

   api

Indices and tables
------------------

* :ref:`genindex`
* :ref:`modindex`
* :ref:`search`

.. _Apache Storm: https://storm.incubator.apache.org/
