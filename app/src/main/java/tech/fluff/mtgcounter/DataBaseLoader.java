package tech.fluff.mtgcounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBaseLoader extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/tech.fluff.mtgcounter/databases/";
    private static String DB_NAME = "cards.db";
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public DataBaseLoader(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }


    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() throws IOException {
        //boolean dbExist = checkDataBase();
        boolean dbExist = false;
        if (dbExist) {
            //do nothing - database already exist
        } else {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            Log.v("Database ", "Creating Database");
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {
                Log.v("Database ", "can't copy database");
                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            Log.v("Database ", "doesn't exist");
            //database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        Log.v("Database ", "wrote database I hope");

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void forcedUpgrade() {
        this.getReadableDatabase();

        try {

            copyDataBase();

        } catch (IOException e) {
            Log.v("Database ", "can't copy database");
            throw new Error("Error copying database");

        }
    }
    public Card queryCard(String name) {
        String table = "cards";
        String[] columns = {"cardtext", "cardtype", "cardid"};
        String selection = "name = ?";
        String[] selectionArgs = {name};
        Cursor cursor = myDataBase.rawQuery("select cardtext, cardtype, cardid from cards where name = ?", selectionArgs);
        //Cursor cursor = myDataBase.query(table, columns, selection, selectionArgs, null, null, null, null);
        Log.v("Query", " done!");
        Map<String, String> cardMap = new HashMap<String, String>();
        int COL_TEXT = cursor.getColumnIndex("cardtext");
        int COL_TYPE = cursor.getColumnIndex("cardtype");
        int COL_ID = cursor.getColumnIndex("cardid");
        cursor.moveToFirst();
        cardMap.put("text", cursor.getString(COL_TEXT));
        cardMap.put("type", cursor.getString(COL_TYPE));
        cardMap.put("COL_ID", cursor.getString(COL_ID));
        Card returnCard = new Card(queryRules(cursor.getString(COL_ID)), cardMap);

        return returnCard;
    }

    public String[] listLife(int gameID) {
        Cursor cursor = myDataBase.query("life", new String[]{"action"}, "gameID = " + gameID, null, null, null, null);
        List<String> result = new ArrayList<String>();
        int COL_NAME = cursor.getColumnIndex("action");
        Log.v("Queried Rows ", Integer.toString(cursor.getCount()));
        while (cursor.moveToNext()) {
            result.add(cursor.getString(COL_NAME));
        }
        return result.toArray(new String[result.size()]);
    }

    public long addLife(int action, int gameID) {
        ContentValues values = new ContentValues();
        values.put("gameID", gameID);
        values.put("action", action);
        long newRowId = myDataBase.insert("life", null, values);
        return newRowId;
    }

    private ArrayList<Map<String, String>> queryRules(String id) {
        String table = "rulings";
        String[] columns = {"ruledate", "ruletext"};
        String selection = "cardid = ?";
        String[] selectionArgs = {id};
        Cursor cursor = myDataBase.rawQuery("select ruledate, ruletext from rulings where cardid = ?", selectionArgs);
        //Cursor cursor = myDataBase.query(table, columns, selection, selectionArgs, null, null, null, null);
        Map<String, String> resultMap = new HashMap<String, String>();
        int COL_DATE = cursor.getColumnIndex("ruledate");
        int COL_RULE = cursor.getColumnIndex("ruletext");
        ArrayList<Map<String, String>> ruleList = new ArrayList<Map<String, String>>();
        while (cursor.moveToNext()) {
            resultMap.put("date", cursor.getString(COL_DATE));
            resultMap.put("text", cursor.getString(COL_RULE));
            ruleList.add(resultMap);
        }
        return ruleList;
    }

    public String[] queryAutocomplete(String searchString) {
        Cursor cursor = myDataBase.rawQuery("select * from cards", null);
        List<String> result = new ArrayList<String>();
        int COL_NAME = cursor.getColumnIndex("name");
        Log.v("Queried Rows ", Integer.toString(cursor.getCount()));
        while (cursor.moveToNext()) {
            result.add(cursor.getString(COL_NAME));
        }
        return result.toArray(new String[result.size()]);
    }


}