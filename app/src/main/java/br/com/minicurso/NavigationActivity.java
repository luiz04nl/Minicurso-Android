/*
Copyright (c) 2015 Luiz Carlos <luiz04nl@gmail.com>
*/

package br.com.minicurso;

import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.drawable.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.widget.*;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import android.net.*;
import android.graphics.*;


public class NavigationActivity extends AppCompatActivity
{
    protected String activityName;
    protected Toolbar toolbar_dark, toolbar_light;
    protected DrawerLayout dlDrawer;
    protected String option;
    protected int i;
    protected Class fragmentClass;
    protected Fragment fragment = null;
    protected ActionBar actionbar;
    protected TextView textUser;
    protected Menu menu;
    protected ActionBarDrawerToggle drawerToggle;
    protected String userName;
    public static final int SELECT_PICTURE = 1;
    public static final int REQUEST_IMAGE_CAPTURE = 2;
    protected de.hdodenhof.circleimageview.CircleImageView profile_image;
    protected String stringImgPessoa;
    protected String imageString;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        this.activityName = getClass().getName();
        this.dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        this.dlDrawer.setDrawerListener(drawerToggle);

        final SharedPreferencesMinicurso sharedPreferencesMinicurso = new
                SharedPreferencesMinicurso(this);

        this.userName = sharedPreferencesMinicurso.getUserName();
        this.textUser = (TextView) findViewById(R.id.textUser);
        this.textUser.setText(userName);

        Intent intent = getIntent();
        Bundle params = intent.getExtras();
        if(params!=null)
        {
            this.option = params.getString("option");
            this.i = Integer.parseInt(option);
            setFragment(this.i);
        }
        else
        {
            setFragment(0);
        }

        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        setFragmentManager();



        final ProfileImage profileImage = new ProfileImage();

