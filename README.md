# ika_netfilecrawler
net file crawler<br>
具备两个模块。 其中Application有GUI界面
用于下面几个功能：<br>
1.json2csv<br>
2.多json合并成一个json<br>
3.xml2csv （属性）<br>
4.多xml合并成一个xml<br>
<br>


另外通过导入本代码，使用工具类（主要对外提供jar包使用），可以爬数据：
  示例代码：
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
