package kuyang.annonloc.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kuyang.annonloc.R;

/**
 * Created by Yao-Jung on 2015/9/4.
 */
public class StreamFragment extends Fragment {
    int mNum;

    public static  StreamFragment newInstance(int num) {
        StreamFragment f = new StreamFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("position", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.streamfragment, container, false);
        return rootView;
    }
}