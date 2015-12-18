package kuyang.annonloc.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import kuyang.annonloc.Adapter.StreamFragmentAdapter;
import kuyang.annonloc.Fragment.StreamFragment;
import kuyang.annonloc.Listener.TabListener;
import kuyang.annonloc.R;

/**
 * Created by Yao-Jung on 2015/9/3.
 */
public class StreamFragmentActivity extends ActionBarActivity {

    private static final String ACTIVITY_TITLE = "Stream List";

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter<String> mAdapter;
    private EditText mEditText;
    private TextView mFooterView;
    private ImageView mNaviagtionIcon;
    private ActionBar actionBar;
    private android.support.v4.view.ViewPager mViewPager;
    private StreamFragmentAdapter streamFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stream_activity_main);
        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mFooterView = (TextView) findViewById(R.id.tvFooterView);
        mFooterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StreamFragmentActivity.this, MapBrowseActivity.class);
                startActivity (intent);
                overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
            }
        });
        streamFragmentAdapter = new StreamFragmentAdapter(getSupportFragmentManager());

        setupEditText();
        setupDrawer();
        setupViewPager();
        setupActionbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        addDrawerItems();
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

    private void setupEditText(){
        mEditText = (EditText) findViewById(R.id.etInputText);
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i("Keyboard", "focus changed");
                if (!hasFocus) {
                    Log.i("Keyboard", "does not have focus");
                    hideKeyboard(v);
                }
            }
        });
    }
    private void setupViewPager(){
        mViewPager  = (ViewPager) findViewById(R.id.main_fragment);

        mViewPager.setAdapter(streamFragmentAdapter);
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the corresponding tab.
                        actionBar.setSelectedNavigationItem(position);
                    }
                });
    }
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void setupActionbar(){
        actionBar = getSupportActionBar();
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.actionbar_view, null);
        actionBar.setCustomView(mCustomView);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        ActionBar.Tab tab_NEAR = actionBar.newTab()
                .setText(R.string.NEAR)
                .setTabListener(
                        new TabListener<StreamFragment>(StreamFragmentActivity.this, "NEAR", StreamFragment.class) {
                            @Override
                            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                                mViewPager.setCurrentItem(tab.getPosition());
                            }
                        }
                );
        ActionBar.Tab tab_PIN = actionBar.newTab()
                .setText(R.string.PIN)
                .setTabListener(
                        new TabListener<StreamFragment>(StreamFragmentActivity.this, "PIN", StreamFragment.class) {
                            @Override
                            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                                mViewPager.setCurrentItem(tab.getPosition());
                            }
                        }
                );
        actionBar.addTab(tab_NEAR);
        actionBar.addTab(tab_PIN);
        this.mNaviagtionIcon =(ImageView) findViewById(R.id.ivNavigate);
        mNaviagtionIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDrawerLayout.isDrawerOpen(Gravity.START)){
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(Gravity.START);
                }
            }
        });
    }
}