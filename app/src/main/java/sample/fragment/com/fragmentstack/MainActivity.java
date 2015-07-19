package sample.fragment.com.fragmentstack;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    DrawerLayout mDrawerLayout;
    Fragment mFragment;
    ListView mDrawerList;
    ActionBarDrawerToggle mActionbarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        String[] slider_items = getResources().getStringArray(R.array.nav_drawer_items);
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, slider_items));

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setupListItemSelected(position);
            }
        });

        mActionbarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.drawable.ic_drawer, R.string.app_name,R.string.app_name){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(backStackCount()==0) {
                    getSupportActionBar().setTitle(MainActivityFragment.TITLE);
                }
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Opened");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };

        mDrawerLayout.setDrawerListener(mActionbarDrawerToggle);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                setActionbarBackState();
                if (backStackCount() == 0) {
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                } else {
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                }

            }
        });

        setActionbarBackState();
        if(backStackCount()==0) {
            mFragment = new MainActivityFragment();
            if (mFragment != null) {
                FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.framelayout, mFragment);
//            fragmentTransaction.addToBackStack(MainActivityFragment.TAG);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Force checking the native fragment manager for a backstack rather than
        // the support lib fragment manager. This is really required since we
        // cannot depend on the
        handleBackButtonPress();
    }

    public void setActionbarBackState(){
        /**
         * Way to set the navigation button on the top right of the action bar.
         * */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (backStackCount() > 0) {
            // Defer code dependent on restoration of previous instance state.
            mDrawerLayout.post(new Runnable() {
                @Override
                public void run() {
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace);
                    // Sync the toggle state after onRestoreInstanceState has occurred.
                    mActionbarDrawerToggle.syncState();
                }
            });
        } else {
            // Defer code dependent on restoration of previous instance state.
            mDrawerLayout.post(new Runnable() {
                @Override
                public void run() {
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
                    // Sync the toggle state after onRestoreInstanceState has occurred.
                    mActionbarDrawerToggle.syncState();
                }
            });
        }

        /**
         * Way to get the title of each fragment that should be restored
         * when stepping back
         * */
        if(getSupportFragmentManager()!=null) {
            List<Fragment> allFrags = getSupportFragmentManager().getFragments();
            int fragIndex = backStackCount();
            if(fragIndex==0){
                getSupportActionBar().setTitle(MainActivityFragment.TITLE);
            }else if(fragIndex>0){
                Fragment item = allFrags.get(getSupportFragmentManager().getBackStackEntryCount());
                if(item!=null){
                    ((ActionbarTitleHelper)item).setActionbarTitleString();
                }
            }
        }

    }

    public int backStackCount(){
        List<Fragment> allFrags = getSupportFragmentManager().getFragments();
        int fragIndex = (allFrags==null)?(0):(getSupportFragmentManager().getBackStackEntryCount());
        return fragIndex;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                // Sync the toggle state after onRestoreInstanceState has occurred.
                mActionbarDrawerToggle.syncState();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mActionbarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_friends).setVisible(!drawerOpen);
        menu.findItem(R.id.action_messages).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    public void handleBackButtonPress(){
        if(!mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            if (!MainActivity.this.getSupportFragmentManager().popBackStackImmediate()) {
                super.onBackPressed();
            }
        }else{
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            // toggle nav drawer on selecting action bar app icon/title
            if (mActionbarDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
        }

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_friends:
                mFragment = new FriendsFragment();
                if(mFragment!=null){
                    FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.framelayout,mFragment);
                    fragmentTransaction.addToBackStack(FriendsFragment.TAG);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.commit();
                }
                return true;
            case R.id.action_messages:
                mFragment = new MessagesFragment();
                if(mFragment!=null){
                    FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.framelayout, mFragment);// as good as clearing the whole stack and adding
                    fragmentTransaction.addToBackStack(MessagesFragment.TAG); // would do the replace of the top item and not remove whats below it
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.commit();
                }
                return true;
            case android.R.id.home:
                handleBackButtonPress();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupListItemSelected(int itemSelection){
        switch (itemSelection){
            case 0:
                if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                }
                break;
            case 1:
                mFragment = new FindPeopleFragment();
                if(mFragment!=null){
                    FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.framelayout, mFragment);// as good as clearing the whole stack and adding
                    fragmentTransaction.addToBackStack(FindPeopleFragment.TAG); // would do the replace of the top item and not remove whats below it
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.commit();
                }
                break;
            case 2:
                mFragment = new PhotosFragment();
                if(mFragment!=null){
                    FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.framelayout, mFragment);// as good as clearing the whole stack and adding
                    fragmentTransaction.addToBackStack(PhotosFragment.TAG); // would do the replace of the top item and not remove whats below it
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.commit();
                }
                break;
            case 3:
                mFragment = new CommunitiesFragment();
                if(mFragment!=null){
                    FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.framelayout, mFragment);// as good as clearing the whole stack and adding
                    fragmentTransaction.addToBackStack(CommunitiesFragment.TAG); // would do the replace of the top item and not remove whats below it
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.commit();
                }
                break;
            case 4:
                mFragment = new PagesFragment();
                if(mFragment!=null){
                    FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.framelayout, mFragment);// as good as clearing the whole stack and adding
                    fragmentTransaction.addToBackStack(PagesFragment.TAG); // would do the replace of the top item and not remove whats below it
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.commit();
                }
                break;
            case 5:
                mFragment = new HotFragment();
                if(mFragment!=null){
                    FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.framelayout, mFragment);// as good as clearing the whole stack and adding
                    fragmentTransaction.addToBackStack(HotFragment.TAG); // would do the replace of the top item and not remove whats below it
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.commit();
                }
                break;
            default:
                break;
        }
    }


    /**
     * Five digit number
     * */
    public static int randomFiveDigitGenerator(){
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

    public static void doLog(Context context, Class dClass, String message){
        android.util.Log.d("TESTING92324",""+context.getPackageName()+"."+dClass.getSimpleName()+"> "+message);
    }
}
