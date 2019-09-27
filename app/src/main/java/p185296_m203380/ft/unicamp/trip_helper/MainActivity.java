package p185296_m203380.ft.unicamp.trip_helper;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;

import adapter.MyFirstAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import fragments.DetailsFragment;
import fragments.FragmentController;
import viagens.Viagens;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentController {

    private FragmentManager fragmentManager;
    public static final String AUTHORS_KEY = "authors";
    public static final String MAIL_KEY = "mail";
    public static final String DETAILS_KEY = "details";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.alunos_recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE).getString(Constants.USERNAME, null) == null) {
            Intent intent = new Intent(this, UserRegisterActivity.class);
            startActivity(intent);
            this.finishAfterTransition();
        } else {
            setSupportActionBar(toolbar);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            navigationView.setNavigationItemSelectedListener(this);
            View headerView = navigationView.getHeaderView(0);
            TextView navUsername = headerView.findViewById(R.id.nav_header_username);
            navUsername.setText(getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE).getString(Constants.USERNAME, null));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyFirstAdapter mAdapter = new MyFirstAdapter(new ArrayList<>(Arrays.asList(Viagens.viagens)));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setMyOnItemClickListener(new MyFirstAdapter.MyOnItemClickListener() {
            @Override
            public void myOnItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                DetailsFragment biography = new DetailsFragment();
                biography.setArguments(bundle);
                replaceFragment(biography, MainActivity.DETAILS_KEY);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (fragmentManager == null) {
            fragmentManager = this.getSupportFragmentManager();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (fragmentManager == null) {
            fragmentManager = this.getSupportFragmentManager();
        }

        if (id == R.id.nav_home) {
            fragmentManager.popBackStack();
        } else if (id == R.id.nav_perfil) {
            Intent intent = new Intent(this, UserRegisterActivity.class);
            startActivity(intent);
            this.finishAfterTransition();
        } else if (id == R.id.nav_details) {
            Fragment detailsFragment = fragmentManager.findFragmentByTag(MainActivity.DETAILS_KEY);
            if (detailsFragment == null) {
                detailsFragment = new DetailsFragment();
            }
            replaceFragment(detailsFragment, MainActivity.DETAILS_KEY);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.authors_frame, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