        this.profile_image = (de.hdodenhof.circleimageview.CircleImageView)
                findViewById(R.id.profile_image);
        final SQLiteOpenHelperMinicurso db = new SQLiteOpenHelperMinicurso(this);
        if ( db.getDrawableProfileImageUsuario(this.userName) != null)
        {
            this.stringImgPessoa = db.getDrawableProfileImageUsuario(this.userName);
            File file = profileImage.getAlbumStorageDir("Minicurso");
            if ( profileImage.isExternalStorageReadable() )
            {
                this.imageString = "ic_profile_image_" + userName + ".png";
                String pathName = file.getPath() + "/" + this.imageString;
                Drawable drawableImgPessoa = Drawable.createFromPath(pathName);
                profileImage.setProfileImage(drawableImgPessoa);
                this.profile_image.setImageDrawable(drawableImgPessoa);
            }
        }





    }



    public void getIntentGaleria()
    {
        ProfileImage profileImage = new ProfileImage();
        Intent intent = profileImage.getIntentGaleria();
        startActivityForResult(Intent.createChooser(intent,
                getResources().getString(R.string.selectPicture)), SELECT_PICTURE);
    }


    public void getIntentCamera()
    {
        ProfileImage profileImage = new ProfileImage();
        Intent intent = profileImage.getIntentCamera();
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        this.profile_image = (de.hdodenhof.circleimageview.CircleImageView)
                findViewById(R.id.profile_image);

        ProfileImage profileImage = new ProfileImage();

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK)
        {
            Uri selectedImageUri = profileImage.getUriProfileImage(data);
            this.profile_image.setImageURI(selectedImageUri);
        }
        else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bitmap imageBitmap = profileImage.getBitmapProfileImage(data);
            this.profile_image.setImageBitmap(imageBitmap);
        }
        Drawable drawable = this.profile_image.getDrawable();
        profileImage.storeProfileImage(drawable, this, this.userName);
        setFragmentManager();
    }


    private ActionBarDrawerToggle setupDrawerToggle()
    {
        return new ActionBarDrawerToggle(this, dlDrawer, toolbar_dark,
                R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView)
    {
        navigationView.setNavigationItemSelectedListener(new
                                                                 NavigationView.OnNavigationItemSelectedListener()
                                                                 {
                                                                     public boolean onNavigationItemSelected(MenuItem menuItem)
                                                                     {
                                                                         selectDrawerItem(menuItem);
                                                                         return true;
                                                                     }
                                                                 });
    }
    public void toolbarLight(Drawable color)
    {
        this.toolbar_dark = (Toolbar) findViewById(R.id.toolbar_dark);
        this.toolbar_dark.setVisibility(View.GONE);
        this.toolbar_light = (Toolbar) findViewById(R.id.toolbar_light);
        this.toolbar_light.setVisibility(View.VISIBLE);
        this.toolbar_light.setTitleTextColor(getResources().getColor(R.color.Black));
        this.toolbar_light.setBackground(color);
        setSupportActionBar(this.toolbar_light);
        this.actionbar = getSupportActionBar();
        this.actionbar.setDisplayHomeAsUpEnabled(true);
    }

    public void toolbarDark(Drawable color)
    {
        this.toolbar_light = (Toolbar) findViewById(R.id.toolbar_light);
        this.toolbar_light.setVisibility(View.GONE);
        this.toolbar_dark = (Toolbar) findViewById(R.id.toolbar_dark);
        this.toolbar_dark.setVisibility(View.VISIBLE);
        this.toolbar_dark.setTitleTextColor(getResources().getColor(R.color.White));
        this.toolbar_dark.setBackground(color);
        setSupportActionBar(this.toolbar_dark);
        this.actionbar = getSupportActionBar();
        this.actionbar.setDisplayHomeAsUpEnabled(true);
    }

    public void actionbarBlue()
    {
        Drawable colorBlue = getResources().getDrawable(R.color.Blue_500);
        toolbarDark(colorBlue);
    };
    public void actionbarYellow()
    {
        Drawable colorYellow = getResources().getDrawable(R.color.Yellow_500);
        toolbarLight(colorYellow);
    };
    public void actionbarRed()
    {
        Drawable colorRed = getResources().getDrawable(R.color.Red_500);
        toolbarDark(colorRed);
    };

    public void actionbarGreen()
    {
        Drawable colorGreen = getResources().getDrawable(R.color.Green_500);
        toolbarDark(colorGreen);
    };
    public void setFragment(int i)
    {
        switch(i)
        {
            case 1:
                actionbarBlue();
                this.fragmentClass = FragmentDashboard.class;
                this.actionbar.setTitle(R.string.dashboard);
                break;
            case 2:
                actionbarYellow();
                this.fragmentClass = FragmentMyProfile.class;
                this.actionbar.setTitle(R.string.myProfile);
                break;
            default:
                actionbarBlue();
                this.fragmentClass = FragmentDashboard.class;
                this.actionbar.setTitle(R.string.dashboard);
        }
    }

    public void selectDrawerItem(MenuItem menuItem)
    {
        switch(menuItem.getItemId())
        {
            case R.id.fragment_dashboard:
                setFragment(1);
                break;
            case R.id.fragment_myprofile:
                setFragment(2);
                break;
            default:
                setFragment(0);
        }
        setFragmentManager();
        menuItem.setChecked(true);
        this.dlDrawer.closeDrawers();
    }
    public void setFragmentManager()
    {
        try{
            this.fragment = (Fragment) this.fragmentClass.newInstance();}
        catch (Exception e){
            e.printStackTrace();}
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, this.fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_black_actions_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_settings:
            {
                Toast.makeText(this, R.string.settings, Toast.LENGTH_SHORT).show();
                setFragmentManager();
                return true;
            }
            case R.id.action_help:
            {
                Toast.makeText(this, R.string.help, Toast.LENGTH_SHORT).show();
                setFragmentManager();
                return true;
            }
            default:
                break;
        }

        if (this.drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        this.drawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        this.drawerToggle.onConfigurationChanged(newConfig);
    }
}