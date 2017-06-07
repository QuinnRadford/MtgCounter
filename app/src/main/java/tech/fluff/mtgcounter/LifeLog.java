package tech.fluff.mtgcounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LifeLog extends AppCompatActivity {
    int vibelength = 65;
    private DataBaseLoader myDbHelper = new DataBaseLoader(this);
    private LinearLayout player1view;
    private LinearLayout player2view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataBaseLoader myDbHelper = new DataBaseLoader(getApplicationContext());
        if (checkFirstLaunch()) {
            boolean success = true;
            try {

                myDbHelper.createDataBase();

            } catch (IOException ioe) {
                success = false;
                throw new Error("Unable to create database");

            }
        }
        try {

            myDbHelper.openDataBase();

        } catch (SQLException sqle) {
            throw sqle;
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log);
        player1view = (LinearLayout) findViewById(R.id.playerOneLife);
        player2view = (LinearLayout) findViewById(R.id.playerTwoLife);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String life1 = extras.getString("START_LIFE_ONE", "null");
            String life2 = extras.getString("START_LIFE_TWO", "null");

            String extra = extras.getString("GAME_ID");
            String[] gameIDs = extra.split(",");
            doSearch(gameIDs[0], player1view, life1);
            if (!gameIDs[1].equals("null") && !life2.equals("null")) {
                doSearch(gameIDs[1], player2view, life2);
            }
        }
    }

    public boolean checkFirstLaunch() {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int currentVersion = 0;
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            currentVersion = pInfo.versionCode;
        } catch (final PackageManager.NameNotFoundException e) {
        }

        final int lastVersion = prefs.getInt("lastVersion", -1);
        if (currentVersion > lastVersion) {

            prefs.edit().putInt("lastVersion", currentVersion).apply();
            Log.v("UPDATE STATUS: ", "NEW");
            return true;
        } else {
            Log.v("UPDATE STATUS: ", "OLD");
            return false;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.rules, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.back:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void doSearch(String gameID, LinearLayout layoutview, String Startingtotal) {
        Context context = layoutview.getContext();
        myDbHelper.openDataBase();
        String[] results = myDbHelper.listLife(Integer.valueOf(gameID));
        layoutview.removeAllViews();
        layoutview.addView(addResult(new TextView(context), "0", Integer.valueOf(Startingtotal)));
        int newTotal = Integer.valueOf(Startingtotal);
        for (String result : results) {
            newTotal = newTotal + Integer.valueOf(result);
            layoutview.addView(addResult(new TextView(context), result, newTotal));
        }


    }


    private TextView addResult(TextView view, String text, int total) {
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        textParams.leftMargin = 8;
        textParams.rightMargin = 8;
        textParams.topMargin = 8;
        textParams.bottomMargin = 8;
        view.setLayoutParams(textParams);
        if (Integer.valueOf(text) >= 0) {
            view.setTextColor(0xff42f47a);
            text = "+" + text;
        } else {
            view.setTextColor(0xfff44141);
        }
        text = String.format("%3s", text) + " : " + String.format("%-3s", String.valueOf(total));
        view.setText(text);
        view.setTextSize(20);
        view.setTypeface(Typeface.MONOSPACE);
        return view;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDbHelper.close();
    }
}