package in.app.dequeue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.app.dequeue.Models.Products;
import in.app.dequeue.Utils.Constants;

public class ProductActivity extends AppCompatActivity{

    List<Products> cart;
    ListView listView;
    double sum = 0;
    ImageView imageView;
    LinearLayout linearLayout;
    Button checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();

    }

   void initViews(){

       cart = new ArrayList<>();

       listView = (ListView) findViewById(R.id.list_item);
       listView.setVisibility(View.GONE);

       linearLayout = findViewById(R.id.abv);
       linearLayout.setVisibility(View.VISIBLE);

       FloatingActionButton fab = findViewById(R.id.fab);
       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(ProductActivity.this, MainActivity.class);
               startActivity(intent);
           }
       });

       LayoutInflater inflater = LayoutInflater.from(ProductActivity.this);

       View view = inflater.inflate(R.layout.button, null, true);

       checkout = view.findViewById(R.id.checkout);

       listView.addFooterView(view);

       imageView = (ImageView) findViewById(R.id.imageView4);

       Glide.with(getApplicationContext()).load(R.mipmap.empty).into(imageView);


   }

    @Override
    protected void onResume() {
        super.onResume();
        addProduct(Constants.pid);
    }

    public void addProduct(String id){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "https://codecorp.in/android/test.php?id="+id;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println();
                        System.out.println("Response is "+ response);
                    try {
                        JSONObject obj = new JSONObject(response);

                        JSONArray array1 = obj.getJSONArray("records");

                        for (int i = 0; i < array1.length(); i++) {

                            JSONObject responseObject = array1.getJSONObject(i);
                            String id = responseObject.optString("id");
                            String pname = responseObject.optString("pname");
                            String bname = responseObject.optString("bname");
                            String price = responseObject.optString("price");
                            Products products = new Products(id, pname, bname, price);
                            cart.add(products);
                            updateSum(Double.parseDouble(price));
                        }

                        listView.setAdapter(new ListViewAdapter(cart, ProductActivity.this));
                        listViewCount();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            });

            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ListViewAdapter extends ArrayAdapter<Products> {

        private List<Products> heroList;
        private Context mCtx;


        public ListViewAdapter(List<Products> heroList, Context mCtx) {
            super(mCtx, R.layout.list_product, heroList);
            this.heroList = heroList;
            this.mCtx = mCtx;
        }

        //this method will return the list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //getting the layoutinflater
            LayoutInflater inflater = LayoutInflater.from(ProductActivity.this);

            View listViewItem = inflater.inflate(R.layout.list_product, null, true);

            final int[] count = {1};

            final Products hero = heroList.get(position);

            //getting text views
            TextView textViewName = listViewItem.findViewById(R.id.title);
            textViewName.setText(hero.getBname()+" "+ hero.getPname());

            TextView textViewPrice = listViewItem.findViewById(R.id.textViewprice);
            textViewPrice.setText(hero.getPrice());

            TextView number = listViewItem.findViewById(R.id.number);

            TextView prd = listViewItem.findViewById(R.id.offer);
            prd.setText(""+Double.parseDouble(textViewPrice.getText().toString())*Double.parseDouble(number.getText().toString()));

            ImageView add = listViewItem.findViewById(R.id.plus);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count[0]++;
                    number.setText(String.valueOf(count[0]));
                    prd.setText(""+Double.parseDouble(textViewPrice.getText().toString())*Double.parseDouble(number.getText().toString()));
                    updateSum(Double.parseDouble(textViewPrice.getText().toString()));
                }
            });

            ImageView minus = listViewItem.findViewById(R.id.minus);
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count[0] !=1){
                        count[0]--;
                        number.setText(String.valueOf(count[0]));
                        prd.setText(""+Double.parseDouble(textViewPrice.getText().toString())*Double.parseDouble(number.getText().toString()));
                        updateSum(-Double.parseDouble(textViewPrice.getText().toString()));
                    }
                }

            });

            ImageView delete = listViewItem.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    heroList.remove(position);
                    notifyDataSetChanged();

                    updateSum(-Double.parseDouble(prd.getText().toString()));

                }

            });

            return listViewItem;
        }

        @Override
        public int getCount() {
            return heroList.size();
        }
    }

    void updateSum(double value){
        sum = sum+ value;
        sumPrint(sum);
    }

    void sumPrint(double sum1){
        checkout.setText("Checkout \n Order Amount :  ₹"+ sum1);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, PrePaymentActivity.class);
                intent.putExtra("amount", ""+(100*sum1));
                startActivity(intent);
            }
        });
    }

    void listViewCount(){
        if(listView.getAdapter().getCount()!=0){
            linearLayout.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
        else {
            listView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }

    }

}
