package com.example.ayush.bottomnavigation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button mSignout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        final Profile profile=Profile.getCurrentProfile();
        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        textView= (TextView) findViewById(R.id.setName);
        mSignout= (Button) findViewById(R.id.SignoutButton);

        String s="";
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            s = bundle.getString("Name");
        }


        if(user!=null) {
            s = s + user.getDisplayName();
            Log.d("lala",FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }
        else
            Toast.makeText(this, "Google Null hai", Toast.LENGTH_SHORT).show();

        if(profile!=null)
        s=s+profile.getName();
        else
            Toast.makeText(this, "Facebook null hai", Toast.LENGTH_SHORT).show();
        
        textView.setText(s);

        mSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();

                Intent mintent=new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(mintent);
            }
        });

        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Favourite", R.drawable.ic_favorite_white_24dp, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Local", R.drawable.ic_local_dining_white_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Location", R.drawable.ic_location_on_white_24dp, R.color.colorPrimaryDark);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Profile", R.drawable.ic_local_dining_white_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("Payment", R.drawable.ic_local_dining_white_24dp, R.color.colorPrimary);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);

        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.colorPrimary));
        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

        bottomNavigation.setForceTint(true);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        bottomNavigation.setCurrentItem(2);
        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setColored(true);

      bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
          @Override
          public boolean onTabSelected(int position, boolean wasSelected) {

              Intent intent=new Intent(getApplicationContext(),SpeakerActivity.class);
              startActivity(intent);
              return true;
          }
      });

    }
    public static void start(Context c) {
        c.startActivity(new Intent(c, SpeakerActivity.class));
    }
}
