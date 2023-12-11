#!/bin/bash

# Compile the application
javac -cp .:lib/jxmapviewer2-2.0.jar:lib/commons-logging-1.3.0.jar navigation/*.java

# Run Black-Box tests
java -cp .:lib/jxmapviewer2-2.0.jar:lib/commons-logging-1.3.0.jar navigation.MainApplication < navigation/black_box_tests.txt

# Run White-Box tests
java java -cp .:lib/jxmapviewer2-2.0.jar:lib/commons-logging-1.3.0.jar navigation.MainApplication < navigation/white_box_tests.txt
