package com.solide.ipcdemo;

interface Communication {
    void sendMsg(String msg);//这里注意传递参数都为基本类型，如果需要传递对象则对象的父类必须都是Parcelable
}
