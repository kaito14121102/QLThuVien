package com.example.minh.qlthuvien.screen;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.minh.qlthuvien.R;
import com.example.minh.qlthuvien.screen.fragment.bookaddnew.BookFragment;
import com.example.minh.qlthuvien.screen.fragment.guest.GuestFragment;
import com.example.minh.qlthuvien.screen.fragment.home.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager mManager = getSupportFragmentManager();
                FragmentTransaction transaction = mManager.beginTransaction();
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        addFragment(fragment, transaction, HomeFragment.TAG);
                        break;
                    case R.id.action_guest:
                        addFragment(fragment, transaction, GuestFragment.TAG);
                        break;
                    case R.id.action_book:
                        addFragment(fragment, transaction, BookFragment.TAG);
                        break;
                    default:
                        break;
                }
                transaction.commit();
                return true;
            }
        });
    }

    public void addFragment(Fragment fragment, FragmentTransaction transaction, String tag) {
        fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            if(tag.equals(HomeFragment.TAG)) {
                fragment = HomeFragment.newInstance();
            }else if(tag.equals(GuestFragment.TAG)){
                fragment = GuestFragment.newInstance();
            }else {
                fragment = BookFragment.newInstance();
            }
            transaction.add(R.id.frame_layout, fragment, tag);
        } else {
            transaction.replace(R.id.frame_layout, fragment);
        }
    }

    public void init() {
        mBottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
    }
}
