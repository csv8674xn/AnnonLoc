package kuyang.annonloc.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

import kuyang.annonloc.Adapter.CommentAdapter;
import kuyang.annonloc.R;
import kuyang.annonloc.Utility.ConnectionUtility;
import kuyang.annonloc.Utility.LocationComment;

/**
 * Created by Yao-Jung on 2015/9/6.
 */
public class LocationConversationActivity extends ActionBarActivity {

    private static String TAG = "LocationConversationActivity";

    private final static String ACTIVITY_TITLE = "Browse";

    private final Context context = this;
    private ListView mDrawerList;
    private ListView conversation_list;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String location_ID ;
    private String location_Name;
    private String location_ImgURL;
    private CommentAdapter mAdapter;
    private ActionBar actionBar;
    private ArrayList<LocationComment> locationCommentList;
    private ImageView mNavigationIcon;
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
        TextView coverText = (TextView) findViewById(R.id.tvCoverName);
        coverText.setText(this.location_Name);
        setupActionbar();
        addDrawerItems();
        setupDrawer();
        setupConversation();
        setupEditText();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
        ArrayAdapter<String> drawerAdapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locationArray);
        mDrawerList.setAdapter(drawerAdapter);
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
//        View header = (View)getLayoutInflater().inflate(R.layout.top_coverphoto,null);
//        new ImageDownloadUtility((ImageView) header.findViewById(R.id.ivHeaderView)).execute(this.location_ImgURL);
//        TextView headerTitle = (TextView) header.findViewById(R.id.tvLocationName);
//        headerTitle.setText(this.location_Name);
//        conversation_list.addHeaderView(header);
//        new ImageDownloadUtility((ImageView) findViewById(R.id.ivLocationCover)).execute(this.location_ImgURL);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap coverBmp = ConnectionUtility.getBitmapFromURL(location_ImgURL);
                setupCover(coverBmp);
            }
        }).start();

        final String locationID = this.location_ID;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = ConnectionUtility.getLocationDetail(context,locationID);
                setupAdapterWithResponse(result);
            }
        }).start();
    }

    private void setupCover(Bitmap bmp){
        final int START_X = 0;
        final int START_Y = bmp.getHeight() / 3 ;
        final int WIDTH_PX = getWindowManager().getDefaultDisplay().getWidth();;
        final int HEIGHT_PX = 100;

        final Bitmap newBitmap = Bitmap.createBitmap(bmp, START_X, START_Y, bmp.getWidth(), HEIGHT_PX, null, false);
        final ImageView image = (ImageView)findViewById(R.id.ivLocationCover);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                image.setImageBitmap(newBitmap);
            }
        });
    }

    private void setupActionbar(){
        actionBar = getSupportActionBar();
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.actionbar_view, null);
        actionBar.setCustomView(mCustomView);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        this.mNavigationIcon =(ImageView) findViewById(R.id.ivNavigate);
        mNavigationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(Gravity.START);
                }
            }
        });
    }

    private void setupAdapterWithResponse(String result){
        ArrayList<String> chatArray = new ArrayList<>();
        try{
            JSONArray commentArray = new JSONArray(result);
            locationCommentList = new ArrayList<>();
            for(int i = 0; i < commentArray.length(); i++){
                locationCommentList.add(new LocationComment(commentArray.getJSONObject(i)));
                chatArray.add(locationCommentList.get(i).getCommentText());
            }
        } catch (Exception e){
            e.getStackTrace();
        }

        mAdapter = new CommentAdapter(this, locationCommentList);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                conversation_list.setAdapter(mAdapter);
            }
        });
    }
    private void setupEditText(){
        final ImageView button = (ImageView) findViewById(R.id.btSend);
        final EditText dtMessage = (EditText) findViewById(R.id.etMessage);
        final String location_ID = this.location_ID;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "TEST " + dtMessage.getText().toString());
                        boolean isPostSuccess = ConnectionUtility.postTalk(context, location_ID, dtMessage.getText().toString(), "fake_icon","fack_id");
                        if(isPostSuccess){
                            Log.d(TAG, "Success Post");
                        } else {
                            Log.d(TAG, "Failed Post");
                        }
                    }
                }).start();
            }
        });
    }
}
