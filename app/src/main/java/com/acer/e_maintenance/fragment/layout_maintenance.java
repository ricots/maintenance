package com.acer.e_maintenance.fragment;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.fragment.TabSoftware;
import com.acer.e_maintenance.fragment.form_maintenance;

public class layout_maintenance extends Fragment {
    private static ViewPager mPager;
    private TabLayout mTabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_layout_maintenance, container, false);
        mPager = (ViewPager) v.findViewById(R.id.pager_main);
        mTabLayout = (TabLayout) v.findViewById(R.id.tab_layout_main);

        mPager.setAdapter(new tabs(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mPager);
        mPager.setCurrentItem(0);
        mPager.setOffscreenPageLimit(1);

        setHasOptionsMenu(true);
        return v;
    }

    class tabs extends FragmentPagerAdapter {

        public tabs(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new form_maintenance();
                case 1:
                    return new form_software();
                case 2:
                    return new tindak_lanjut();
                /*case 2:
                    return new TabSoftware();
                case 3:
                    return new TabSoftware();
                case 4:
                    return new TabSoftware();*/
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Hardware";
                case 1:
                    return "Software";

            case 2:
            return "Tindak Lanjut";
        }
            return "";
        }
    }
}
