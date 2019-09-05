package in.app.dequeue;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;


public class PrePaymentActivity extends AppCompatActivity  {

    EditText phone, amount;
    WebView webview;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_payment);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
       // Button btn = (Button) findViewById(R.id.start_transaction);
        Checkout.preload(getApplicationContext());

        //phone = (EditText) findViewById(R.id.phone);
       // amount = (EditText) findViewById(R.id.amountid);
        amount.setText(getIntent().getStringExtra("amount"));
        amount.setVisibility(View.INVISIBLE);

    }



   }
