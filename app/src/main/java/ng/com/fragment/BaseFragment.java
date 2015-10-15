package ng.com.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ng.com.interf.BaseFragmentInterface;

/**
 * Created by niangang on 2015/10/10.
 */
public abstract class BaseFragment extends Fragment implements BaseFragmentInterface {
    protected Activity mActivity;
    protected LayoutInflater mInflater;

    private View view;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mActivity = getActivity();
        view = initFragView(inflater, container, savedInstanceState);


        initView();

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }

    protected abstract View initFragView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


}
