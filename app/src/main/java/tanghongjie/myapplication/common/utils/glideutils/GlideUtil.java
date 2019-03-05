package tanghongjie.myapplication.common.utils.glideutils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import java.io.File;

import tanghongjie.myapplication.R;
import tanghongjie.myapplication.beas.activity.BaseActivity;
import tanghongjie.myapplication.common.utils.LogUtils;
import tanghongjie.myapplication.common.utils.UIUtils;
import tanghongjie.myapplication.constast.HttpUrls;

/**
 * 创建时间: 2017/08/30 11:57
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:glide二次封装工具类
 * 修改时间:
 * 修改描述:
 */
public class GlideUtil {
    private static final String HTTP_FLAG = "http";
    private static int dp150 = UIUtils.dp2px(150);
    private static int dp100 = UIUtils.dp2px(100);
    private static int dp80 = UIUtils.dp2px(80);
    private static int dp60 = UIUtils.dp2px(60);

    //渐现动画
    private static ViewPropertyAnimation.Animator animationObject =
            new ViewPropertyAnimation.Animator() {
        @Override
        public void animate(View view) {
            view.setAlpha(0f);
            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
            fadeAnim.setDuration(1500);
            fadeAnim.start();
        }
    };

    /**
     * 加载用户小图
     *
     * @param url
     * @param imageView
     */
    public static void loadHeadImage(String url, ImageView imageView) {
        if (imageView == null) {
            return;
        }

        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.ic_headimg_def);
        } else {
            String imageUrl = url;
            if (!url.contains(HTTP_FLAG)) {
                imageUrl = HttpUrls.IMAGE_HOST + url;
            }
            LogUtils.d(imageUrl);
            Glide.with(UIUtils.getContext())
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//展示小大的图片缓存
                    .centerCrop()
                    .priority(Priority.HIGH)
                    .override(dp80, dp80)
                    .skipMemoryCache(false)
                    .placeholder(R.drawable.ic_headimg_def)
                    .error(R.drawable.ic_headimg_def)
                    .into(imageView);
        }
    }


    /**
     * 加载用户本地小图
     *
     * @param imageView
     */
    public static void loadHeadImage(File file, ImageView imageView) {
        if (imageView == null || file == null || !file.exists()) {
            return;
        }
        LogUtils.d("用户本地小图:" + file.getAbsolutePath());

        Glide.with(UIUtils.getContext())
                .load(file)
                .centerCrop()
                .priority(Priority.HIGH)
                .override(dp80, dp80)
                .skipMemoryCache(false)
                .placeholder(R.drawable.ic_headimg_def)
                .error(R.drawable.ic_headimg_def)
                .into(imageView);
    }

    /**
     * 加载网络图
     *
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public static void loadImage(Context context, String url, ImageView imageView,
                                 int width, int height) {
        if (imageView == null) {
            return;
        }

        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.ic_img_normal);
        } else {
            String imageUrl = url;

            LogUtils.d(imageUrl);
            Glide.with(context)
                    .load(imageUrl)
                    //.dontAnimate()//不做动画
                    .centerCrop()
                    .thumbnail(0.5f)
                    .animate(animationObject)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//展示小大的图片缓存
                    //.crossFade()//淡入淡出效果
                    //.animate( android.R.anim.slide_in_left ) // 在图片从网络加载完并准备好之后将从左边滑入
                    .override(width, height)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_img_normal)
                    .error(R.drawable.ic_img_load_fail)
                    .into(imageView);
        }
    }

    /**
     * 加载网络大图
     *
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.ic_img_normal);
        } else {
            String imageUrl = url;
            if (!url.contains(HTTP_FLAG)) {
                imageUrl = HttpUrls.IMAGE_HOST + url;
            }
            LogUtils.e("加载网络大图:" + imageUrl);
            Glide.with(context)
                    .load(imageUrl)
                    .animate(animationObject)
                    .fitCenter()
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.1f)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_img_normal)
                    .error(R.drawable.ic_img_load_fail)
                    .into(imageView);
        }

    }

    /**
     * 加载用户图像大图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadHeadBigImage(Context context, String url, ImageView imageView) {
        if (context == null || imageView == null) {
            return;
        }

        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.ic_headimg_def);
        } else {
            String imageUrl = url;
            if (!url.contains(HTTP_FLAG)) {
                imageUrl = HttpUrls.IMAGE_HOST + url;
            }
            Glide.with(context)
                    .load(imageUrl)
                    .animate(animationObject)//渐变动画
                    .fitCenter()
                    .priority(Priority.HIGH)//优先加载大图
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//展示小大的图片缓存
                    .thumbnail(0.1f)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_headimg_def)
                    .error(R.drawable.ic_headimg_def)
                    .into(imageView);
        }

    }

    /**
     * 加载本地大图
     *
     * @param file
     * @param imageView
     * @param width
     * @param height
     * @param loadIcon  加载占位图
     */
    public static void loadLocalBigImage(File file, ImageView imageView, int width, int height,
                                         int loadIcon) {
        if (imageView == null) {
            return;
        }

        if (file == null || !file.exists()) {
            imageView.setImageResource(R.drawable.ic_img_normal);
        } else {
            LogUtils.d("glide本地图片:" + file.getAbsolutePath());
            Glide.with(UIUtils.getContext())
                    .load(file)
                    //.dontAnimate()//不做动画
                    .centerCrop()
                    .animate(animationObject)
                    //.crossFade()//淡入淡出效果
                    .override(width, height)
                    // .priority( Priority.HIGH )
                    .skipMemoryCache(true)
                    .placeholder(loadIcon)
                    .error(R.drawable.ic_img_load_fail)
                    .into(imageView);
        }
    }

    /***
     * 加载车辆图片
     * @param mContext
     * @param url
     * @param imageView
     */
    public static void loadCarIcon(Context mContext, String url, ImageView imageView) {
        if (imageView == null || mContext == null) {
            return;
        }

        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.ic_def_carimg);
        } else {
            String imageUrl = url;
            if (!url.contains(HTTP_FLAG)) {
                imageUrl = HttpUrls.IMAGE_HOST + url;
            }
            LogUtils.d("loadCarIcon--imageUrl:" + imageUrl);
            Glide.with(mContext)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .priority(Priority.HIGH)
                    .override(dp150, dp100)
                    .skipMemoryCache(false)
                    .placeholder(R.drawable.ic_def_carimg)
                    .error(R.drawable.ic_def_carimg)
                    .into(imageView);
        }

    }

    /***
     * 资料认证页面显示图片,本地图片
     * @param mContext
     * @param imageView
     * @param url
     * @param defResId
     * @param width
     * @param height
     */
    public static void loadIdentityAuthImage(Context mContext, ImageView imageView, String url,
                                             int defResId, int width, int height) {
        if (imageView == null || mContext == null) {
            return;
        }
        LogUtils.e("认证图片本地路径:" + url);
        imageView.setScaleType(ScaleType.FIT_CENTER);
        Glide.with(mContext)
                .load(new File(url))
                .centerCrop()
                .priority(Priority.HIGH)
                .override(width, height)
                .into(imageView);
    }

    /***
     * 加载网络缩略图片
     * @param mContext
     * @param url
     * @param imageView
     */
    public static void loadNetThumbnailImage(Context mContext, String url, ImageView imageView,
                                             int width, int height) {
        if (imageView == null || mContext == null) {
            return;
        }

        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.shape_placeholder);
        } else {
            String imageUrl = url;
            if (!url.contains(HTTP_FLAG)) {
                imageUrl = HttpUrls.IMAGE_HOST + url;
            }
            LogUtils.d(imageUrl);

            Glide.with(mContext)
                    .load(imageUrl)
                    //展示小大的图片缓存
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .priority(Priority.HIGH)
                    .override(width, height)
                    //缓存到内存中
                    .skipMemoryCache(false)
                    .placeholder(R.drawable.shape_placeholder)
                    .error(R.drawable.shape_placeholder)
                    .into(imageView);
        }

    }

    /***
     * 加载反馈的本地图片
     * @param mContext
     * @param url
     * @param imageView
     */
    public static void loadFeedbackLocalImage(Context mContext, String url, ImageView imageView, int
            width, int height) {
        if (imageView == null || mContext == null) {
            return;
        }

        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.shape_placeholder);
        } else {
            String imageUrl = url;
            LogUtils.d("imageUrl:" + imageUrl);
            Glide.with(mContext)
                    .load(new File(imageUrl))
                    //展示小大的图片缓存
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .priority(Priority.HIGH)
                    .override(width, height)
                    .skipMemoryCache(false)
                    .placeholder(R.drawable.shape_placeholder)
                    .error(R.drawable.shape_placeholder)
                    .into(imageView);
        }

    }

    /**
     * 加载广告本地
     *
     * @param url
     * @param imageView
     */
    public static void loadAdSliderLocalImage(Context context, String url,
                                              final ImageView imageView,
                                              int height, int width) {

        if (imageView == null || context == null) {
            return;
        }

        if (context instanceof BaseActivity && ((BaseActivity) context).isFinishing()) {
            return;
        }

        imageView.setScaleType(ScaleType.CENTER_CROP);

        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.shape_placeholder_transparent);
        } else {
            //LogUtils.e("GlideUtils--loadAdSliderLocalImage()--加载本地大图-url:" + imageUrl);
            Glide.with(context)
                    .load(new File(url))
                    .override(width, height)
                    .placeholder(R.drawable.shape_placeholder_transparent)
                    .error(R.drawable.shape_placeholder_transparent)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imageView);
        }
    }

    /**
     * 清除内存中的缓存 必须在UI线程中调用
     */
    public static void clearMemory() {
        try {
            if (!UIUtils.isRunMainThread()) {
                UIUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(UIUtils.getContext()).clearMemory();
                    }
                });
            } else {
                Glide.get(UIUtils.getContext()).clearMemory();
            }
        } catch (Exception e) {
            LogUtils.e(" clearMemory()--异常");
        }

    }

    public static void clearMemory(final Activity activity) {
        try {
            if (activity == null || activity.isFinishing()) {
                return;
            }
            if (!UIUtils.isRunMainThread()) {
                UIUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(activity).clearMemory();
                    }
                });
            } else {
                Glide.get(activity).clearMemory();
            }
        } catch (Exception e) {
            LogUtils.e(" clearMemory(VideoChatBaseActivity activity)--异常");
        }
    }

    public static void clearMemory(Context context) {
        try {
            if (context != null) {
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            LogUtils.e(" clearMemory(Context mContext)--异常");
        }
    }

    /**
     * 清除磁盘中的缓存 必须在后台线程中调用，建议同时clearMemory()
     */
    public static void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    public static void pauseRequest(Context context) {
        Glide.with(context).pauseRequests();
    }

    public static void resumeRequest(Context context) {
        Glide.with(context).resumeRequests();
    }

}
