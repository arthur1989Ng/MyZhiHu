package ng.com.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * Created by niangang on 2015/10/9.
 */
public class HttpUtils {


    private static AsyncHttpClient client = new AsyncHttpClient();


    public static void get(String url, ResponseHandlerInterface responseHandler) {


        client.get(Constant.BASEURL + url, responseHandler);
    }


    public static void getImage(String url, ResponseHandlerInterface responseHandler) {


        client.get(url, responseHandler);

    }

}
