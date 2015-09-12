package kuyang.annonloc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kuyang.annonloc.R;
import kuyang.annonloc.Utility.LocationContent;

/**
 * Created by Yao-Jung on 2015/9/4.
 */
public class ContentItemAdapter extends RecyclerView.Adapter<ContentItemAdapter.TextViewHolder> {
    private LocationContent[] locationContents ;
    private  final LayoutInflater mLayoutInflater ;
    private  final  Context  mContext ;

    public  ContentItemAdapter ( Context context )  {
        mContext  =  context ;
        mLayoutInflater  =  LayoutInflater.from(context);
    }
    public  ContentItemAdapter ( Context context, LocationContent[] locationContent)  {
        mContext  =  context ;
        mLayoutInflater  =  LayoutInflater.from(context);
        this.locationContents = locationContent;

    }
    @Override
    public ContentItemAdapter.TextViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return  new TextViewHolder(mLayoutInflater.inflate(R.layout.content_cardview, viewGroup, false));
    }


    @Override
    public void onBindViewHolder(ContentItemAdapter.TextViewHolder viewHolder, int position) {
        viewHolder.mLocation.setText(locationContents[position].getLocationName());
        viewHolder.mComment1.setText(locationContents[position].getComments().get(1));
        viewHolder.mComment2.setText(locationContents[position].getComments().get(2));
        viewHolder.mComment3.setText(locationContents[position].getComments().get(3));
    }

    @Override
    public int getItemCount() {
        return  locationContents == null? 0 : locationContents.length;
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder{
        public TextView mLocation;
        public TextView mComment1, mComment2, mComment3;

        public TextViewHolder(View itemView) {
            super(itemView);
            mLocation = (TextView) itemView.findViewById(R.id.tvLocation);
            mComment1 = (TextView) itemView.findViewById(R.id.tvReplyone);
            mComment2 = (TextView) itemView.findViewById(R.id.tvReplytwo);
            mComment3 = (TextView) itemView.findViewById(R.id.tvReplythree);
        }
    }
}
