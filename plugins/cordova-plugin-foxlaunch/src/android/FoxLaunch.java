package com.foxai.foxlaunch;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

public class FoxLaunch extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext cb) throws JSONException {
        String pkg = args.getString(0);
        Context ctx = cordova.getActivity().getApplicationContext();
        PackageManager pm = ctx.getPackageManager();

        if (action.equals("isInstalled")) {
            boolean installed = false;
            try { pm.getPackageInfo(pkg, 0); installed = true; } catch (Exception e) { }
            cb.success(installed ? 1 : 0);
            return true;
        }

        if (action.equals("launch")) {
            Intent i = pm.getLaunchIntentForPackage(pkg);
            if (i == null) { cb.error("not_installed"); return true; }
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(i);
            cb.success();
            return true;
        }

        if (action.equals("openStore")) {
            try {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + pkg));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(i);
            } catch (Exception e) {
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + pkg));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(i);
            }
            cb.success();
            return true;
        }

        return false;
    }
}
