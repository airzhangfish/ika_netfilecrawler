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
 * ���������ļ�����������һ����˵��������̻���jar�����뼴��ʹ��������ط�����
 * @author airzhangfish
 *
 */
public class NetSample {

	public static void main(String[] args) {
      //��ʼ��
		IKANetCrawlerManager manager=new IKANetCrawlerManager();
		manager.setGZIPMode(true);
		manager.setMitlThread(3);
		
		//ͷ��
		manager.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		manager.addHeader("Accept-Encoding", "gzip,deflate");
		manager.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_2; en-US) AppleWebKit/533.3 (KHTML, like Gecko) Chrome/5.0.354.0 Safari/533.3");
		
		//���ص�ַ���
		manager.addNetText("https://gz.startcarlife.com/find-car?sort=all&make=car_15&module=m3",null, "C:\\Users\\airzhangfish\\Desktop\\test\\textget.html", true,true);
		manager.addNetText("http://bbs.a9vg.com/forum.php", "sort=all","C:\\Users\\airzhangfish\\Desktop\\test\\textpost.html",true, true);
		manager.addNetText("https://icdn.startcarlife.com/img/1708/1/1708166fb02049x1.jpg",null, "C:\\Users\\airzhangfish\\Desktop\\test\\textfile.jpg",false, true);
		
		//��ʼ����
		manager.startNetDownload();
		
		
		//����������ֱ�������ı�
		String text=manager.getNetText("https://gz.startcarlife.com/find-car?sort=all&make=car_15&module=m3", null, true);
		
		//����������������ȡ
		Vector<String> vec=IKARegularManager.readTextParten(text,"([0-9]*)(/�վ�)",1);
		
		//������������������
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<vec.size();i++){
			sb.append(vec.get(i)+",\r\n");
		}
		FileUtil.saveFile("", "C:\\Users\\airzhangfish\\Desktop\\cece.csv", sb.toString());
		
	}

}
