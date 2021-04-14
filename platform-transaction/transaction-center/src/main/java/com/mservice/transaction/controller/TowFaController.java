package com.mservice.transaction.controller;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.ICredentialRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: wejam
 * @Description 二次验证
 * @Date: 2021/3/9 下午5:10
 */
@Slf4j
public class TowFaController {

    private static final String SECRET_KEY = "WP56BJUY6CNMBJRLTV5QYC2C7NOJWA5D";
    private static final String NAME_SECRET_KEY = "OI5OW74VJYEY5XW4OJQOAA7KJHDZ3VVM";

    private static final String KEY_FORMAT = "otpauth://totp/%s?secret=%s";
    private static final GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();

//    public static void main(String[] args) {
//
//        //key WP56BJUY6CNMBJRLTV5QYC2C7NOJWA5D
////        GoogleAuthenticator gAuth = new GoogleAuthenticator();
////        final GoogleAuthenticatorKey gKey = gAuth.createCredentials();
////        String key = gKey.getKey();
////        System.out.println("key:"+key);
//
//        //secret valid
////        GoogleAuthenticator gAuth = new GoogleAuthenticator();
////        int code = gAuth.getTotpPassword(SECRET_KEY);
////        System.out.println("code:"+code);
////
////        boolean isCodeValid = gAuth.authorize(SECRET_KEY, code);
////
////        System.out.println("result:"+isCodeValid);
//
//
//        googleAuthenticator.setCredentialRepository(new ICredentialRepository() {
//            @Override
//            public String getSecretKey(String userName) {
//
//                return "USCWFXIRZDO4KDGF5NGPMDEJKPZBQNLH";
//            }
//
//            @Override
//            public void saveUserCredentials(String s, String s1, int i, List<Integer> list) {
//                return;
//            }
//
//        });
////
////        String wwj = getQrUrl("wwj");
////        System.out.println(wwj);
//
//        boolean wwj = validCode("wwj", 588979);
//        System.out.println(wwj);
//
//    }



    /**
     * 生成二维码链接
     */
    private static String getQrUrl(String username) {
        //每次调用createCredentials都会生成新的secretKey
        GoogleAuthenticatorKey key = googleAuthenticator.createCredentials(username);
        log.info("username={},secretKey={}", username, key.getKey());
        return String.format(KEY_FORMAT, username, key.getKey());
    }

    // 验证动态密码  username 帐号, code  app上的6位数字
    public static boolean validCode(String username, int code) {
        return googleAuthenticator.authorizeUser(username, code);
    }
}
