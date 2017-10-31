package com.ikags.netcrawler;

import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class NetCrawlerSample3 {

	public static void main(String[] args) {

		NetCrawlerSample3 dp = new NetCrawlerSample3();
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
				// crawerNetData(start, count);
				 //清洗数据
				//scannerData();
				
				
				
				
				readNetTextParten("别克凯越", "https://gz.startcarlife.com/find-car?sort=all&make=car_15&module=m3");
				readNetTextParten("别克GL8", "https://gz.startcarlife.com/find-car?sort=all&make=car_15&module=m0");
				readNetTextParten("日产阳光", "https://gz.startcarlife.com/find-car?sort=all&make=car_98&module=m7");
				readNetTextParten("日产轩逸", "https://gz.startcarlife.com/find-car?sort=all&make=car_98&module=m6");
				readNetTextParten("日产天籁", "https://gz.startcarlife.com/find-car?sort=all&make=car_98&module=m4");
				readNetTextParten("本田雅阁", "https://gz.startcarlife.com/find-car?sort=all&make=car_12&module=m10");
				readNetTextParten("本田锋范", "https://gz.startcarlife.com/find-car?sort=all&make=car_12&module=m6");
				readNetTextParten("本田奥德赛", "https://gz.startcarlife.com/find-car?sort=all&make=car_12&module=m4");
				readNetTextParten("大众朗逸", "https://gz.startcarlife.com/find-car?sort=all&make=car_25&module=m13");
				readNetTextParten("大众宝来", "https://gz.startcarlife.com/find-car?sort=all&make=car_25&module=m14");
				readNetTextParten("大众速腾", "https://gz.startcarlife.com/find-car?sort=all&make=car_25&module=m12");
				readNetTextParten("起亚K2", "https://gz.startcarlife.com/find-car?sort=all&make=car_97&module=m1");
				readNetTextParten("起亚K3", "https://gz.startcarlife.com/find-car?sort=all&make=car_97&module=m2");
				readNetTextParten("雪佛兰科鲁兹", "https://gz.startcarlife.com/find-car?sort=all&make=car_125&module=m1");
				readNetTextParten("宝马3系", "https://gz.startcarlife.com/find-car?sort=all&make=car_5&module=m3");
				readNetTextParten("福特嘉年华", "https://gz.startcarlife.com/find-car?sort=all&make=car_38&module=m0");
				readNetTextParten("马自达6", "https://gz.startcarlife.com/find-car?sort=all&make=car_86&module=m2");
				readNetTextParten("现代瑞纳", "https://gz.startcarlife.com/find-car?sort=all&make=car_124&module=m4");
				readNetTextParten("丰田凯美瑞", "https://gz.startcarlife.com/find-car?sort=all&make=car_35&module=m1");
				readNetTextParten("丰田卡罗拉", "https://gz.startcarlife.com/find-car?sort=all&make=car_35&module=m7");
				
				
				StringBuffer sb=new StringBuffer();
				for(int i=0;i<vecdata.size();i++){
					sb.append(vecdata.get(i)+"\r\n");
					System.out.println(vecdata.get(i));
				}
				String data=sb.toString();
				String savepath="C:\\Users\\airzhangfish\\Desktop\\";
				String savefilename= "test.csv";
				if (data != null && data.length() > 0) {
				FileUtil.saveFile(savepath, savefilename, data);
			}
			}

		};
		t.start();
	}
	
	Vector<String> vecdata=new Vector<String>();
	
	private void readNetTextParten(String name1,String url){
		try {
			// 下载地址汇总，CSV格式
		
			Vector<BHHeader> vec = NetUtil.getCustomHeaders();


				// 需要填写的项目
				String mmurl = url; 
				int i=0;
				String savepath="C:\\Users\\airzhangfish\\Desktop\\";
				String savefilename= "test.csv";
				
				File nowfile = new File(savepath + savefilename);
//				if (nowfile.exists() == true) {
//					System.out.println(i + ",local had mmurl2=" + mmurl);
//				} else {
						System.out.println(i + ",net load mmurl2=" + mmurl);
						//String data = NetUtil.getNetText(mmurl, vec, "UTF-8");
						String data = NetUtil.getNetText(mmurl,vec, "UTF-8");
						
						//¥117/折后日均
						//"name:vunv,age:20"
						//(name:)([a-zA-Z]*)(,age:)([0-9]*)
						Pattern p = Pattern.compile("(¥)([0-9]*)(/折后日均)");
						Matcher m = p.matcher(data);
						while (m.find()) {
						//System.out.println(name1+","+m.group(2));
						vecdata.addElement(name1+","+m.group(2));
						}
						 p = Pattern.compile("(¥)([0-9]*)(/日均)");
						 m = p.matcher(data);
						while (m.find()) {
							//System.out.println(name1+","+m.group(2));
							vecdata.addElement(name1+","+m.group(2));
						}
//				}		
						
						
//						if (data != null && data.length() > 0) {
//							FileUtil.saveFile(savepath, savefilename, data);
//						}
//				}

			System.out.println("saveover");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
			for (int i = 1; i < 19; i++) {
				
				
				// 需要填写的项目
				String mmurl = "http://www.atzuche.com/car/searchListMap/1?cityCode=440300&sceneCode=U002&filterCondition%5Blon%5D=114.025974&filterCondition%5Blat%5D=22.546054&filterCondition%5Bseq%5D=4&filterCondition%5BminPrice%5D=0&filterCondition%5BmaxPrice%5D=997&pageNum="+i+"&pageSize=100";
				String savepath="C:\\Users\\airzhangfish\\Desktop\\carlist\\";
				String savefilename= "list"+i+".json";
				
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
