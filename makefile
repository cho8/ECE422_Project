

JFLAGS = -g
subdir = darwin  #for macs
#subdir = linux  #for linux
library = libinsertionsort.dylib  #for macs
#library = libinsertionsort.so  #for linux

all: 
	echo “Remember to make variables depending on OS”
	javac $(JFLAGS) src/lib/*.java

clean :
	rm -f src/lib/*.class

native:
	javac $(JFLAGS) src/lib/NativeInsertionSort.java;
	cd src; javah lib.NativeInsertionSort; 
	cd src; gcc -I$$JAVA_HOME/include -I$$JAVA_HOME/include/$(subdir) -shared -fpic -o $(library) lib_insertionsort.c;
	
