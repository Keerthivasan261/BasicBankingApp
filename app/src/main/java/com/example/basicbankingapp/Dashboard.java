package com.example.basicbankingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class Dashboard extends AppCompatActivity {

    //variables
    private CardView allUsers,allTransactions;
    private AllUsersDatabaseHelper db;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();



        //hooks
        allUsers = findViewById(R.id.userCardView);
        allTransactions = findViewById(R.id.transacCardView);
        db = new AllUsersDatabaseHelper(this);


        allUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(Dashboard.this,AllUsers.class);
                startActivity(intent);

            }
        });

        allTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,ViewAllTransactions.class);
                startActivity(intent);

            }
        });


//         Inserting Dummy Data
        if(sharedPreferences.getBoolean("isDataInserted",false) == false)
        {

            editor.putBoolean("isDataInserted",true);
            editor.apply();

            String[] names = {"Keerthi", "Vikram", "John", "Harish", "Micheal", "Adhiti", "Peter", "Suresh", "Naresh", "Harish"};
            String[] emails = {"Keerthi@gmail.com", "Vikram@gmail.com", "John@gmail.com", "Harish@gmail.com", "Micheal@gmail.com", "Adhiti@gmail.com", "Peter@gmail.com", "Suresh@gmail.com", "Naresh@gmail.com", "Harish@gmail.com"};
            String[] gender = {"Male","Male","Male","Male","Male","Female","Male","Male","Male","Male"};
            String[] currentBalance = {"20000","50000","100000","30000","50000","60000","80000","120000","45000","65000"};

            for(int i=0;i<names.length;i++){
               InsertIntoDataBase task = new InsertIntoDataBase();
               task.execute(names[i],emails[i],gender[i],currentBalance[i]);
            }


        }



    }//OnCreate Ends


    //AsyncTask for Inserting data in the Database
    class InsertIntoDataBase extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {

            boolean isInserted = db.insertToUserInfo(strings[0],strings[1],strings[2],strings[3]);
            return "Insertion Completed " + isInserted;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("MyTag",s);
        }

    }


}