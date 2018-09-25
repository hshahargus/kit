package com.argusoft.kite.login.service;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import java.io.IOException;
import org.json.JSONException;

/**
 *
 * @author hshah
 */
public interface LoginService {

    public void setRequestToken(String requestToken, String apiSecret) throws KiteException, JSONException, IOException;

    public void fillKiteSdkFromDB() throws KiteException, JSONException;

}
