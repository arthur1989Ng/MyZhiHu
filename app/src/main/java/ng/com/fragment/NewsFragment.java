package ng.com.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ng.com.myzhihu.R;
import ng.com.utils.LogUtils;

public class NewsFragment extends BaseFragment {


    private View view;

    @Override
    protected View initFragView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        String id = getArguments().getString("id");
        String title = getArguments().getString("title");
        LogUtils.log("----id-----" + id);
        LogUtils.log("----title-----" + title);

        view = inflater.inflate(R.layout.fragment_news, container, false);

        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

//        String id = getArguments().getString("id");
//        String title = getArguments().getString("title");
//        LogUtils.log("----id-----" + id);
//        LogUtils.log("----title-----" + title);


    }
}
