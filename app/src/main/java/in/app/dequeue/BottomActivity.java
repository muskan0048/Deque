package in.app.dequeue;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.view.MenuItem;

import in.app.dequeue.Fragments.AccountFragment;
import in.app.dequeue.Fragments.HomeFragment;
import in.app.dequeue.Fragments.ScanFragment;

public class BottomActivity extends AppCompatActivity {
    Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            fragment = null;

            switch (item.getItemId()) {


                case R.id.navigation_home:
                    fragment = new HomeFragment();
                case R.id.navigation_dashboard:
                    fragment = new ScanFragment();
                 //   mTextMessage.setText(R.string.title_dashboard);
                case R.id.navigation_notifications:
                   // mTextMessage.setText(R.string.title_notifications);
                    fragment = new AccountFragment();
            }

            return loadFragment(fragment);
        }
    };

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
        BottomNavigationView navView = findViewById(R.id.nav_view);
     //   mTextMessage = findViewById(R.id.message);
        loadFragment(new HomeFragment());
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}
