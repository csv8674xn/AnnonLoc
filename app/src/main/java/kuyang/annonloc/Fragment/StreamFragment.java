package kuyang.annonloc.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kuyang.annonloc.Activity.StreamFragmentActivity;
import kuyang.annonloc.Adapter.ContentItemAdapter;
import kuyang.annonloc.R;

/**
 * Created by Yao-Jung on 2015/9/4.
 */
public class StreamFragment extends Fragment {

    int mNum;
    private RecyclerView mRecyclerView ;
    private ContentItemAdapter contentItemAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static  StreamFragment newInstance(int num) {
        StreamFragment f = new StreamFragment();
        Bundle args = new Bundle();
        args.putInt("position", num);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.streamfragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvItems);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);

        swipeRefreshLayout.setEnabled(false);
        mRecyclerView.setLayoutManager ( new LinearLayoutManager((StreamFragmentActivity)getActivity()));
        contentItemAdapter = new ContentItemAdapter(getActivity());
        mRecyclerView.setAdapter(contentItemAdapter);

        return rootView;
    }

}