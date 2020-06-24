package com.mcoding.common.util.encryption;

/**
 * des 加密解密工具
 * @author hzy
 *
 */
public class DESUtils {

	private static DES des;
	
	private static DES getInstance() throws Exception{
		if (des == null) {
			des = new DES();
		}
		return des;
	}

	/**
	 * DES 加密
	 * @param strIn 待加密的字符
	 * @param key 加密的密钥
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String strIn, String key) throws Exception {
		DES des = new DES(key);
		return des.encrypt(strIn);
	}
	
	/**
	 * DES加密
	 * @param strIn
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String strIn) throws Exception {
		return getInstance().encrypt(strIn);
	}
	
	/**  
	 * 加密字节数组  
	 *   
	 * @param arrB 需加密的字节数组  
	 * @return 加密后的字节数组  
	 * @throws Exception  
	 */  
	public static byte[] encrypt(byte[] arrB) throws Exception {   
		return getInstance().decrypt(arrB);
	}  
	
	/**  
	 * 解密字符串  
	 * @param strIn 需解密的字符串  
	 * @param key 解密的密钥  
	 * @return 解密后的字符串  
	 * @throws Exception  
	 */  
	public static String decrypt(String strIn, String key) throws Exception { 
		DES des = new DES(key);
		return des.decrypt(strIn);
	}   
	
	/**  
	 * 解密字符串  
	 * @param strIn 需解密的字符串  
	 * @return 解密后的字符串  
	 * @throws Exception  
	 */  
	public static String decrypt(String strIn) throws Exception {   
		return getInstance().decrypt(strIn);
	}   

	/**  
	 * 解密字节数组  
	 *   
	 * @param arrB 需解密的字节数组  
	 * @return 解密后的字节数组  
	 * @throws Exception  
	 */  
	public static byte[] decrypt(byte[] arrB) throws Exception { 
		return getInstance().decrypt(arrB);   
	} 
}
