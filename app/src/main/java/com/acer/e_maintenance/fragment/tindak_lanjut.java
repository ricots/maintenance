package com.acer.e_maintenance.fragment;

import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acer.e_maintenance.R;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class tindak_lanjut extends Fragment {
    BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_tindak_lanjut, container, false);
        bottomNavigationView = (BottomNavigationView) v.findViewById(R.id.bottom_navigation);
        setupNavigationView();
        return v;
    }

    private void setupNavigationView() {

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
                tindak_lanjut_hard fragment2 = new tindak_lanjut_hard();
                android.support.v4.app.FragmentTransaction fragmentTransaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.rootLayout,fragment2);
                fragmentTransaction2.commit();
                break;
            case R.id.action_soft:
                tindak_lanjut_soft fragment = new tindak_lanjut_soft();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.rootLayout,fragment);
                fragmentTransaction.commit();
                break;
        }
    }

    protected void pushFragment(android.app.Fragment fragment) {
        if (fragment == null)
            return;

        FragmentManager fragmentManager = getActivity().getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.rootLayout, fragment);
                ft.commit();
            }
        }
    }
}
