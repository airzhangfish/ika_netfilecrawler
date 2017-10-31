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
				String mmurl = "http://www.XXXXXX.com/car/reDetail/" + carid+".html";
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
	
