package com.robertkiszelirk.placesiwere;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BlankFragment blankFragment;

    HandleJson jsonHandler;
    int selectedTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            selectedTab = savedInstanceState.getInt("currentTab");
        }
        setData(selectedTab);
    }

    @Override
    public void onResume() {

        setData(selectedTab);

        super.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    //THE PAGER ADAPTER
    class PagerAdapter extends FragmentStatePagerAdapter {

        ArrayList<String> tabTitles = jsonHandler.getCategories();
        Context context;

        PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return tabTitles.size();
        }

        @Override
        public Fragment getItem(int position) {
            //SET FRAGMENT
            blankFragment = new BlankFragment();
            blankFragment.setJsonObject(jsonHandler.getPlaceObject(position));
            return blankFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //GENERATE TITLE
            return tabTitles.get(position);
        }

        View getTabView(int position) {
            //SET TAB TEXT
            View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(tabTitles.get(position));
            return tab;
        }

    }

    protected void setData(int currentTab){

        //SET JSON FILE DATA
        jsonHandler = new HandleJson();
        jsonHandler.setJSON(MainActivity.this);

        //SET TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText(jsonHandler.getCityName());

        //GET VIEWPAGER AND SET PAGER ADAPTER TO IT
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(pagerAdapter);

        //SET VIEWPAGER TO TAB LAYOUT
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(currentTab).select();
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTab = tab.getPosition();
                super.onTabSelected(tab);
            }
        });

        //SET CUSTOM VIEW FOR TABS
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

    }

    //HANDLE ORIENTATION CHANGE
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt("currentTab",selectedTab);

        super.onSaveInstanceState(outState);
    }
}
