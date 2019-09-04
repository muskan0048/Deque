package in.app.dequeue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class RegisterActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword, edtPhone, edtName;
    TextView  txtLogin;
    ImageView imgFb, imgGoogle, img;
    CircularProgressButton register;
    FirebaseAuth firebaseAuth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
    }

    void initViews(){

        dialog = new ProgressDialog(getApplicationContext());

        firebaseAuth = FirebaseAuth.getInstance();

        edtName = findViewById(R.id.editTextName);
        edtEmail = findViewById(R.id.editTextEmail);
        edtPassword = findViewById(R.id.editTextPassword);
        edtPhone = findViewById(R.id.editTextMobile);

        txtLogin = findViewById(R.id.textViewRegister);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        register = findViewById(R.id.cirRegisterButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser(edtEmail.getText().toString().trim(), edtPassword.getText().toString().trim());
            }
        });

        imgGoogle = findViewById(R.id.google);
        imgGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleRegister();
            }
        });

        imgFb = findViewById(R.id.fb);
        imgFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbRegister();
            }
        });

        img = findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    void createUser(String email, String password){
        dialog.show();
        firebaseAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Register", "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            user(user);
                            dialog.hide();

                            Intent intent = new Intent(RegisterActivity.this, BottomActivity.class);
                            startActivity(intent);
                            finish();

                         //   updateUI(user);
                        } else {
                            dialog.hide();
                            // If sign in fails, display a message to the user.
                            Log.w("Register", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }

                        // ...
                    }
                });
    }

    void googleRegister(){

    }

    void fbRegister(){

    }

    void user(FirebaseUser user){
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(edtName.getText().toString())
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Register", "User profile updated.");
                        }
                    }
                });

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Register", "Email sent.");
                        }
                    }
                });


    }
}
