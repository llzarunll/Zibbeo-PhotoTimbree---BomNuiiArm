package com.zibbeo.phototrimbree;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zibbeo.phototrimbree.Account.ZPTAccountInfoView;
import com.zibbeo.phototrimbree.Account.ZPTAccountView;
import com.zibbeo.phototrimbree.Cart.ZPTCartListView;
import com.zibbeo.phototrimbree.CreatePostcard.Massage.ZPTMessageComposerView;
import com.zibbeo.phototrimbree.Credit.ZPTCreditStateView;
import com.zibbeo.phototrimbree.PostCard.ZPTPostcardListView;
import com.zibbeo.phototrimbree.Preferences.ZPTPreferencesView;

public class BaseNavigationDrawer extends AppCompatActivity {

    protected ListView mDrawerList;
    protected DrawerLayout mDrawerLayout;
    protected ArrayAdapter<String> mAdapter;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected AXSessionAuthentification mAuthen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_navigation_drawer);

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mAuthen = new AXSessionAuthentification(getApplicationContext());

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    //set up left menu
    private void addDrawerItems() {
        Resources tRes = getResources();
        String[] tMenuList = tRes.getStringArray(R.array.menuList);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tMenuList);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(ZPTHomeView.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
                selectView(position);
                Log.i("position", "" + position);
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });
    }

    //set up left menu icon
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
//                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    //select fragment from menu
    private void selectView(int position) {


        switch (position) {
            case 0:
//                Intent intent0 = new Intent(this, ZPTHomeView.class);
                Intent intent0 = new Intent(this, ZPTHomeView.class);
                startActivity(intent0);
                break;
            case 1:
                //check status
                if (mAuthen.getStatus().equals(String.valueOf(AXSessionAuthentification.
                        AXSessionAuthentificationStatut.AXSessionAuthentificationStatutConnected))) {
                    Intent intent1 = new Intent(this, ZPTAccountInfoView.class);
                    startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(this, ZPTAccountView.class);
                    startActivity(intent1);
                }
                break;
            case 3:
                Intent intent3 = new Intent(this, ZPTCartListView.class);
                startActivity(intent3);
                break;
            case 4:
                Intent intent4 = new Intent(this, ZPTCreditStateView.class);
                startActivity(intent4);
                break;
            case 5:
                Intent intent5 = new Intent(this, ZPTPreferencesView.class);
                startActivity(intent5);
                break;
            case 2:
                Intent intent02 = new Intent(this, ZPTPostcardListView.class);
                startActivity(intent02);
            default:
                break;
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
