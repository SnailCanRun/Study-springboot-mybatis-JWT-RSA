package com.zyq.learnjwt.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA {
    private static String rsaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVMffR9pCN5NBKKAtIFGf630RTrji4CBERUJY8ucbXhjmA5Ro5X5wTtyrnR1Q3g/C5gvNT/aSf/8L3CgWowuuTalMx97d16hcRKSHBT1u15/SzXPCj9I99XHSggHZ7Ax6fHg2UwFiN/RiVyTQEiPfyclfG1/7UYGLbEEm5/b3MhQIDAQAB";
    private static String rsaPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJUx99H2kI3k0EooC0gUZ/rfRFOuOLgIERFQljy5xteGOYDlGjlfnBO3KudHVDeD8LmC81P9pJ//wvcKBajC65NqUzH3t3XqFxEpIcFPW7Xn9LNc8KP0j31cdKCAdnsDHp8eDZTAWI39GJXJNASI9/JyV8bX/tRgYtsQSbn9vcyFAgMBAAECgYA3YpJWDotBMaUlMlJl0rV7CzEpQ27o2o3DeMAdEPZBAiIMuIAQtm6RXxj9PJyF82QE6hj8oBdiEidFYCITsTCUOZ+UjGku+oJw11lVI5D91Ax+FwSMQtvz1f/QHMj8xU6+Ti0eVeECEml22ldao514PU7oLR6SeiVCfEejwEc4oQJBAN/OP5g/AJF54SVNZw12VWN9xrMySr4OTLmQHOacYJASFS1oCehV8t8Q4eAYEvRkoszis5qbHzMt6TO0Qk0HjS0CQQCqqCcp/OZm+7TvuCW4UxGxjLxpkjQ46XsdUG5MHHvB9jQsMEZtUdnOM4wC+JlcV6vrdiIAorsSdiqn8FlwbkO5AkEA3WiKa5CwfDsKLMAVWjEeKo4su+zeGoXFC7LxlIdR1FW/U6tgWdRlsJ5gBzVp0xOYJ3+DVUlvJU+fLIKBMPluhQJATSc6lYwFqldvxNTvELdVj3y+DciQnY5JH8amJvjVpqolijjFDlVr8AJz8T/tLcdhLVG6K6lmhAEyZbyO4vqm2QJBAJxK8eBTbUYkYkDtV+IuiY61ful24+SYu9QZetbbY3zINn7Fv74QbLIyYRbO6cO4MkX26LuY3F16GrMCnU6ES5M=";

    public static void initKey() {
        //1.初始化密钥
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);//64的整数倍
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            rsaPublicKey = Base64.encodeBase64String(keyPair.getPublic().getEncoded());
            rsaPrivateKey = Base64.encodeBase64String(keyPair.getPrivate().getEncoded());
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    /**
     * 公钥加密
     * @param src
     * @return
     * @throws Exception
     */
    public static String pubEncrypt(String src) throws Exception{
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(rsaPublicKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] result = cipher.doFinal(src.getBytes());
        return Base64.encodeBase64String(result);
    }

    /**
     * 公钥解密
     * @param cryptograph
     * @return
     * @throws Exception
     */
    public static String pubDecrypt(String cryptograph) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(rsaPublicKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,publicKey);

        byte[] result = Base64.decodeBase64(cryptograph);
        result = cipher.doFinal(result);
        return new String(result);
    }

    /**
     * 私钥加密
     * @param src
     * @return
     * @throws Exception
     */
    public static String priEncrypt(String src) throws Exception{
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(rsaPrivateKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        byte[] result = cipher.doFinal(src.getBytes());
        return Base64.encodeBase64String(result);
    }

    /**
     * 私钥解密
     * @param cryptograph
     * @return
     * @throws Exception
     */
    public static String priDecrypt(String cryptograph) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(rsaPrivateKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,privateKey);

        byte[] result = Base64.decodeBase64(cryptograph);
        result = cipher.doFinal(result);
        return new String(result);
    }


//    public static void main(String[] args) throws Exception{
//        RSA.initKey();
//        String msg = "我是加密信息";
//        String encryt = RSA.pubEncrypt(msg);
//        String msg1 = RSA.priDecrypt(encryt);
//    }
}
