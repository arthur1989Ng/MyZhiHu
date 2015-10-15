package ng.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ng.com.entity.MenuItem;
import ng.com.myzhihu.R;

/**
 * Created by niangang on 2015/10/14.
 */
public class NewsTypeAdapter extends BaseAdapter {

    private List<MenuItem> menuItems;

    private Context mContext;
    private LayoutInflater inflater;

    public NewsTypeAdapter(List<MenuItem> mItems, Context context) {

        this.menuItems = mItems;
        this.mContext = context;


        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.menu_item, null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(viewHolder);

        }

        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.tvTitle.setText(menuItems.get(position).getTitle());


        return convertView;
    }

    //在外面先定义，ViewHolder静态类
    static class ViewHolder {
        public TextView tvTitle;
    }

}
