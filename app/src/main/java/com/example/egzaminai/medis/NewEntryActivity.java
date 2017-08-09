package com.example.egzaminai.medis;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;



public class NewEntryActivity extends Activity {

    private Button add_button, update_button, delete_button;

    Spinner driver, circuit, time;
    RadioGroup team, team_chief;
    RadioButton team_ferrari, team_red_bull, team_mercedes, team_williams, team_toro_rosso, team_chief_maurizio_arrivabene, team_chief_christian_horner, team_chief_toto_wolff, team_chief_frank_williams, team_chief_franz_tost;
    Registracija driverEntry;
    VartotojoRegistracija vartotojas;

    private static final String DELETE_URL = "http://trainreaction.byethost16.com/mobile/delete.php";
    private static final String ADD_URL = "http://trainreaction.byethost16.com/mobile/new_post1.php";
    private static final String UPDATE_URL = "http://trainreaction.byethost16.com/mobile/update.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        Intent intent = getIntent();
        driverEntry = (Registracija) intent.getSerializableExtra(AdapterRegistracija.ENTRY);

        vartotojas = new VartotojoRegistracija(getApplicationContext());

        if (driverEntry.getId().equals("-1")) { // new entry

        } else { // existing entry

        }

        Toast.makeText(this, driverEntry.getLenktynininkas() + " " + driverEntry.getId(), Toast.LENGTH_SHORT).show();

        driver = (Spinner) findViewById(R.id.new_entry_driver_id);
        circuit = (Spinner) findViewById(R.id.new_entry_circuit_id);
        time = (Spinner) findViewById(R.id.new_entry_time_id);

        this.add_button = (Button) findViewById(R.id.new_entry_add_button_id);
        this.update_button = (Button) findViewById(R.id.new_entry_update_button_id);
        this.delete_button = (Button) findViewById(R.id.new_entry_delete_button_id);

        if (vartotojas.getUsernameForLogin().equalsIgnoreCase("administratorius")) {//adminas - enable all buttons
            this.add_button.setEnabled(true);
            this.update_button.setEnabled(true);
            this.delete_button.setEnabled(true);
        } else { //user - disable all
            if (driverEntry.getId().equals("-1")) { // new entry
                this.add_button.setEnabled(true);
                this.update_button.setEnabled(false);
                this.delete_button.setEnabled(false);
            } else { // existing entry
                this.add_button.setEnabled(false);
                this.update_button.setEnabled(false);
                this.delete_button.setEnabled(false);
            }
        }

        team = (RadioGroup) findViewById(R.id.new_entry_teamRadioGroup);

        team_ferrari = (RadioButton) findViewById(R.id.new_entry_team_ferrari_id);
        team_red_bull = (RadioButton) findViewById(R.id.new_entry_team_red_bull_id);
        team_mercedes = (RadioButton) findViewById(R.id.new_entry_team_mercedes_id);
        team_williams = (RadioButton) findViewById(R.id.new_entry_team_williams_id);
        team_toro_rosso = (RadioButton) findViewById(R.id.new_entry_team_toro_rosso_id);

        if(driverEntry.getKomandos().equals(getApplicationContext().getResources().getString(R.string.new_entry_ferrari))){
            team_ferrari.setChecked(true);
        } else if (driverEntry.getKomandos().equals(getApplicationContext().getResources().getString(R.string.new_entry_red_bull))){
            team_red_bull.setChecked(true);
        } else if (driverEntry.getKomandos().equals(getApplicationContext().getResources().getString(R.string.new_entry_mercedes))){
            team_mercedes.setChecked(true);
        } else if (driverEntry.getKomandos().equals(getApplicationContext().getResources().getString(R.string.new_entry_williams))){
            team_williams.setChecked(true);
        } else if (driverEntry.getKomandos().equals(getApplicationContext().getResources().getString(R.string.new_entry_toro_rosso))){
            team_toro_rosso.setChecked(true);
        }

        team_chief = (RadioGroup) findViewById(R.id.new_entry_team_chiefRadioGroup);
        team_chief_maurizio_arrivabene = (RadioButton) findViewById(R.id.new_entry_team_chief_maurizio_arrivabene_id);
        team_chief_christian_horner = (RadioButton) findViewById(R.id.new_entry_team_chief_christian_horner_id);
        team_chief_toto_wolff = (RadioButton) findViewById(R.id.new_entry_team_chief_toto_wolff_id);
        team_chief_frank_williams = (RadioButton) findViewById(R.id.new_entry_team_chief_frank_williams_id);
        team_chief_franz_tost = (RadioButton) findViewById(R.id.new_entry_team_chief_franz_tost_id);

