package org.jymf.utils;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSAUtil {

	 public static final String KEY_ALGORTHM="RSA";
	 public static final String SIGNATURE_ALGORITHM="MD5withRSA";
	  
	 public static final String PUBLIC_KEY = "RSAPublicKey";//公钥
	 public static final String PRIVATE_KEY = "RSAPrivateKey";//私钥
	 private static int keyLength = 1024;
	 private static int maxEncryptBloc = keyLength/8-11;
	 private static int maxDecryptBloc = 128;
	 
	 public static final String publicKey =	"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCO5TBFVgid+FILri1eFuSvCBGZkryMvsph32t1"+
											"1gXL5Sgwvv2ERVxw7g9kNpDTLG/1E3NeprESFlcJR9iZnNHObkow3UDcaZMoC2z560sr4cRcsY3w"+
											"qP1v6OMohFAlz4q9RdNO/WuGTijIkRSRv7tgOPDoCCmBSTvSc0iwYvlMGQIDAQAB";
	
	 
	 public static final String privateKey ="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI7lMEVWCJ34UguuLV4W5K8IEZmS"+
											"vIy+ymHfa3XWBcvlKDC+/YRFXHDuD2Q2kNMsb/UTc16msRIWVwlH2Jmc0c5uSjDdQNxpkygLbPnr"+
											"SyvhxFyxjfCo/W/o4yiEUCXPir1F0079a4ZOKMiRFJG/u2A48OgIKYFJO9JzSLBi+UwZAgMBAAEC"+
											 "gYAKpz+gC7f8vwSu5j9vollOS1W0k2IHRDombde/I0OvXdhPOg4AjINF0BnJqN/HPqriJJ8XUWom"+
											 "JRA8GuUlHrHti1qV/bDAePwj9brODc4XoAARU4vkp+PoKGPeTbx3JAUtngUyj4LL2t7YmO+tuA5m"+
											 "fUHmpL3dxifBaMB2oN36eQJBANIIs5BTycieafQvIFswUHyFBX5whWKWjRuID2PrTdK6scJwBcJA"+
											 "svdn0mmGetJ7GTONEtoygKwmeiuTz1qjux8CQQCuKv59uAp4Dl3L26x4urC9z3YHJPQJR8LTbqhK"+
											 "D0ourPWnf1bRDK3yVDfeZdDxBGoJ4B4vF0AdXrhYX+1uyUnHAkEAn/SehAE7wHzcpmmpk6kE8v//"+
											 "gh7BhqavilMhnXAbkaw7KqBT0m+hnIfGGDWhnmC/faInxGMH2ZNoIX3tQdNF8wJANv7QeTYh6CLx"+
											 "LyvgXAbnRCg8JqlefYexVz372dbWH8LxlsA4063jRz+cnJcd0N0J9rBUF56J2jxMjWtM98yLhQJA"+
											 "HNB6hPvP30eUotTlLl48AbQPptJ/dnllBWUSU3jJG9uoLwzZBLgTbWaEL5twyrj5QOevOh551vKw"+
											 "+eW9hVlAZg==";
	 
	 /**
	     * 初始化密钥
	     * @return
	     * @throws Exception
	     */
	    public static Map<String,Object> initKey()throws Exception{
	        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORTHM);
	        keyPairGenerator.initialize(keyLength);
	        KeyPair keyPair = keyPairGenerator.generateKeyPair();
	         
	        //公钥
	        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
	        //私钥
	        RSAPrivateKey privateKey =  (RSAPrivateKey) keyPair.getPrivate();
	         
	        Map<String,Object> keyMap = new HashMap<String, Object>(2);
	        keyMap.put(PUBLIC_KEY, publicKey);
	        keyMap.put(PRIVATE_KEY, privateKey);
	         
	        return keyMap;
	    }
	 
	    /**
	     * 取得公钥，并转化为String类型
	     * @param keyMap
	     * @return
	     * @throws Exception
	     */
	    public static String getPublicKey(Map<String, Object> keyMap)throws Exception{
	        Key key = (Key) keyMap.get(PUBLIC_KEY);  
	        return Coder.encryptBASE64(key.getEncoded());     
	    }
	 
	    /**
	     * 取得私钥，并转化为String类型
	     * @param keyMap
	     * @return
	     * @throws Exception
	     */
	    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception{
	        Key key = (Key) keyMap.get(PRIVATE_KEY);  
	        return Coder.encryptBASE64(key.getEncoded());     
	    }

	    
	    /**
	     * 用私钥加密
	     * @param data  加密数据
	     * @param key   密钥
	     * @return
	     * @throws Exception
	     */
	    public static byte[] encryptByPrivateKey(byte[] data,String key)throws Exception{
	        byte[] keyBytes = Coder.decryptBASE64(key);
	        //取私钥
	        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
	        
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
	        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
	        
	        //对数据加密
	        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
	        
	        int inputLen = data.length;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        byte[] cache;
	        int i = 0;
	        // 对数据分段加密
	        while (inputLen - offSet > 0) {
	            if (inputLen - offSet > maxEncryptBloc) {
	                cache = cipher.doFinal(data, offSet, maxEncryptBloc);
	            } else {
	                cache = cipher.doFinal(data, offSet, inputLen - offSet);
	            }
	            out.write(cache, 0, cache.length);
	            i++;
	            offSet = i * maxEncryptBloc;
	        }
	        byte[] encryptedData = out.toByteArray();
	        out.close();
	        
	        return encryptedData;
	    }
	    
	    
	    /**
	     * 用私钥解密
	     * @param data  加密数据
	     * @param key   密钥
	     * @return
	     * @throws Exception
	     */
	    public static byte[] decryptByPrivateKey(byte[] data,String key)throws Exception{
	        //对私钥解密
	        byte[] keyBytes = Coder.decryptBASE64(key);
	         
	        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
	        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
	        //对数据解密
	        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
	        
	        int inputLen = data.length;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        byte[] cache;
	        int i = 0;
	        // 对数据分段解密
	        while (inputLen - offSet > 0) {
	            if (inputLen - offSet > maxDecryptBloc) {
	                cache = cipher.doFinal(data, offSet, maxDecryptBloc);
	            } else {
	                cache = cipher.doFinal(data, offSet, inputLen - offSet);
	            }
	            out.write(cache, 0, cache.length);
	            i++;
	            offSet = i * maxDecryptBloc;
	        }
	        byte[] decryptedData = out.toByteArray();
	        out.close();
	        
	        return decryptedData;
	    }
	    
	    
	    /**
	     * 用公钥加密
	     * @param data  加密数据
	     * @param key   密钥
	     * @return
	     * @throws Exception
	     */
	    public static byte[] encryptByPublicKey(byte[] data,String key)throws Exception{
	        byte[] keyBytes = Coder.decryptBASE64(key);
	        //取公钥
	        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
	        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
	         
	        //对数据解密
	        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        
	        int inputLen = data.length;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        byte[] cache;
	        int i = 0;
	        // 对数据分段加密
	        while (inputLen - offSet > 0) {
	            if (inputLen - offSet > maxEncryptBloc) {
	                cache = cipher.doFinal(data, offSet, maxEncryptBloc);
	            } else {
	                cache = cipher.doFinal(data, offSet, inputLen - offSet);
	            }
	            out.write(cache, 0, cache.length);
	            i++;
	            offSet = i * maxEncryptBloc;
	        }
	        byte[] encryptedData = out.toByteArray();
	        out.close();
	        
	        return encryptedData;
	    }
	    
	    /**
	     * 用公钥解密
	     * @param data  加密数据
	     * @param key   密钥
	     * @return
	     * @throws Exception
	     */
	    public static byte[] decryptByPublicKey(byte[] data,String key)throws Exception{
	        //对公钥解密
	        byte[] keyBytes = Coder.decryptBASE64(key);
	        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
	        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
	         
	        //对数据解密
	        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	        cipher.init(Cipher.DECRYPT_MODE, publicKey);
	         
	        int inputLen = data.length;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        byte[] cache;
	        int i = 0;
	        // 对数据分段解密
	        while (inputLen - offSet > 0) {
	            if (inputLen - offSet > maxDecryptBloc) {
	                cache = cipher.doFinal(data, offSet, maxDecryptBloc);
	            } else {
	                cache = cipher.doFinal(data, offSet, inputLen - offSet);
	            }
	            out.write(cache, 0, cache.length);
	            i++;
	            offSet = i * maxDecryptBloc;
	        }
	        byte[] decryptedData = out.toByteArray();
	        out.close();
	        
	        return decryptedData;
	    }
	    
	    
	    /**
	     *  用私钥对信息生成数字签名
	     * @param data  //加密数据
	     * @param privateKey    //私钥
	     * @return
	     * @throws Exception
	     */
	    public static String sign(byte[] data,String privateKey)throws Exception{
	        //解密私钥
	        byte[] keyBytes = Coder.decryptBASE64(privateKey);
	        //构造PKCS8EncodedKeySpec对象
	        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
	        //指定加密算法
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
	        //取私钥匙对象
	        PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
	        //用私钥对信息生成数字签名
	        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
	        signature.initSign(privateKey2);
	        signature.update(data);
	         
	        return Coder.encryptBASE64(signature.sign());
	    }
	    
	    /**
	     * 校验数字签名
	     * @param data  加密数据
	     * @param publicKey 公钥
	     * @param sign  数字签名
	     * @return
	     * @throws Exception
	     */
	    public static boolean verify(byte[] data,String publicKey,String sign)throws Exception{
	        //解密公钥
	        byte[] keyBytes = Coder.decryptBASE64(publicKey);
	        //构造X509EncodedKeySpec对象
	        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
	        //指定加密算法
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
	        //取公钥匙对象
	        PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);
	         
	        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
	        signature.initVerify(publicKey2);
	        signature.update(data);
	        //验证签名是否正常
	        return signature.verify(Coder.decryptBASE64(sign));
	         
	    }
	    
}
