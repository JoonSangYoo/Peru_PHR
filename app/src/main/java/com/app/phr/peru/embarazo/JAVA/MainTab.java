package com.app.phr.peru.embarazo.JAVA;

/**
 * Created by hansol on 2016-08-10.
 * fragment와 연결이 되어있는 main tab
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;


import com.app.phr.peru.embarazo.JAVA.BackPressCloseHandler;
import com.app.phr.peru.embarazo.R;

import java.util.ArrayList;
import java.util.List;

public class MainTab extends AppCompatActivity implements Communicator{

    private static Bundle arguments;
    private BackPressCloseHandler backPressCloseHandler;
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String Phr;
    private int[] tabIcons = {
            R.mipmap.icon_phr,
            R.mipmap.icon_edu,
            R.mipmap.icon_myinfo
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(PreferencePutter.PREF_FILE_NAME, Activity.MODE_PRIVATE);


        setContentView(R.layout.main_tab);
        backPressCloseHandler = new BackPressCloseHandler(this);
        //action bar 대신 toolbar를 사용

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar 에서 action bar의 support를 받아옴
        setSupportActionBar(toolbar);
        //타이틀 변경
        getSupportActionBar().setTitle(preferences.getString(PreferencePutter.PATIENT_NAME, "Peru PHR"));
        //새로고침 아이콘 표시
        //getSupportActionBar().setDisplayShowCustomEnabled(true);
        // 홈 아이콘 표시
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // setupViewPager(viewPager);

        //tab레이아웃을 사용함. main_tab.xml과 연결되어 있음
        View view1 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        view1.findViewById(R.id.icon);
        view1.findViewById(R.id.phr);

        View view2 = getLayoutInflater().inflate(R.layout.custom_tab2, null);
        view2.findViewById(R.id.edu);
        view2.findViewById(R.id.edut);

        View view3 = getLayoutInflater().inflate(R.layout.custom_tab3, null);
        view3.findViewById(R.id.mi);
        view3.findViewById(R.id.mit);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view3));
   //     tabLayout.addTab(tabLayout.newTab().setText("Registro personal de salud"));
 //       tabLayout.addTab(tabLayout.newTab().setText("Materiales de educación"));

        //tabLayout.addTab(tabLayout.newTab().setText("Mi información"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //tabLayout.setupWithViewPager(viewPager);
       // setupTabIcons();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()

        {
            @Override
            public void onTabSelected (TabLayout.Tab tab){
                if(tab.getPosition()==0) {

                    viewPager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected (TabLayout.Tab tab){


            }

            @Override
            public void onTabReselected (TabLayout.Tab tab){

            }



        });


    }

    public Fragment findFragmentByPosition(int position) {
        return getSupportFragmentManager().findFragmentByTag(
                "android:switcher:" + viewPager.getId() + ":"
                        + ((FragmentPagerAdapter) viewPager.getAdapter()).getItemId(position));
    }

    @Override
    public void respond(String data){
        //android.app.FragmentManager manager = getFragmentManager();

        FragmentMyInfo f2 =(FragmentMyInfo)(((PagerAdapter) viewPager.getAdapter()).getRegisteredFragment(2));
        f2.changeText(data);

    }


    public void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static void setArguments(Bundle arguments) {
            MainTab.arguments = arguments;
            }

    public static Bundle getArguments() {
            return arguments;
            }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

    //tab 아이콘 바꿀 수 있음
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }



    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;
        SparseArray<Fragment> registeredFragments = new SparseArray<>();
        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    FragmentPHR tab1 = new FragmentPHR();
                    return tab1;
                case 1:
                    FragmentEducationInfo tab2 = new FragmentEducationInfo();
                    return tab2;
                case 2:
                    FragmentMyInfo tab3 = new FragmentMyInfo();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }


        @Override
        public int getCount() {
            return mNumOfTabs;
        }


    }


    //tab에서 보여줄 탭 fragment 이름
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(NetworkUtil.getConnectivityStatusBoolean(this)){

        }
        adapter.addFrag(new FragmentPHR(), "PHR");
        //adapter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        adapter.addFrag(new FragmentEducationInfo(), "Edu-Info");
        adapter.addFrag(new FragmentMyInfo(), "My Info");
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {

            super(manager);

        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        //fragment 탭 이동마다 초기화 안될 시에 추가 할 것

        @Override
        public int getItemPosition(Object item) {

            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }


}
