package kuyang.annonloc.Activity;

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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import kuyang.annonloc.R;

/**
 * Created by Yao-Jung on 2015/9/6.
 */
public class LocationConversationActivity extends ActionBarActivity {

    private final static String ACTIVITY_TITLE = "Browse";

    private ListView mDrawerList;
    private ListView conversation_list;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter<String> mAdapter;
    private ImageView mHeaderView;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_consersation_activity);

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
        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
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
        conversation_list.addHeaderView(header);
        String[] chatArray = { "hi", "me? ", "kidding?", "USC!!", "no shit" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, chatArray);
        conversation_list.setAdapter(mAdapter);
    }
}
