package com.example.wangjiankang.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wangjiankang on 2016/3/14.
 */
public class PackageAdapter extends ArrayAdapter<Package> {

    private int resourceId;

    public PackageAdapter(Context context, int textViewResourceId, List<Package> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Package p = getItem(position);
        View view;
        ViewHolder holder;
        if(convertView != null) {
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        } else {
            view =  LayoutInflater.from(getContext()).inflate(resourceId, null);
            holder = new ViewHolder();
            holder.package_icon = (ImageView) view.findViewById(R.id.package_icon);
            holder.package_name = (TextView) view.findViewById(R.id.package_name);
            holder.application_name = (TextView) view.findViewById(R.id.application_name);
            view.setTag(holder);
        }
        holder.package_icon.setImageDrawable(p.getIcon());
        holder.package_name.setText(p.getPackageName());
        holder.application_name.setText(p.getApplicationName());
        return view;
    }

    class ViewHolder {
        public ImageView package_icon;
        public TextView package_name;
        public TextView application_name;
    }
}
