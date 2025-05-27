# JavaFx Makefile
JFLAGS = -g -encoding ISO8859_1
JC = javac
JVM = java
RM = rm
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        Component.java \
        Publisher.java \
        Subscriber.java \
        Follower.java \
        Topic.java \
        Broker.java \
        Recorder.java \
        T1Stage3.java 

MAIN = T1Stage3

default: classes

classes: $(CLASSES:.java=.class)

run:
	$(JVM) $(MAIN)

clean:
	$(RM) *.class
