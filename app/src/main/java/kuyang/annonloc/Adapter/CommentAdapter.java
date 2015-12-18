package kuyang.annonloc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kuyang.annonloc.R;
import kuyang.annonloc.Utility.LocationComment;

/**
 * Created by Yao-Jung on 2015/12/15.
 */
public class CommentAdapter extends BaseAdapter{
    private ArrayList<LocationComment> commentList ;
    private LayoutInflater mInflater;
    public CommentAdapter(Context context, ArrayList<LocationComment> commentList){
        this.commentList = commentList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.chat_item_row, null);
            holder.tvId = (TextView)convertView.findViewById(R.id.tvId);
            holder.tvComment = (TextView)convertView.findViewById(R.id.tvComment);
            holder.ivLeft = (ImageView)convertView.findViewById(R.id.ivProfileLeft);
            holder.ivRight = (ImageView)convertView.findViewById(R.id.ivProfileRight);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder)convertView.getTag();
        }
        LocationComment thisCommnetObj = (LocationComment)getItem(position);
        holder.tvId.setText(thisCommnetObj.getCommentUserId());
        holder.tvComment.setText(thisCommnetObj.getCommentText());
        holder.ivLeft.setImageResource(R.drawable.icon_01);
        return convertView;
    }
    public static class ViewHolder {
        public TextView tvId;
        public TextView tvComment;
        public ImageView ivLeft;
        public ImageView ivRight;
    }
}
