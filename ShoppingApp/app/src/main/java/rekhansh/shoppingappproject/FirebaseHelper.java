package rekhansh.shoppingappproject;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class FirebaseHelper
{
    public static final FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    //public static final FirebaseDatabase mDatabase  = FirebaseDatabase.getInstance();;
    public static final DatabaseReference myRefRoot =  FirebaseDatabase.getInstance().getReference();


}
