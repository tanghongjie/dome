/*
 * Copyright © Yan Zhenjie
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
package tanghongjie.myapplication.constast;

import android.Manifest;
import android.content.pm.PackageManager;

/**
 * 创建时间: 2017/08/02 21:30
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:权限常量,为了适配android8.0权限:申请权限时必须按组申请,否则8.0可能会出问题
 *         参考http://blog.csdn.net/yanzhenjie1003/article/details/76719487
 * 修改时间:
 * 修改描述:
 */
public final class PermissionConstant {
    //有权限code
    public static final int  HAS_PERMISSION= PackageManager.PERMISSION_GRANTED;
    public static final String[] CALENDAR;
    public static final String[] CAMERA;
    public static final String[] CONTACTS;
    public static final String[] LOCATION;
    public static final String[] MICROPHONE;
    public static final String[] PHONE;
    public static final String[] SENSORS;
    public static final String[] SMS;
    public static final String[] STORAGE;

    static {
        CALENDAR = new String[]{
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR};

        CAMERA = new String[]{
                Manifest.permission.CAMERA};

        CONTACTS = new String[]{
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.GET_ACCOUNTS};

        LOCATION = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        MICROPHONE = new String[]{
                Manifest.permission.RECORD_AUDIO};

        PHONE = new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.WRITE_CALL_LOG,
                Manifest.permission.USE_SIP,
                Manifest.permission.PROCESS_OUTGOING_CALLS};

        SENSORS = new String[]{
                Manifest.permission.BODY_SENSORS};

        SMS = new String[]{
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_WAP_PUSH,
                Manifest.permission.RECEIVE_MMS};

        STORAGE = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    //后续获取权限时在此定义相关的请求code
    //定位和存储权限请求
    public static final int LOCATION_STORAGE_REQUEST_CODE=1;

    //请求相机
    public static final int CAMERA_CODE = 2;

    //浏览相册存储权限
    public static final int ALBUM_STORAGE_CODE = 3;

}