        if(driverEntry.getTreneris().equals(getApplicationContext().getResources().getString(R.string.new_entry_maurizio_arrivabene))){
            team_chief_maurizio_arrivabene.setChecked(true);
        } else if (driverEntry.getTreneris().equals(getApplicationContext().getResources().getString(R.string.new_entry_christian_horner))){
            team_chief_christian_horner.setChecked(true);
        } else if (driverEntry.getTreneris().equals(getApplicationContext().getResources().getString(R.string.new_entry_toto_wolff))){
            team_chief_toto_wolff.setChecked(true);
        } else if (driverEntry.getTreneris().equals(getApplicationContext().getResources().getString(R.string.new_entry_frank_williams))){
            team_chief_frank_williams.setChecked(true);
        } else if (driverEntry.getTreneris().equals(getApplicationContext().getResources().getString(R.string.new_entry_franz_tost))){
            team_chief_franz_tost.setChecked(true);
        }

        List<String> listDrivers = new ArrayList<String>();
        listDrivers.add(getApplicationContext().getResources().getString(R.string.new_entry_sebastian_vettel));
        listDrivers.add(getApplicationContext().getResources().getString(R.string.new_entry_lewis_hamilton));
        listDrivers.add(getApplicationContext().getResources().getString(R.string.new_entry_felipe_massa));
        listDrivers.add(getApplicationContext().getResources().getString(R.string.new_entry_carlos_sainz));
        listDrivers.add(getApplicationContext().getResources().getString(R.string.new_entry_kimi_raikkonen));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listDrivers);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driver.setAdapter(dataAdapter);
        if (!driverEntry.getLenktynininkas().equals(null)) {
            int spinnerPosition = dataAdapter.getPosition(driverEntry.getLenktynininkas());
            driver.setSelection(spinnerPosition);
        }


        List<String> listCircuit = new ArrayList<String>();
        listCircuit.add(getApplicationContext().getResources().getString(R.string.new_entry_monte_carlo_monaco));
        listCircuit.add(getApplicationContext().getResources().getString(R.string.new_entry_spa_belgium));
        listCircuit.add(getApplicationContext().getResources().getString(R.string.new_entry_sepang_malaysia));
        listCircuit.add(getApplicationContext().getResources().getString(R.string.new_entry_melbourne_australia));
        listCircuit.add(getApplicationContext().getResources().getString(R.string.new_entry_montreal_canada));

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listCircuit);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        circuit.setAdapter(dataAdapter2);
        if (!driverEntry.getTrasa().equals(null)) {
            int spinnerPosition = dataAdapter2.getPosition(driverEntry.getTrasa());
            circuit.setSelection(spinnerPosition);
        }


        List<String> listTime = new ArrayList<String>();
        listTime.add(getApplicationContext().getResources().getString(R.string.new_entry_1));
        listTime.add(getApplicationContext().getResources().getString(R.string.new_entry_2));
        listTime.add(getApplicationContext().getResources().getString(R.string.new_entry_3));
        listTime.add(getApplicationContext().getResources().getString(R.string.new_entry_4));
        listTime.add(getApplicationContext().getResources().getString(R.string.new_entry_5));
        listTime.add(getApplicationContext().getResources().getString(R.string.new_entry_6));
        listTime.add(getApplicationContext().getResources().getString(R.string.new_entry_7));
        listTime.add(getApplicationContext().getResources().getString(R.string.new_entry_8));
        listTime.add(getApplicationContext().getResources().getString(R.string.new_entry_9));
        listTime.add(getApplicationContext().getResources().getString(R.string.new_entry_10));

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listTime);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time.setAdapter(dataAdapter3);
        if (!driverEntry.getLaikas().equals(null)) {
            int spinnerPosition = dataAdapter3.getPosition(driverEntry.getLaikas());
            time.setSelection(spinnerPosition);
        }

        addButton();
        deleteButton();
        updateButton();
    }

    public void deleteButton() {
        delete_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                delete(driverEntry.getId());
            }
        });
    }

    public void updateButton() {
        update_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //(String id, String vartotojas, String driver, String circuit, String time, String team, String team_chief)
                int selectedTeamId = team.getCheckedRadioButtonId();
                team_ferrari = (RadioButton) findViewById(selectedTeamId);

                int selectedTeamCheafId = team_chief.getCheckedRadioButtonId();
                team_chief_maurizio_arrivabene = (RadioButton) findViewById(selectedTeamCheafId);

                //String id, String data, String vartotojas, String lenktynininkas, String trasa, String laikas, String komandos, String treneris
                Registracija driver = new Registracija(driverEntry.getId(), driverEntry.getData(), vartotojas.getUsernameForLogin(), String.valueOf(NewEntryActivity.this.driver.getSelectedItem()), String.valueOf(circuit.getSelectedItem()),
                        String.valueOf(time.getSelectedItem()), team_ferrari.getText().toString(), team_chief_maurizio_arrivabene.getText().toString());

                update(driver.getId(), driver.getData(), driver.getVartotojas(), driver.getLenktynininkas(), driver.getTrasa(), driver.getLaikas(),
                        driver.getKomandos(), driver.getTreneris());
            }
        });
    }

    public void addButton() {


        add_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean cancel = false;
                boolean show = true;

                if (show) {
                    int selectedTeamId = team.getCheckedRadioButtonId();
                    team_ferrari = (RadioButton) findViewById(selectedTeamId);

                    int selectedTeamCheafId = team_chief.getCheckedRadioButtonId();
                    team_chief_maurizio_arrivabene = (RadioButton) findViewById(selectedTeamCheafId);

                    //public Registracija(String vartotojas, String lenktynininkas, String trasa, String laikas, String komandos, String treneris) {
                    Registracija driver = new Registracija(vartotojas.getUsernameForLogin(), String.valueOf(NewEntryActivity.this.driver.getSelectedItem()), String.valueOf(circuit.getSelectedItem()),
                            String.valueOf(time.getSelectedItem()), team_ferrari.getText().toString(), team_chief_maurizio_arrivabene.getText().toString());

                    add(driver.getVartotojas(), driver.getLenktynininkas(), driver.getTrasa(), driver.getLaikas(), driver.getKomandos(), driver.getTreneris());

                    /*Toast.makeText(NewEntryActivity.this,
                            team_ferrari.getText().toString() + "\n" + String.valueOf(driver.getSelectedItem()),
                            Toast.LENGTH_LONG).show();*/
                }
            }
        });
    }

    private void add(String vartotojas, String driver, String circuit, String time, String team, String team_chief) {

        class NewEntry extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            DB database = new DB();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(NewEntryActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(NewEntryActivity.this, SearchActivity.class);
                startActivity(intent);
            }

            @Override
            protected String doInBackground(String... params) {
                // Pirmas string raktas, antras string reiksme
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("driver", params[0]);
                data.put("circuit", params[1]);
                data.put("time", params[2]);
                data.put("team", params[3]);
                data.put("team_chief", params[4]);
                data.put("vartotojas", params[5]);

                String result = database.sendPostRequest(ADD_URL, data);

                return result;
            }
        }

        NewEntry ru = new NewEntry();
        ru.execute(driver, circuit, time, team, team_chief, vartotojas);
    }

    private void delete(String id) {

        class DeleteEntry extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            DB database = new DB();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(NewEntryActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(NewEntryActivity.this, SearchActivity.class);
                startActivity(intent);
            }

            @Override
            protected String doInBackground(String... params) {
                // Pirmas string raktas, antras string reiksme
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("id", params[0]);

                String result = database.sendPostRequest(DELETE_URL, data);

                return result;
            }
        }

        DeleteEntry deleteEntry = new DeleteEntry();
        deleteEntry.execute(id);
    }
