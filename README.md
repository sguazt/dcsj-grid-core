dcsj-grid-core
==============

A set of Java libraries aimed at providing core functionalities for the grid computing paradigm. 


Compilation
-----------

	$ ant clean all

Distribution
-------------

	$ ant dist

Examples
--------

* Compilation:

	$ javac -cp lib/dcs-jcommons.jar:lib/javacc.jar:dcs-jgrid-core.jar -d examples/build examples/src/java/it/unipmn/di/dcs/grid/core/examples/<java-file>


* Execution:
1. JDF Parser
	$ java -cp lib/dcs-jcommons.jar:lib/javacc.jar:dcs-jgrid-core.jar:examples/build  it.unipmn.di.dcs.grid.core.examples.JdfParser < examples/data/<jdf-file>

