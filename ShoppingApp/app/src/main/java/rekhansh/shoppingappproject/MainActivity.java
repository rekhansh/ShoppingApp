package rekhansh.shoppingappproject;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import rekhansh.shoppingappproject.Fragments.AboutUsFragment;
import rekhansh.shoppingappproject.Fragments.HomeFragment;
import rekhansh.shoppingappproject.Fragments.OffersFragment;
import rekhansh.shoppingappproject.entities.ItemObject;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,HomeFragment.OnHomeFragmentInteractionListener {

   private FirebaseAuth.AuthStateListener mAuthListener;

    private static final int RC_SIGN_IN = 1;

    private TextView username;
    private TextView useremail;
    private ImageView userphoto ;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting Content View
        setContentView(R.layout.activity_main);

        //Setting Toolbar For Activity
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setting Drawer with Activity
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Setting NavigationBar
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        //Setting Fragment in Main container
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(getSupportFragmentManager().findFragmentById(R.id.frame_container)==null)
        {
            HomeFragment homeFragment = new HomeFragment();
            fragmentTransaction.add(R.id.frame_container,homeFragment).commit();
        }

        //Menu From NavigationBar
        menu = navigationView.getMenu();

        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);
        useremail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userEmail);
        userphoto=(ImageView) navigationView.getHeaderView(0).findViewById(R.id.userPhoto);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    // User is signed in
                    // Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    username.setText(user.getDisplayName());
                    useremail.setText(user.getEmail());

                    Glide.with(userphoto.getContext()).load(user.getPhotoUrl()).into(userphoto).onLoadFailed(getResources().getDrawable(R.drawable.default_person_pic));
                    menu.findItem(R.id.nav_logout).setVisible(true);
                    menu.setGroupVisible(R.id.nav_user_group,true);
                    menu.findItem(R.id.nav_login).setVisible(false);

                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                    username.setText("Guest");
                    useremail.setText("");
                    userphoto.setImageResource(R.drawable.default_person_pic);

                    menu.findItem(R.id.nav_logout).setVisible(false);
                    menu.setGroupVisible(R.id.nav_user_group,false);
                    menu.findItem(R.id.nav_login).setVisible(true);
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseHelper.mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseHelper.mAuth.removeAuthStateListener(mAuthListener);
        }
    }

        @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            Toast.makeText(this,"To Be Implemented",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            HomeFragment homeFragment = new HomeFragment();
            fragmentTransaction.replace(R.id.frame_container, homeFragment).commit();

        }
        else if(id==R.id.nav_offers)
        {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            OffersFragment offersFragment = new OffersFragment();
            fragmentTransaction.replace(R.id.frame_container, offersFragment).commit();
            setTitle("Offers");
        }
        else if(id==R.id.nav_wish_list)
        {
            Toast.makeText(this,"To Be Implemented",Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_my_orders)
        {
            Toast.makeText(this,"To Be Implemented",Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_login)
        {
            setLogin();
        }
        else if(id == R.id.nav_logout)
        {
            setLogout();
        }
        else if(id==R.id.nav_settings)
        {
            Toast.makeText(this,"To Be Implemented",Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.nav_about_us)
        {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            AboutUsFragment aboutUsFragment = new AboutUsFragment();
            fragmentTransaction.replace(R.id.frame_container, aboutUsFragment).commit();
            setTitle("About Us");
        }
        else if(id==R.id.nav_help)
        {
            Toast.makeText(this,"To Be Implemented",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN)
        {
            if(resultCode==RESULT_OK)
            {
                Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show();

            }
            else if(resultCode==RESULT_CANCELED)
            {

                Toast.makeText(this, "Sign In Cancel", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setLogin()
    {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.EmailBuilder().build(),
                                new AuthUI.IdpConfig.GoogleBuilder().build()
//                                new AuthUI.IdpConfig.FacebookBuilder().build(),
//                                new AuthUI.IdpConfig.TwitterBuilder().build(),
//                                new AuthUI.IdpConfig.PhoneBuilder().build()
                        )).build(),RC_SIGN_IN);
    }
    private void setLogout()
    {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.logout_msg)
                .setTitle(R.string.logout_title);

        // 3. Get the AlertDialog from create()


// Add the buttons

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id)
        {
            FirebaseHelper.mAuth.signOut();
        }});

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            // User cancelled the dialog
        }});

    AlertDialog dialog = builder.create();
        dialog.show();
}

    @Override
    public void OnHomeFragmentBuyButtonClick(ItemObject selectedItem) {
        Toast.makeText(this,"To Be Implemented",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnHomeFragmentAddToCartButtonClick(ItemObject selectedItem) {
        Toast.makeText(this,"To Be Implemented",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnHomeFragmentAddToWishListButtonClick(ItemObject selectedItem) {
        Toast.makeText(this,"To Be Implemented",Toast.LENGTH_SHORT).show();
    }
}
