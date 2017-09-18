package com.ikags.utils.bxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.ikags.utils.FileUtil;
import com.ikags.utils.StringUtil;
import com.ikags.utils.bxml.BXmlDriver;
import com.ikags.utils.bxml.BXmlElement;

public class J2SETest {
	public static void main(String[] args){
		
		testjson(); //JSON读取
		//testxml();  //xml读取
	}
	
	public static void testjson(){
		String path="C:\\Users\\airzhangfish\\Desktop\\首汽租车\\store长沙.txt";
		

		try{
			System.out.println("startload");
			String data=StringUtil.getLocalFileText(path);
			BXmlElement root=new BXmlElement();
			root.setTagName("root");
			root=BJsonDriver.loadJson(root,data);
//			root.printNode(1);
			
			String savecsvdata=BXmlUtil.converterBXml2CSV(root,true,true);
			FileUtil.saveFile(path, ".csv", savecsvdata);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void testxml(){
		File file=new File("C:\\Users\\airzhangfish\\Desktop\\首汽租车\\store三亚.txt");
		try{
			long[] long1=new long[10];
			long[] long2=new long[10];
//			for(int i=0;i<10;i++){
				
				//SAX解析器速度
				InputStream in2 = new FileInputStream(file);
		  		long starttime2=System.currentTimeMillis();
		  		BXmlElement root2=testBXML2(in2);
				long endtime2=System.currentTimeMillis();
				
				root2.printNode(1);
				
			
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	public static BXmlElement testBXML2(InputStream in){
		BXmlElement root=null;
	      try{
	  		 root=BXmlDriver.loadXML(in);
	        }catch(Exception ex){
	      	  ex.printStackTrace();
	        }	
	      return root;
	}
	
	
	
}
