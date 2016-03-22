#include <stdio.h>
#include <stdlib.h>
#include <jni.h>
#include "lib_NativeInsertionSort.h"

/* For compiling on Macs:
    gcc -I$JAVA_HOME/include -I$JAVA_HOME/include/darwin
    */

JNIEXPORT jint JNICALL Java_lib_NativeInsertionSort_sort
(JNIEnv *env, jclass jclss, jintArray buf) {

	int elem, j;
	jboolean *is_copy = 0;
	jint *myCopy;
	jsize len  = (*env)->GetArrayLength(env, buf);
	jint *arry = (*env)->GetIntArrayElements(env, buf, is_copy);
	jint mem = 6;
	if (arry == NULL){
		printf("Cannot obtain arry from JVM\n");
		exit(0);
	}

	for (int i=0; i<len; i++) {
		j=i;
		mem +=3;
		while (j>0 && arry[j-1] > arry[j]) {
			elem=arry[j];
			arry[j] = arry[j-1];
			arry[j-1]=elem;
			j -= 1;
			mem += 10;
		}
	}
  (*env)->ReleaseIntArrayElements(env, buf, arry, 0);
	return mem;
}
