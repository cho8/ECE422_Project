
JFLAGS = -g

UNAME := $(shell uname)
ifeq ($(UNAME), Linux)
subdir = linux  #for linux
library = libtinyencryption.so  #for linux
endif

ifeq ($(UNAME), Darwin) #macs
subdir = darwin  #for macs
library = libtinyencryption.dylib
endif



all: native 
	@echo OS detected: ${UNAME}
	@echo Make sure to ‘export LD_LIBRARY_PATH=LD_LIBRARY_PATH:.‘
native: classes
	javah lib.TinyEncryption; 
	gcc -std=c99 -I$$JAVA_HOME/include -I$$JAVA_HOME/include/$(subdir) -shared -fpic -o $(library) lib_tinyencryption.c;
	
classes :
	javac $(JFLAGS) lib/*.java

clean :
	rm -f lib/*.class *.txt *.so *.dylib
	
