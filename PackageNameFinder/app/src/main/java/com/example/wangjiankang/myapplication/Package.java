package com.example.wangjiankang.myapplication;

import android.graphics.drawable.Drawable;

/**
 * Created by wangjiankang on 2016/3/14.
 */
public class Package {

    private String package_name;

    private String application_name;

    private Drawable icon;

    public String getPackageName() {
        return package_name;
    }

    public String getApplicationName() {
        return application_name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setPackageName(String name) {
        this.package_name = name;
    }

    public void setApplicationName(String name) {
        this.application_name = name;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
