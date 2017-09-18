package com.ikags.ikaconverter.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;

import javax.swing.JTextArea;

import com.ikags.utils.FileUtil;
import com.ikags.utils.StringUtil;
import com.ikags.utils.bxml.BJsonDriver;
import com.ikags.utils.bxml.BXmlDriver;
import com.ikags.utils.bxml.BXmlElement;
import com.ikags.utils.bxml.BXmlUtil;

public class Xml2csvTask {

	JTextArea mJTextArea=null;
	
	public Xml2csvTask(){
	}
	
	
	public void setTextArea(JTextArea  jta){
		mJTextArea=jta;
	}
	
	
	/**
	 * 多文件
	 * @param path
	 * @param file
	 */
	public void setFilesPath(String path,File[] files){
		
		try{
			String[] rawdata=new String[files.length];
			for(int i=0;i<files.length;i++){
				rawdata[i] =""+StringUtil.getLocalFileText(files[i].getPath());
				addStringLine("load file from:"+files[i].getPath());
			}
			
			StringBuffer sb=new StringBuffer();
			sb.append("<allfiles>");
			for(int i=0;i<rawdata.length;i++){
				sb.append("<localfile filename=\""+files[i].getName()+"\">");
				sb.append(rawdata[i]);
				sb.append("</localfile>");
			}
			sb.append("</allfiles>");
			addStringLine("mix file data...");
			
			try{
				String name="mixfile_"+System.currentTimeMillis()+".xml";
				FileUtil.saveFile(files[0].getParent()+"\\",name, sb.toString());
				addStringLine("xml save over,file="+files[0].getParent()+"\\"+name);		
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	
//		addStringLine("load file from:"+path+file);
//		startJson2csv(path,file);
		
		
	}
	
	
	/**
	 * 单个文件
	 * @param path
	 * @param file
	 */
	public void setFilePath(String path,String file){
	
		addStringLine("load file from:"+path+file);
		startXml2csv(path,file);
		
		
	}
	
	
	
	public void addStringLine(String info){
		//String lastinfo=mJTextArea.getText();	
		LocalDateTime localDateTime = LocalDateTime.now();
		String newstr= localDateTime+"> "+info +"\n";
		mJTextArea.append(newstr);
	}
	
	
	public void startXml2csv(String path,String filename){
		
		//2.xml转化为bxml
		//4.保存
		
		
		//1. 读取json
		String data=""+StringUtil.getLocalFileText(path+filename);
		addStringLine("file size="+data.length()+",start to load xml....");

		//2.json转化为bxml
		BXmlElement root=new BXmlElement();
		try{
			root.setTagName("root");
			root=BXmlDriver.loadXML(data);
			//root.printNode(1);
			StringBuffer sb=new StringBuffer();
			root.printNodeBuffer(sb,1);
			addStringLine("xml datat:\n"+sb.toString());
		}catch(Exception ex){
			addStringLine("xml had error:"+ex.getMessage());
			ex.printStackTrace();
		}

		//3.bxml转为csv （应该是个递归）
		try{
			addStringLine("start converter to csv");			
			String savecsvdata=BXmlUtil.converterBXml2CSV(root,true,false);
		   // System.out.println("csv datat:\n"+savecsvdata);
			addStringLine("csv datat:\n"+savecsvdata);
			FileUtil.saveFile(path+filename, "_.csv", savecsvdata);
			addStringLine("csv save over");		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	
	
	
	
	
}
