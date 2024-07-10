package com.example.inventura;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SecondActivity extends AppCompatActivity {

    private ArrayList<String> dataList1;
    private ArrayList<String> dataList2;
    private CustomArrayAdapter adapter1;
    private CustomArrayAdapter adapter2;
    private DatabaseHelper dbHelper;
    private Set<String> highlightSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        ListView listView1 = findViewById(R.id.listView);
        ListView listView2 = findViewById(R.id.listView1);

        dbHelper = new DatabaseHelper(this);

        dataList1 = dbHelper.getAllItems(DatabaseHelper.TABLE_DATA_SET1);
        dataList2 = dbHelper.getAllItems(DatabaseHelper.TABLE_DATA_SET2);

        highlightSet = new HashSet<>();

        // Find items that match in both lists
        for (String item : dataList1) {
            if (dataList2.contains(item)) {
                highlightSet.add(item);
            }
        }

        adapter1 = new CustomArrayAdapter(this, dataList1, highlightSet);
        listView1.setAdapter(adapter1);

        adapter2 = new CustomArrayAdapter(this, dataList2, highlightSet);
        listView2.setAdapter(adapter2);

        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteConfirmationDialog(dataList1, adapter1, position, DatabaseHelper.TABLE_DATA_SET1);
                return true;
            }
        });

        listView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteConfirmationDialog(dataList2, adapter2, position, DatabaseHelper.TABLE_DATA_SET2);
                return true;
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

    private void showDeleteConfirmationDialog(ArrayList<String> dataList, CustomArrayAdapter adapter, int position, String dataSetTable) {
        new AlertDialog.Builder(this)
                .setTitle("Potvrda brisanja")
                .setMessage("Jeste li sigurni da želite obrisati ovu stavku?")
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String removedItem = dataList.remove(position);
                        adapter.notifyDataSetChanged();

                        dbHelper.deleteItem(dataSetTable, removedItem);

                        highlightSet.remove(removedItem);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Ne", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}