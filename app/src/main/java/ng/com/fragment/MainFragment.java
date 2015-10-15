package ng.com.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ng.com.entity.Latest;
import ng.com.myzhihu.MainActivity;
import ng.com.myzhihu.R;
import ng.com.view.Kanner;

public class MainFragment extends BaseFragment {
    private View view;
    private ListView listView;
    private Kanner kanner;

    @Override
    protected View initFragView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_main, container, false);
        ((MainActivity) mActivity).setToolbarTitle("今日热文");
        listView = (ListView) view.findViewById(R.id.lv_news);
        View header = inflater.inflate(R.layout.kanner, listView, false);
        kanner = (Kanner) header.findViewById(R.id.kanner);

        listView.addHeaderView(header);
        return view;
    }


    @Override
    public void initData() {





        kanner.setOnItemClickListener(new Kanner.OnItemClickListener() {
            @Override
            public void click(View v, Latest.TopStoriesEntity entity) {

            }
        });

    }

    public void updateTheme() {

    }


    @Override
    public void initView() {

    }
}

