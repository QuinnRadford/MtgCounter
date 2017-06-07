package tech.fluff.mtgcounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class TwoPlayerReversed extends AppCompatActivity {
    int vibelength = 65;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_reversed);
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
            String LifeTotal = extras.getString("EXTRA_LIFE");
            TextView lifeText = (TextView) findViewById(R.id.lifeText);
            lifeText.setText(LifeTotal);
            TextView lifeText2 = (TextView) findViewById(R.id.lifeText2);
            lifeText2.setText(LifeTotal);
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
    }

    public void minusLife(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 1));
    }

    public void add3Life(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 2));
    }

    public void minus3Life(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 2));
    }

    public void addEnergy(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 5));
    }

    public void minusEnergy(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 5));
    }

    public void addStorm(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 1));
    }

    public void minusStorm(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 1));
    }

    public void addPoison(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.poisonText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 1));
    }

    public void minusPoison(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.poisonText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 1));
    }

    public void addLife2(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText2);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 1));
    }

    public void minusLife2(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText2);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 1));
    }

    public void add3Life2(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText2);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 2));
    }

    public void minus3Life2(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText2);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 2));
    }

    public void addEnergy2(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText2);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 5));
    }

    public void minusEnergy2(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText2);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 5));
    }


}