//driver.getId(), driver.getData(), driver.getVartotojas(), driver.getLenktynininkas(), driver.getTrasa(), driver.getLaikas(), driver.getKomandos(), driver.getTreneris()
    private void update(String id, String data, String vartotojas, String driver, String circuit, String time, String team, String team_chief) {

        class UpdateEntry extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            DB database = new DB();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(NewEntryActivity.this, "Please Wait", null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(NewEntryActivity.this, SearchActivity.class);
                startActivity(intent);
            }

            @Override
            protected String doInBackground(String... params) {
                // Pirmas string raktas, antras string reiksme
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("id", params[0]);
                data.put("data", params[1]);
                data.put("vartotojas", params[2]);
                data.put("driver", params[3]);
                data.put("circuit", params[4]);
                data.put("time", params[5]);
                data.put("team", params[6]);
                data.put("team_chief", params[7]);


                String result = database.sendPostRequest(UPDATE_URL, data);

                return result;
            }
        }

        UpdateEntry updateEntry = new UpdateEntry();
        Toast.makeText(getApplicationContext(), id + "\n" + driver  + "\n" + circuit +  "\n" + time  + "\n" + team  + "\n" + team_chief + vartotojas , Toast.LENGTH_LONG).show();
        //String id, String data, String vartotojas, String driver, String circuit, String time, String team, String team_chief
        updateEntry.execute(id, data, vartotojas, driver, circuit, time, team, team_chief);
    }
}

