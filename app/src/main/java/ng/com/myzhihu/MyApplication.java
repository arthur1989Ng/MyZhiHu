package ng.com.myzhihu;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by niangang on 2015/10/8.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        File cache = StorageUtils.getCacheDirectory(getApplicationContext());

        ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(getApplicationContext());
        ImageLoader.getInstance().init(config);


    }
}
