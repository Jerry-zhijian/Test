package c.b.s.common.util;

import c.b.s.common.util.constant.BoxinConstant;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 加密
 * Created by guiqingqing on 2018/8/14.
 */
public class SaaSEncrypt {
    private static final String MD5_ALGORITHM = "MD5";
    private static final String CHARSET = "UTF-8";
    private static final String RSA_ALGORITHM = "RSA";

    /**
     * 生成密钥对
     * @param keySize
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static Map<String, String> createKeys(int keySize) throws NoSuchAlgorithmException {
        // 为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator keyPairGenerator;
        keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        // 初始化KeyPairGenerator对象, 密钥长度
        keyPairGenerator.initialize(keySize);
        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64String(publicKey.getEncoded());
        // 得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64String(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);
        return keyPairMap;
    }

    /**
     * 得到公钥
     * @param publicKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
        return rsaPublicKey;
    }

    /**
     * 得到私钥
     * @param privateKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        return rsaPrivateKey;
    }

    /**
     * 公钥加密
     * @param data
     * @param rsaPublicKey
     * @return
     * @throws Exception
     */
    public static String publicEncrypt(String data, RSAPublicKey rsaPublicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), rsaPublicKey.getModulus().bitLength()));
    }

    /**
     * 私钥解密
     * @param data
     * @param rsaPrivateKey
     * @return
     * @throws Exception
     */
    public static String privateDecrypt(String data, RSAPrivateKey rsaPrivateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), rsaPrivateKey.getModulus().bitLength()), CHARSET);
    }

    /**
     * 私钥加密
     * @param data
     * @param rsaPrivateKey
     * @return
     * @throws Exception
     */
    public static String privateEncrypt(String data, RSAPrivateKey rsaPrivateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
        return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), rsaPrivateKey.getModulus().bitLength()));
    }

    /**
     * 公钥解密
     * @param data
     * @param rsaPublicKey
     * @return
     * @throws Exception
     */
    public static String publicDecrypt(String data, RSAPublicKey rsaPublicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
        return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), rsaPublicKey.getModulus().bitLength()), CHARSET);
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] data, int keySize) throws Exception {
        int maxBlock = opmode == Cipher.DECRYPT_MODE ? keySize / 8 : keySize / 8 - 11;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        while (data.length > offSet) {
            if (data.length - offSet > maxBlock) {
                buff = cipher.doFinal(data, offSet, maxBlock);
            } else {
                buff = cipher.doFinal(data, offSet, data.length - offSet);
            }
            out.write(buff, 0, buff.length);
            i++;
            offSet = i * maxBlock;
        }
        byte[] resultDatas = out.toByteArray();
        out.close();
        return resultDatas;
    }

    /**
     * 采用Base64+MD5的方式给密码加密
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encrypt(String data) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance(MD5_ALGORITHM);
        md5.update(new BASE64Encoder().encodeBuffer(data.getBytes()).replaceAll("[\r\n]", "").getBytes());
        return new BigInteger(md5.digest()).toString(16);
    }

    public static void main(String[] args) throws Exception {
        long tenantId = 1;
        long assetId = 12;
        String code = "wmxt";
        String source = tenantId + "-" + assetId + "-" + code;
        String target = publicEncrypt(source, getPublicKey(BoxinConstant.MOBILE_PUBLICKEY));
        System.out.println("密文:\r\n" + target.replaceAll("-", "").substring(0, 32));
        System.exit(1);

        String password = "zhiniu1234";
        System.out.println(encrypt(password));

        Map<String, String> keyMap = createKeys(1024);
        String publicKey = keyMap.get("publicKey");
        String privateKey = keyMap.get("privateKey");

        System.out.println("公钥:\r\n" + publicKey);
        System.out.println("私钥:\r\n" + privateKey);

        System.out.println("公钥加密-私钥解密");
        String str = "苍苍竹林寺，杳杳钟声晚。\n" +
                "荷笠带斜阳，青山独归远。";
        System.out.println("明文:\r\n" + str);
        System.out.println("明文大小: " + str.getBytes().length);
        String encodedData = publicEncrypt(str, getPublicKey(publicKey));
        System.out.println("密文:\r\n" + encodedData);
        String decodedData = privateDecrypt(encodedData, getPrivateKey(privateKey));
        System.out.println("解密后文字:\r\n" + decodedData);
    }
}