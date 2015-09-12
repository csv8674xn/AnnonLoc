package kuyang.annonloc.Fragment;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import kuyang.annonloc.Adapter.ContentItemAdapter;
import kuyang.annonloc.R;
import kuyang.annonloc.Utility.ConnectionUtility;
import kuyang.annonloc.Utility.LocationContent;

/**
 * Created by Yao-Jung on 2015/9/4.
 */
public class StreamFragment extends Fragment {

    private RecyclerView mRecyclerView ;
    private ContentItemAdapter contentItemAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LocationContent contentArray[];

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

        setupAndSendLocation();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvItems);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }
    private void setupAndSendLocation(){
        new Thread(new Runnable() {
            public void run() {
                LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria, true);
                Location myLocation = locationManager.getLastKnownLocation(provider);
                LatLng currLocation = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
                String result = ConnectionUtility.getLocation(getActivity(), currLocation);
                setupAdapterWithResponse(result);
            }
        }).start();
    }

    private void setupAdapterWithResponse(String result){
        try{
            JSONArray contentJSONArray = new JSONArray(result);
            int contentCount = contentJSONArray.length();
            contentArray=new LocationContent[contentCount];
            for(int i = 0; i < contentJSONArray.length(); i++){
                contentArray[i] = new LocationContent(contentJSONArray.getJSONObject(i));
            }
        } catch (Exception e){
            e.getStackTrace();
        }
        contentItemAdapter = new ContentItemAdapter(getActivity(),contentArray);
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                mRecyclerView.setAdapter(contentItemAdapter);
            }
        });
    }
}