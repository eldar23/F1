package com.example.egzaminai.medis;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends Activity {

    // kelias byethost iki register.php failiuko
    private static final String REGISTER_URL = "http://trainreaction.byethost16.com/mobile/register.php";

    private EditText username;
    private EditText password;
    private EditText password2;
    private EditText email;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username    =   (EditText) findViewById(R.id.register_username_id);
        password    =   (EditText) findViewById(R.id.register_password_id);
        password2   =   (EditText) findViewById(R.id.register_password2_id);
        email = (EditText) findViewById(R.id.register_email_id);
        register    =   (Button) findViewById(R.id.register_register_button_id);

        username.setError(null);
        password.setError(null);
        password2.setError(null);
        email.setError(null);

        register.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                boolean cancel = false;
                View focusView = null;

                //Registracija(String regiVardas, String regiEmail, String regiSlaptazodis)


                if (TextUtils.isEmpty(username.getText().toString())) {
                    username.setError("Neįvestas vardas");
                    focusView = username;
                    cancel = true;
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setError("Neįvestas slaptažodis");
                    focusView = password;
                    cancel = true;
                }
                if (TextUtils.isEmpty(password2.getText().toString())) {
                    password2.setError("Neįvestas pakartotinis slaptažodis");
                    focusView = password2;
                    cancel = true;
                }

                if (!password.getText().toString().equals(password2.getText().toString())) {
                    password2.setError("Slaptažodžiai turi sutapti");
                    focusView = password2;
                    cancel = true;
                }

                if (!validateEmail(email.getText().toString())) {
                    email.setError("neteisingai ivestas el. paštas");
                    focusView = email;
                    cancel = true;
                }
                if (cancel) {
                    focusView.requestFocus();
                }else {
                    // public VartotojoRegistracija(String usernameRegister, String password, String email) {
                    VartotojoRegistracija naujaReg = new VartotojoRegistracija(username.getText().toString(), password.getText().toString(), email.getText().toString());
                    registerUser(naujaReg.getUsernameForRegistration(), naujaReg.getPasswordForRegistration(), naujaReg.getEmail());
                    //Toast.makeText(RegisterActivity.this, naujaReg.getUsernameForRegistration() + "\n" + naujaReg.getPasswordForRegistration() + "\n" + naujaReg.getEmail(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
            }

        });
    }

    private void registerUser(String username, String password, String email) {
        String urlSuffix = "?username="+username+"&password="+password+"&email="+email;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this, "Prašome palaukti",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    // byethost naudoja antibot sistema, todel reikia kiekvienam rankutėmis suvesti cookie turinį,
                    // kuris pas kiekviena bus skirtingas. kaip tai padaryti zemiau nuoroda
                    // http://stackoverflow.com/questions/31912000/byethost-server-passing-html-values-checking-your-browser-with-json-string
                    con.setRequestProperty("Cookie", "__test=7a4d917e220fbf9a55cab3046bd1a3b7; expires=2038 m. sausio 1 d., penktadienis 01:55:55; path=/");
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                }catch(Exception e){
                    return null;
                }
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);
    }

    public static boolean validateEmail(String emailStr) {
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
