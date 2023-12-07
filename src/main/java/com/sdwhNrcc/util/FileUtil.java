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
		//�ο�����:https://blog.51cto.com/u_16175460/6757937
		try {
	        // ��base64�ַ�������Ϊ�ֽ�����
	        byte[] imageData = Base64.getDecoder().decode(base64Str);
	
	        // ����һ���յ������
	        OutputStream outputStream = new FileOutputStream("D:/resource/SdwhNrcc/signAvatar/output.jpg");
	
	        // ���ֽ�����д�������
	        outputStream.write(imageData);
	
	        // �ر������
	        outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static MultipartFile createMultipartFileByPath(String path) {
		MultipartFile cMultiFile = null;
		try {
			//�ο�����:https://blog.csdn.net/cucgyfjklx/article/details/123133555
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
