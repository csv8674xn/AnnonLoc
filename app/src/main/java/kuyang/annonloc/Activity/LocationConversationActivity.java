package kuyang.annonloc.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

import kuyang.annonloc.R;
import kuyang.annonloc.Utility.ConnectionUtility;
import kuyang.annonloc.Utility.ImageDownloadUtility;
import kuyang.annonloc.Utility.LocationComment;

/**
 * Created by Yao-Jung on 2015/9/6.
 */
public class LocationConversationActivity extends ActionBarActivity {

    private final static String ACTIVITY_TITLE = "Browse";

    private final Context context = this;
    private ListView mDrawerList;
    private ListView conversation_list;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String location_ID ;
    private String location_Name;
    private String location_ImgURL;
    private ArrayAdapter<String> mAdapter;
    private ActionBar actionBar;
    private LocationComment[] locationCommentArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_consersation_activity);

        Intent intent = getIntent();
        this.location_ID = intent.getExtras().getString("Location_ID");
        this.location_Name = intent.getExtras().getString("Location_Name");
        this.location_ImgURL = intent.getExtras().getString("Img_Url");
        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        conversation_list = (ListView) findViewById(R.id.lvConversationList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBar = getSupportActionBar();
        addDrawerItems();
        setupDrawer();
        setupConversation();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addDrawerItems() {
        String[] locationArray = { "Current", "My Location 1", "My Location 2", "My Location 3", "My Location 4" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, locationArray);
        mDrawerList.setAdapter(mAdapter);
    }

    private void setupDrawer(){
        mDrawerToggle  = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.drawer_open,R.string.drawer_close){
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Swith Location");
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(ACTIVITY_TITLE);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    private void setupConversation(){
        View header = (View)getLayoutInflater().inflate(R.layout.top_coverphoto,null);
        new ImageDownloadUtility((ImageView) header.findViewById(R.id.ivHeaderView)).execute(this.location_ImgURL);
        TextView headerTitle = (TextView) header.findViewById(R.id.tvLocationName);
        headerTitle.setText(this.location_Name);
        conversation_list.addHeaderView(header);
        final String locationID = this.location_ID;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = ConnectionUtility.getLocationDetail(context,locationID);
                setupAdapterWithResponse(result);
            }
        }).start();
    }

    private void setupAdapterWithResponse(String result){
        ArrayList<String> chatArray = new ArrayList<>();
        try{
            JSONArray commentArray = new JSONArray(result);
            locationCommentArray = new LocationComment[commentArray.length()];
            for(int i = 0; i < commentArray.length(); i++){
                locationCommentArray[i] = new LocationComment(commentArray.getJSONObject(i));
                chatArray.add(locationCommentArray[i].getCommentText());
            }
        } catch (Exception e){
            e.getStackTrace();
        }

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, chatArray);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                conversation_list.setAdapter(mAdapter);
            }
        });
    }
}
