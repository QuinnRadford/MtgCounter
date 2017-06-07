package tech.fluff.mtgcounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class OnePlayer extends AppCompatActivity {

    SwipeRefreshLayout mSwipeRefreshLayout;
    private int vibelength = 65;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_player);
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

    public void seeRules(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        Intent intent = new Intent(getBaseContext(), CardsAndRulings.class);
        startActivity(intent);
    }



    public void rollDice(View view) {
        TextView rollText = (TextView) view.getRootView().findViewById(R.id.rollDice);
        Random r = new Random();
        rollText.setText(String.valueOf(r.nextInt(20) + 1));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

    public void addLife(View view) {
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 1));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

    public void minusLife(View view) {
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 1));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

    public void addLife10(View view) {
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 10));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

    public void minusLife10(View view) {
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 10));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

    public void add3Life(View view) {
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 3));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

    public void minus3Life(View view) {
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 3));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

    public void add2Life(View view) {
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 2));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

    public void minus2Life(View view) {
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 2));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

    public void addEnergy(View view) {
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 5));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

    public void minusEnergy(View view) {
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.lifeText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 5));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

    public void addPoison(View view) {
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.poisonText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 1));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

    public void minusPoison(View view) {
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.poisonText);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 1));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

    public void plusCounter1(View view) {
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.plusCounter1);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 1));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

    public void minusCounter1(View view) {
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.plusCounter1);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 1));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

    public void plusCounter2(View view) {
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.plusCounter2);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 1));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

    public void minusCounter2(View view) {
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.plusCounter2);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 1));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

    public void plusCounter3(View view) {
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.plusCounter3);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 1));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

    public void minusCounter3(View view) {
        TextView lifeText = (TextView) view.getRootView().findViewById(R.id.plusCounter3);
        String lifeValue = lifeText.getText().toString();
        lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 1));
        ButtonPress.vibe(getBaseContext(), vibelength);
    }

}
