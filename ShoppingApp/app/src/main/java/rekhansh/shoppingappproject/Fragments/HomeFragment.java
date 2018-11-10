package rekhansh.shoppingappproject.Fragments;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import rekhansh.shoppingappproject.FirebaseHelper;
import rekhansh.shoppingappproject.entities.ItemObject;
import rekhansh.shoppingappproject.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.Query;


/**
 * Default Fragment for App
 * Activities containing this fragment MUST implement the {@link OnHomeFragmentInteractionListener}
 * interface.
 */
public class HomeFragment extends Fragment {

    private OnHomeFragmentInteractionListener mListener;

    //Firebase List Apdaper
    private FirebaseListAdapter<ItemObject> adapter;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        //Setting Root For JSON file of Items Database
        Query query = FirebaseHelper.myRefRoot.child("Items");

        //Getting Views in Root VIew
        ListView listView = rootView.findViewById(R.id.list_home_fragment);
        ProgressBar progressBar = rootView.findViewById(R.id.progressBar_home_fragment);
        //Setting Empty View For Progress Bar
        listView.setEmptyView(progressBar);

        //Setting List Options For FirebaseListAdapter
        FirebaseListOptions<ItemObject> options =
                new FirebaseListOptions.Builder<ItemObject>()
                        .setQuery(query, ItemObject.class)
                        .setLayout(R.layout.item_home_item)
                        .build();

        //Setting FirebaseListAdapter
        adapter = new FirebaseListAdapter<ItemObject>(options) {
            @Override
            protected void populateView(View v, final ItemObject model, int position)
            {
                //Getting Views of List Item at position
                TextView title = v.findViewById(R.id.item_item_title);
                TextView price =  v.findViewById(R.id.item_item_price);
                ImageView image = v.findViewById(R.id.item_item_image);
                TextView description = v.findViewById(R.id.item_item_description);
                MaterialButton button_buy = v.findViewById(R.id.item_btn_buy);
                MaterialButton button_add_to_cart = v.findViewById(R.id.item_btn_add_to_cart);
                MaterialButton button_wishlist= v.findViewById(R.id.item_btn_wishlist);


                //Setting Views of List Item at position with Data of ItemObject
                title.setText(model.getTitle());
                price.setText("Rs. " + String.valueOf(model.getPrice()));
                Glide.with(image.getContext()).load(model.getImageUrl()).into(image).onLoadFailed(getResources().getDrawable(R.drawable.ic_shopping_cart_white_24dp));
                description.setText(model.getDescription());
                button_buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.OnHomeFragmentBuyButtonClick(model);
                    }
                });

                button_add_to_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.OnHomeFragmentAddToCartButtonClick(model);
                    }
                });
                button_wishlist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.OnHomeFragmentAddToWishListButtonClick(model);
                    }
                });
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentInteractionListener) {
            mListener = (OnHomeFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()+ " must implement OnHomeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnHomeFragmentInteractionListener {
         void OnHomeFragmentBuyButtonClick(ItemObject selectedItem);
        void OnHomeFragmentAddToCartButtonClick(ItemObject selectedItem);
        void OnHomeFragmentAddToWishListButtonClick(ItemObject selectedItem);
    }
}
