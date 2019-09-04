package in.app.dequeue;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;


public class PrePaymentActivity extends AppCompatActivity implements PaymentResultListener {

    EditText phone, amount;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_payment);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Button btn = (Button) findViewById(R.id.start_transaction);
        Checkout.preload(getApplicationContext());

        phone = (EditText) findViewById(R.id.phone);
        amount = (EditText) findViewById(R.id.amountid);
        amount.setText(getIntent().getStringExtra("amount"));
        amount.setVisibility(View.INVISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  startpay();
                startPayment();
            }
        });
    }

    public void startPayment() {
        /**
         * Instantiate Checkout
         */

        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
        checkout.setImage(R.mipmap.self1);

        /**
         * Reference to current activity
         */
        final Activity activity = this;
/**
 * Pass your payment options to the Razorpay Checkout as a JSONObject
 */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", "Codecorp");

            /**
             * Description can be anything
             * eg: Order #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Order #123456");
            //options.put("order_id", "order_9A33XWu170gUtm");
            options.put("currency", "INR");
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser user = firebaseAuth.getCurrentUser();
            options.put("email", user.getEmail());
          //  options.put("contact", "9876543210");
            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", amount.getText().toString());

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("Activity", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        System.out.println(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        System.out.println(s+i);


    }
}