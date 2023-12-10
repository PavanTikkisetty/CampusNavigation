#!/bin/bash

# Compile the application
javac -cp .:lib/jxmapviewer2-2.0.jar:lib/commons-logging-1.3.0.jar navigation/*.java

# Run application
java -cp .:lib/jxmapviewer2-2.0.jar:lib/commons-logging-1.3.0.jar navigation.MainApplication