package com.ikags.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
/**
 * �����ļ������Ĺ�����
 * 
 * @author airzhangfish
 * 
 */
public class FileUtil {

	public static final String TAG = "FileUtil";

	public static String getRandomName() {
		String longname = String.valueOf(System.currentTimeMillis());
		return longname;
	}

	public static void saveFile(String path, String name, String data) {
		try {
			File file = new File(path + name);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			// д�ļ�
			FileOutputStream out;
			out = new FileOutputStream(file);
			out.write(data.getBytes("UTF-8"));
			out.close();
		} catch (Exception e) {
			//Log.v(TAG, "path="+path+",name="+name+",data="+data);
			e.printStackTrace();
		}
	}

	public static void saveFile(String path, String name, InputStream in) {
		try {
			File file = new File(path + name);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			// д�ļ�
			FileOutputStream out;
			out = new FileOutputStream(file);
			byte[] buffer = new byte[50 * 1024];
			try {
				for (int n; (n = in.read(buffer)) != -1;) {
					out.write(buffer, 0, n);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean mkdirs(String mkdirName) {
		try {
			File dirFile = new File(mkdirName);
			boolean bret = false;
			if (!dirFile.exists()) {
				try {
					bret = dirFile.mkdirs();
					return bret;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return false;
		} catch (Exception err) {
			err.printStackTrace();
			return false;
		}
	}

	public static boolean isSdcardExsit() {
		boolean ret = false;
//		String sDcString = android.os.Environment.getExternalStorageState();
//		if (sDcString.equals(android.os.Environment.MEDIA_MOUNTED)) {
//			ret = true;
//		}
		return ret;

	}

	/**
	 * ɾ���ļ���(�����Լ��ļ���)
	 * 
	 * @param filePathAndName
	 *            String �ļ���·�������� ��c:/fqf
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFolder(String folderPath) {
		try {
			deleteDirs(folderPath); // ɾ����������������
			File myFilePath = new File(folderPath);
			myFilePath.delete(); // ɾ�����ļ���

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * ɾ���ļ�������������ļ�(�������Լ�)
	 * 
	 * @param path
	 *            String �ļ���·�� �� c:/fqf
	 */
	public static void deleteDirs(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				deleteDirs(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
				delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
			}
		}
	}

	public static void deleteFile(String defaultPath, String filename) {
		int index = 0;
		index = filename.indexOf('/');
		File file = null;
		if (defaultPath == null) {
			defaultPath = "";
		}
		try {
			if (index == 0) {// lhy:2011.5.5 ����Զ���·��������
				file = new File(filename);
			} else {
				file = new File(defaultPath + filename);
			}
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteFile(String filename) {
		try {
			File file = new File(filename);
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
