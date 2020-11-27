package com.zyq.learnjwt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.org.apache.bcel.internal.generic.RET;
import com.zyq.learnjwt.entity.User;
import org.apache.catalina.AccessLog;

import java.util.Calendar;
import java.util.Map;

public class JWTUtils {
    private static final String SIGN = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAISwJMOEDQr1eCE5SJYPh2LDFr6t1FgK5XyLTagI/YBRvIrpTqvvkjma+ToXM/7LzxPcepKUC8m+BGtIvOXfwiKrgeLMzsH0nIqRSQS0K76+EyOCH/cGsSdXdLcgzlsHUGTxoLmJIsju4y7HaMKUBWzfSYfspn0KLGoa1weIHOJJAgMBAAECgYAGA12HJsLYjktFZi0t78I47ZJoTcRjwQqpXD7GsLGcURf0S6JvFEpSJf3JbifDEkVuJv2/CsqF+ACHRCKbYEqR6p/foUhYSwOQ97HQpCYfWHvTMdwrivXTpvtJP1gpdCxVA3OxgtJilpzJvKzfTCu6hDA1tzadcBSh5ajvai8mCQJBAOCB/FreSjykCsZaF2tO7aib6/puxkrYrwOBVbasW47BYp+OCi8ipj/Pc3LE52OnqmOHH9MvvwTmIkFBFX11dgcCQQCXTO/Tr9uWif+ZC4PI4ggyU49N0tul1XyRacsJILjvLopa+fM6omfmH5i59AUpjfhGxdzbjBAEwFXJ05GULFEvAkAKeqa/yw5mBDYoif372URIkXaTSuZgifqpTDkm/KaNZENpNSUOjCx8X/+AhOZUBXhzitQWdG0TGnbQ2uvY3kmzAkAmk8Faki5FFe6L0TnK0IcSfee+ChNjFynp0bWpICTHJ3CzfaOcwagILuo8D5H5ZG93bB/CAtZav8ef7EfkJeCVAkAJ6XJT7kMmwKIPyue0/gSovVn+2Qh3jHC3NqSIpu0H+XT3bHSkuNdzZTpgm8zKCunlQGqqg2bdUkxkaa+CSW/6";

    /**
     * 生成token
     * @param map 传入的payload
     * @return 返回token
     */
    public static String getToken(Map<String,String> map){
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v) -> {
            builder.withClaim(k,v);
        });
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 3600);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(SIGN)).toString();
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public static DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }
}
