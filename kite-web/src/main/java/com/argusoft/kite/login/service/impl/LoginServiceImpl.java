package com.argusoft.kite.login.service.impl;

import com.argusoft.kite.login.dao.LastAccessTokenDao;
import com.argusoft.kite.login.model.LastAccessTokenDetailEntity;
import com.argusoft.kite.login.service.LoginService;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.User;
import java.io.IOException;
import java.util.List;
import javax.transaction.Transactional;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author kvithlani
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private KiteConnect kiteSdk;

    @Autowired
    private LastAccessTokenDao lastAccessTokenDao;

    @Override
    public void setRequestToken(String requestToken, String apiSecret) throws KiteException, JSONException, IOException {
        // Get accessToken as follows,
        User userModel = kiteSdk.generateSession(requestToken, apiSecret);
        System.out.println("userModel :: " + userModel);
        // Set request token and public t oken which are obtained from login process.
        kiteSdk.setAccessToken(userModel.accessToken);
        kiteSdk.setPublicToken(userModel.publicToken);

        List<LastAccessTokenDetailEntity> lastAccessTokenDetailEntitys = lastAccessTokenDao.retrieveAll();
        LastAccessTokenDetailEntity accessTokenDetailEntity = null;
        if (!CollectionUtils.isEmpty(lastAccessTokenDetailEntitys)) {
            accessTokenDetailEntity = lastAccessTokenDetailEntitys.get(0);
        } else {
            accessTokenDetailEntity = new LastAccessTokenDetailEntity();
        }
        accessTokenDetailEntity.setAccessToken(kiteSdk.getAccessToken());
        accessTokenDetailEntity.setPublicToken(kiteSdk.getPublicToken());
        accessTokenDetailEntity.setApiSecret(apiSecret);
        accessTokenDetailEntity.setRequestToken(requestToken);
        accessTokenDetailEntity.setUserId(kiteSdk.getUserId());
        lastAccessTokenDao.createOrUpdate(accessTokenDetailEntity);

    }

    @Override
    public void fillKiteSdkFromDB() throws KiteException, JSONException {
        List<LastAccessTokenDetailEntity> lastAccessTokenDetailEntitys = lastAccessTokenDao.retrieveAll();
        LastAccessTokenDetailEntity accessTokenDetailEntity = null;
        if (!CollectionUtils.isEmpty(lastAccessTokenDetailEntitys)) {
            accessTokenDetailEntity = lastAccessTokenDetailEntitys.get(0);
            kiteSdk.setAccessToken(accessTokenDetailEntity.getAccessToken());
            kiteSdk.setPublicToken(accessTokenDetailEntity.getPublicToken());
            kiteSdk.setUserId(accessTokenDetailEntity.getUserId());
        }
    }

}
