package tanghongjie.myapplication.common.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog.Builder;

import tanghongjie.myapplication.R;
import tanghongjie.myapplication.beas.activity.BaseActivity;
import tanghongjie.myapplication.beas.fragment.BaseFragment;
import tanghongjie.myapplication.constast.Constant;
import tanghongjie.myapplication.constast.PermissionConstant;


/**
 * 创建时间:2018/01/16 14:45
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:
 * 修改时间:
 * 修改描述:
 */
public class PermissionUtils {
    private static final String TAG="--PermissionUtils--";

    /**
     * 禁止权限时弹窗提示用户
     * @param context
     * @param permissionCode
     */
    public static void showPermissionDialog(Context context, int permissionCode) {
        String tips = UIUtils.getString(R.string.permission_fail_tips);

        //根据请求权限的code,返回对应的提示语
        if (permissionCode == PermissionConstant.LOCATION_STORAGE_REQUEST_CODE) {
            //禁止了定位
            tips = context.getString(R.string.forbid_location_tips);
        }else if(permissionCode == PermissionConstant.CAMERA_CODE){
            //禁用相机提示
            tips = context.getString(R.string.forbid_camera_tips);
        } else if(permissionCode == PermissionConstant.ALBUM_STORAGE_CODE){
            //禁用浏览相册存储权限
            tips = context.getString(R.string.forbid_sd_tips);
        }

        //显示禁用权限提示框
        Builder builder =  new Builder(context);
        builder.setTitle(context.getResources().getString(R.string.permission_no_title));
        builder.setCancelable(false);
        builder.setMessage(tips);
        builder.setPositiveButton(context.getResources().getString(R.string.permission_iknow), null);
        builder.show();
    }

    /**
     * 处理activity回调的权限请求
     * @param activity
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(BaseActivity activity, int requestCode, String[] permissions, int[] grantResults) {
        if (activity==null||activity.isFinishing()){
            return;
        }
        if (grantResults==null||grantResults.length==0||permissions==null||permissions.length==0){
            return;
        }
        if (grantResults.length!=permissions.length){
            return;
        }

        if (Constant.IS_DEBUG){
            LogUtils.e(TAG+"长度activity--grantResults:"+grantResults.length+"--permissions:"+permissions.length);
        }

        //首先判断,如果是获取SD卡权限,需要创建sd中的文件夹
        //权限的名称
        int permissionsCount = permissions.length;
        for (int i = 0; i < permissionsCount; i++) {
            if (PermissionConstant.STORAGE[0].equals(permissions[i])||PermissionConstant.STORAGE[1].equals(permissions[i])) {
                //如果是读写SD卡权限,就创建本地文件夹
                if (grantResults[i]== PermissionConstant.HAS_PERMISSION){
                    //获取了权限
                   FileUtils. initDir();
                    LogUtils.e(TAG+"获取了SD卡权限,创建文件夹");
                }
                break;
            }
        }

        //按照功能来区分
        switch (requestCode) {
            //打开地图:获取定位权限和存储
            case PermissionConstant.LOCATION_STORAGE_REQUEST_CODE:
                //1.SD 2.定位
                int count1 = grantResults.length;
                int index1 = -1;
                for (int i = 0; i < count1; i++) {
                    if (grantResults[i] != PermissionConstant.HAS_PERMISSION) {
                        //权限没有打开
                        index1 = i;
                        break;
                    }
                }
                if (index1 == -1) {
                    //所有的权限全部打开了
                    activity.onGetLocationAndStoragePermission();
                } else {
                    //显示禁用权限提示语
                    PermissionUtils.showPermissionDialog(activity, requestCode);
                }
                break;

            case PermissionConstant.CAMERA_CODE:
                //请求相机
                if (grantResults[0] == PermissionConstant.HAS_PERMISSION) {
                    activity.onCameraPermission();
                } else {
                    //显示禁用权限提示语
                    PermissionUtils.showPermissionDialog(activity,requestCode);
                }
                break;

            case PermissionConstant.ALBUM_STORAGE_CODE:
                //浏览相册,存储权限
                if (grantResults[0] == PermissionConstant.HAS_PERMISSION) {
                    activity.onStoragePermission();
                } else {
                    //显示禁用权限提示语
                    PermissionUtils.showPermissionDialog(activity,requestCode);
                }
                break;

            default: break;
        }
    }

    /**
     * fragment基类专用处理权限回调逻辑
     * @param baseFragment
     * @param mActivity
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResultForFragment(BaseFragment baseFragment,
                                                             BaseActivity mActivity, int requestCode,
                                                             String[] permissions, int[] grantResults) {
        if (mActivity==null||mActivity.isFinishing()||baseFragment==null){
            return;
        }
        if (grantResults==null||grantResults.length==0||permissions==null||permissions.length==0){
            return;
        }
        if (grantResults.length!=permissions.length){
            return;
        }

        if (Constant.IS_DEBUG){
            LogUtils.e(TAG+"长度fragment--grantResults:"+grantResults.length+"--permissions:"+permissions.length);
        }

        //首先判断,如果是获取SD卡权限,需要创建sd中的文件夹
        //权限的名称
        int permissionsCount = permissions.length;
        for (int i = 0; i < permissionsCount; i++) {
            if (PermissionConstant.STORAGE[0].equals(permissions[i])||PermissionConstant.STORAGE[1].equals(permissions[i])) {
                //如果是读写SD卡权限,就创建本地文件夹
                if (grantResults[i]==PermissionConstant.HAS_PERMISSION){
                    //获取了权限
                    FileUtils.createDir(Constant.SD_ROOT_PATH);
                    LogUtils.e(TAG+"获取了SD卡权限,创建文件夹");
                }
                break;
            }
        }

        //按照功能来区分:显示对应的提示
        switch (requestCode){
            //根据需求修改

            default:break;

        }

    }
}
