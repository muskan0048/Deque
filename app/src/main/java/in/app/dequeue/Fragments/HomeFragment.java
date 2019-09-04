package in.app.dequeue.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.app.dequeue.Models.Store;
import in.app.dequeue.ProductActivity;
import in.app.dequeue.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    List<Store> storeList = new ArrayList();
    ListView recyclerView;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = v.findViewById(R.id.recycle);
        loadData();
        return v;
    }

    void loadData(){
        Store s1 = new Store("s1", "Store 1");
        storeList.add(s1);
        Store s2 = new Store("s2", "Store 2");
        storeList.add(s2);
        Store s3 = new Store("s3", "Store 3");
        storeList.add(s3);
        Store s4 = new Store("s4", "Store 4");
        storeList.add(s4);
        Store s5 = new Store("s5", "Store 5");
        storeList.add(s5);
        Store s6 = new Store("s6", "Store 6");
        storeList.add(s6);
        ListViewAdapter adapter = new ListViewAdapter(storeList, getActivity());

        //adding the adapter to listview
        recyclerView.setAdapter(adapter);
    }

    public class ListViewAdapter extends ArrayAdapter<Store> {

        //the hero list that will be displayed
        private List<Store> heroList;
        private Context mCtx;

        //the context object


        public ListViewAdapter(List<Store> heroList, Context mCtx) {
            super(mCtx, R.layout.list_items, heroList);
            this.heroList = heroList;
            this.mCtx = mCtx;
        }

        //this method will return the list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //getting the layoutinflater
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            //creating a view with our xml layout
            View listViewItem = inflater.inflate(R.layout.list_items, null, true);

            //getting text views
            TextView textViewName = listViewItem.findViewById(R.id.textView2);

            //Getting the hero for the specified position
            final Store hero = heroList.get(position);

            //setting hero values to textviews
            textViewName.setText(hero.getName());
            listViewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ProductActivity.class);
                    intent.putExtra("sid", hero.getId());
                    startActivity(intent);
                }
            });

            //returning the listitem
            return listViewItem;
        }
    }

}
