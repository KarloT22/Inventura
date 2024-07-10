package com.example.inventura;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class InventuraActivity extends AppCompatActivity {
    private EditText editText1, editText2, editText3;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventura);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);

        dbHelper = new DatabaseHelper(this);

        Button dodaj = findViewById(R.id.gumb_dodaj);
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = editText1.getText().toString().trim().toUpperCase();
                String text2 = editText2.getText().toString().trim();
                String text3 = editText3.getText().toString().trim().toUpperCase();

                // Combine texts from all three EditTexts into one string
                String combinedText = text1 + " - " + text2 + " - " + text3;

                // Insert item into dataSet2 table with quantity
                long result = dbHelper.insertItemDataSet2(combinedText, Integer.parseInt(text2));

                // Check if insertion was successful (result is the row ID if successful, -1 if failed)
                if (result != -1) {
                    // Show success message or perform actions after successful insertion
                } else {
                    // Show error message or handle failed insertion
                }
            }
        });

        Button pregled = findViewById(R.id.gumb_pregled);
        pregled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InventuraActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        Button nazad = findViewById(R.id.gumb_nazad);
        nazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}