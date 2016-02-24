
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
	@echo make sure to set LD_LIBRARY_PATH
native: classes
	cd src; javah lib.NativeInsertionSort; 
	cd src; gcc -std=c99 -I$$JAVA_HOME/include -I$$JAVA_HOME/include/$(subdir) -shared -fpic -o $(library) lib_insertionsort.c;
	
classes :
	javac $(JFLAGS) src/lib/*.java

clean :
	rm -f src/lib/*.class src/*.txt src/*.so src/*.dylib
	
