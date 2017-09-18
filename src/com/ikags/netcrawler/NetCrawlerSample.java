package com.ikags.netcrawler;

import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;

import com.ikags.utils.BHHeader;
import com.ikags.utils.FileUtil;
import com.ikags.utils.NetUtil;
import com.ikags.utils.StringUtil;


/**
 * 爬虫网络文件事例方法，一般来说把这个工程或者jar包导入即可使用所有相关方法。
 * @author airzhangfish
 *
 */
public class NetCrawlerSample {

	public static void main(String[] args) {

		NetCrawlerSample dp = new NetCrawlerSample();
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
				 crawerNetData(start, count);
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
			File file = new File("C:\\Users\\airzhangfish\\Desktop\\carlist_data3.csv");
			Vector vdata = StringUtil.getCSVData(new FileInputStream(file), "UTF-8");
			Vector<BHHeader> vec = NetUtil.getCustomHeaders();
			System.out.println("下载数据量=" + vdata.size());
			
			//下载的数据量
			for (int index = start; index < start + count; index++) {
				
				
				// 需要填写的项目
				String carid=(String) ((Vector) vdata.get(index)).get(0);
				String mmurl = "http://www.atzuche.com/car/reDetail/" + carid+".html";
				String savepath="C:\\Users\\airzhangfish\\Desktop\\cardetail3\\";
				String savefilename= carid+".html";
				
				File nowfile = new File(savepath + savefilename);
				if (nowfile.exists() == true) {
					System.out.println(index + ",local had mmurl2=" + mmurl);
				} else {
					if (carid != null && carid.length() > 0) {
						System.out.println(index + ",net load mmurl2=" + mmurl);
						String data = NetUtil.getNetText(mmurl, vec, "UTF-8");
						// POST数据用法：String data = NetUtil.getNetPostTextNogzip(murl, post, vec, encode);
						
						if (data != null && data.length() > 0) {
							FileUtil.saveFile(savepath, savefilename, data);
						}
					}
				}
			}
			System.out.println("saveover");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	/**
	 * 扫描本地下载数据，提取需要的数据
	 */
	private void scannerData(){
        String detailfilepath="C:\\Users\\airzhangfish\\Desktop\\cardetail3\\";
		File filelist = new File(detailfilepath);
		String[] htmlfiles = filelist.list();

		for (int page = 0; page < htmlfiles.length; page++) {
			String data = StringUtil.getLocalFileText(detailfilepath + htmlfiles[page]);
			//分析数据
			String tag = StringUtil.getTextContent(data, "<em>", "</em>");
			tag = tag.replaceAll("\n", "").trim(); //清洗格式
		}
		
		//保存
		FileUtil.saveFile(detailfilepath, "savefilename.csv", "XXXXX");
		
	}
	

	
	
	
}
