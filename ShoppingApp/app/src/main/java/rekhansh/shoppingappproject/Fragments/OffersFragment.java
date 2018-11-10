package rekhansh.shoppingappproject.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.Query;

import rekhansh.shoppingappproject.FirebaseHelper;
import rekhansh.shoppingappproject.R;
import rekhansh.shoppingappproject.entities.Offers;

/**
 * Fragment for Offers
 */
public class OffersFragment extends Fragment {


    public OffersFragment() {
        // Required empty public constructor
    }

    //Firebase List Apdaper
    private FirebaseListAdapter<Offers> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_offers, container, false);

        //Setting Root For JSON file of Items Database
        Query query = FirebaseHelper.myRefRoot.child("Offers");

        //Getting Views in Root VIew
        ListView listView = rootView.findViewById(R.id.list_offers_fragment);
        ProgressBar progressBar = rootView.findViewById(R.id.progressBar_offers_fragment);
        //Setting Empty View For Progress Bar
        listView.setEmptyView(progressBar);

        //Setting List Options For FirebaseListAdapter
        FirebaseListOptions<Offers> options =
                new FirebaseListOptions.Builder<Offers>()
                        .setQuery(query, Offers.class)
                        .setLayout(R.layout.item_offers)
                        .build();

        //Setting FirebaseListAdapter
        adapter = new FirebaseListAdapter<Offers>(options) {
            @Override
            protected void populateView(View v, Offers model, int position)
            {
                //Getting Views of List Item at position
                TextView title = v.findViewById(R.id.item_offer_title);
                TextView description = v.findViewById(R.id.item_offer_description);

                //Setting Views of List Item at position with Data of ItemObject
                title.setText(model.getTitle());
                description.setText(model.getDescription());
            }
        };

        //Attaching Adapter to ListView
        listView.setAdapter(adapter);

        //Returning RootView For Fragment
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        //Start Listening for changes in Firebase Database
        adapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        //Stop Listening for changes in Firebase Database
        adapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Start Listening for changes in Firebase Database
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //Stop Listening for changes in Firebase Database
        adapter.stopListening();
    }
}
