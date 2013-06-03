All:	JavaC Jar
JavaC: src/simulation/*.java
	javac -d class -sourcepath src src/simulation/*.java
Jar:
	jar -cvfm simulation.jar manifest class/simulation/*.class 
