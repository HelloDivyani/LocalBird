package com.example.rssongira.localbird;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by RSSongira on 4/4/2017.
 */
public class Custom_MainList extends ArrayAdapter<String> {
    private String[] names;
    private String[] desc;
    private Integer[] imageid;
    private Activity context;

    public Custom_MainList(Activity context, String[] names, String[] desc, Integer[] imageid) {
        super(context, R.layout.custom_mainlist, names);
        this.context = context;
        this.names = names;
        this.desc = desc;
        this.imageid = imageid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.custom_mainlist, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewDesc);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);

        textViewName.setText(names[position]);
        textViewDesc.setText(desc[position]);
        image.setImageResource(imageid[position]);
        return  listViewItem;
    }
}
