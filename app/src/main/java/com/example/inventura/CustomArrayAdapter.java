package com.example.inventura;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

public class CustomArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> values;
    private final Set<String> highlightSet;

    public CustomArrayAdapter(Context context, List<String> values, Set<String> highlightSet) {
        super(context, android.R.layout.simple_list_item_1, values);
        this.context = context;
        this.values = values;
        this.highlightSet = highlightSet;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        TextView textView = rowView.findViewById(android.R.id.text1);

        String item = values.get(position);
        textView.setText(item);

        if (highlightSet.contains(item)) {
            textView.setBackgroundColor(Color.YELLOW);
        } else {
            textView.setBackgroundColor(Color.TRANSPARENT);
        }

        return rowView;
    }
}