package tanghongjie.myapplication.common.utils;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.widget.Toast;

import tanghongjie.myapplication.beas.BaseApplication;


/**
 * 创建时间:2018/02/02 11:59
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:自定义toast背景和位置
 * 修改时间:
 * 修改描述:
 */
public class Ts_sys_backup {
	private static Toast toast = null ;

	/**
	 * 不用activity,直接toast
	 * @param info
	 */
	@SuppressLint("ShowToast")
	public  static void show(String info){
		if(toast == null){
			toast = Toast.makeText(BaseApplication.getInstance(), info, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		}else{
			toast.setText(info) ;
			toast.setDuration(Toast.LENGTH_SHORT);
		}
		toast.show() ;
	}

	/**
	 * 显示长时间toast
	 * @param info
	 */
	@SuppressLint("ShowToast")
	public  static void showToastLong(String info){
		if(toast == null){
			toast = Toast.makeText(BaseApplication.getInstance(), info, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		}else{
			toast.setText(info) ;
			toast.setDuration(Toast.LENGTH_LONG);
		}
		toast.show() ;
	}

	/**
	 * 清除toast
	 */
	public static void clearTs(){
		toast=null;
	}

}
