package in.app.dequeue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseAuth = FirebaseAuth.getInstance();

    }
    @Override
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
           //     MainActivity.this.finish();
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user!=null){
                    Intent mainIntent = new Intent(SplashActivity.this, BottomActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
                else {
                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                    finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
