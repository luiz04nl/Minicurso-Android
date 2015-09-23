/*
    Copyright (c) 2015 Luiz Carlos <luiz04nl@gmail.com>
*/

package br.com.minicurso;

import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.util.*;
import java.io.*;

public class ProfileImage
{
    private static final String LOG_TAG = "LOG_TAG";
    protected static Drawable profileImage;
    protected String imageString;

    public boolean isExternalStorageWritable()
    {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable()
    {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
        {
            return true;
        }
        return false;
    }

    public File getAlbumStorageDir(String albumName)
    {
        File file = new
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                albumName);
        if (!file.mkdirs())
        {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }

    public Uri getUriProfileImage(Intent data)
    {
        Uri selectedImageUri = data.getData();
        return selectedImageUri;
    }

    public Bitmap getBitmapProfileImage(Intent data)
    {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        return imageBitmap;
    }

    public Drawable getDrawableProfileImage()
    {
        return profileImage;
    }

    public void setProfileImage(Drawable profileImage)
    {
        this.profileImage = profileImage;
    }

    public Intent getIntentGaleria()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        return intent;
    }

    public Intent getIntentCamera()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        return intent;
    }

    public void storeProfileImage(Drawable drawable, Context context, String user)
                {
                    setProfileImage(drawable);
                    if (getDrawableProfileImage() != null)
                    {
                        if ( isExternalStorageWritable() )
                        {
                            this.imageString = "ic_profile_image_" + user + ".png";
                            File fileOut = new File(getAlbumStorageDir("Minicurso"),
                                    this.imageString );

                            Drawable drawableImgPessoa = getDrawableProfileImage();
                            Bitmap bm = Bitmap.createBitmap(140, 140, Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(bm);
                            drawableImgPessoa.setBounds(0, 0, 140, 140);
                            drawableImgPessoa.draw(canvas);
                            FileOutputStream outStream = null;
                            try
                            {
                                outStream = new FileOutputStream(fileOut);
                            }
                            catch (FileNotFoundException e)
                            {
                                e.printStackTrace();
                            }
                            try
                            {
                                outStream.flush();
                                outStream.close();
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                            SQLiteOpenHelperMinicurso db = new SQLiteOpenHelperMinicurso(context);
                            db.UpdateImgProfileUsuario(user, this.imageString);
                        }
                    }
                }
}