package tanghongjie.myapplication.common.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import tanghongjie.myapplication.R;


/**
 * 创建时间:2018/02/02 11:59
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:自定义toast背景和位置
 * 修改时间:
 * 修改描述:
 */
public class Ts_backup {
	private static Toast toast;
	private static TextView text;

	private Ts_backup() {
		super();
	}

	/**
	 * 不用activity,直接toast
	 * @param info
	 */
	public  static void show(String info){
		try {
			if(toast==null||null==text){
				LayoutInflater inflater = UIUtils.getInflater();
				View layout =inflater.inflate(R.layout.layout_custom_toast, null);
				text = (TextView) layout.findViewById(R.id.text);

				toast = new Toast(UIUtils.getContext());
				toast.setView(layout);
				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			}
			toast.setDuration(Toast.LENGTH_SHORT);
			text.setText(info);
			toast.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  static void showToastLong(String info){
		try {
			if(toast==null||null==text){
				LayoutInflater inflater = UIUtils.getInflater();
				View layout =inflater.inflate(R.layout.layout_custom_toast, null);
				text = (TextView) layout.findViewById(R.id.text);

				toast = new Toast(UIUtils.getContext());
				toast.setView(layout);
				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			}
			toast.setDuration(Toast.LENGTH_LONG);
			text.setText(info);
			toast.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 清除toast
	 */
	public static void clearTs(){
		toast=null;
		text=null;
	}

}
