package kuyang.annonloc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kuyang.annonloc.R;

/**
 * Created by Yao-Jung on 2015/9/4.
 */
public class ContentItemAdapter extends RecyclerView.Adapter<ContentItemAdapter.TextViewHolder> {
    private String[] mTitles = {"Leavy Library", "RTCC", "Tommy", "RTH"};
    private  final LayoutInflater mLayoutInflater ;
    private  final  Context  mContext ;

    public  ContentItemAdapter ( Context context )  {
        mContext  =  context ;
        mLayoutInflater  =  LayoutInflater.from(context);
    }
    @Override
    public ContentItemAdapter.TextViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return  new TextViewHolder(mLayoutInflater.inflate(R.layout.content_cardview, viewGroup, false));
    }


    @Override
    public void onBindViewHolder(ContentItemAdapter.TextViewHolder viewHolder, int position) {
        viewHolder.mLocation.setText(mTitles[position]);
    }

    @Override
    public int getItemCount() {
        return  mTitles == null? 0 : mTitles.length;
    }
    public static class TextViewHolder extends RecyclerView.ViewHolder{
        public TextView mLocation;

        public TextViewHolder(View itemView) {
            super(itemView);
            mLocation = (TextView) itemView.findViewById(R.id.tvLocation);
        }
    }
}
