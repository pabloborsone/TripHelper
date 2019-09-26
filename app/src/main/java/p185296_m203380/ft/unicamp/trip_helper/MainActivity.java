package p185296_m203380.ft.unicamp.trip_helper;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
import fragments.AuthorsFragment;
import fragments.BiographyFragment;
import fragments.FragmentController;
import fragments.MailFragment;
import fragments.AlunosFragment;
import viagens.Viagens;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentController {

    private FragmentManager fragmentManager;
    public static final String AUTHORS_KEY = "authors";
    public static final String MAIL_KEY = "mail";
    public static final String STUDENTS_KEY = "students";
    public static final String BIOGRAPHY_KEY = "biography";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        RecyclerView mRecyclerView = findViewById(R.id.alunos_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyFirstAdapter mAdapter = new MyFirstAdapter(new ArrayList<>(Arrays.asList(Viagens.viagens)));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setMyOnItemClickListener(new MyFirstAdapter.MyOnItemClickListener() {
            @Override
            public void myOnItemClick(String nome) {
                Toast.makeText(MainActivity.this, nome, Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.setMyOnItemLongClickListener(new MyFirstAdapter.MyOnItemLongClickListener() {
            @Override
            public void myOnItemLongClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                BiographyFragment biography = new BiographyFragment();
                biography.setArguments(bundle);
                replaceFragment(biography, MainActivity.BIOGRAPHY_KEY);
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

        if (id == R.id.action_contact) {
            Fragment mailFragment = fragmentManager.findFragmentByTag(MainActivity.MAIL_KEY);
            if (mailFragment == null) {
                mailFragment = new MailFragment();
            }
            replaceFragment(mailFragment, MainActivity.MAIL_KEY);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (fragmentManager == null) {
            fragmentManager = this.getSupportFragmentManager();
        }

        if (id == R.id.nav_authors) {
            Fragment authorFragment = fragmentManager.findFragmentByTag(MainActivity.AUTHORS_KEY);
            if (authorFragment == null) {
                authorFragment = new AuthorsFragment();
            }
            replaceFragment(authorFragment, MainActivity.AUTHORS_KEY);
        } else if (id == R.id.nav_students) {
            Fragment studentFragment = fragmentManager.findFragmentByTag(MainActivity.STUDENTS_KEY);
            if (studentFragment == null) {
                studentFragment = new AlunosFragment();
            }
            replaceFragment(studentFragment, MainActivity.STUDENTS_KEY);
        } else if (id == R.id.nav_biography) {
            Fragment biographyFragment = fragmentManager.findFragmentByTag(MainActivity.BIOGRAPHY_KEY);
            if (biographyFragment == null) {
                biographyFragment = new BiographyFragment();
            }
            replaceFragment(biographyFragment, MainActivity.BIOGRAPHY_KEY);
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
