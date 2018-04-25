package syte.femininapv3;

import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ViewPager viewPager,viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        NavigationView navView = (NavigationView) findViewById(R.id.navigation_view);
        if (navView != null){
            setupDrawerContent(navView);
        }

        viewPager = (ViewPager)findViewById(R.id.tab_viewpager);
        if (viewPager != null){
            setupViewPager(viewPager);
        }


        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#C62828"));
        tabLayout.setSelectedTabIndicatorHeight((int) (1 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#FFFF6565"), Color.parseColor("#C62828"));


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2 = (ViewPager)findViewById(R.id.tab_viewpager2);
        if (viewPager2 != null){
            setupViewPager2(viewPager2);
        }


    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new NovidadesListWebServiceFragment(), "Home");
        adapter.addFrag(new NovidadesListWebServiceFragment(), "Novidades");
        adapter.addFrag(new NovidadesListWebServiceFragment(), "Roupas");
        adapter.addFrag(new FloatingLabelsFragment(), "Bolsas");
        adapter.addFrag(new FABLayoutFragment(), "Acessórios");
        adapter.addFrag(new SnackBarFragment(), "Snackbar");

        viewPager.setAdapter(adapter);
    }

    //VIEW PAGER DE PROMOCOES E OFERTAS-------------
    private void setupViewPager2(ViewPager viewPager2){
        ViewPagerAdapter2 adapter2 = new ViewPagerAdapter2(getSupportFragmentManager());
        //adapter2.addFrag(new CapaFragment(), "Home");
        //adapter2.addFrag(new CapaFragment(), "Home");

        viewPager2.setAdapter(adapter2);
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);

                switch (menuItem.getItemId()) {
                    case R.id.drawer_coordinator:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.drawer_labels:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.drawer_fab:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.drawer_snackbar:
                        viewPager.setCurrentItem(3);
                        break;

                }

                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager){
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

        public void addFrag(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }
    }

    static class ViewPagerAdapter2 extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter2(FragmentManager manager){
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

        public void addFrag(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        switch (id){
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
