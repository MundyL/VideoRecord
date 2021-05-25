package com.lm.videorecord.utils;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by amdin on 2017/11/30.
 */

public class PermissionUtils {

    public final static int PERMISSION_REQUEST_CODE = 0;

    private Activity activity;
    private String[] permissions;
    private List<String> lstReq;
    private String[] reqpermissions;

    public PermissionUtils(Activity activity) {
        this.activity = activity;
        lstReq = new ArrayList<>();
    }


    public boolean checkPermission(String[] permissions) {
        this.permissions = permissions;
        lstReq.clear();
        boolean b = true;
        for (String p : permissions) {
            boolean isP = ContextCompat.checkSelfPermission(activity, p) != PackageManager.PERMISSION_GRANTED;
            if (isP) {
                b = false;
                lstReq.add(p);
            }
        }
        if (lstReq.size() > 0) {
            reqpermissions = new String[lstReq.size()];
            for (int i = 0; i < reqpermissions.length; i++) {
                reqpermissions[i] = lstReq.get(i);
            }
        }
        return b;
    }

    public void requestPermissions() {
        ActivityCompat.requestPermissions(activity, reqpermissions, PERMISSION_REQUEST_CODE);
    }

    public boolean isAgreePermission(int[] grantResults) {
        boolean b = true;
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_GRANTED) {

            } else {
                b = false;
            }
        }
        return b;
    }


}
