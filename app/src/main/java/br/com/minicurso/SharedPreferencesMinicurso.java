/*
    Copyright (c) 2015 Luiz Carlos <luiz04nl@gmail.com>
*/

package br.com.minicurso;
import android.app.*;
import android.content.*;

public class SharedPreferencesMinicurso
{
    protected Activity context;
    protected String UserName;
    public static final String PREFS_NAME = "MinicursoPrefsFile";
    public SharedPreferencesMinicurso(Activity context)
    {
        this.context = context;
    }

    public void setUserName(String UserName)
{
    SharedPreferences settings = this.context.getSharedPreferences(PREFS_NAME, 0);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString("UserName", UserName);
    editor.commit();
}
    public String getUserName()
    {
        SharedPreferences settings = this.context.getSharedPreferences(PREFS_NAME, 0);
        this.UserName = settings.getString("UserName", UserName);
        return this.UserName;
    }
}