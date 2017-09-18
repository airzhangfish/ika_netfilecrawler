package com.ikags.utils.bxml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * XML处理方法封装
 * 
 * @author zhangxiasheng
 * @param <E>
 *
 */
public class BXmlUtil<E> {

	/**
	 * bxml 转化为格式
	 * 
	 * @param bxml
	 * @return
	 */
	public Object converter2Item(BXmlElement bxml) {
		return null;
	}

	public static String converterBXml2CSV(BXmlElement bxml, boolean fillmatirx, boolean isjson) {
		String[][] matirx = converterBXml2Mtrix(bxml, fillmatirx, isjson);
		String datastr = converterMatrix2CSV(matirx, rowline, maxwidth);
		return datastr;
	}

	/**
	 * bxml 转化为格式
	 * 
	 * @param bxml
	 * @return
	 */
	public static String[][] converterBXml2Mtrix(BXmlElement bxml, boolean fillmatirx, boolean isjson) {
		String[][] matix = new String[1000000][255];
		String mytag = "";
		rowline = 1;
		maxwidth = 0;
		if (isjson == true) {
			creatMatrixBXml(matix, mytag, bxml);
		} else {
			creatMatrixcontentBXml(matix, mytag, bxml);
		}
		System.out.println("rowline=" + rowline + ",maxwidth=" + maxwidth);
		String rawdata = converterMatrix2CSV(matix, rowline, maxwidth);
		if (fillmatirx == true) {
			String bufferstr = null;
			for (int i = 0; i <= maxwidth; i++) {
				bufferstr = null;
				for (int j = 0; j <= rowline; j++) {
					if (matix[j][i] != null && matix[j][i].length() > 0) {
						bufferstr = matix[j][i];
					} else {
						if (bufferstr != null && bufferstr.length() > 0) {
							matix[j][i] = bufferstr;
						}
					}
				}
			}
		}
		return matix;
	}

	public static String converterMatrix2CSV(String[][] matix, int maxrow, int maxwidth) {
		// 转化格式

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i <= maxrow; i++) {
			for (int j = 0; j <= maxwidth; j++) {
				if (matix[i][j] != null) {
					// 数据过滤
					matix[i][j] = matix[i][j].replaceAll(",", "");
					matix[i][j] = matix[i][j].replaceAll("\r", "");
					matix[i][j] = matix[i][j].replaceAll("\n", "");
					sb.append(matix[i][j].trim() + ",");
				} else {
					sb.append(",");
				}
			}
			sb.append("\n");
		}
		String rawdata = sb.toString();
		return rawdata;
	}

	private static int rowline = 1;
	private static int maxwidth = 0;
	private static void creatMatrixBXml(String[][] matirx, String tag, BXmlElement bxml) {

		// 循环写入
		// 1.找如果没有这个tag，则填入没有的空的一个tag。数字昔日如rowline那一列。
		// 2.如果找到那个tag，写入那一列
		// 3.如果那一列不为空，则新一行写入那一列

		for (int i = 0; i < bxml.getAttributeCounts(); i++) {
			for (int j = 0; j < matirx[0].length; j++) {
				String cellname = tag + bxml.getAttributeName(i);

				if (matirx[0][j] != null && matirx[0][j].equals(cellname)) {

					if (matirx[rowline][j] == null) {
						matirx[rowline][j] = "" + bxml.getAttributeValue(i);
					} else {
						rowline++;
						matirx[rowline][j] = "" + bxml.getAttributeValue(i);
					}
					break;
				} else if (matirx[0][j] == null) {
					matirx[0][j] = cellname;
					matirx[rowline][j] = "" + bxml.getAttributeValue(i);
					if (j > maxwidth) {
						maxwidth = j;
					}
					break;
				}
			}
		}

		for (int i = 0; i < bxml.getChildren().size(); i++) {
			BXmlElement child = bxml.getChildrenElement(i);
			creatMatrixBXml(matirx, tag + "_" + child.getTagName(), child);
		}
	}

	private static void creatMatrixcontentBXml(String[][] matirx, String tag, BXmlElement bxml) {

		// 循环写入
		// 1.找如果没有这个tag，则填入没有的空的一个tag。数字昔日如rowline那一列。
		// 2.如果找到那个tag，写入那一列
		// 3.如果那一列不为空，则新一行写入那一列
		
		
		
		for (int i = 0; i < bxml.getAttributeCounts(); i++) {
			for (int j = 0; j < matirx[0].length; j++) {
				String cellname = tag + bxml.getAttributeName(i);

				if (matirx[0][j] != null && matirx[0][j].equals(cellname)) {

					if (matirx[rowline][j] == null) {
						matirx[rowline][j] = "" + bxml.getAttributeValue(i);
					} else {
						rowline++;
						matirx[rowline][j] = "" + bxml.getAttributeValue(i);
					}
					break;
				} else if (matirx[0][j] == null) {
					matirx[0][j] = cellname;
					matirx[rowline][j] = "" + bxml.getAttributeValue(i);
					if (j > maxwidth) {
						maxwidth = j;
					}
					break;
				}
			}
		}
		
		
//		TODO 暂时不需要提取content
//		for (int j = 0; j < matirx[0].length; j++) {
//			String cellname =tag + "_xmlcentent";
//			String data="" + bxml.getContents();
//			if(data.length()>0){			
//			if (matirx[0][j] != null && matirx[0][j].equals(cellname)) {
//
//				if (matirx[rowline][j] == null) {
//					matirx[rowline][j] = "" + bxml.getContents();
//				} else {
//					rowline++;
//					matirx[rowline][j] = "" + bxml.getContents();
//				}
//				break;
//			} else if (matirx[0][j] == null) {
//				matirx[0][j] = cellname;
//				matirx[rowline][j] = "" + bxml.getContents();
//				if (j > maxwidth) {
//					maxwidth = j;
//				}
//				break;
//			}
//		}
//		}
		

		for (int i = 0; i < bxml.getChildren().size(); i++) {
			BXmlElement child = bxml.getChildrenElement(i);
			creatMatrixBXml(matirx, tag + "_" + child.getTagName(), child);
		}
	}

	public static int testIsArrayORObject(String sJSON) {
		/*
		 * return 0:既不是array也不是object return 1：是object return 2 ：是Array
		 */
		try {
			JSONArray array = new JSONArray(sJSON);
			return 2;
		} catch (JSONException e) {// 抛错 说明JSON字符不是数组或根本就不是JSON
			try {
				JSONObject object = new JSONObject(sJSON);
				return 1;
			} catch (JSONException e2) {// 抛错 说明JSON字符根本就不是JSON
				// e2.printStackTrace();
				return 0;
			}
		}

	}
	
	
	
	
	
	
/**
 *  用于处理 获取带标签的数据，我只要字符串不要标签 标签全部替换成 _
 * @param ul1element
 * @return
 */
	public static String getULContent(BXmlElement ul1element) {
		String info = "";
		for (int i = 0; i < ul1element.getChildren().size(); i++) {
			BXmlElement bxml = ul1element.getChildren().get(i);
			if (bxml.getChildContents() != null) {
				if (info.equals("")) {
					info = "" + bxml.getChildContents();
				} else {
					info = info + " _ " + bxml.getChildContents();
				}
			}
		}

		if (info.equals("") && ul1element.getChildContents() != null) {
			info = "" + ul1element.getChildContents();
		}
		return info;
	}

}
