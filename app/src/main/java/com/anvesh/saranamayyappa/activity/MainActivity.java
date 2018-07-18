package com.anvesh.saranamayyappa.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anvesh.saranamayyappa.Fragments.Feeds;
import com.anvesh.saranamayyappa.Fragments.Fun2win;
import com.anvesh.saranamayyappa.Fragments.Home;
import com.anvesh.saranamayyappa.Fragments.Material;
import com.anvesh.saranamayyappa.Fragments.Nearby;
import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.service.ReadContactService;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    //Fragment fragment;
    BottomNavigationView navigation;
    FloatingActionButton floatingActionButton;
    FragmentTransaction fragmentTransaction;
    TextView toolbarText;
    ImageView notificationImage, settings;
    CircleImageView profile,basicprofile;
    TextView profiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setBottomNavigation();
        floatingActionButton = (FloatingActionButton) findViewById(R.id.tools1);
        floatingActionButton.setOnClickListener(this);
        fabSelected();
        setToolbar();
        listners();
        getReadContactsPermission();
        Toast.makeText(this, "  " +AyyappaPref.getLatitude() + "  " +AyyappaPref.getLongitude(), Toast.LENGTH_SHORT).show();


        toolbarText.setText("Home");
    }

    private void listners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabSelected();
            }
        });
        notificationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);

            }
        });
        basicprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SignUp.class);
                i.putExtra("flag", 1);
                startActivityForResult(i, 120);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SignUp.class);
                i.putExtra("flag", 1);
                startActivityForResult(i, 120);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolbar();
        //fabSelected();

    }

    public void getReadContactsPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, AyyappaConstants.PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.

            Intent msgIntent = new Intent(MainActivity.this, ReadContactService.class);
            startService(msgIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == AyyappaConstants.PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, AyyappaConstants.PERMISSIONS_REQUEST_READ_CONTACTS);
                    //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
                } else {
                    // Android version is lesser than 6.0 or the permission is already granted.
                    Intent msgIntent = new Intent(MainActivity.this, ReadContactService.class);
                    startService(msgIntent);
                }
            } else {
                Toast.makeText(this, "Until you grant the permission, we can't display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setToolbar() {
        System.out.println("Near By" + AyyappaPref.getProfileImagePath());

        /* if (profile.getDrawable() == null && profiles.getText().toString().isEmpty())*/

        if (profile == null && profiles == null) {
            basicprofile.setVisibility(View.VISIBLE);
            profile.setVisibility(View.INVISIBLE);
            profiles.setVisibility(View.INVISIBLE);
        } else if (!TextUtils.isEmpty(AyyappaPref.getProfileImagePath())) {
            profile.setVisibility(View.VISIBLE);
            Picasso.with(MainActivity.this)
                    .load(AyyappaPref.getProfileImagePath())
                    .into(profile);
                  profiles.setVisibility(View.INVISIBLE);
                  basicprofile.setVisibility(View.INVISIBLE);
        } else {
            profile.setVisibility(View.INVISIBLE);
            String username = AyyappaPref.getUserName();
            String fn = username.trim();
            if (fn.length() > 1) {
                String fl = fn.substring(0, 1);
                profiles.setText(fl);
                profiles.setVisibility(View.VISIBLE);
            }

        }


    }

    private void setBottomNavigation() {
        navigation.setOnNavigationItemSelectedListener(this);
        BottomNavigationHelper.disableShiftMode(navigation);
    }

    public void updateNavigationBarState(int i) {
        navigation.getMenu().getItem(i).setChecked(true);
        if(i!=2){
            floatingActionButton.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}},new int[]{getResources().getColor(R.color.white)}));
            DrawableCompat.setTint(floatingActionButton.getDrawable(),getResources().getColor(R.color.black));
        }else{
            floatingActionButton.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}},new int[]{getResources().getColor(R.color.colorPrimary)}));
            DrawableCompat.setTint(floatingActionButton.getDrawable(),getResources().getColor(R.color.white));
        }
    }


    public void findViews(){
        navigation =  findViewById(R.id.bottomNavigationView);
        toolbarText = findViewById(R.id.tv_ToolTitle);
        notificationImage = findViewById(R.id.iv_Notification);
        settings = findViewById(R.id.settings);
        profile = findViewById(R.id.profile);
        profiles = findViewById(R.id.profiles);
        basicprofile = findViewById(R.id.basicprofile);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        floatingActionButton.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}},new int[]{getResources().getColor(R.color.white)}));
        DrawableCompat.setTint(floatingActionButton.getDrawable(),getResources().getColor(R.color.black));
        switch (item.getItemId()) {
            case R.id.feeds:
                toolbarText.setText("Feeds");
                fragmentTransaction(new Feeds());
                break;
            case R.id.nearby:
                toolbarText.setText("Near By");
                fragmentTransaction(new Nearby());
                break;
            case R.id.Home:
                fabSelected();
                break;
            case R.id.fun2win:
                toolbarText.setText("Fun2Win");
                fragmentTransaction(new Fun2win());
                break;
            case R.id.material:
                toolbarText.setText("Material");
                fragmentTransaction(new Material());
                break;
        }

        return true;
    }
    public void fragmentTransaction(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameContent, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onClick(View view) {

    }

    private void fabSelected() {
        toolbarText.setText("Home");
        fragmentTransaction(new Home());
       updateNavigationBarState(2);
    }
}
