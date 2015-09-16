/*
	Copyright (c) 2015 Luiz Carlos <luiz04nl@gmail.com>
*/

package br.com.minicurso;

import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.security.*;
import android.content.*;

public class LoginActivity extends ActionBarActivity
{
    protected Button btnSignIn, btnSignUp, btnRecuperarpassword;
    protected AutoCompleteTextView txtEmail, txtPassword;
    protected String Email, Password;
    protected String userName;
    protected SharedPreferencesMinicurso sharedPreferencesMinicurso;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.sharedPreferencesMinicurso = new SharedPreferencesMinicurso(this);
        this.userName = sharedPreferencesMinicurso.getUserName();

        if ( userName != null )
        {
            nextActivity();
        }
        else
        {
                setContentView(R.layout.activity_login);

                this.txtEmail = (AutoCompleteTextView) findViewById(R.id.editEmail);
                this.txtPassword = (AutoCompleteTextView) findViewById(R.id.editPassword);

                final SharedPreferencesMinicurso sharedPreferencesMinicurso = new SharedPreferencesMinicurso(this);

                this.btnSignIn = (Button) findViewById(R.id.btn_signIn_small);
                this.btnSignIn.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        if (ValidaLogin())
                        {
                            sharedPreferencesMinicurso.setUserName(Email);
                            nextActivity();
                        }
                    }
                });

                this.btnRecuperarpassword = (Button) findViewById(R.id.btn_forgotYourpassword);
                this.btnRecuperarpassword.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Toast.makeText(getApplication(), "Ainda não Implementado", Toast.LENGTH_SHORT).show();
                    }
                });

                this.btnSignUp = (Button) findViewById(R.id.btn_signUp);
                this.btnSignUp.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Toast.makeText(getApplication(), "Ainda não Implementado", Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }

    public void nextActivity()
    {
        final Intent it = new Intent(getApplication(), NavigationActivity.class);
        final Bundle params = new Bundle();
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        params.putString("option", "1");
        it.putExtras(params);
        startActivity(it);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public boolean ValidaLogin()
    {
        Email = txtEmail.getText().toString();
        Password = txtPassword.getText().toString();

        if ( Email.isEmpty())
        {
            txtEmail.setError(getResources().getString(R.string.empty_email));
            return false;
        }
        if ( Password.isEmpty())
        {
            txtPassword.setError(getResources().getString(R.string.empty_password));
            return false;
        }

        final SQLiteOpenHelperMinicurso db = new SQLiteOpenHelperMinicurso(getApplication());

        String retornoConsulta, tabelaConsulta, campoConhecido, valorConhecido;
        tabelaConsulta = "tbusuario";
        String[] retornoEsperado = {"senha"};
        campoConhecido = "Email";
        valorConhecido = Email;

        retornoConsulta = db.getData(tabelaConsulta, retornoEsperado, campoConhecido, valorConhecido);

        String PasswordHash = PasswordHash(this.Password);

        if ( !retornoConsulta.equals(PasswordHash) )
        {
            txtEmail.setError(getResources().getString(R.string.email_password_invalid));
            txtPassword.setError(getResources().getString(R.string.email_password_invalid));
            return false;
        }

        return true;
    }

   public String PasswordHash(String password)
   {

       MessageDigest algorithm = null;
       try
       {
           algorithm = MessageDigest.getInstance("MD5");
       }
       catch (NoSuchAlgorithmException e)
       {
           e.printStackTrace();
       }

       byte messageDigest[] = new byte[0];
       try
       {
           messageDigest = algorithm.digest(password.getBytes("UTF-8"));
       }
       catch (UnsupportedEncodingException e)
       {
           e.printStackTrace();
       }

       StringBuilder hexString = new StringBuilder();

       for (byte b : messageDigest)
       {
           hexString.append(String.format("%02X", 0xFF & b));
       }

       return hexString.toString();
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

}
