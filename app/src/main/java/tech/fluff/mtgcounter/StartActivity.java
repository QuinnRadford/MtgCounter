package tech.fluff.mtgcounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class StartActivity extends AppCompatActivity {
    int vibelength = 65;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
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

    public void onePlayerStart(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        Intent intent = new Intent(getBaseContext(), LifeTotal.class);
        intent.putExtra("EXTRA_PLAYERS", "1");
        startActivity(intent);
    }

    public void twoPlayerStart(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        Intent intent = new Intent(getBaseContext(), LifeTotal.class);
        intent.putExtra("EXTRA_PLAYERS", "2");
        startActivity(intent);
    }

    public void threePlayerStart(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        Intent intent = new Intent(getBaseContext(), LifeTotal.class);
        intent.putExtra("EXTRA_PLAYERS", "3");
        startActivity(intent);
    }

    public void fourPlayerStart(View view) {
        ButtonPress.vibe(getBaseContext(), vibelength);
        Intent intent = new Intent(getBaseContext(), LifeTotal.class);
        intent.putExtra("EXTRA_PLAYERS", "4");
        startActivity(intent);
    }
}
