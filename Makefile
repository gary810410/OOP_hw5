All:	JavaC Jar
JavaC: src/simulation/*.java
	javac -d bin -sourcepath src src/simulation/MyCarSimulation.java
Jar:
	jar -cvfm simulation.jar manifest bin/simulation/*.class 
