package com.dragon.cryptology.rsa;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * RSA编码
 * @author wl
 *
 */
public class RSACoder {
	private static final String KEY_ALGORITHM = "RSA";      //非对称加密密钥算法
	//private static final String KEY_ALGORITHM = "RSA/ECB/PKCS1Padding";
	private static final String PUBLIC_KEY="RSAPublicKey";  //公钥
	private static final String PRIVATE_KEY="RSAPrivateKey";//私钥
	private static final int KEY_SIZE = 1024;                // 密钥长度
	
	
	/**
	 * 私钥解密
	 * @param data    待解密数据
	 * @param key     私钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception{
		//获取私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		//生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		//对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());

		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		
		return cipher.doFinal(data);
	}
	
	/**
	 * 公钥解密
	 * @param data    待解密数据
	 * @param key     公钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception{
		//获取公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		//生成公钥
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		//对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		
		return cipher.doFinal(data);
	}
	
	/**
	 * 公钥加密
	 * @param data    待加密数据
	 * @param key     公钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptBypublicKey(byte[] data, byte[] key) throws Exception{
		//获取公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		//生成公钥
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		//对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		
		return cipher.doFinal(data);
	}
	
	/**
	 * 私钥加密
	 * @param data    待加密数据
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws Exception{
		//获取私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		//生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		//对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		
		return cipher.doFinal(data);
	}
	
	/**
	 * 获取私钥
	 * @param keyMap  密钥Map
	 * @return byte[] 私钥
	 * @throws Exception
	 */
	public static byte[] getPrivateKey(Map<String, Object> keyMap) throws Exception{
		Key key = (Key)keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	}
	
	/**
	 * 获取公钥
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte[] getPublicKey(Map<String, Object> keyMap) throws Exception{
		Key key = (Key)keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}
	
	public static Map<String, Object> initKey() throws Exception{
		//实例化密钥对生成器
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		//初始化密钥对生成器
		keyPairGen.initialize(KEY_SIZE);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
}
