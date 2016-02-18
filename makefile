JFLAGS = -g
subdir = darwin #for macs
#subdir = linux #for linux

all: 
	javac $(JFLAGS) src/lib/*.java

clean :
	rm -f src/lib/*.class

native:
	javac $(JFLAGS) src/lib/NativeInsertionSort.java;
	cd src; javah lib.NativeInsertionSort; 
	cd src; gcc -I$$JAVA_HOME/include -I$$JAVA_HOME/include/$(subdir) -shared -fpic -o libinsertionsort.so lib_insertionsort.c;
	
