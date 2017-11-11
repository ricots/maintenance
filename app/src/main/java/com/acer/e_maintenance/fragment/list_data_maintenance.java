package com.acer.e_maintenance.fragment;

import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.acer.e_maintenance.R;

public class list_data_maintenance extends Fragment {
    BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_list_data_maintenance, container, false);
        bottomNavigationView = (BottomNavigationView) v.findViewById(R.id.bottom_navigation_data);
        setupNavigationView_data();
        return v;
    }

    private void setupNavigationView_data() {

        if (bottomNavigationView != null) {
            Menu menu = bottomNavigationView.getMenu();
            selectFragment(menu.getItem(0));
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            selectFragment(item);
                            return false;
                        }
                    });
        }
    }
    protected void selectFragment(MenuItem item) {

        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.action_hard:
                data_hardware fragment2 = new data_hardware();
                android.support.v4.app.FragmentTransaction fragmentTransaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.rootLayout_data,fragment2);
                fragmentTransaction2.commit();
                break;
            case R.id.action_soft:
                data_soft fragment = new data_soft();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.rootLayout_data,fragment);
                fragmentTransaction.commit();
                break;
        }
    }

    protected void pushFragment(android.app.Fragment fragment) {
        if (fragment == null)
            return;

        FragmentManager fragmentManager = getActivity().getFragmentManager();
        if (fragmentManager != null) {
            android.app.FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.rootLayout_data, fragment);
                ft.commit();
            }
        }
    }
}
