package com.ikags.ikaconverter.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;

import javax.swing.JTextArea;

import com.ikags.utils.FileUtil;
import com.ikags.utils.StringUtil;
import com.ikags.utils.bxml.BJsonDriver;
import com.ikags.utils.bxml.BXmlElement;
import com.ikags.utils.bxml.BXmlUtil;

public class Json2csvTask {

	JTextArea mJTextArea=null;
	
	public Json2csvTask(){
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
			sb.append("{\"data\":[");
			for(int i=0;i<rawdata.length;i++){
				sb.append("{\"filename\":\""+files[i].getName()+"\",\"filedata\":");
				sb.append(rawdata[i]);
				if(i==rawdata.length-1){
					sb.append("}");
				}else{
					sb.append("},");	
				}
				
			}
			sb.append("]}");
			addStringLine("mix file data...");
			
			try{
				String name="mixfile_"+System.currentTimeMillis()+".json";
				FileUtil.saveFile(files[0].getParent()+"\\",name, sb.toString());
				addStringLine("json save over,file="+files[0].getParent()+"\\"+name);		
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
		startJson2csv(path,file);
		
		
	}
	
	
	
	public void addStringLine(String info){
		//String lastinfo=mJTextArea.getText();	
		LocalDateTime localDateTime = LocalDateTime.now();
		String newstr= localDateTime+"> "+info +"\n";
		mJTextArea.append(newstr);
	}
	
	
	public void startJson2csv(String path,String filename){
		
		//1. 读取json
		//2.json转化为bxml
		//3.bxml转为csv （应该是个递归）
		//4.保存
		
		
		//1. 读取json
		String data=""+StringUtil.getLocalFileText(path+filename);
		addStringLine("file size="+data.length()+",start to load json....");

		//2.json转化为bxml
		BXmlElement root=new BXmlElement();
		try{
			root.setTagName("root");
			root=BJsonDriver.loadJson(root,data);
			//root.printNode(1);
			StringBuffer sb=new StringBuffer();
			root.printNodeBuffer(sb,1);
			addStringLine("json datat:\n"+sb.toString());
		}catch(Exception ex){
			addStringLine("json had error:"+ex.getMessage());
			ex.printStackTrace();
		}

		//3.bxml转为csv （应该是个递归）
		try{
			addStringLine("start converter to csv");			
			String savecsvdata=BXmlUtil.converterBXml2CSV(root,true,true);
		   // System.out.println("csv datat:\n"+savecsvdata);
			addStringLine("csv datat:\n"+savecsvdata);
			FileUtil.saveFile(path+filename, "_.csv", savecsvdata);
			addStringLine("csv save over");		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	
	
	
	
	
}
