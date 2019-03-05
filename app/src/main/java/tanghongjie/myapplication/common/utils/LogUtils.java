package tanghongjie.myapplication.common.utils;

import android.text.TextUtils;
import android.util.Log;

public class LogUtils {

    public static void d(String mContent) {
        if (TextUtils.isEmpty(mContent)){
            return;
        }
        Log.d("gte",mContent);
    }
    public static void e(String mContent) {
        if (TextUtils.isEmpty(mContent)){
            return;
        }
        Log.e("gte",mContent);
    }

}

