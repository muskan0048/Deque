package in.app.dequeue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.razorpay.Checkout;

import org.json.JSONObject;

public class CheckoutActivity extends AppCompatActivity {

    TextView sub, total, checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Checkout.preload(getApplicationContext());

        initViews();

    }

    void initViews(){

        sub = findViewById(R.id.txtSub);
        total = findViewById(R.id.txtTotal);
        checkout = findViewById(R.id.chk);

        sub.setText(getIntent().getStringExtra("amount"));
        total.setText(getIntent().getStringExtra("amount"));

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

    }

    void startPayment(){
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
          //  options.put("order_id", "order_9A33XWu170gUtm");
            options.put("currency", "INR");
            //options.put("currency", "INR");
            //options.put("currency", "INR");

            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", sub.getText().toString());

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("a", "Error in starting Razorpay Checkout", e);
        }
    }
    }

