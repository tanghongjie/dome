package tanghongjie.myapplication.common.utils.glideutils;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

import java.io.InputStream;

/**
 * 创建时间: 2017/08/30 11:57
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:
 * 修改时间:
 * 修改描述:
 */
public class ProgressModelLoader implements StreamModelLoader<String> {
    private ProgressResponseListener progressListener;

    public ProgressModelLoader( ProgressResponseListener progressListener) {
        this.progressListener = progressListener;
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(String model, int width, int height) {
        return new ProgressDataFetcher(model, progressListener);
    }

}
