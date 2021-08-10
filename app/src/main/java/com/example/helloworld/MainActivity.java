package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
    public void onRegisterBtnClick (View view){
        TextView textViewName = findViewById(R.id.textViewName);
        textViewName.setTextColor(getResources().getColor(R.color.my_purple));
        EditText edtTxtName = findViewById(R.id.edtTxtName);
        textViewName.setText( edtTxtName.getText().toString());

        TextView textViewLastName = findViewById(R.id.textViewLastName);
        textViewName.setTextColor(getResources().getColor(R.color.my_purple));
        EditText edtTxtLastName = findViewById(R.id.edtTxtLastName);
        textViewLastName.setText( edtTxtLastName.getText().toString());

        TextView textViewEmail = findViewById(R.id.textViewEmail);
        textViewName.setTextColor(getResources().getColor(R.color.my_purple));
        EditText edtEmail = findViewById(R.id.edtEmail);
        textViewEmail.setText( edtEmail.getText().toString());
    }


}