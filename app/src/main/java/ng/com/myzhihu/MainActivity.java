package ng.com.myzhihu;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import ng.com.fragment.MainFragment;
import ng.com.fragment.MenuFragment;


public class MainActivity extends BaseActivity {

    private FrameLayout flContent;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    //    private ActionBarDrawerToggle drawerToggle;
    private boolean isLight;
    private String curId;
    private long firstTime;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getLayoutId();


        loadLatest();

    }


    @Override
    protected void onBeforeSetContentLayout() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        isLight = sp.getBoolean("isLight", true);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public void loadLatest() {
        getSupportFragmentManager().beginTransaction().
                setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left).
                replace(R.id.fl_content, new MainFragment(), "latest").
                commit();
        curId = "latest";
    }

    @Override
    public void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(isLight ? R.color.light_toolbar : R.color.dark_toolbar));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiplayout);
        flContent = (FrameLayout) findViewById(R.id.fl_content);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public boolean isLight() {
        return isLight;
    }

    @Override
    public void initData() {


        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);
        setStatusBarColor(getResources().getColor(isLight ? R.color.light_toolbar : R.color.dark_toolbar));

        swipeRefreshLayout.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                replaceFragment();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    public void setCurId(String id) {
        curId = id;
    }

    public void replaceFragment() {

        if (curId.equals("latest")) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left).replace(R.id.fl_content, new MainFragment()
                    , "latest").commit();
        }

    }

    public void closeMenu() {
        drawerLayout.closeDrawers();
    }

    public void setSwipeRefreshEnable(boolean enable) {
        swipeRefreshLayout.setEnabled(enable);
    }

    public void setToolbarTitle(String text) {
        toolbar.setTitle(text);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.getItem(0).setTitle(sp.getBoolean("isLight", true) ? "夜间模式" : "日间模式");
        return true;
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            closeMenu();
        } else {


            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {

                Snackbar snackbar = Snackbar.make(flContent, "再按一次退出", Snackbar.LENGTH_SHORT);
                snackbar.getView().setBackgroundColor(getResources().getColor(isLight ? android.R.color.holo_blue_dark : android.R.color.black));
                snackbar.show();
                firstTime = secondTime;
            } else {

                AppManager.getInstance().finishActivity(this);
            }
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_mode) {
            isLight = !isLight;
            // TODO: 15-8-29 现在只有这个activity有夜间模式，打开日报详情还不是啊
            item.setTitle(isLight ? "夜间模式" : "日间模式");
            toolbar.setBackgroundColor(getResources().getColor(isLight ? R.color.light_toolbar : R.color.dark_toolbar));
            setStatusBarColor(getResources().getColor(isLight ? R.color.light_toolbar : R.color.dark_toolbar));
            if (curId.equals("latest")) {
                ((MainFragment) getSupportFragmentManager().findFragmentByTag("latest")).updateTheme();
            } else {
//                ((NewsFragment) getSupportFragmentManager().findFragmentByTag("news")).updateTheme();
            }
            ((MenuFragment) getSupportFragmentManager().findFragmentById(R.id.fr_menu)).updateTheme();
            sp.edit().putBoolean("isLight", isLight).apply();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setStatusBarColor(int statusBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // If both system bars are black, we can remove these from our layout,
            // removing or shrinking the SurfaceFlinger overlay required for our views.
            Window window = this.getWindow();
            if (statusBarColor == Color.BLACK && window.getNavigationBarColor() == Color.BLACK) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            window.setStatusBarColor(statusBarColor);
        }
    }
}
