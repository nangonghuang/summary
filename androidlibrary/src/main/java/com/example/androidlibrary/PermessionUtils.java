package com.example.androidlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class PermessionUtils {

    private boolean isPermissionGranted(Context context, String permission) {
        int checkSelfPermission = ActivityCompat.checkSelfPermission(context, permission);
        return checkSelfPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(Activity activity, String permission, int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            Toast.makeText(activity, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
    }

    private void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        boolean showToast = false;
        for (String permis : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permis)) {
                showToast = true;
            }
        }
        if (showToast) {
            Toast.makeText(activity, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
        }
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        if (requestCode == PERMISSION_REQUEST) {
//            String permission = permissions[0];
//            if (permission.equals(Manifest.permission.READ_PHONE_STATE)) {
//                int grantResult = grantResults[0];
//                if (grantResult == PackageManager.PERMISSION_GRANTED) {
//                    androidUdid = AppUtils.getAPPID(this);
//                } else {
//                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
//                        Toast.makeText(this, "权限已被禁止", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        }
//    }
}
