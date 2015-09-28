/*
Copyright (c) 2015 Luiz Carlos <luiz04nl@gmail.com>
*/

package br.com.minicurso;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.util.*;

public class SQLiteOpenHelperMinicurso extends SQLiteOpenHelper
{
    protected static int DATABASE_VERSION = 1;
    protected static String DATABASE_NAME = "minicurso.sqlite.db";
    protected static String LOG_TAG = "minicurso.sqlite";
    protected Context contexto;

    public SQLiteOpenHelperMinicurso(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.contexto = context;
    }

    public void onCreate(SQLiteDatabase db)
    {
        String[] sql = contexto.getString(R.string.SQLiteHelper_onCreate).split(";");
        db.beginTransaction();
        try
        {
            ExecutarComandosSQL(db, sql);
            db.setTransactionSuccessful();
        }
        catch (SQLException e)
        {
            Log.e("Erro Interno", e.toString());
        }
        finally
        {
            db.endTransaction();
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(LOG_TAG, "Atualizando a base de dados da versão " + oldVersion + " para " + newVersion + ", que destruirá todos os dados antigos");
                String[] sql =
            contexto.getString(R.string.SQLiteHelper_onUpgrade).split(";");
        db.beginTransaction();
        try
        {
            ExecutarComandosSQL(db, sql);
            db.setTransactionSuccessful();
        }
        catch (SQLException e)
        {
            Log.e("Erro Interno", e.toString());
            throw e;
        }
        finally
        {
            db.endTransaction();
        }
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void ExecutarComandosSQL(SQLiteDatabase db, String[] sql)
    {
        for (String s : sql)
        {
            if (s.trim().length() > 0)
            {
                db.execSQL(s);
            }
        }
    }




    public String getData(String tabela, String[] projection, String campo, String valor)
    {
        SQLiteDatabase db = getReadableDatabase();
        String consulta = "nf";
        String selection = campo + " LIKE ?";
        String[] selectionArgs = {String.valueOf(valor)};
        String sortOrder = campo + " DESC";
        Cursor c = db.query(
                tabela,
                projection,
                selection,
                selectionArgs,null,null,sortOrder
        );
        if (c.moveToFirst())
        {
            do
            {
                c.moveToFirst();
                consulta = c.getString(c.getColumnIndex(projection[0]));
            }
            while (c.moveToNext());
        }
        c.close();
        db.close();
        return consulta;
    }


    public boolean UpdateImgProfileUsuario( String Usuario, String imageString)
    {
        String codPessoa, tabela, campo, valor;
        tabela = "tbusuario";
        String[] retornoUsuario = {"cdpessoa"};
        campo = "email";
        valor = Usuario;
        codPessoa = getData(tabela, retornoUsuario, campo, valor);
        ContentValues valuesPessoa = new ContentValues();
        valuesPessoa.put("imgpessoa", imageString);
        SQLiteDatabase dbwritePessoa = this.getWritableDatabase();
        dbwritePessoa.update("tbpessoa", valuesPessoa, "cdpessoa " + " = ? ",
                new String[]{codPessoa});
        dbwritePessoa.close();
        return true;
    }

    public String getDrawableProfileImageUsuario(String Email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        final String SQL_QUERY = "SELECT imgpessoa FROM tbpessoa P INNER JOIN tbusuario U " +
        " ON P.cdpessoa = U.cdpessoa and email = '" + Email + "'";
        Cursor c = db.rawQuery(SQL_QUERY, null);
        String imgPessoa = null;
        if (c.moveToFirst())
        {
            do
            {
                c.moveToFirst();
                imgPessoa = c.getString(c.getColumnIndex("imgpessoa"));
            }
            while (c.moveToNext());
        }
        c.close();
        db.close();
        return imgPessoa;
    }


}
