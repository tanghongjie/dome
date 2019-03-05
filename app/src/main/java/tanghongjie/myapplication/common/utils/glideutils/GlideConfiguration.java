package tanghongjie.myapplication.common.utils.glideutils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.target.ViewTarget;

import tanghongjie.myapplication.R;
import tanghongjie.myapplication.common.utils.FileUtils;
import tanghongjie.myapplication.constast.Constant;


/**
 * 创建时间: 2017/08/30 11:57
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:glide缓存相关的配置文件
 * 修改时间:
 * 修改描述:
 */
public class GlideConfiguration implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        ViewTarget.setTagId(R.id.glide_tag_id);
        //设置图片的显示格式
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
        //设置磁盘缓存目录
        FileUtils.createDir(Constant.GLIDE_CACHE_PATH);
        String downloadDirectoryPath= Constant.GLIDE_CACHE_PATH;
        //设置缓存大小
        int cacheSize100MegaBytes = 500000000;
        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath,cacheSize100MegaBytes));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        // register ModelLoaders here.
    }

}
