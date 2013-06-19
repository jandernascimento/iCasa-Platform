iCasa Platform
=====

This project aims at providing a complete toolkit to build M2M applications.

License
=====

This project relies on Apache v2 license (<http://www.apache.org/licenses/LICENSE-2.0.html>).

Contributors
=====

This project has been created by the LIG laboratory Adele team of Grenoble University.
The main contributors are 
- _Adele team_
- _Orange Labs_

Source Organization
====

- _appstore_: Contains the iCasa store sources.
- _dependencies_: Contains packages of used dependencies such as ROSE and Cilia.
- _distribution_: Contains distributions.
- _doc_: Contains documentation about development environment setup.
- _parent_: Contain the global build configuration including licensing information.
- _platform_: Contains technical services and device drivers used by the applications and distributions.
- _portal_: Contains the iCasa web portal sources.
- _tests_: Contains integration tests.

How to use it
=====

Use a distribution
----

1. Install jdk 6 (NOT java 7 !!!)
2. Unzip one of the distribution
3. Execute PRODUCT file (or PRODUCT.bat file on Windows)

Build a new distribution
----

Install all build prerequisites.
Then add the following maven repositories to your project pom.xml file.
```xml
<repository>
	<id>maven-icasa-repository-release</id>
	<name>icasa - Release</name>
	<url>https://repository-icasa.forge.cloudbees.com/release/</url>
	<layout>default</layout>
</repository>
<repository>
	<id>maven-icasa-repository-snapshot</id>
	<name>icasa - Snapshot</name>
	<url>https://repository-icasa.forge.cloudbees.com/snapshot/</url>
	<layout>default</layout>
</repository>
```
TODO

Build
=====

Prerequisites
-----

- install Maven 3.x
- install jdk 6 (NOT java 7 !!!)

Instructions
----

Use the following command to compile the project
> mvn clean install

Continuous Integration
----

The project is built every week on the following continuous integration server :
<https://icasa.ci.cloudbees.com/>

Maven Repositories
----

```xml
<repository>
	<id>maven-icasa-repository-release</id>
	<name>icasa - Release</name>
	<url>https://repository-icasa.forge.cloudbees.com/release/</url>
	<layout>default</layout>
</repository>
<repository>
	<id>maven-icasa-repository-snapshot</id>
	<name>icasa - Snapshot</name>
	<url>https://repository-icasa.forge.cloudbees.com/snapshot/</url>
	<layout>default</layout>
</repository>
```

Contribute to this project
====

Released Version semantic
----

 major.minor.revision 

 * _major_ changed when there are modification or addition in the functionalities. 
 * _minor_ changed when minor features or critical fixes have been added.
 * _revision_ changed when minor bugs are fixed.

Developer Guidelines
----
 
If you want to contribute to this project, you MUST follow the developper guidelines:
- Use Sun naming convention in your code.
- You should prefix private class member by an underscore (e.g. : _bundleContext).
- All project directory names must be lower case without dots (you can use - instead of underscores).
- All packages must start with fr.liglab.adele.icasa
- All Maven artifact group id must be fr.liglab.adele.icasa
- All maven artifact id must not contain fr.liglab.adele.icasa and must be lower case (cannot use underscore, prefer dots)
- All maven project pom.xml file must inherent from parent pom (group id = fr.liglab.adele.icasa and artifact id = platform-parent)
