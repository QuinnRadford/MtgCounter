package tech.fluff.mtgcounter;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class ThreePlayer extends AppCompatActivity {
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_player);

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
    }

    public void rollDice(View view) {
        TextView rollText = (TextView) view.getRootView().findViewById(R.id.rollDice);
        Random r = new Random();
        rollText.setText(String.valueOf(r.nextInt(20) + 1));
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
}
