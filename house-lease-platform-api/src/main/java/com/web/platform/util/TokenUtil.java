package com.web.platform.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.web.platform.common.Const;
import com.web.platform.pojo.CurrentLoginUser;

import java.util.Date;

/**
 * @author li
 **/
public class TokenUtil {

    public static String createLoginToken(CurrentLoginUser loginUser) {
        Date date = new Date(System.currentTimeMillis() + Const.ADMIN_EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(Const.TOKEN_SECRET);
        String jsonStr = JSON.toJSONString(loginUser);
        return JWT.create()
                .withClaim("body", jsonStr)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    public static CurrentLoginUser getPayload(String token) {
        DecodedJWT jwt = JWT.decode(token);
        String jsonStr = jwt.getClaim("body").asString();
        CurrentLoginUser userToken = JSONObject.parseObject(jsonStr, CurrentLoginUser.class);
        return userToken;
    }

    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(Const.TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            //效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }
}
