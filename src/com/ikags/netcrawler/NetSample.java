package com.ikags.netcrawler;

import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;

import com.ikags.utils.BHHeader;
import com.ikags.utils.FileUtil;
import com.ikags.utils.IKANetCrawlerManager;
import com.ikags.utils.IKARegularManager;
import com.ikags.utils.NetUtil;
import com.ikags.utils.StringUtil;


/**
 * 爬虫网络文件事例方法，一般来说把这个工程或者jar包导入即可使用所有相关方法。
 * @author airzhangfish
 *
 */
public class NetSample {

	public static void main(String[] args) {
      //初始化
		IKANetCrawlerManager manager=new IKANetCrawlerManager();
		manager.setGZIPMode(true);
		manager.setMitlThread(3);
		
		//头部
		manager.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		manager.addHeader("Accept-Encoding", "gzip,deflate");
		manager.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_2; en-US) AppleWebKit/533.3 (KHTML, like Gecko) Chrome/5.0.354.0 Safari/533.3");
		
		//下载地址添加
		manager.addNetText("https://gz.startcarlife.com/find-car?sort=all&make=car_15&module=m3",null, "C:\\Users\\airzhangfish\\Desktop\\test\\textget.html", true,true);
		manager.addNetText("http://bbs.a9vg.com/forum.php", "sort=all","C:\\Users\\airzhangfish\\Desktop\\test\\textpost.html",true, true);
		manager.addNetText("https://icdn.startcarlife.com/img/1708/1/1708166fb02049x1.jpg",null, "C:\\Users\\airzhangfish\\Desktop\\test\\textfile.jpg",false, true);
		
		//开始下载
		manager.startNetDownload();
		
		
		//其他方法：直接下载文本
		String text=manager.getNetText("https://gz.startcarlife.com/find-car?sort=all&make=car_15&module=m3", null, true);
		
		//其他方法：正则提取
		Vector<String> vec=IKARegularManager.readTextParten(text,"([0-9]*)(/日均)",1);
		
		//其他方法：保存数据
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<vec.size();i++){
			sb.append(vec.get(i)+",\r\n");
		}
		FileUtil.saveFile("", "C:\\Users\\airzhangfish\\Desktop\\cece.csv", sb.toString());
		
	}

}
