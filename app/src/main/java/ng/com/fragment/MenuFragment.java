package ng.com.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ng.com.adapter.NewsTypeAdapter;
import ng.com.entity.MenuItem;
import ng.com.myzhihu.MainActivity;
import ng.com.myzhihu.R;
import ng.com.utils.Constant;
import ng.com.utils.HttpUtils;
import ng.com.utils.PreUtils;


public class MenuFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private TextView tvDownload, tvCollect, tvMain, tvLogin, tvBackUp;
    private ListView lvMenu;
    private MenuFragment menuFragment;

    private List<MenuItem> menuItems;


    private NewsTypeAdapter adapter;


    @Override

    protected View initFragView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);

        return view;
    }


    @Override
    public void initView() {

        tvDownload = (TextView) view.findViewById(R.id.tv_offline_download);
        tvCollect = (TextView) view.findViewById(R.id.tv_collect);
        tvMain = (TextView) view.findViewById(R.id.tv_main);
        tvLogin = (TextView) view.findViewById(R.id.tv_login);
        lvMenu = (ListView) view.findViewById(R.id.lv_item);


    }


    @Override
    public void initData() {
        tvDownload.setOnClickListener(this);
        tvCollect.setOnClickListener(this);
        tvMain.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        menuItems = new ArrayList<MenuItem>();

        tvLogin.setText("111");


        if (HttpUtils.isNetworkConnected(mActivity)) {
            HttpUtils.get(Constant.THEMES, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    String json = response.toString();
                    PreUtils.putStringToDefault(mActivity, Constant.THEMES, json);
                    parseJson(response);
                }
            });

        } else {


            String json = PreUtils.getStringFromDefault(mActivity, Constant.THEMES, "");


            try {
                JSONObject jsonObject = new JSONObject(json);

                parseJson(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewsFragment newsFragment = new NewsFragment();

                Bundle args = new Bundle();
                args.putString("id", menuItems.get(position).getId());
                args.putString("title", menuItems.get(position).getTitle());
                newsFragment.setArguments(args);
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left).replace(R.id.fl_content, newsFragment, "news").commit();


                ((MainActivity) mActivity).setCurId(menuItems.get(position).getId());
                ((MainActivity) mActivity).closeMenu();


            }
        });


    }

    @Override
    public void onClick(View v) {

    }

    private void parseJson(JSONObject response) {

        try {
            JSONArray itemsArray = response.getJSONArray("others");


            for (int i = 0; i < itemsArray.length(); i++) {


                MenuItem item = new MenuItem();

                JSONObject itemObject = itemsArray.getJSONObject(i);
                item.setId(itemObject.getString("id"));
                item.setTitle(itemObject.getString("name"));

                menuItems.add(item);
            }

            adapter = new NewsTypeAdapter(menuItems, mActivity);

            lvMenu.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void updateTheme() {

    }
}



