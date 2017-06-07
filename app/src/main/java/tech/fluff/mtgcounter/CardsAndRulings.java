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

public class CardsAndRulings extends AppCompatActivity {
    int vibelength = 65;
    private DataBaseLoader myDbHelper = new DataBaseLoader(this);

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

        String[] CARDS = myDbHelper.queryAutocomplete("*");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rules);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, CARDS);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        textView.setAdapter(adapter);
        textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                }
                AutoCompleteTextView cardtext = (AutoCompleteTextView) v.getRootView().findViewById(R.id.autoComplete);
                doSearch(cardtext.getText().toString(), v);
                cardtext.setText("");
                return false;
            }
        });

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
    public void searchRules(View view) {
        AutoCompleteTextView cardtext = (AutoCompleteTextView) view.getRootView().findViewById(R.id.autoComplete);
        View viewFocus = this.getCurrentFocus();
        if (viewFocus != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(viewFocus.getWindowToken(), 0);
        }

        String cardToSearch = cardtext.getText().toString();
        Log.v("Now Searching: ", cardToSearch);
        doSearch(cardToSearch, view);
        cardtext.setText("");
    }

    private void doSearch(String card, View view) {
        Context context = view.getContext();
        myDbHelper.openDataBase();
        Card resultCard = myDbHelper.queryCard(card);
        Map<String, String> resultcard = resultCard.getCard();
        ArrayList<Map<String, String>> resultrule = resultCard.getRules();
        String cardText = resultcard.get("text");
        String cardType = resultcard.get("type");
        LinearLayout resultLayout = (LinearLayout) view.getRootView().findViewById(R.id.resultsList);
        resultLayout.removeAllViews();


        //card names
        TextView cardNameView = new TextView(context);
        cardNameView.setTypeface(null, Typeface.BOLD);
        cardNameView.setTextSize(20);
        resultLayout.addView(addResult(cardNameView, card));

        View titlespacer = new View(context);
        LinearLayout.LayoutParams spacerTitleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        spacerTitleParams.setMargins(0, 8, 0, 8);
        titlespacer.setLayoutParams(spacerTitleParams);
        titlespacer.getLayoutParams().height = 5;
        resultLayout.addView(titlespacer);


        //card types
        TextView cardTypeView = new TextView(context);
        cardTypeView.setTypeface(null, Typeface.ITALIC);
        resultLayout.addView(addResult(cardTypeView, filterResponse(cardType)));

        //card text
        TextView cardTextView = new TextView(context);
        resultLayout.addView(addResult(cardTextView, filterResponse(cardText)));
        TextView cardRuleTitleView = new TextView(context);

        View cardSpacer = new View(context);
        cardSpacer.setLayoutParams(spacerTitleParams);
        cardSpacer.getLayoutParams().height = 5;
        resultLayout.addView(cardSpacer);

        cardRuleTitleView.setTypeface(null, Typeface.BOLD);
        cardRuleTitleView.setTextSize(20);
        resultLayout.addView(addResult(cardRuleTitleView, "Card Rulings"));
        //card rules
        if (!resultrule.isEmpty()) {
            //title


            for (Map<String, String> rule : resultrule) {
                LinearLayout ruleContainer = new LinearLayout(context);
                LinearLayout.LayoutParams contParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                contParams.setMargins(0, 10, 0, 10);
                ruleContainer.setOrientation(LinearLayout.VERTICAL);
                ruleContainer.setPadding(15, 15, 15, 15);
                ruleContainer.setBackgroundColor(0xFFffffff);
                ruleContainer.setLayoutParams(contParams);
                ruleContainer.setElevation(2);
                resultLayout.addView(ruleContainer);

                TextView cardRuleDateView = new TextView(context);
                cardRuleDateView.setTypeface(null, Typeface.BOLD_ITALIC);
                ruleContainer.addView(addResult(cardRuleDateView, rule.get("date")));

                TextView cardRuleTextView = new TextView(context);
                ruleContainer.addView(addResult(cardRuleTextView, rule.get("text")));


            }
        } else {
            LinearLayout ruleContainer = new LinearLayout(context);
            LinearLayout.LayoutParams contParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            contParams.setMargins(0, 10, 0, 10);
            ruleContainer.setOrientation(LinearLayout.VERTICAL);
            ruleContainer.setPadding(20, 20, 20, 20);
            ruleContainer.setBackgroundColor(0xFFffffff);
            ruleContainer.setLayoutParams(contParams);
            ruleContainer.setElevation(2);
            resultLayout.addView(ruleContainer);

            TextView cardRuleDateView = new TextView(context);
            cardRuleDateView.setTypeface(null, Typeface.BOLD);
            ruleContainer.addView(addResult(cardRuleDateView, "No Rulings Found."));

        }


    }

    private String filterResponse(String res) {
        Pattern pt = Pattern.compile("[^a-zA-Z0-9\\s\\.\\,\\+\\-\\/\\\\]");
        Matcher match = pt.matcher(res);
        while (match.find()) {
            String s = match.group();
            res = res.replaceAll("\\" + s, "");
        }
        return res;
    }

    private View addResult(TextView view, String text) {
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        textParams.leftMargin = 8;
        textParams.rightMargin = 8;
        textParams.topMargin = 8;
        textParams.bottomMargin = 8;
        view.setLayoutParams(textParams);
        view.setText(text);
        return view;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataBaseLoader myDbHelper = new DataBaseLoader(getApplicationContext());
        myDbHelper.close();
    }
}