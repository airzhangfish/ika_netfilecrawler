# ika_netfilecrawler
net file crawler<br>
具备两个模块。 其中Application有GUI界面
用于下面几个功能：<br>
1.json2csv<br>
2.多json合并成一个json<br>
3.xml2csv （属性）<br>
4.多xml合并成一个xml<br>
<br>


另外通过导入本代码，使用工具类（主要对外提供jar包使用），可以爬数据：<br>
示例代码：<br>
//初始化<br>
IKANetCrawlerManager manager=new IKANetCrawlerManager();<br>
manager.setGZIPMode(true);<br>
manager.setMitlThread(3);<br>
<br>
//头部<br>
manager.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");<br>
manager.addHeader("Accept-Encoding", "gzip,deflate");<br>
manager.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_2; en-US) AppleWebKit/533.3 (KHTML, like Gecko) Chrome/5.0.354.0 Safari/533.3");<br>
<br>
//下载地址添加<br>
manager.addNetText("https://gz.startcarlife.com/find-car?sort=all&make=car_15&module=m3",null, "C:\\Users\\airzhangfish\\Desktop\\test\\textget.html", true,true);<br>
manager.addNetText("http://bbs.a9vg.com/forum.php", "sort=all","C:\\Users\\airzhangfish\\Desktop\\test\\textpost.html",true, true);<br>
manager.addNetText("https://icdn.startcarlife.com/img/1708/1/1708166fb02049x1.jpg",null, "C:\\Users\\airzhangfish\\Desktop\\test\\textfile.jpg",false, true);<br>
<br>
//开始下载<br>
manager.startNetDownload();<br>
<br>
//其他方法：直接下载文本<br>
String text=manager.getNetText("https://gz.startcarlife.com/find-car?sort=all&make=car_15&module=m3", null, true);<br>
<br>
//其他方法：正则提取<br>
Vector<String> vec=IKARegularManager.readTextParten(text,"([0-9]*)(/日均)",1);<br>
<br>
//其他方法：保存数据<br>
StringBuffer sb=new StringBuffer();<br>
for(int i=0;i<vec.size();i++){<br>
sb.append(vec.get(i)+",\r\n");<br>
}<br>
FileUtil.saveFile("", "C:\\Users\\airzhangfish\\Desktop\\cece.csv", sb.toString());<br>
<br>
/**<br>
 * 扫描本地下载数据，提取需要的数据<br>
 */<br>
private void scannerData(){<br>
String detailfilepath="C:\\Users\\airzhangfish\\Desktop\\cardetail3\\";<br>
File filelist = new File(detailfilepath);<br>
String[] htmlfiles = filelist.list();<br>
for (int page = 0; page < htmlfiles.length; page++) {<br>
String data = StringUtil.getLocalFileText(detailfilepath + htmlfiles[page]);<br>
//分析数据<br>
String tag = StringUtil.getTextContent(data, "<em>", "</em>");<br>
tag = tag.replaceAll("\n", "").trim(); //清洗格式<br>
}<br>
//保存<br>
FileUtil.saveFile(detailfilepath, "savefilename.csv", "XXXXX");<br>
}<br>
