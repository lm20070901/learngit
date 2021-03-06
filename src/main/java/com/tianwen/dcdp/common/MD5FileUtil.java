package com.tianwen.dcdp.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 计算文件md5值
 * @author WangYanlong
 * @date Sep 2, 2015
 */
public class MD5FileUtil {
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	protected static MessageDigest messagedigest = null;
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			//logger.error("MD5FileUtil messagedigest初始化失败", e);
		}
	}

	public static String getFileMD5String(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);  
        FileChannel ch = in.getChannel();  
          
        //700000000 bytes are about 670M  
        int maxSize=700000000;  
          
        long startPosition=0L;  
        long step=file.length()/maxSize;  
          
        if(step == 0){  
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,file.length());  
            messagedigest.update(byteBuffer);  
            return bufferToHex(messagedigest.digest());  
        }  
          
        for(int i=0;i<step;i++){  
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, startPosition,maxSize);  
            messagedigest.update(byteBuffer);  
            startPosition+=maxSize;  
        }  
          
        if(startPosition==file.length()){  
            return bufferToHex(messagedigest.digest());  
        }  
  
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, startPosition,file.length()-startPosition);  
        messagedigest.update(byteBuffer);  
          
              
        return bufferToHex(messagedigest.digest());
	}

	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	public static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static boolean checkPassword(String password, String md5PwdStr) {
		String s = getMD5String(password);
		return s.equals(md5PwdStr);
	}

	public static void main(String[] args) throws IOException {
		long begin = System.currentTimeMillis();

		File big = new File("E:\\a\\跟我学才艺\\绘画\\董戈翔写意蔬果01\\董戈翔写意蔬果01.mp4");
		String md5 = getFileMD5String(big);

		long end = System.currentTimeMillis();
		System.out.println("md5:" + md5);
		System.out.println("time:" + ((end - begin) / 1000) + "s");

	}
}
