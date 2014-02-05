efactory
========

EFactory, clone of https://code.google.com/a/eclipselabs.org/p/efactory/.
More background e.g. on:

* first blog http://blog2.vorburger.ch/2013/06/efactory-ii-xobjects-ehutn.html (incl. the introductory screen cast http://www.youtube.com/watch?v=ToUWqIRHkHY)
* second blog http://blog2.vorburger.ch/2013/12/efactory-ii-eson-201311-update.html (and it's screencast at http://www.youtube.com/watch?v=fSq_zes4kv8)
* slides from the Modeling Symposium during EclipseCon Europe 2013 at https://docs.google.com/presentation/d/1pHLD9p1NSa6fuToCAZkk9GnCcriXco8RdmrwyoOxYds/


How to Install?
---------------

* Eclipse Help > Install New Software... Add... new Repository Location http://www.vorburger.ch/efactory-repository/


How to Develop?
---------------

Get on the [eclipse-emf-eson-dev Google Group](https://groups.google.com/forum/#!forum/eclipse-emf-eson-dev) and start yapping...

* get an Eclipse Dev IDE with PDE & modeling (Xtext) 
* import all projects in this repository into Eclipse 
* Window > Preferences > Plug-In Development > Target Platform: Switch to EFactory as active target definition (from com.googlecode.efactory.target/com.googlecode.efactory.target.target) 

The Target Platform is known to be a PITA, so if you have problems with above,
you might instead want to simply use your "Running Platform" as TP.. you'll most likely
just have to add the "EMF Compare Core SDK" (e.g. v2.2.0-20131217 as available in Luna; 
that includes the org.eclipse.emf.compare;bundle-version="3.0.0" we use, just for tests).

Alternatively, please help by manually fixing the *.target and sending a PR for it! ;-)

Note that com.googlecode.efactory.target.target intentionally uses non-latest versions of Eclipse Platform and Xtext,
because EFactory is used in a closed source product currently still stuck at those older versions, and we want the build
to catch any unintentional use of newer APIs.  (Thus, ideally, you'd want to use the same TP for build and IDE...)

How to increase the version numbers in pom.xml and MANIFEST.MF and feature.xml files:

   $ mvn -Dtycho.mode=maven org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=X.Y.0-SNAPSHOT


Continuous Integration
----------------------

see https://travis-ci.org/vorburger/efactory
[![Build Status](https://travis-ci.org/vorburger/efactory.png?branch=master)](https://travis-ci.org/vorburger/efactory)


[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/vorburger/efactory/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

