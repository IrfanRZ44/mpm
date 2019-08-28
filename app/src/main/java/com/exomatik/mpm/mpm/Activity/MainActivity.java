package com.exomatik.mpm.mpm.Activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.mpm.mpm.Featured.UserPreference;
import com.exomatik.mpm.mpm.Fragment.*;
import com.exomatik.mpm.mpm.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static int request = 0;
    private DrawerLayout drawer;
    private CircleImageView imagePerson;
    private UserPreference mUserPreference;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);

        Toolbar localToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(localToolbar);

        mUserPreference = new UserPreference(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView localNavigationView = (NavigationView) findViewById(R.id.nav_view);

        Menu navMenu = localNavigationView.getMenu();
        localNavigationView.setNavigationItemSelectedListener(this);
        if (this.mUserPreference.getKEY_USER() == null) {
            navMenu.findItem(R.id.nav_setting).setVisible(true);
        }
        else {
            navMenu.findItem(R.id.nav_setting).setVisible(false);
        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, localToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (request == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                    , new Kegiatan()).commit();
        } else if (request == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                    , new TanggalHijriah()).commit();
        } else if (request == 2){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                    , new Belajar()).commit();
        }

        View localView = localNavigationView.getHeaderView(0);
        this.imagePerson = ((CircleImageView) localView.findViewById(R.id.image_person));

        this.imagePerson.setImageResource(R.drawable.mpm75);

        this.imagePerson.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                Toast.makeText(MainActivity.this, "Assalamualaikum Warrahmatullahi Akhi / Ukhti", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_kegiatan:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                        , new Kegiatan()).commit();
                break;
            case R.id.nav_setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                        , new SettingLogin()).commit();
                break;
            case R.id.nav_date:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                        , new TanggalHijriah()).commit();
                break;
            case R.id.nav_galeri:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                        , new fragmentKegiatan()).commit();
                break;
//            case R.id.nav_quran:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
//                        , new Quran()).commit();
//                break;
            case R.id.nav_belajar:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                        , new Belajar()).commit();
                break;
            case R.id.nav_news:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                        , new Berita()).commit();
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                        , new ProfileOrganisasi()).commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}