package com.ikags.utils;

import java.io.File;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IKARegularManager {

	/**
	 * 通过正则方式提取数据， 例如 readTextParten("价格199/日均","(价格)([0-9]*)(/日均)",2) ，结果vec中储存一个数据，是199
	 * 
	 * @param rawdata
	 * @param regular
	 * @param groupid
	 * @return
	 */
	public static Vector<String> readTextParten(String rawdata, String regular, int groupid) {
		Vector<String> vecdata = new Vector<String>();
		Pattern p = Pattern.compile(regular);
		Matcher m = p.matcher(rawdata);
		int count=0;
		while (m.find()) {
			vecdata.add("" + m.group(groupid));
			//System.out.println(count+"=="+m.group(groupid));
			count++;
		}
		return vecdata;
	}

	/**
	 * 废弃代码
	 * 
	 * @param url
	 * @param rawdata
	 * @param regular
	 * @param groupid
	 */
	private void readNetTextParten(String url, String rawdata, String regular, int groupid) {
		try {
			// 下载地址汇总，CSV格式

			Vector<BHHeader> vec = NetUtil.getCustomHeaders();

			// 需要填写的项目
			String mmurl = url;
			int i = 0;
			String savepath = "C:\\Users\\airzhangfish\\Desktop\\";
			String savefilename = "test.csv";

			File nowfile = new File(savepath + savefilename);

			System.out.println(i + ",net load mmurl2=" + mmurl);
			// String data = NetUtil.getNetText(mmurl, vec, "UTF-8");
			String data = NetUtil.getNetText(mmurl, vec, "UTF-8");

			// ¥117/折后日均
			// "name:vunv,age:20"
			// (name:)([a-zA-Z]*)(,age:)([0-9]*)
			Pattern p = Pattern.compile("(¥)([0-9]*)(/折后日均)");
			Matcher m = p.matcher(data);
			while (m.find()) {
				// System.out.println(name1+","+m.group(2));
				//vecdata.addElement(name1 + "," + m.group(2));
			}
			p = Pattern.compile("(X)([0-9]*)(/日均)");
			m = p.matcher(data);
			while (m.find()) {
				// System.out.println(name1+","+m.group(2));
				//vecdata.addElement(name1 + "," + m.group(2));
			}

			// if (data != null && data.length() > 0) {
			// FileUtil.saveFile(savepath, savefilename, data);
			// }
			// }
			System.out.println("saveover");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
