package main.master.c31.FacebookPageRequirement.pick3topicimages;

import android.content.Intent;
import android.os.Build;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import main.master.c31.R;

public class PickFBRequirementMainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
   public static ArrayList<String> listofrequirement ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_fbrequirement_main);


        Intent intent=getIntent();
        listofrequirement = intent.getStringArrayListExtra("list");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
      //  setupViewPager(viewPager);


        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setupViewtestPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

        if (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.statusbarcolor));
        }
    }

  /*  private void setupViewPager(ViewPager viewPager) {
        Bundle bundle = new Bundle();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for(int i =0 ; i<listofrequirement.size() ; i++){
            if(i==listofrequirement.size()-1)
            {
                bundle.putInt("edttext"+i, i);
                PickImagefbINFRAFragment pickImagefbINFRAFragment = new PickImagefbINFRAFragment();
                pickImagefbINFRAFragment.setArguments(bundle);
                int ifd = i+1;
                adapter.addFragment(pickImagefbINFRAFragment, "R "+ifd);
              //  viewPager.setTag(i);
            }
            else {
                bundle.putInt("edttext"+i, i);
                PickImagefbKIDSFragment pickImagefbKIDSFragment = new PickImagefbKIDSFragment();
                pickImagefbKIDSFragment.setArguments(bundle);
                int ifd = i+1;
                adapter.addFragment(pickImagefbKIDSFragment, "R "+ifd);
               // viewPager.setTag(i);
            }

        }
*//*        adapter.addFragment(new PickImagefbKIDSFragment(), "KIDS");
        adapter.addFragment(new PickImagefbSTAFFFragment(), "STAFF");
        adapter.addFragment(new PickImagefbINFRAFragment(), "INFRA");*//*
        viewPager.setAdapter(adapter);
    }*/


 /*   class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
    public void onBackPressed() {

        super.onBackPressed();
    }
*/


    private void setupViewtestPager(ViewPager viewPager) {

        for (int k = 0; k <listofrequirement.size() ; k++) {
            tabLayout.addTab(tabLayout.newTab().setText("" + k));
        }

        PlansPagerAdapter adapter = new PlansPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//Bonus Code : If your tab layout has more than 2 tabs then tab will scroll other wise they will take whole width of the screen
        if (tabLayout.getTabCount() == 2) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    public class PlansPagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;
        public PlansPagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            return PickImagefbKIDSFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }

}
