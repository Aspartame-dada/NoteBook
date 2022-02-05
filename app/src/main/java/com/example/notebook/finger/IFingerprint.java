package com.example.notebook.finger;

import android.content.Context;
import android.os.Build;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public interface IFingerprint {
    boolean isHardwareDetected();

    void authenticate(FingerprintListener listener);

    class Builder {
        private Context mContext;

        public Builder(Context context) {
            this.mContext = context;
        }

        public IFingerprint build() {
            IFingerprint fingerprint = null;//创建指纹识别器
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                fingerprint = new IFingerprint() {
                    @Override
                    public boolean isHardwareDetected() {
                        return false;
                    }//进行硬件检测

                    @Override
                    public void authenticate(FingerprintListener listener) {
                        listener.onFailed();
                    }
                };
            } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {//根据api版本选择硬件检测方式
                fingerprint = new Fingerprint23(mContext);
            } else {
                fingerprint=new Fingerprint28(mContext);
            }
            FingerprintImpl impl = new FingerprintImpl(fingerprint);
            return (IFingerprint) Proxy.newProxyInstance(getClass().getClassLoader(),
                    new Class[]{IFingerprint.class}, new InvocationHandler() {
                IFingerprint print = impl;

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    return method.invoke(print, args);
                }
            });
        }
    }
}
