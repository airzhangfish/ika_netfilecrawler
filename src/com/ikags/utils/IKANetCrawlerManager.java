package com.ikags.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import com.ikags.utils.model.NetDownInfo;

/**
 * 下载文件的方法 //初始化 IKANetCrawlerManager manager=new IKANetCrawlerManager(); manager.setGZIPMode(true); manager.setMitlThread(3); manager.addHeader("Accept", "application/json, text/javascript; q=0.01"); manager.addHeader("Accept-Encoding", "gzip,deflate"); manager.addHeader("User-Agent",
 * "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_2; en-US) AppleWebKit/533.3 (KHTML, like Gecko) Chrome/5.0.354.0 Safari/533.3"); //下载地址添加 manager.addNetText("https://gz.startcarlife.com/find-car?sort=all&make=car_15&module=m3",null, "C:\\Users\\airzhangfish\\Desktop\\test\\textget.html",
 * true,true); manager.addNetText("http://bbs.a9vg.com/forum.php", "sort=all","C:\\Users\\airzhangfish\\Desktop\\test\\textpost.html",true, true); manager.addNetText("https://icdn.startcarlife.com/img/1708/1/1708166fb02049x1.jpg",null, "C:\\Users\\airzhangfish\\Desktop\\test\\textfile.jpg",false,
 * true); //开始下载 manager.startNetDownload();
 * 
 * @author airzhangfish
 *
 */

public class IKANetCrawlerManager {

	boolean gzipmode = true;
	int threadCount = 1;
	Vector<BHHeader> vecheader = new Vector<BHHeader>();
	Vector<NetDownInfo> downloadQueue = new Vector<NetDownInfo>();
	public IKANetCrawlerManager() {
		System.out.println("init IKANetCrawlerManager");
	}

	/**
	 * 设置是否gzip模式
	 * 
	 * @param name
	 * @param value
	 */
	public void setGZIPMode(boolean isGzip) {
		gzipmode = isGzip;
	}

	/**
	 * 清空下载队列
	 */
	public void clearDownloadQueue() {
		downloadQueue.clear();
	}
	public void clearHeader() {
		vecheader.clear();
	}

	/**
	 * 最多使用多少条线程下载
	 * 
	 * @param threadcount
	 */
	public void setMitlThread(int threadcount) {
		threadCount = threadcount;
	}

	/**
	 * 添加程序头部,两个参数都传空，则为默认头部
	 * 
	 * @param name
	 * @param value
	 */
	public void addHeader(String name, String value) {
		if (name == null && value == null) {
			vecheader = NetUtil.getCustomHeaders();
		} else {
			vecheader.add(new BHHeader(name, value));
		}
	}

	/**
	 * 在下载队列添加一条网络数据
	 * 
	 * @param url
	 * @param postdata
	 * @param localfile
	 * @param isText
	 * @param isrewrite
	 */
	public void addNetText(String url, String postdata, String localfile, boolean isText, boolean isrewrite) {
		NetDownInfo ndinfo = new NetDownInfo();
		ndinfo.url = url;
		ndinfo.postdata = postdata;
		ndinfo.localpathname = localfile;
		ndinfo.isrewrite = isrewrite; // 是否覆盖
		if (isText == false) {
			ndinfo.mode = 2;
		} else if (postdata != null) {
			ndinfo.mode = 1;
		} else {
			ndinfo.mode = 1;
		}
		downloadQueue.add(ndinfo);
	}

	/**
	 * 启动数据下载
	 */
	public void startNetDownload() {
		System.out.println("download totalcount=" + downloadQueue.size());
		Vector[] threadvec = new Vector[threadCount];
		for (int i = 0; i < downloadQueue.size(); i++) {
			NetDownInfo ndinfo = downloadQueue.get(i);
			if (threadvec[i % threadCount] == null) {
				threadvec[i % threadCount] = new Vector<NetDownInfo>();
			}
			threadvec[i % threadCount].add(ndinfo);
		}

		for (int i = 0; i < threadCount; i++) {
			if (threadvec[i] != null) {
				System.out.println("download Thread[" + i + "].size=" + threadvec[i].size());
				threadDownload(threadvec[i]);
			}
		}
	}

	private void threadDownload(final Vector<NetDownInfo> queue) {
		Thread t = new Thread() {
			public void run() {
				// 下载的数据量
				for (int i = 0; i < queue.size(); i++) {
					NetDownInfo ndinfo = queue.get(i);
					File nowfile = new File(ndinfo.localpathname);
					if (nowfile.exists() == true && ndinfo.isrewrite == false) {
						System.out.println("local had file=" + nowfile + " ( " + ndinfo.url + "?" + ndinfo.postdata + " )");
					} else {
						System.out.println("net load file=" + nowfile + " ( " + ndinfo.url + "?" + ndinfo.postdata + " )");

						String data = null;
						if (gzipmode == true && ndinfo.mode == 0) { // gzip+get
							data = NetUtil.getNetText(ndinfo.url, vecheader, "UTF-8");
							FileUtil.saveFile("", ndinfo.localpathname, "" + data);
						} else if (gzipmode == true && ndinfo.mode == 1) {// gzip+post
							data = NetUtil.getNetPostText(ndinfo.url, ndinfo.postdata, vecheader, "UTF-8");
							FileUtil.saveFile("", ndinfo.localpathname, "" + data);
						} else if (gzipmode == false && ndinfo.mode == 0) {// no gzip+get
							data = NetUtil.getNetTextNogzip(ndinfo.url, vecheader, "UTF-8");
							FileUtil.saveFile("", ndinfo.localpathname, "" + data);
						} else if (gzipmode == false && ndinfo.mode == 1) {// no gzip+post
							data = NetUtil.getNetPostTextNogzip(ndinfo.url, ndinfo.postdata, vecheader, "UTF-8");
							FileUtil.saveFile("", ndinfo.localpathname, "" + data);
						} else if (gzipmode == true && ndinfo.mode == 2) {// gzip+get
							InputStream is = NetUtil.getNetInputStream(ndinfo.url, vecheader);
							FileUtil.saveFile("", ndinfo.localpathname, is);
							// GZIPInputStream gis=null;
							// try {
							// InputStream is = NetUtil.getNetInputStream(ndinfo.url, vecheader);
							// gis = new GZIPInputStream(is);
							// FileUtil.saveFile("", ndinfo.localpathname, gis);
							// } catch (IOException e) {
							// e.printStackTrace();
							// }
						} else if (gzipmode == false && ndinfo.mode == 2) {// no gzip+get
							InputStream is = NetUtil.getNetInputStream(ndinfo.url, vecheader);
							FileUtil.saveFile("", ndinfo.localpathname, is);
						}
					}
				}
			}
		};
		t.start();
	}

	/**
	 * 直接获取text数据
	 * 
	 * @param url
	 * @return
	 */
	public String getNetText(String url, String postdata, boolean isZip) {

		String data = null;
		if (isZip == true && postdata == null) {
			data = NetUtil.getNetText(url, vecheader, "UTF-8");
		} else if (isZip == true && postdata != null) {
			data = NetUtil.getNetPostText(url, postdata, vecheader, "UTF-8");
		} else if (isZip == false && postdata == null) {
			data = NetUtil.getNetTextNogzip(url, vecheader, "UTF-8");
		} else if (isZip == false && postdata != null) {
			data = NetUtil.getNetPostTextNogzip(url, postdata, vecheader, "UTF-8");
		}
		return data;
	}

}
