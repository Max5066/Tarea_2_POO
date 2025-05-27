# JavaFx Makefile
JFLAGS = -g -encoding ISO8859_1
JC = javac
JVM = java
RM = rm
FILE= configurationFile.txt
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
        T1Stage2.java 

MAIN = T1Stage2

default: classes

classes: $(CLASSES:.java=.class)

run:
	$(JVM) $(MAIN) $(FILE)

clean:
	$(RM) *.class
