#include <jni.h>
#include "com_example_tux0_Process.h"

#include <opencv2/opencv.hpp>
using namespace cv;


extern "C"{

JNIEXPORT void JNICALL
Java_com_example_tux0_Process_ConvertRGBtoGray(
        JNIEnv *env,
jclass instance,
        jlong matAddrInput,
jlong matAddrResult){


Mat &matInput = *(Mat *)matAddrInput;
Mat &matResult = *(Mat *)matAddrResult;

cvtColor(matInput, matResult, COLOR_RGBA2GRAY);
matResult = matInput;

}
}