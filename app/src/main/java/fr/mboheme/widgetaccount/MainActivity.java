package fr.mboheme.widgetaccount;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String PLAYLISTS = "play_list";
    private static final String PREFS_NAME = "name_accounts" ;
    private static final String PREFS_NAME_ACCCOUNT = "pref_name_account";
    private SharedPreferences savednotes;
    private String numberAccout;
    private char[][] playlists;
    private SharedPreferences.Editor prefsEditor;

    final Context context = this;
    private Button button;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkFirstRun();
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }

    private void checkFirstRun() {

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(Constant.PREF_VERSION_CODE_KEY, Constant.DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            return;

        } else if (savedVersionCode == Constant.DOESNT_EXIST) {

            // TODO This is a new install (or the user cleared the shared preferences)
            initFirstInstall();

        } else if (currentVersionCode > savedVersionCode) {

            // TODO This is an upgrade
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(Constant.PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }

    private void initFirstInstall() {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom);
        dialog.setTitle("Number accounts");

        // set the custom dialog components - text, image and button
        final EditText text = (EditText) dialog.findViewById(R.id.number_accounts);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = getSharedPreferences(Constant.KEY_PREF_NUMBER_ACCOUNT, MODE_PRIVATE).edit();
                editor.putInt(Constant.KEY_PREF_NUMBER_ACCOUNT, Integer.parseInt(text.getText().toString()));
                dialog.dismiss();

                initNameAccount();

            }
        });

        dialog.show();

    }

    private void initNameAccount() {


        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_name_account);
        dialog.setTitle("Name accounts");

        SharedPreferences prefs = getSharedPreferences(Constant.KEY_PREF_NUMBER_ACCOUNT, MODE_PRIVATE);
        int numberAccount = prefs.getInt(Constant.KEY_PREF_NUMBER_ACCOUNT, -1);

        if (numberAccount != -1) {
            for(int i = 0; i < numberAccount; i++) {
                // set the custom dialog components - text, image and button
                final EditText text = (EditText) dialog.findViewById(R.id.number_accounts);
            }

            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences.Editor editor = getSharedPreferences(Constant.KEY_PREF_NUMBER_ACCOUNT, MODE_PRIVATE).edit();
                    editor.putInt(Constant.KEY_PREF_NUMBER_ACCOUNT, Integer.parseInt(text.getText().toString()));
                    dialog.dismiss();

                    initNameAccount();

                }
            });

            dialog.show();
        }
    }

    public void addEventListener(final TextView textView){
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom);
        dialog.setTitle("Rename account");

        // set the custom dialog components - text, image and button
        final EditText text = (EditText) dialog.findViewById(R.id.number_accounts);
        text.setText("Insert name account");

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(text.getText());
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
            MainActivity.this.startActivity(myIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
