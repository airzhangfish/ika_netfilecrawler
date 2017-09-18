package com.ikags.netcrawler;

import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;

import com.ikags.utils.BHHeader;
import com.ikags.utils.FileUtil;
import com.ikags.utils.NetUtil;
import com.ikags.utils.StringUtil;


/**
 * ���������ļ�����������һ����˵��������̻���jar�����뼴��ʹ��������ط�����
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

				// ���̴߳�������
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

				// ����������
				getlistpage(0, 1);
			}

		};
		t.start();

	}


	private void getlistpage(final int start, final int count) {

		Thread t = new Thread() {

			public void run() {
				
				//��������
				 crawerNetData(start, count);
				 //��ϴ����
				scannerData();
			}

		};
		t.start();
	}
	
	
	/**
	 * �����������ط���
	 * @param start
	 * @param count
	 */
	private void crawerNetData(final int start, final int count){
		try {
			// ���ص�ַ���ܣ�CSV��ʽ
			File file = new File("C:\\Users\\airzhangfish\\Desktop\\carlist_data3.csv");
			Vector vdata = StringUtil.getCSVData(new FileInputStream(file), "UTF-8");
			Vector<BHHeader> vec = NetUtil.getCustomHeaders();
			System.out.println("����������=" + vdata.size());
			
			//���ص�������
			for (int index = start; index < start + count; index++) {
				
				
				// ��Ҫ��д����Ŀ
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
						// POST�����÷���String data = NetUtil.getNetPostTextNogzip(murl, post, vec, encode);
						
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
	 * ɨ�豾���������ݣ���ȡ��Ҫ������
	 */
	private void scannerData(){
        String detailfilepath="C:\\Users\\airzhangfish\\Desktop\\cardetail3\\";
		File filelist = new File(detailfilepath);
		String[] htmlfiles = filelist.list();

		for (int page = 0; page < htmlfiles.length; page++) {
			String data = StringUtil.getLocalFileText(detailfilepath + htmlfiles[page]);
			//��������
			String tag = StringUtil.getTextContent(data, "<em>", "</em>");
			tag = tag.replaceAll("\n", "").trim(); //��ϴ��ʽ
		}
		
		//����
		FileUtil.saveFile(detailfilepath, "savefilename.csv", "XXXXX");
		
	}
	

	
	
	
}
