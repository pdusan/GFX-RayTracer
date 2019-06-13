JFLAGS = -g
JC = javac -d class/
JVM= java -cp class/

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	RayTracer.java \
	Scene.java \
	Camera.java \
	Light.java \
	Point.java \
	Vector.java \
	Ray.java \
	Shape.java \
	Sphere.java 

MAIN = RayTracer

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN) $(INPUT)

clean:
	del src\class_files\*.class
