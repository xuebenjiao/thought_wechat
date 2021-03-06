/*
 * Copyright © Zhenjie Yan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yanzhenjie.permission.checker;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;

/**
 * Created by Zhenjie Yan on 2018/1/15.
 */
class CameraTest implements PermissionTest {

    private Context mContext;

    CameraTest(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean test() throws Throwable {
        Camera camera = null;
        try {
            int cameraCount = Camera.getNumberOfCameras();
            if (cameraCount <= 0) return true;

            camera = Camera.open(cameraCount - 1);
            Camera.Parameters parameters = camera.getParameters();
            if(!Build.MODEL.equalsIgnoreCase("I6310B")) {//解决优博讯设置申请权限时执行此代码出现异常，导致权限申请失败
                camera.setParameters(parameters);
            }

            camera.setPreviewCallback(PREVIEW_CALLBACK);
            if(!Build.MODEL.equalsIgnoreCase("I6310B")) {//解决优博讯设置申请权限时执行此代码出现异常，导致权限申请失败
                camera.startPreview();
            }
            return true;
        } catch (Throwable e) {
            PackageManager packageManager = mContext.getPackageManager();
            return !packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA);
        } finally {
            if (camera != null) {
                camera.stopPreview();
                camera.setPreviewCallback(null);
                camera.release();
            }
        }
    }

    private static final Camera.PreviewCallback PREVIEW_CALLBACK = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
        }
    };
}