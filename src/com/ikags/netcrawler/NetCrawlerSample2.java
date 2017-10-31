package com.ikags.netcrawler;

import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;

import com.ikags.utils.BHHeader;
import com.ikags.utils.FileUtil;
import com.ikags.utils.NetUtil;
import com.ikags.utils.StringUtil;
import com.ikags.utils.bxml.BJsonDriver;
import com.ikags.utils.bxml.BXmlElement;
import com.ikags.utils.bxml.BXmlUtil;


/**
 * 爬虫网络文件事例方法，一般来说把这个工程或者jar包导入即可使用所有相关方法。
 * @author airzhangfish
 *
 */
public class NetCrawlerSample2 {

	public static void main(String[] args) {

		NetCrawlerSample2 dp = new NetCrawlerSample2();
		dp.runThreadrun();

	}

	public void runThreadrun() {

		Thread t = new Thread() {

			public void run() {

				// 多线程处理数据
//				 getCarlistpage(0, 1000);
//				 getCarlistpage(1000, 1000);
//				 getCarlistpage(2000, 1000);
//				 getCarlistpage(3000, 1000);
//				 getCarlistpage(4000, 1000);
//				 getCarlistpage(5000, 1000);
//				 getCarlistpage(6000, 1000);
//				 getCarlistpage(7000, 1000);
//				 getCarlistpage(8000, 1000);
//				 getCarlistpage(9000, 1000);

				// 测试用数据
				getlistpage(0, 1);
			}

		};
		t.start();

	}


	private void getlistpage(final int start, final int count) {

		Thread t = new Thread() {

			public void run() {
				
				//下载数据
				 //crawerNetData(start, count);
				 //清洗数据
				scannerData();
			}

		};
		t.start();
	}
	
	
	/**
	 * 网络爬虫下载方法
	 * @param start
	 * @param count
	 */
	private void crawerNetData(final int start, final int count){
		try {
			// 下载地址汇总，CSV格式
		
			Vector<BHHeader> vec = NetUtil.getCustomHeaders();

			//下载的数据量
			for (int i = 1; i <= 16; i++) {
				
				
				// 需要填写的项目
				String mmurl = "http://www.atzuche.com/car/searchListMap/2?cityCode=440100&sceneCode=U002&filterCondition%5Blon%5D=113.30765&filterCondition%5Blat%5D=23.120049&filterCondition%5Bseq%5D=4&pageNum="+i+"&pageSize=100";
				String savepath="C:\\Users\\airzhangfish\\Desktop\\carlist\\";
				String savefilename= "gz_"+i+".json";
				
				File nowfile = new File(savepath + savefilename);
				if (nowfile.exists() == true) {
					System.out.println(i + ",local had mmurl2=" + mmurl);
				} else {
						System.out.println(i + ",net load mmurl2=" + mmurl);
						//String data = NetUtil.getNetText(mmurl, vec, "UTF-8");
						String data = NetUtil.getNetTextNogzip(mmurl,vec, "UTF-8");
						
						if (data != null && data.length() > 0) {
							FileUtil.saveFile(savepath, savefilename, data);
						}
				}
			}
			System.out.println("saveover");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
	
	
	/**
	 * 扫描本地下载数据，提取需要的数据
	 */
	private void scannerData(){
        String detailfilepath="C:\\Users\\airzhangfish\\Desktop\\carlist\\";
		File filelist = new File(detailfilepath);
		String[] htmlfiles = filelist.list();

		for (int page = 0; page < htmlfiles.length; page++) {
			String data = StringUtil.getLocalFileText(detailfilepath + htmlfiles[page]);
			//分析数据
			BXmlElement root=new BXmlElement();
			root.setTagName("root");
			root=BJsonDriver.loadJson(root,data);

			Vector<BXmlElement> children=root.getChildrenElement(0).getChildrenElement(0).getChildren();
			for(int i=0;i<children.size();i++){
				BXmlElement bxml=children.get(i);
				String type=bxml.getAttributeValue("type");
				String dayPrice=bxml.getAttributeValue("dayPrice");
				System.out.println(type+","+dayPrice);
			}
		}
		
		//保存
//		FileUtil.saveFile(detailfilepath, "savefilename.csv", "XXXXX");
		
	}
	

	
	
	
}
