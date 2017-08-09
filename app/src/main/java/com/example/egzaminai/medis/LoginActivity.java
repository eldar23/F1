package com.example.egzaminai.medis;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends Activity {

    private Button login_button, register_button;
    private EditText login_text, password_text;

    private CheckBox rememberMeCheckBox;

    private static final String REGISTER_URL = "http://trainreaction.byethost16.com/mobile/LoginConfirmation.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //kreipimasis i paveldimos klases metoda
        setContentView(R.layout.activity_login); //susiejame xml su kodu


        RegisterButton();
        Authentication();

    }


    public void RegisterButton() {
        register_button = (Button) findViewById(R.id.login_register_button_id);

        register_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                //myIntent.putExtra("key", value); //Optional parameters
                LoginActivity.this.startActivity(myIntent);

            }

        });
    }

    public void Authentication() {

        login_button = (Button)findViewById(R.id.login_login_button_id);
        login_text = (EditText)findViewById(R.id.login_username_id);
        password_text = (EditText)findViewById(R.id.login_password_id);
        rememberMeCheckBox = (CheckBox) findViewById(R.id.remember_me_id);

        final VartotojoRegistracija dude = new VartotojoRegistracija(getApplicationContext());

        rememberMeCheckBox.setChecked(dude.isRemembered());

        if (dude.isRemembered()) {
            login_text.setText(dude.getUsernameForLogin(), TextView.BufferType.EDITABLE);
            password_text.setText(dude.getPasswordForLogin(), TextView.BufferType.EDITABLE);
        }else {
            login_text.setText("", TextView.BufferType.EDITABLE);
            password_text.setText("", TextView.BufferType.EDITABLE);
        }


        login_text.setError(null);
        password_text.setError(null);



        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = login_text.getText().toString();
                String password = password_text.getText().toString();

               /* Toast.makeText(getApplicationContext(),
                        "username:"+username+"\n"+
                        "password:"+password, Toast.LENGTH_SHORT).show();*/

                boolean cancel = false;
                View focusView = null;

                if (!IsValid(username)) {

                    login_text.setError(getString(R.string.login_invalid_username));
                    focusView = login_text;
                    cancel = true;
                }

                if (!IsValid(password)) {
                    password_text.setError(getString(R.string.login_invalid_password));
                    focusView = password_text;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {

                    if (rememberMeCheckBox.isChecked()){
                        dude.setUsernameLogin(username);
                        dude.setPasswordForLogin(password);
                        dude.setRemembered(true);
                    }else {
                        dude.setUsernameLogin(username);
                        dude.setPasswordForLogin(password);
                        dude.setRemembered(false);
                    }
                    postToDatabase(dude);
                   /* if (login_text.getText().toString().equals("admin") &&
                        password_text.getText().toString().equals("yourmom")) {
                    Toast.makeText(getApplicationContext(),
                            "Palauk...", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(LoginActivity.this, MeniuActivity.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    LoginActivity.this.startActivity(myIntent);
                }else{
                    Toast.makeText(getApplicationContext(), "Neteisingai", Toast.LENGTH_SHORT).show();
                }*/

                   /* Intent myIntent = new Intent(LoginActivity.this, MeniuActivity.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    LoginActivity.this.startActivity(myIntent);*/
                }



            }


        });
    }

    private boolean IsValid(String credentials) {
        final String CREDENTIALS_PATTERN = "^([0-9a-zA-Z]{3,15})+$";
        Pattern pattern = Pattern.compile(CREDENTIALS_PATTERN);

        Matcher matcher = pattern.matcher (credentials);
        return matcher.matches();
    }

    private void postToDatabase (final VartotojoRegistracija dude){
        class NewEntry extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            DB database = new DB();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                if (s.equals("202")){
                    Intent myIntent = new Intent(LoginActivity.this, SearchActivity.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    LoginActivity.this.startActivity(myIntent);
                    Toast.makeText(getApplicationContext(),"paejo " + s,Toast.LENGTH_LONG).show();

                }else if (s.equals("203")){
                    Toast.makeText(getApplicationContext(),"Tokio vartotojo vardo su slaptažodžiu nėra",Toast.LENGTH_LONG).show();
                }
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                // Pirmas string raktas, antras string reiksme
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("username",params[0]);
                data.put("password",params[1]);



                String result = database.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }



        NewEntry ru = new NewEntry();
        /*ru.execute(type,String.valueOf(order),gender,String.valueOf(price),color,String.valueOf(length),
                String.valueOf(width),delivery);*/
        ru.execute(dude.getUsernameForLogin(), dude.getPasswordForLogin());

    }

}
