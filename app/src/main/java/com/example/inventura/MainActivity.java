package com.example.inventura;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private EditText editText1, editText2, editText3;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);

        sharedPreferences = getSharedPreferences("MojSharedPreferences", MODE_PRIVATE);

        Button dodaj = findViewById(R.id.gumb_dodaj);
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = editText1.getText().toString().trim().toUpperCase(Locale.getDefault());
                String text2 = editText2.getText().toString().trim().toUpperCase(Locale.getDefault());
                String text3 = editText3.getText().toString().trim().toUpperCase(Locale.getDefault());

                // Kombiniranje tekstova iz sva tri EditText-a u jedan string
                String combinedText = text1 + " - " + text2 + " - " + text3;

                // Dohvaćanje trenutnog skupa podataka
                Set<String> dataSet = sharedPreferences.getStringSet("dataSet1", new HashSet<>());

                // Provjera postojanja elementa u listi
                boolean containsItem = false;
                for (String item : dataSet) {
                    if (item.toUpperCase(Locale.getDefault()).equals(combinedText)) {
                        containsItem = true;
                        break;
                    }
                }

                if (containsItem) {
                    Snackbar.make(v, "Stavka već postoji", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(getResources().getColor(android.R.color.holo_red_light)).show();
                    return; // Ako element već postoji, prekidamo izvršavanje i ne dodajemo ga u listu
                }

                // Spremanje ažuriranog skupa podataka
                dataSet.add(combinedText);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("dataSet1", dataSet);

                // Spremanje količine pod ključem 'text1'
                editor.putInt(text1, Integer.parseInt(text2));
                editor.apply();

                Snackbar.make(v, "Stavka je uspješno dodana", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(getResources().getColor(android.R.color.holo_green_light)).show();
            }
        });

        Button pregled = findViewById(R.id.gumb_pregled);
        pregled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        Button inventura = findViewById(R.id.gumb_inventura);
        inventura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, InventuraActivity.class);
                startActivity(intent1);
            }
        });
    }
}