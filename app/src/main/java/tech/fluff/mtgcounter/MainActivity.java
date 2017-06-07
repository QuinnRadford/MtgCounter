package tech.fluff.mtgcounter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.SQLException;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Bundle extras = getIntent().getExtras();


    }

    public void lifeCounter(View view) {
        ButtonPress.vibe(getBaseContext(), 65);
        Intent intent;
        intent = new Intent(getBaseContext(), StartActivity.class);
        startActivity(intent);
    }

    public void rulePage(View view) {
        ButtonPress.vibe(getBaseContext(), 65);
        Intent intent;
        intent = new Intent(getBaseContext(), CardsAndRulings.class);
        startActivity(intent);
    }
}

