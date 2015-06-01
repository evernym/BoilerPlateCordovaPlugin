package com.plugin.boilerplate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;


import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author awysocki
 */

public class BoilerPlate extends CordovaPlugin {
	public static final String TAG = "PushPlugin";

	public static final String REGISTER = "register";
	public static final String PUSHMESSAGE = "pushMessage";

	private static CordovaWebView gWebView;
	private static String gReceiveMessage;
    private static boolean gForeground = false;

	/**
	 * Gets the application context from cordova's main activity.
	 * @return the application context
	 */
	private Context getApplicationContext() {
		return this.cordova.getActivity().getApplicationContext();
	}

	@Override
	public boolean execute(String action, JSONArray data, CallbackContext callbackContext) {

		boolean result = false;

		Log.v(TAG, "execute: action=" + action);

		if (REGISTER.equals(action)) {

			Log.v(TAG, "execute: data=" + data.toString());

			try {
				JSONObject jo = data.getJSONObject(0);

				gWebView = this.webView;
				Log.v(TAG, "execute: jo=" + jo.toString());

				gReceiveMessage = (String) jo.get("receiveMessage");
				result = true;
				callbackContext.success();
			} catch (JSONException e) {
				Log.e(TAG, "execute: Got JSON Exception " + e.getMessage());
				result = false;
				callbackContext.error(e.getMessage());
			}

		} else if (PUSHMESSAGE.equals(action)) {

			Log.v(TAG, "PUSHMESSAGE");
			result = true;
			callbackContext.success();
		}

		return result;
	}

	/*
	 * Sends a json object to the client as parameter to a method which is defined in gReceiveMessage.
	 */
	public static void sendJavascript(JSONObject _json) {
		String _d = "javascript:" + gReceiveMessage + "(" + _json.toString() + ")";
		Log.v(TAG, "sendJavascript: " + _d);

		if (gReceiveMessage != null && gWebView != null) {
			gWebView.sendJavascript(_d);
		}
	}


    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        gForeground = true;
    }

	@Override
    public void onPause(boolean multitasking) {
        super.onPause(multitasking);
        gForeground = false;
    }

    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        gForeground = true;
    }
    
   

    @Override
    public void onDestroy() {
        super.onDestroy();
        gForeground = false;
		gReceiveMessage = null;
		gWebView = null;
    }

    public static boolean isInForeground()
    {
      return gForeground;
    }

    public static boolean isActive()
    {
    	return gWebView != null;
    }
}
