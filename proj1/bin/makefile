
JFLAGS = -g

UNAME := $(shell uname)
ifeq ($(UNAME), Linux)
subdir = linux  #for linux
library = libinsertionsort.so  #for linux
endif

ifeq ($(UNAME), Darwin) #macs
subdir = darwin  #for macs
library = libinsertionsort.dylib
endif



all: native 
	@echo OS detected: ${UNAME}
	@echo Make sure to set LD_LIBRARY_PATH
native: classes
	javah lib.NativeInsertionSort; 
	gcc -std=c99 -I$$JAVA_HOME/include -I$$JAVA_HOME/include/$(subdir) -shared -fpic -o $(library) lib_insertionsort.c;
	
classes :
	javac $(JFLAGS) lib/*.java

clean :
	rm -f lib/*.class *.txt *.so *.dylib
	
