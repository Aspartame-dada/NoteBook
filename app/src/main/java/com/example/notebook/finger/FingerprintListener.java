package com.example.notebook.finger;

public interface FingerprintListener {//指纹识别监听接口
    void onSuccess();//成功

    void onFailed();//失败
}
