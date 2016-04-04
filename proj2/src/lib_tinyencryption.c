#include <stdio.h>
#include <stdlib.h>
#include <jni.h>
#include "lib_TinyEncryption.h"

/* For compiling on Macs:
    gcc -I$JAVA_HOME/include -I$JAVA_HOME/include/darwin
    */

void encrypt (long *v, long *k){
/* TEA encryption algorithm */
unsigned long y = v[0], z=v[1], sum = 0;
unsigned long delta = 0x9e3779b9, n=32;

	while (n-- > 0){
		sum += delta;
		y += (z<<4) + k[0] ^ z + sum ^ (z>>5) + k[1];
		z += (y<<4) + k[2] ^ y + sum ^ (y>>5) + k[3];
	}

	v[0] = y;
	v[1] = z;
}

void decrypt (long *v, long *k){
/* TEA decryption routine */
unsigned long n=32, sum, y=v[0], z=v[1];
unsigned long delta=0x9e3779b9l;

	sum = delta<<5;
	while (n-- > 0){
		z -= (y<<4) + k[2] ^ y + sum ^ (y>>5) + k[3];
		y -= (z<<4) + k[0] ^ z + sum ^ (z>>5) + k[1];
		sum -= delta;
	}
	v[0] = y;
	v[1] = z;
}

JNIEXPORT void JNICALL Java_lib_TinyEncryption_encrypt
  (JNIEnv* env, jclass jclss, jbyteArray jmessage, jlongArray jkey) {
  jsize len = (*env)->GetArrayLength(env, jmessage);
  jbyte* message = (*env)->GetByteArrayElements(env,jmessage, NULL);
  jlong* key = (*env)->GetLongArrayElements(env, jkey, NULL);
  
  for (int i =0; i < (len-8); i += 16) {
    encrypt((long *) &message[i], (long*) key);
  }
  (*env)->ReleaseByteArrayElements(env,jmessage, message, 0);
};

JNIEXPORT void JNICALL Java_lib_TinyEncryption_decrypt
  (JNIEnv* env, jclass jclss, jbyteArray jmessage, jlongArray jkey) {
  jsize len = (*env)->GetArrayLength(env, jmessage);
  jbyte* message = (*env)->GetByteArrayElements(env, jmessage, NULL);
  jlong* key = (*env)->GetLongArrayElements(env, jkey, NULL);
  
  for (int i = 0; i< (len-8); i += 16) {
    decrypt((long*) &message[i], (long*) key);
  }
  (*env)->ReleaseByteArrayElements(env, jmessage, message, 0);
};






