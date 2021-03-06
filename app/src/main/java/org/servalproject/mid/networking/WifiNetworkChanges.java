package org.servalproject.mid.networking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import org.servalproject.mid.Serval;

public class WifiNetworkChanges extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		WifiClient wifiClient = Networks.getInstance().wifiClient;

		if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
			wifiClient.onStateChanged(intent);
		}
	}
}
