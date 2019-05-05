package com.dragon.cryptology.rsa;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;

public class RSACodeTest {

	private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5U59SXflG2SsLbY1kUuxF6uyHQ31p3lK08jVGLGRmPu1PS6AkZq3BFrGPz1vsPdtM9VMAVZHFGVm9VMCjKkVkSnBXj5HwiMP7T8FtY/wNY8kl+LD/is1Q1NO6h7ZCqbhdou9Y1m7tTEkcv0BlubRa5YSBxiaRKkp67+tk+IDUBQIDAQAB";
	private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALlTn1Jd+UbZKwttjWRS7EXq7IdDfWneUrTyNUYsZGY+7U9LoCRmrcEWsY/PW+w920z1UwBVkcUZWb1UwKMqRWRKcFePkfCIw/tPwW1j/A1jySX4sP+KzVDU07qHtkKpuF2i71jWbu1MSRy/QGW5tFrlhIHGJpEqSnrv62T4gNQFAgMBAAECgYBK5zllmQW/VNbI76ZN/hXwzFp88RInT+wtphdSHW8WxCg1/GLlU3q2p+1sKmsEuJuCJ0U8xVj9SJm0pCSXzeXtSekzZkF81X+j8CGN1fpKM2nWnRv7PHczjWvsvGlVoQIWjDPyJv0PT9yM1LJSqUjZsAvIatSbgulDYUxhRlepAQJBAPL3j9mazrxQaqM0M/oJYnwvRdv3a2igVWjOrRPFa09AhjvqFCsSAoXBDlvGW5nBRswT8FYSzM1ZErNt/BRYWh0CQQDDRIoEKfo4uxIHgHyHyp15Oq1m2Qsy7h9UhSXOCPHZ0EwrTpTlqxofuBpIlAZNJYnYmCaFNrsW7I16lB3KA/0JAkAJ7Z/NLoxAGbEh1iJBl1yU4oGiSWv0LIkQ7VEZO7n01RgnHpuQXv4HZSonCeX4VHOGJ1Js0fvYcElZV6czVaDpAkBUsscW9UpXDzXL0Tyy/fz+V6hdYynLZqRqWfLDAvg83XAcnNJTKLuFJKsp7iMplW3MaHrU75+JWwQnNlYRiVdZAkEAwmkIWKGUTmWjkgZVdAS+fgiOHlMszmuFMvPrxEIIsCsKMsO0z0QjWmoyNimmSuQMubM2sWO+2g+W3yXOob0uNQ==";
	private byte[] publicKey;
	private byte[] privateKey;
	
	@Before
	public void initKey() throws Exception{
		Map<String, Object> keyMap = RSACoder.initKey();
		publicKey = Base64.decodeBase64(PUBLIC_KEY);//RSACoder.getPublicKey(keyMap);
		privateKey = Base64.decodeBase64(PRIVATE_KEY);//RSACoder.getPrivateKey(keyMap);

		System.out.println("公钥：\n" + Base64.encodeBase64String(publicKey));
		System.out.println("私钥：\n" + Base64.encodeBase64String(privateKey));
	}
	@Test
	public void test() throws Exception{
		System.err.println("\n---私钥加密--公钥解密---\n");
		String inputStr1 = "RSA加密算法";
		byte[] data1 = inputStr1.getBytes();
		System.out.println("原文:\n" + inputStr1);
		//加密
		byte[] encodeData1 = RSACoder.encryptByPrivateKey(data1, privateKey);
		System.out.println("加密后:\n" +  Base64.encodeBase64String(encodeData1));
		//解密
		byte[] decodeData1 = RSACoder.decryptByPublicKey(encodeData1, publicKey);
		String outputStr1 = new String(decodeData1);
		System.out.println("解密后:\n" +  outputStr1);
		//校验
		assertEquals(inputStr1, outputStr1);
		
		System.err.println("\n---公钥加密--私钥解密---\n");
		String inputStr2 = "公钥加密--私钥解密";
		byte[] data2 = inputStr2.getBytes();
		System.out.println("原文:\n" + inputStr2);
		//加密
		byte[] encodeData2 = RSACoder.encryptBypublicKey(data2, publicKey);
		System.out.println("加密后:\n" +  Base64.encodeBase64String(encodeData2));
		//解密
		byte[] decodeData2 = RSACoder.decryptByPrivateKey(encodeData2, privateKey);
		String outputStr2 = new String(decodeData2);
		System.out.println("解密后:\n" +  outputStr2);
		//校验
		assertEquals(inputStr2, outputStr2);
		
	}
	
}
