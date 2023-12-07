package com.sdwhNrcc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

public class FileUtil {

	public static void decodeBase64ToImage(String base64Str, String imagePath) {
		//参考链接:https://blog.51cto.com/u_16175460/6757937
		try {
	        // 将base64字符串解码为字节数组
	        byte[] imageData = Base64.getDecoder().decode(base64Str);
	
	        // 创建一个空的输出流
	        OutputStream outputStream = new FileOutputStream("D:/resource/SdwhNrcc/signAvatar/output.jpg");
	
	        // 将字节数组写入输出流
	        outputStream.write(imageData);
	
	        // 关闭输出流
	        outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static MultipartFile createMultipartFileByPath(String path) {
		MultipartFile cMultiFile = null;
		try {
			//参考链接:https://blog.csdn.net/cucgyfjklx/article/details/123133555
	        File file = new File("D:/resource/SdwhNrcc/signAvatar/output.jpg");
	        cMultiFile = new MockMultipartFile("file", file.getName(), null, new FileInputStream(file));
	        System.out.println("size="+cMultiFile.getSize());
        } catch (Exception e) {
            e.printStackTrace();
        }
		finally {
	        return cMultiFile;
		}
	}
}
