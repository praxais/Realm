package com.xais.prajwal.realm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xais.prajwal.realm.model.Person;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private TextView textoutput;
    private EditText name, age;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        name = (EditText) findViewById(R.id.edtName);
        age = (EditText) findViewById(R.id.edtAge);
        textoutput = (TextView) findViewById(R.id.txtOutput);



        realm = Realm.getDefaultInstance();



    }

    public void onSave(View view) {

        String string_name = name.getText().toString().trim();
        int string_age = Integer.parseInt(age.getText().toString().trim());

        saveToDatabase(string_name, string_age);

    }

    public void onShow(View view) {
        showOutput();
    }


    private void saveToDatabase(final String string_name, final int string_age) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person person = realm.createObject(Person.class);
                person.setName(string_name);
                person.setAge(string_age);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                //on Transaction success
                Log.d("Database_DB", ">>>>>>>>>>Sucess<<<<<<<<<<<<");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                //on Transaction error
                Log.d("Database_DB", error.getMessage());
            }
        });
    }


    private void showOutput() {

        RealmResults<Person> personRealmResults = realm.where(Person.class).findAll();

        String output = "";

        for (Person person: personRealmResults){
            output += person.toString();
        }


        textoutput.setText(output);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }


}
