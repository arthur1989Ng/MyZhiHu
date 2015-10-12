package ng.com.myzhihu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ng.com.utils.Constant;
import ng.com.utils.HttpUtils;
import ng.com.utils.LogUtils;

public class SplashActivity extends AppCompatActivity {


    private ImageView imgStart;

    private TextView tvAuthor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        imgStart = (ImageView) findViewById(R.id.img_splash);
        tvAuthor = (TextView) findViewById(R.id.tv_author);
        initStartImg();
    }


    private void initStartImg() {


        File dir = getFilesDir();

        final File imgFile = new File(dir, "start.jpg");


        if (imgFile.exists()) {
            imgStart.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
        } else {
            imgStart.setImageResource(R.mipmap.splash);

//            imgStart.setBackgroundResource(R.mipmap.splash);
        }


        //设置缩放动画

        /**float fromX 动画起始时 X坐标上的伸缩尺寸
         float toX 动画结束时 X坐标上的伸缩尺寸
         float fromY 动画起始时Y坐标上的伸缩尺寸
         float toY 动画结束时Y坐标上的伸缩尺寸
         int pivotXType 动画在X轴相对于物件位置类型
         float pivotXValue 动画相对于物件的X坐标的开始位置
         int pivotYType 动画在Y轴相对于物件位置类型
         float pivotYValue 动画相对于物件的Y坐标的开始位置
         */
        ScaleAnimation animation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        //动画执行完后是否停留在执行完的状态
        animation.setFillAfter(true);

        animation.setDuration(4000);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {


                HttpUtils.get(Constant.START, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {


                        try {
                            JSONObject jsonObject = new JSONObject(new String(bytes));


                            String author = jsonObject.getString("text");

                            String imgURL = jsonObject.getString("img");


                            LogUtils.log(author);
                            LogUtils.log(imgURL);
                            tvAuthor.setText(author);


                            HttpUtils.getImage(imgURL, new BinaryHttpResponseHandler() {
                                @Override
                                public void onSuccess(int i, Header[] headers, byte[] bytes) {

                                    saveImage(imgFile, bytes);


                                    imgStart.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));

                                    startActivity();
                                }

                                @Override
                                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                                    startActivity();

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                    }
                });
            }

            @Override
            public void onAnimationEnd(Animation animation) {


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        imgStart.setAnimation(animation);

    }


    private void startActivity() {
//        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//        startActivity(intent);
//        overridePendingTransition(android.R.anim.fade_in,
//                android.R.anim.fade_out);
//        finish();
    }


    public void saveImage(File file, byte[] bytes) {

        try {
            if (file.exists()) {
                file.delete();

            }
            FileOutputStream fos = new FileOutputStream(file);


            fos.write(bytes);

            fos.flush();

            fos.close();

        } catch (IOException e) {
            e.printStackTrace();


        }

    }

}
