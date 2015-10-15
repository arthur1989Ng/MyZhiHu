package ng.com.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.zip.Inflater;

import ng.com.entity.StoriesEntity;
import ng.com.fragment.BaseFragment;
import ng.com.myzhihu.MainActivity;
import ng.com.myzhihu.R;

/**
 * Created by niangang on 2015/10/15.
 */
public class MainNewsAdapter extends BaseAdapter {


    private Context mContext;

    private List<StoriesEntity> storiesEntityList;
    private ImageLoader mImageloader;
    private DisplayImageOptions options;
    private boolean isLight;
    private LayoutInflater inflater;

    public MainNewsAdapter(Context context) {

        this.mContext = context;
        mImageloader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();

        inflater = LayoutInflater.from(mContext);
        isLight = ((MainActivity) context).isLight();
    }

    @Override
    public int getCount() {
        return storiesEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return storiesEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
//            convertView = inflater.inflate(R.layout.)


        }


        return convertView;
    }


    public static class ViewHolder {
        TextView tvTopic;
        TextView tvTitle;
        ImageView ivTitle;


    }
}
