package com.example.electricmeterreader;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterElements extends ArrayAdapter<Object> {
    private Activity context;

    public AdapterElements(Activity context, Element [] elements) {
        super(context, R.layout.items, elements);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.items, null);
        }
        Element element = (Element) getItem(position);
        TextView tvSsid = (TextView) convertView.findViewById(R.id.tvSSID);
        tvSsid.setText(element.getTitle());

        TextView tvSecurity = (TextView)convertView.findViewById(R.id.tvSecurity);
        tvSecurity.setText(element.getSecurity());

        TextView tvLevel = (TextView)convertView.findViewById(R.id.tvLevel);
        int i = element.getLevel();
        if (i>-50){
            tvLevel.setText("High");
        } else if (i<=-50 && i>-80){
            tvLevel.setText("Middle");
        } else if (i<=-80){
            tvLevel.setText("Low");
        }
        return convertView;
    }
}
