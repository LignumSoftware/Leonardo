package com.MaNoS.DarcyRibeiro.Tech.Leonardo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

    private Context mycontext;
    private String dbPath = null;

    // private String DB_PATH =
    // mycontext.getApplicationContext().getPackageName()+"/databases/";
    private static String DB_NAME = "Leonardo.db";// the extension may
    // be .sqlite or .db
    public SQLiteDatabase myDataBase;

	/*
     * private String DB_PATH = "/data/data/" +
	 * mycontext.getApplicationContext().getPackageName() + "/databases/";
	 */

    @SuppressLint("SdCardPath")
    public DataBaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, 1);
        Log.d("DataBaseHelper construtory()", "Inicio do método");

        this.mycontext = context;

        dbPath = "/data/data/" + mycontext.getApplicationContext().getPackageName() + "/databases/";
        Log.i("Databaser helper - Path", dbPath);
        boolean dbexist = checkdatabase();
        if (dbexist) {
            opendatabase();
        } else {
            Log.i("db", "Database doesn't exist");
            createdatabase();
        }
    }

    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if (dbexist) {
            Log.i("DB", "banco já existe");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkdatabase() {
        boolean checkdb = false;
        try {
            String myPath = dbPath + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            Log.i("Checkdatabase DB", "banco n�o existe");
        }
        return checkdb;
    }

    public void copydatabase() throws IOException {
        // Open your local db as the input stream
        InputStream myinput = mycontext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outfilename = dbPath + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }

        // Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public SQLiteDatabase opendatabase() {
        // Open the database
        String mypath = dbPath + DB_NAME;
        SQLiteDatabase db = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
        Log.i("DataBaseHelper.opendatabase()", "abri o banco " + mypath);
        return db;
    }

    public synchronized void close() {
        if (myDataBase != null) {
            Log.i("Close", "fechando o banco (close) " + myDataBase.getPath());
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    public void closeDB() {
        this.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}