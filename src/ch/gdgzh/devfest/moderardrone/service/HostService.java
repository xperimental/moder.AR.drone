package ch.gdgzh.devfest.moderardrone.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import ch.gdgzh.devfest.moderardrone.AppConstants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;

public class HostService extends Service {

    private static final int BUFFER_SIZE = 8192;
    private static final String TAG = "HostService";

    private HostServiceBinder binder;
    private ServerThread hostServer;
    private BeaconThread beaconServer;
    private HostServiceStatus status;

    public HostServiceStatus getStatus() {
        return status;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        binder = new HostServiceBinder();

        try {
            hostServer = new ServerThread();
            beaconServer = new BeaconThread(hostServer.getAddress(), hostServer.getPort(), hostServer.getBroadcast());

            hostServer.start();
            beaconServer.start();
            status = HostServiceStatus.LOBBY;
        } catch (IOException e) {
            status = HostServiceStatus.ERROR;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public static void bind(Context context, HostServiceConnection serviceConnection) {
        Intent intent = new Intent(context, HostService.class);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public class HostServiceBinder extends Binder {

        public HostService getService() {
            return HostService.this;
        }

    }

    private class ServerThread extends Thread {

        private ServerSocket serverSocket;

        private final int serverPort = AppConstants.SERVER_PORT;
        private InetAddress serverAddress;
        private InetAddress broadcastAddress;

        public InetAddress getAddress() {
            return serverAddress;
        }

        public InetAddress getBroadcast() {
            return broadcastAddress;
        }

        public int getPort() {
            return serverPort;
        }

        private ServerThread() throws IOException {
            super("HostThread");

            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            boolean addressFound = false;
            while (interfaces.hasMoreElements() && !addressFound) {
                NetworkInterface intf = interfaces.nextElement();
                for (InterfaceAddress address : intf.getInterfaceAddresses()) {
                    if (address.getAddress().getHostAddress().startsWith("192.168.")) {
                        serverAddress = address.getAddress();
                        broadcastAddress = address.getBroadcast();
                        addressFound = true;
                    }
                }
            }
            if (!addressFound) {
                Log.e(TAG, "No address found!");
                throw new IOException("No address found!");
            } else {
                this.serverSocket = new ServerSocket(serverPort);
            }
        }
    }

    private class BeaconThread extends Thread {

        private static final long BEACON_TIME = 1000;

        private final InetAddress serverAddress;
        private final int serverPort;
        private final InetAddress broadcastAddress;
        private DatagramSocket beaconSocket;

        private BeaconThread(InetAddress serverAddress, int serverPort, InetAddress broadcastAddress) throws IOException {
            super("BeaconThread");
            this.serverAddress = serverAddress;
            this.serverPort = serverPort;
            this.broadcastAddress = broadcastAddress;

            this.beaconSocket = new DatagramSocket(AppConstants.SERVER_PORT);
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                byte[] beaconData = getBeaconData();
                DatagramPacket packet = new DatagramPacket(beaconData, beaconData.length, broadcastAddress, AppConstants.SERVER_PORT);
                try {
                    beaconSocket.send(packet);
                    Thread.sleep(BEACON_TIME);
                } catch (InterruptedException ignored) {
                } catch (IOException e) {
                    Log.e(TAG, "Error sending beacon: " + e.getMessage());
                }
            }
        }

        private byte[] getBeaconData() {
            StringBuilder sb = new StringBuilder();
            sb.append(serverAddress.getHostAddress());
            sb.append(';');
            sb.append(serverPort);
            return sb.toString().getBytes(Charset.forName("ASCII"));
        }
    }

}
