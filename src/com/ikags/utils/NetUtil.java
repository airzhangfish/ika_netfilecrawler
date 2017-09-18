package com.ikags.utils;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

public class NetUtil {

	
	public static String getNetText(String murl,Vector<BHHeader> vec,String encode){
		URLConnection urlConn = null; 
		URL urlObject; 
		String textdata=null;
		try{ 
			//���ò���
		    urlObject = new URL(murl); 
		    urlConn = urlObject.openConnection(); 
		    urlConn.setConnectTimeout(10000);   // ����Http��������ĳ�ʱʱ�䣨��λ�����룩 
		    urlConn.setReadTimeout(10000);   // Http����ʧ��ʱ�������Ӵ��� 
		    if(vec!=null){
		    	for(int i=0;i<vec.size();i++){
		    		BHHeader header=vec.elementAt(i);
		    		urlConn.setRequestProperty(header.name,header.value);  //����ͷ��		
		    	}
		    }
			InputStream is = urlConn.getInputStream(); 
			textdata=StringUtil.getInputStreamText(new GZIPInputStream(is), encode);
		   }catch (Exception e){ 
			   e.printStackTrace();
		   }
	    return textdata;
	}
	

	public static String getNetTextNogzip(String murl,Vector<BHHeader> vec,String encode){
		URLConnection urlConn = null; 
		URL urlObject; 
		String textdata=null;
		try{ 
			//���ò���
		    urlObject = new URL(murl); 
		    urlConn = urlObject.openConnection(); 
		    urlConn.setConnectTimeout(10000);   // ����Http��������ĳ�ʱʱ�䣨��λ�����룩 
		    urlConn.setReadTimeout(10000);   // Http����ʧ��ʱ�������Ӵ��� 
		    if(vec!=null){
		    	for(int i=0;i<vec.size();i++){
		    		BHHeader header=vec.elementAt(i);
		    		urlConn.setRequestProperty(header.name,header.value);  //����ͷ��		
		    	}
		    }
			InputStream is = urlConn.getInputStream(); 
			textdata=StringUtil.getInputStreamText(is, encode);
		   }catch (Exception e){ 
			   e.printStackTrace();
		   }
	    return textdata;
	}
	
	
	public static String getNetPostTextNogzip(String murl,String post,Vector<BHHeader> vec,String encode){
		URLConnection urlConn = null; 
		URL urlObject; 
		String textdata=null;
		try{ 
			//���ò���
		    urlObject = new URL(murl); 
		    urlConn = urlObject.openConnection(); 
		    urlConn.setConnectTimeout(10000);   // ����Http��������ĳ�ʱʱ�䣨��λ�����룩 
		    urlConn.setReadTimeout(10000);   // Http����ʧ��ʱ�������Ӵ��� 
		    if(vec!=null){
		    	for(int i=0;i<vec.size();i++){
		    		BHHeader header=vec.elementAt(i);
		    		urlConn.setRequestProperty(header.name,header.value);  //����ͷ��		
		    	}
		    }
		    
		    // ����POST�������������������  
		    urlConn.setDoOutput(true);  
		    urlConn.setDoInput(true);  
		    // ��ȡURLConnection�����Ӧ�������  
		    PrintWriter out = new PrintWriter(urlConn.getOutputStream());  
		    // �����������  
		    out.print(post);  
		    // flush������Ļ���  
		    out.flush();  
		    
		    
			InputStream is = urlConn.getInputStream(); 
			textdata=StringUtil.getInputStreamText(is, encode);
		   }catch (Exception e){ 
			   e.printStackTrace();
		   }
	    return textdata;
	}
	
	
	
	
	public static Vector<BHHeader> getCustomHeaders() {
		Vector<BHHeader> vec = new Vector<BHHeader>();
		vec.add(new BHHeader("Accept", "text/html,application/html"));
//		vec.add(new BHHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.2.1; zh-cn; HUAWEI G700T Build/HUAWEI G700T) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30"));
		vec.add(new BHHeader("Accept-Encoding", "gzip,deflate"));
		vec.add(new BHHeader("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_2; en-US) AppleWebKit/533.3 (KHTML, like Gecko) Chrome/5.0.354.0 Safari/533.3"));
		
		return vec;
	}

}
