package ng.com.myzhihu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import ng.com.interf.BaseViewInterface;

public abstract class BaseActivity extends AppCompatActivity implements BaseViewInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        onBeforeSetContentLayout();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        initView();
        initData();
    }


    protected void onBeforeSetContentLayout() {
    }

    protected int getLayoutId() {
        return 0;
    }

    protected boolean hasActionBar() {
        return true;
    }

}
