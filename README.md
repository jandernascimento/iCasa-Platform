M2MAppBuilder
=====

This project aims at providing a complete toolkit to build M2M applications.

Prerequisites
=====

- install Maven 3.x
- install jdk 6 (NOT java 7 !!!)

License
=====

This project relies to Apache v2 license.

Repository 
=====

Add the following maven repositories to your build.

```xml
<repository>
	<id>maven-m2mappbuilder-repository-release</id>
	<name>M2MAppBuilder - Release</name>
	<url>https://repository-icasa.forge.cloudbees.com/release/</url>
	<layout>default</layout>
</repository>
<repository>
	<id>maven-m2mappbuilder-repository-snapshot</id>
	<name>M2MAppBuilder - Snapshot</name>
	<url>https://repository-icasa.forge.cloudbees.com/snapshot/</url>
	<layout>default</layout>
</repository>
```

Released Version semantic
====

 major.minor.revision 

 * _major_ changed when there are modification or addition in the functionalities. 
 * _minor_ changed when minor features or critical fixes have been added.
 * _revision_ changed when minor bugs are fixed.

Contribute to this project
====

If you want to contribute to this project, you MUST follow the developper guidelines:
- Use Sun naming convention in your code.
- You should prefix private class member by an underscore (e.g. : _bundleContext).
- All project directory names must be lower case without dots (you can use - instead of underscores).
- All packages must start with fr.liglab.adele.m2mappbuilder
- All Maven artifact group id must be fr.liglab.adele.m2mappbuilder
- All maven artifact id must not contain fr.liglab.adele.m2mappbuilder and must be lower case (cannot use underscore, prefer dots)
- All maven project pom.xml file must inherent from parent pom (group id = fr.liglab.adele.m2mappbuilder and artifact id = parent)
