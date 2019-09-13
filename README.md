[![Build Status](https://travis-ci.org/Lars-m/flow2week1.svg?branch=master)](https://travis-ci.org/Lars-m/flow2week1)

To use "as is" create these two databases locally (you probably already have the latter)
- person
- startcode_test

This code uses a slightly changed version of EMF_Creator and config.properties that will make it possible to have more than one project on your droplet.

Add this line to */opt/tomcat/bin/setenv.sh* on your droplet (assuming you allready have added values for DEPLOYED, USER and PW

`export CONNECTION_STR_PERSON="jdbc:mysql://localhost:3306/person"`

Remember to restart Tomcat and to create the database person

See config.properties and EMF_Creator.java for how this works **I would really like some feedback on this solution as to whether is should be include in the start code**





### Preconditions
*In order to use this code, you should have a local developer setup + a "matching" droplet on Digital Ocean as described in the 3. semester guidelines* 
# Getting Started (original links from the start code)

This project contains two major documentation files: 
 - [First time users - getting started](README_proof_of_concept.md)
 - [How to use for future projects](README_how_to_use.md)
