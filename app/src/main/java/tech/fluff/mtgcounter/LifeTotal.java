package tech.fluff.mtgcounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

public class LifeTotal extends AppCompatActivity {
    private String players = null;
    private CheckBox mirrormode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_total);
        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            players = extras.getString("EXTRA_PLAYERS");

            if (players.equals("2")) {
                mirrormode = (CheckBox) findViewById(R.id.mirrorMode);
                mirrormode.setVisibility(View.VISIBLE);
            }
            Log.w("Players", players);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.front_options, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.gohome:
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void twenty(View view) {
        ButtonPress.vibe(getBaseContext(), 65);
        Intent intent;
        mirrormode = (CheckBox) view.getRootView().findViewById(R.id.mirrorMode);
        Log.v("Checked?", String.valueOf(mirrormode.isChecked()));
        switch (players) {
            case "1":
                intent = new Intent(getBaseContext(), OnePlayer.class);
                intent.putExtra("EXTRA_LIFE", "20");
                startActivity(intent);
                break;
            case "2":
                if (mirrormode.isChecked()) {
                    intent = new Intent(getBaseContext(), TwoPlayerReversed.class);
                } else {
                    intent = new Intent(getBaseContext(), TwoPlayer.class);
                }
                intent.putExtra("EXTRA_LIFE", "20");
                startActivity(intent);
                break;
            case "3":
                intent = new Intent(getBaseContext(), ThreePlayer.class);
                intent.putExtra("EXTRA_LIFE", "20");
                startActivity(intent);
                break;
            case "4":
                intent = new Intent(getBaseContext(), FourPlayer.class);
                intent.putExtra("EXTRA_LIFE", "20");
                startActivity(intent);
                break;
        }


    }

    public void thirty(View view) {
        ButtonPress.vibe(getBaseContext(), 65);
        mirrormode = (CheckBox) view.getRootView().findViewById(R.id.mirrorMode);
        Intent intent;
        Log.v("Checked?", String.valueOf(mirrormode.isChecked()));
        switch (players) {
            case "1":
                intent = new Intent(getBaseContext(), OnePlayer.class);
                intent.putExtra("EXTRA_LIFE", "30");
                startActivity(intent);
                break;
            case "2":
                if (mirrormode.isChecked()) {
                    intent = new Intent(getBaseContext(), TwoPlayerReversed.class);
                } else {
                    intent = new Intent(getBaseContext(), TwoPlayer.class);
                }
                intent.putExtra("EXTRA_LIFE", "30");
                startActivity(intent);
                break;
            case "3":
                intent = new Intent(getBaseContext(), ThreePlayer.class);
                intent.putExtra("EXTRA_LIFE", "30");
                startActivity(intent);
                break;
            case "4":
                intent = new Intent(getBaseContext(), FourPlayer.class);
                intent.putExtra("EXTRA_LIFE", "30");
                startActivity(intent);
                break;
        }
    }

    public void fourty(View view) {
        ButtonPress.vibe(getBaseContext(), 65);
        mirrormode = (CheckBox) view.getRootView().findViewById(R.id.mirrorMode);
        Log.v("Checked?", String.valueOf(mirrormode.isChecked()));
        Intent intent;
        switch (players) {
            case "1":
                intent = new Intent(getBaseContext(), OnePlayer.class);
                intent.putExtra("EXTRA_LIFE", "40");
                startActivity(intent);
                break;
            case "2":
                if (mirrormode.isChecked()) {
                    intent = new Intent(getBaseContext(), TwoPlayerReversed.class);
                } else {
                    intent = new Intent(getBaseContext(), TwoPlayer.class);
                }
                intent.putExtra("EXTRA_LIFE", "40");
                startActivity(intent);
                break;
            case "3":
                intent = new Intent(getBaseContext(), FourPlayer.class);
                intent.putExtra("EXTRA_LIFE", "40");
                startActivity(intent);
                break;
            case "4":
                intent = new Intent(getBaseContext(), FourPlayer.class);
                intent.putExtra("EXTRA_LIFE", "40");
                startActivity(intent);
                break;
        }
    }
}

