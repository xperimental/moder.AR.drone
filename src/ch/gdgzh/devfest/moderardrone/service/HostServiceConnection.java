package ch.gdgzh.devfest.moderardrone.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class HostServiceConnection implements ServiceConnection {

    private HostService service;

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        HostService.HostServiceBinder hostBinder = (HostService.HostServiceBinder) iBinder;
        service = hostBinder.getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        service = null;
    }

}
