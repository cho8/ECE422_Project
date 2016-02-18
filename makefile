JFLAGS = -g
JC = javac


all: $(classes)
	$(JC) $(JFLAGS) src/lib/*.java

clean :
	rm -f src/lib/*.class
