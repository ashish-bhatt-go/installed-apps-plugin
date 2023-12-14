package com.installed.apps.plugin;

import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Process;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InstalledApps {
    ObjectMapper objectMapper = new ObjectMapper();
    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getInstalledApps(String value, Context context) {
        List<InstalledAppsInfo> installedAppsInfos = new ArrayList<>();

        Object launcherApps = context.getSystemService(Context.LAUNCHER_APPS_SERVICE);

        // Get the list of launcher activity info
        List<LauncherActivityInfo> activityList = ((LauncherApps)launcherApps).getActivityList(null, Process.myUserHandle());
        for (LauncherActivityInfo it : activityList) {
            InstalledAppsInfo installedAppsInfo = new InstalledAppsInfo();

            installedAppsInfo.packageName = it.getComponentName().getPackageName();
            installedAppsInfo.encodedIcon = drawableToBase64EncodedString(it.getIcon(0));
            installedAppsInfos.add(installedAppsInfo);
        }
        try {
            return objectMapper.writeValueAsString(installedAppsInfos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Bitmap getBitmapAppIcon(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof AdaptiveIconDrawable) {
            Drawable backgroundDr = ((AdaptiveIconDrawable) drawable).getBackground();
            Drawable foregroundDr = ((AdaptiveIconDrawable) drawable).getForeground();

            Drawable[] drr = new Drawable[2];
            drr[0] = backgroundDr;
            drr[1] = foregroundDr;

            LayerDrawable layerDrawable = new LayerDrawable(drr);

            int width = layerDrawable.getIntrinsicWidth();
            int height = layerDrawable.getIntrinsicHeight();

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);

            layerDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            layerDrawable.draw(canvas);

            return bitmap;
        }
        return null;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String drawableToBase64EncodedString(Drawable drawable) {
        Bitmap bitmap = getBitmapAppIcon(drawable);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, bos);
        byte[] bitMapData = bos.toByteArray();
        return Base64.encodeToString(bitMapData, Base64.NO_WRAP);
    }

    static class InstalledAppsInfo {
        public String packageName;
        public String encodedIcon;
    }
}
