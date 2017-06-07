package tech.fluff.mtgcounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class TwoPlayer extends AppCompatActivity {
    int vibelength = 65;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private DataBaseLoader myDbHelper = new DataBaseLoader(this);
    private String gameID = "";
    private String gameID1 = "";
    private String gameID2 = "";
    private String LifeTotal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DateFormat df = new SimpleDateFormat("MMddHHmmss");
        gameID = df.format(Calendar.getInstance().getTime());
        gameID1 = gameID;
        gameID2 = String.valueOf(Integer.valueOf(gameID) + 1);
        Log.v("gameID: ", gameID);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player);
        Bundle extras = getIntent().getExtras();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                refreshLayout();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        if (extras != null) {
            LifeTotal = extras.getString("EXTRA_LIFE");
            TextView lifeText = (TextView) findViewById(R.id.lifeText);
            lifeText.setText(LifeTotal);
            TextView lifeText2 = (TextView) findViewById(R.id.lifeText2);
            lifeText2.setText(LifeTotal);
        }
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
        inflater.inflate(R.menu.main_options, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.rulings:
                startActivity(new Intent(this, CardsAndRulings.class));
                return true;
            case R.id.log:
                Intent intent = new Intent(getBaseContext(), LifeLog.class);
                intent.putExtra("GAME_ID", gameID1 + "," + gameID2);
                intent.putExtra("START_LIFE_ONE", LifeTotal);
                intent.putExtra("START_LIFE_TWO", LifeTotal);
                startActivity(intent);
                return true;
            case R.id.reset:
                refreshLayout();
                return true;
            case R.id.home:
                new AlertDialog.Builder(this)
                        .setTitle("Exit Game")
                        .setMessage("Do you really want to exit?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void refreshLayout() {
        new AlertDialog.Builder(this)
                .setTitle("Reset Counters")
                .setMessage("Do you really want to reset?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        recreate();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void goHome(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Exit Game")
                .setMessage("Do you really want to exit?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();

    }

    public void rollDice(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView rollText = (TextView) view.getRootView().findViewById(R.id.rollDice);
        Random r = new Random();
        rollText.setText(String.valueOf(r.nextInt(20) + 1));
    }

    public void addLife(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 1));
        int idval = Integer.valueOf(gameID1);
        long newRow = myDbHelper.addLife(1, idval);
    }

    public void minusLife(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 1));
        int idval = Integer.valueOf(gameID1);
        long newRow = myDbHelper.addLife(-1, idval);
    }

    public void add3Life(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 2));
        int idval = Integer.valueOf(gameID1);
        long newRow = myDbHelper.addLife(2, idval);
    }

    public void minus3Life(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 2));
        int idval = Integer.valueOf(gameID1);
        long newRow = myDbHelper.addLife(-2, idval);
    }

    public void addEnergy(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 5));
        int idval = Integer.valueOf(gameID1);
        long newRow = myDbHelper.addLife(5, idval);
    }

    public void minusEnergy(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 5));
        int idval = Integer.valueOf(gameID1);
        long newRow = myDbHelper.addLife(-5, idval);
    }


    public void addLife2(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText2);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 1));
        int idval = Integer.valueOf(gameID2);
        long newRow = myDbHelper.addLife(1, idval);
    }

    public void minusLife2(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText2);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 1));
        int idval = Integer.valueOf(gameID2);
        long newRow = myDbHelper.addLife(-1, idval);
    }


    public void add3Life2(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText2);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 2));
        int idval = Integer.valueOf(gameID2);
        long newRow = myDbHelper.addLife(2, idval);
    }

    public void minus3Life2(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText2);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 2));
        int idval = Integer.valueOf(gameID2);
        long newRow = myDbHelper.addLife(-2, idval);
    }

    public void addEnergy2(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText2);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 5));
        int idval = Integer.valueOf(gameID2);
        long newRow = myDbHelper.addLife(5, idval);
    }

    public void minusEnergy2(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText2);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 5));
        int idval = Integer.valueOf(gameID2);
        long newRow = myDbHelper.addLife(-5, idval);
    }


}
