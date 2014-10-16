Taxonomy Categorizer
====================

A Protege 4 plugin for efficient classification of taxonomies.
Currently only OWL class taxonomies are supported.

Protege plugin
--------------------------
1) binary distribution can be created using
    
      mvn clean package
      
2) Eclipse project for plugin development in OSGI runtime can be created using

      mvn eclipse:eclipse package
   
   In addition to the project, this generates the MANIFEST.MF in target/classes/META-INF. 
   To run the package in the Eclipse OSGI bundle, just copy the META-INF into the project 
   root. Also, put the 'plugin.xml' file into the root.
