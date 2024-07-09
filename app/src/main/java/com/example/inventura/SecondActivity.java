package com.example.inventura;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SecondActivity extends AppCompatActivity {

    private ArrayList<String> dataList1;
    private ArrayList<String> dataList2;
    private CustomArrayAdapter adapter1;
    private CustomArrayAdapter adapter2;
    private SharedPreferences sharedPreferences;
    private Set<String> highlightSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Inicijalizacija ListView-a
        ListView listView1 = findViewById(R.id.listView);
        ListView listView2 = findViewById(R.id.listView1);

        sharedPreferences = getSharedPreferences("MojSharedPreferences", MODE_PRIVATE);
        dataList1 = new ArrayList<>();
        dataList2 = new ArrayList<>();
        highlightSet = new HashSet<>();

        // Dohvaćanje skupa podataka iz SharedPreferences
        Set<String> dataSet1 = sharedPreferences.getStringSet("dataSet1", new HashSet<>());
        dataList1.addAll(dataSet1);
        Collections.sort(dataList1); // Sortiranje elemenata abecedno

        Set<String> dataSet2 = sharedPreferences.getStringSet("dataSet2", new HashSet<>());
        dataList2.addAll(dataSet2);
        Collections.sort(dataList2); // Sortiranje elemenata abecedno

        // Pronalaženje stavki koje se poklapaju
        for (String item : dataList1) {
            if (dataList2.contains(item)) {
                highlightSet.add(item);
            }
        }

        // Inicijalizacija prilagođenih adaptera sa ArrayList-om i setom za highlightanje
        adapter1 = new CustomArrayAdapter(this, dataList1, highlightSet);
        listView1.setAdapter(adapter1);

        adapter2 = new CustomArrayAdapter(this, dataList2, highlightSet);
        listView2.setAdapter(adapter2);

        // Dodavanje dugog pritiska za brisanje elemenata u listView1
        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteConfirmationDialog(dataList1, adapter1, position, "dataSet1");
                return true;
            }
        });

        // Dodavanje dugog pritiska za brisanje elemenata u listView2
        listView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteConfirmationDialog(dataList2, adapter2, position, "dataSet2");
                return true;
            }
        });

        // Postavljanje listener-a za gumb "Nazad"
        Button nazad = findViewById(R.id.gumb_nazad);
        nazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Završava trenutnu aktivnost
            }
        });
    }

    private void showDeleteConfirmationDialog(ArrayList<String> dataList, CustomArrayAdapter adapter, int position, String dataSetKey) {
        new AlertDialog.Builder(this)
                .setTitle("Potvrda brisanja")
                .setMessage("Jeste li sigurni da želite obrisati ovu stavku?")
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Brisanje stavke iz liste
                        String removedItem = dataList.remove(position);
                        adapter.notifyDataSetChanged();

                        // Ažuriranje skupa podataka u SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Set<String> dataSet = sharedPreferences.getStringSet(dataSetKey, new HashSet<>());
                        if (dataSet != null) {
                            dataSet.remove(removedItem);
                            editor.putStringSet(dataSetKey, dataSet);
                        }
                        editor.apply();

                        // Ako je uklonjena stavka bila u highlightSet, također je uklonimo od tamo
                        highlightSet.remove(removedItem);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Ne", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}