package com.ikags.utils.bxml;

import java.util.Iterator;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * XML处理方法封装
 * 
 * @author zhangxiasheng
 *
 */
public class BJsonDriver {

	/**
	 * 读取文本方式的xml
	 * 
	 * @param text
	 * @return
	 */
	public static BXmlElement loadJson(BXmlElement root, String text) {
		BXmlElement rootelement = null;
		JSONObject obj = loadJSONObject(text);
		JSONArray array = null;
		if (obj == null) {
			array = loadJSONArray(text);
		}
		if (obj != null) {
			rootelement = jsonobj2Element(root, obj);
		} else if (array != null) {
			rootelement = jsonarray2Element(root, array);
		}
		return rootelement;
	}

	private static BXmlElement jsonobj2Element(BXmlElement root, JSONObject obj) {
		Iterator keys = obj.keys();
		Vector<String> namelist = new Vector<String>();
		Vector<String> valuelist = new Vector<String>();
		while (keys.hasNext()) {
			
			try{
			
			String key = keys.next().toString();
			String value = obj.optString(key);
			int type = BXmlUtil.testIsArrayORObject(value);
			switch (type) {
				case 0 : // 普通参数
					namelist.add("" + key);
					valuelist.add("" + value);
				break;
				case 1 :// object
					BXmlElement child = new BXmlElement();
					child.setTagName("" + key);
					child.parent = root;
					JSONObject object = loadJSONObject(value); 
					if (object != null) {
						child = jsonobj2Element(child, object);
						root.addChildrenElement(child);
					}
				break;
				case 2 :// Array
					BXmlElement child2 = new BXmlElement();
					child2.setTagName("" + key);
					child2.parent = root;
					JSONArray array = loadJSONArray(value) ;
					if (array != null) {
						child2 = jsonarray2Element(child2, array);
						root.addChildrenElement(child2);
					}
				break;
			}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
		root.addAttri(namelist, valuelist);
		return root;
	}

	private static BXmlElement jsonarray2Element(BXmlElement root, JSONArray array) {
		
		if(array!=null && array.length()>0){
			for(int i=0;i<array.length();i++){
		           int type=-1;
				try {
					String strdata=array.get(i).toString();
					type = BXmlUtil.testIsArrayORObject(strdata);
					switch (type) {
						case 1 :// object
							BXmlElement child = new BXmlElement();
							child.setTagName("");
							child.parent = root;
							JSONObject object = loadJSONObject(strdata); 
							if (object != null) {
								child = jsonobj2Element(child, object);
								root.addChildrenElement(child);
							}
						break;
						case 2 :// Array
							BXmlElement child2 = new BXmlElement();
							child2.setTagName("");
							child2.parent = root;
							JSONArray array1 = loadJSONArray(strdata) ;
							if (array != null) {
								child2 = jsonarray2Element(child2, array1);
								root.addChildrenElement(child2);
							}
						break;
					}
				} catch (JSONException e) {

					e.printStackTrace();
				}
			}	
		}
		return root;
	}

	private static void resetElement(BXmlElement root) {
		int size = root.getChildren().size();
		for (int i = 0; i < size; i++) {
			BXmlElement element = (BXmlElement) root.getChildren().elementAt(i);
			element.parent = root;
			if (i < size - 1) {
				element.next = (BXmlElement) root.getChildren().elementAt(i + 1);
			}
			if (i > 0) {
				element.previous = (BXmlElement) root.getChildren().elementAt(i - 1);
			}
			resetElement(element);
		}
	}

	/**
	 * 读取文本方式的JSONObject
	 * 
	 * @param text
	 * @return
	 */
	public static JSONObject loadJSONObject(String text) {
		JSONObject obj = null;
		try {
			obj = new JSONObject(text);
		} catch (Exception ex) {
			System.out.println("loadJSONObject error="+text);
			ex.printStackTrace();
		}
		return obj;
	}

	/**
	 * 读取文本方式的JSONArray
	 * 
	 * @param text
	 * @return
	 */
	public static JSONArray loadJSONArray(String text) {
		JSONArray array = null;
		try {
			array = new JSONArray(text);
		} catch (Exception ex) {
			System.out.println("loadJSONArray error="+text);
			ex.printStackTrace();
		}
		return array;
	}


}
