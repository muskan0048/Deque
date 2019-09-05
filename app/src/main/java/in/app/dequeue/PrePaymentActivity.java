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
import com.razorpay.Razorpay;

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

   void startPayment(){
       Razorpay razorpay = new Razorpay(PrePaymentActivity.this);
       razorpay.getPaymentMethods(new Razorpay.PaymentMethodsCallback() {
           @Override
           public void onPaymentMethodsReceived(String result) {
               try {
                   JSONObject paymentMethods = new JSONObject(result);
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }

           @Override
           public void onError(String error){

           }
       });

       webview = findViewById(R.id.payment_webview);
       // Hide the webview until the payment details are submitted
       webview.setVisibility(View.GONE);

       razorpay.setWebView(webview);

       try {

           JSONObject data = new JSONObject();
           data.put("amount", 1000); // pass in currency subunits. For example, paise. Amount: 1000 equals ₹10
           data.put("email", "somecustomer@somesite.com");
           data.put("contact", "9876543210");
           JSONObject notes = new JSONObject();
           notes.put("custom_field", "abc");
           data.put("notes", notes);
           data.put("method", "netbanking");
           // Method specific fields
           data.put("bank", "HDFC");

           // Make webview visible before submitting payment details
           webview.setVisibility(View.VISIBLE);

           razorpay.submit(data, new PaymentResultListener() {
               @Override
               public void onPaymentSuccess(String razorpayPaymentId) {
                   // Razorpay payment ID is passed here after a successful payment
               }

               @Override
               public void onPaymentError(int code, String description) {
                   // Error code and description is passed here
               }
           });

       } catch (Exception e) {
           Log.e("", "Error in submitting payment details", e);
       }
   }

   }
