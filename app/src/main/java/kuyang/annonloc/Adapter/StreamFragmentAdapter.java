package kuyang.annonloc.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import kuyang.annonloc.Fragment.StreamFragment;

/**
 * Created by Yao-Jung on 2015/9/4.
 */
public class StreamFragmentAdapter extends FragmentPagerAdapter{
    static final int NUM_ITEMS = 2;
    public StreamFragmentAdapter(android.support.v4.app.FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return StreamFragment.newInstance(position);
    }

    @Override
    public int getCount(){
        return NUM_ITEMS;
    }
}
