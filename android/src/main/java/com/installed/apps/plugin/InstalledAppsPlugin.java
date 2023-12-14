package com.installed.apps.plugin;

import android.os.Process;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "InstalledApps")
public class InstalledAppsPlugin extends Plugin {

    private InstalledApps implementation = new InstalledApps();

    @PluginMethod
    public void getInstalledApps(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.getInstalledApps(value, getContext()));
        call.resolve(ret);
    }
}
