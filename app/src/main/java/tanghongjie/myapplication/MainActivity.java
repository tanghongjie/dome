package tanghongjie.myapplication;

import android.view.View;

import tanghongjie.myapplication.beas.activity.BaseSimpleActivity;

public class MainActivity extends BaseSimpleActivity {


    @Override
    public void initTitleView() {
setTitleText("协力出行啦过来的");
    }


    @Override
    public int getLayoutRes() {
        return R.layout.layout_activity_common_right_imageview;
    }
    @Override
    public int getSuccessLayoutResId() {
        return  R.layout.activity_main;
    }

    @Override
    public void initView(View successView) {
        setBackButtonVisible(false);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v, int id) {

    }
}
