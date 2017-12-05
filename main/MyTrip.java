package ie.wit.explorewaterford.main;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;


import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import ie.wit.explorewaterford.R;
import ie.wit.explorewaterford.fragments.InfoFragment;
import ie.wit.explorewaterford.fragments.MainFragment;
import ie.wit.explorewaterford.fragments.MapsFragment;
import ie.wit.explorewaterford.fragments.VideoFragment;

import static ie.wit.explorewaterford.fragments.MainFragment.mainFragment;
import static ie.wit.explorewaterford.fragments.VideoFragment.youTubePlayerFragment;


public class MyTrip extends Activity {

    public static String showOnMapString = "";
    String fragmentTracker = "";
    public static BottomBar bottomBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent upIntent = NavUtils.getParentActivityIntent(this);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        MainFragment fragment = MainFragment.newInstance();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();


        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {



                Fragment fragment;
                FragmentTransaction ft = getFragmentManager().beginTransaction();

                //without this onDestroy method the video continues to exist after fragment changes from VideoFragment, meaning audio can be heard
                if(youTubePlayerFragment != null) {
                    youTubePlayerFragment.onDestroy();
                }

                if (tabId == R.id.tab_home) {
                    fragment = MainFragment.newInstance();
                    ft.replace(R.id.fragment_container, fragment);
                    ft.addToBackStack(null);
                    ft.commit();

                } else if (tabId == R.id.tab_maps) {
                    fragment = MapsFragment.newInstance();
                    ft.replace(R.id.fragment_container, fragment);
                    ft.addToBackStack(null);
                    ft.commit();

                } else if (tabId == R.id.tab_video) {
                    fragment = VideoFragment.newInstance();
                    ft.replace(R.id.fragment_container, fragment);
                    ft.addToBackStack(null);
                    ft.commit();

                } else if (tabId == R.id.tab_info) {
                    fragment = InfoFragment.newInstance();
                    ft.replace(R.id.fragment_container, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }

        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                Fragment fragment;
                FragmentTransaction ft = getFragmentManager().beginTransaction();

                if (tabId == R.id.tab_home) {
                    fragment = MainFragment.newInstance();
                    ft.replace(R.id.fragment_container, fragment);
                    ft.addToBackStack(null);
                    ft.commit();

                } else if (tabId == R.id.tab_maps) {
                    fragment = MapsFragment.newInstance();
                    ft.replace(R.id.fragment_container, fragment);
                    ft.addToBackStack(null);
                    ft.commit();

                } else if (tabId == R.id.tab_video) {
                    fragment = VideoFragment.newInstance();
                    ft.replace(R.id.fragment_container, fragment);
                    ft.addToBackStack(null);
                    ft.commit();

                } else if (tabId == R.id.tab_info) {
                    fragment = InfoFragment.newInstance();
                    ft.replace(R.id.fragment_container, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }

            }
        });

    }



    public static void goToMap(){

    }

}
