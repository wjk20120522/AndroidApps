package com.example.wangjiankang.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView lv;

    private Button system, third;
    private ArrayList<Package> system_packages = new ArrayList<>();
    private ArrayList<Package> application_packages = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        system = (Button) findViewById(R.id.system_app);
        third = (Button) findViewById(R.id.third_app);
        lv = (ListView) findViewById(R.id.lv);

        final PackageManager packageManager = getPackageManager();
        List<PackageInfo> packs = packageManager.getInstalledPackages(0);
        for(int i=0; i<packs.size(); i++) {
            PackageInfo p = packs.get(i);
            ApplicationInfo appInfo = p.applicationInfo;
            Package pa = new Package();
            pa.setPackageName(appInfo.packageName);
            pa.setApplicationName(packageManager.getApplicationLabel(appInfo).toString());
            pa.setIcon(packageManager.getApplicationIcon(appInfo));
            if((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {     // system app
                system_packages.add(pa);
            } else {                            // third-party app
                application_packages.add(pa);
            }
        }

        final PackageAdapter application_adapter = new PackageAdapter(this, R.layout.package_item, application_packages);
        final PackageAdapter system_adapter = new PackageAdapter(this, R.layout.package_item, system_packages);

        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.setAdapter(application_adapter);
            }
        });

        system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.setAdapter(system_adapter);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Package p = (Package) lv.getItemAtPosition(position);
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View popupWindow = layoutInflater.inflate(R.layout.popup_window, null);
                ImageView pop_title_icon = (ImageView) popupWindow.findViewById(R.id.pop_title_icon);
                TextView pop_title_name = (TextView) popupWindow.findViewById(R.id.pop_title_name);
                Button run = (Button) popupWindow.findViewById(R.id.run);
                pop_title_icon.setImageDrawable(p.getIcon());
                pop_title_name.setText(p.getApplicationName());
                run.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent resolveIntent = packageManager.getLaunchIntentForPackage(p.getPackageName());
                        if (resolveIntent != null) {
                            startActivity(resolveIntent);
                        }
                    }
                });
                PopupWindow m_popupWindow = new PopupWindow(popupWindow, getWindowManager().getDefaultDisplay().getWidth()/2,
                        getWindowManager().getDefaultDisplay().getHeight()/4);
                m_popupWindow.setFocusable(true);
                ColorDrawable dw = new ColorDrawable(0xb0ffffff);
                m_popupWindow.setBackgroundDrawable(dw);
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.6f;
                getWindow().setAttributes(lp);
                m_popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1.0f;
                        getWindow().setAttributes(lp);
                    }
                });
                m_popupWindow.showAtLocation(findViewById(R.id.page), Gravity.CENTER, 0, 0);
            }
        });

        third.performClick();
    }


}
