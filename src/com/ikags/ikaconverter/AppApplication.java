package com.ikags.ikaconverter;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import com.ikags.ikaconverter.task.Json2csvTask;
import com.ikags.ikaconverter.task.Xml2csvTask;
import com.ikags.utils.CommonUtil;
/**
 * 主要界面
 * @author airzhangfish
 *
 */
public class AppApplication extends JFrame implements ActionListener {

	String mVerion="-Version 1.0.0";
	String titlename = "Ika netfile crawler    "+mVerion;
	// 关于
	private String aboutStr ="Creator by \n airzhangfish";

	private static final long serialVersionUID = 1L;
	private JTabbedPane jtp;
	
	private JMenuBar jMenuBar1 = new JMenuBar();
	private JMenu jMenuFile = new JMenu("File");
	private JMenuItem jMenuFileExit = new JMenuItem("Exit");
	private JMenuItem JMenutools = new JMenu("Tools");
	private JMenu jMenuHelp = new JMenu("Help");
	private JMenuItem jMenuHelpAbout = new JMenuItem("About");
	private JMenuItem jMenuHelpHomepage = new JMenuItem("Homepage");

	private JMenuItem jMenuFileLoad1 = new JMenuItem("Read Json2csv file...");
	private JMenuItem jMenuFileLoad2 = new JMenuItem("Read multi json files 2 one json file...");
	
	private JMenuItem jMenuFileLoad3 = new JMenuItem("Read xml2csv file...");
	private JMenuItem jMenuFileLoad4 = new JMenuItem("Read multi xml files 2 one xml file...");

	Json2csvTask j2t=new Json2csvTask();
	Xml2csvTask x2t=new Xml2csvTask();
	public void actionPerformed(ActionEvent actionEvent) {
		Object source = actionEvent.getSource();


		if (source == jMenuFileLoad1) {
			FileDialog xs = new FileDialog(this, "load json file", FileDialog.LOAD);
			xs.setMultipleMode(false);
			xs.setFile("*");
			xs.setVisible(true);
			String f = xs.getFile();
			String lastDir = xs.getDirectory();
			if (f != null) {
			j2t.setFilePath(lastDir, f);
			}
		}
		
		

		if (source == jMenuFileLoad2) {
			FileDialog xs = new FileDialog(this, "load json files", FileDialog.LOAD);
			xs.setMultipleMode(true);
			xs.setFile("*");
			xs.setVisible(true);
			File[] fs = xs.getFiles();
			String lastDir = xs.getDirectory();
			if (fs != null) {
			j2t.setFilesPath(lastDir, fs);
			}
		}
	
		
		if (source == jMenuFileLoad3) {
			FileDialog xs = new FileDialog(this, "load xml file", FileDialog.LOAD);
			xs.setMultipleMode(false);
			xs.setFile("*");
			xs.setVisible(true);
			String f = xs.getFile();
			String lastDir = xs.getDirectory();
			if (f != null) {
				x2t.setFilePath(lastDir, f);
			}
		}
		
		

		if (source == jMenuFileLoad4) {
			FileDialog xs = new FileDialog(this, "load xml files", FileDialog.LOAD);
			xs.setMultipleMode(true);
			xs.setFile("*");
			xs.setVisible(true);
			File[] fs = xs.getFiles();
			String lastDir = xs.getDirectory();
			if (fs != null) {
				x2t.setFilesPath(lastDir, fs);
			}
		}
	
		

		// 关于
		if (source == jMenuHelpAbout) {
			JOptionPane.showMessageDialog(this, aboutStr, "About", JOptionPane.INFORMATION_MESSAGE);
		}
		// 软件退出
		if (source == jMenuFileExit) {
			System.exit(0);
		}
		// 打开作者主页
		if (source == jMenuHelpHomepage) {
			CommonUtil.browserURL("https://www.baidu.com");
		}
	}

	public AppApplication() {

		this.setSize(1024, 768); // 窗体的大小
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true); // 窗体
		this.setTitle(titlename); // 设置标题

		enableInputMethods(true);

		JMenutools.add(jMenuFileLoad1);
		JMenutools.add(jMenuFileLoad2);
		JMenutools.add(jMenuFileLoad3);
		JMenutools.add(jMenuFileLoad4);
		jMenuFile.add(jMenuFileExit);
		jMenuFileLoad1.addActionListener(this);
		jMenuFileLoad2.addActionListener(this);
		jMenuFileLoad3.addActionListener(this);
		jMenuFileLoad4.addActionListener(this);
		jMenuFileExit.addActionListener(this);
		
		
		jMenuHelp.add(jMenuHelpAbout);
		jMenuHelpAbout.addActionListener(this);
		jMenuHelp.add(jMenuHelpHomepage);
		jMenuHelpHomepage.addActionListener(this);
		// 总工具栏

		jMenuBar1.add(jMenuFile);
		jMenuBar1.add(JMenutools); 
		jMenuBar1.add(jMenuHelp);
		this.setJMenuBar(jMenuBar1);

		
	
		JTextArea textArea = new JTextArea();
		Font font = new Font("simhei",0,20);
		textArea.setFont(font);
		textArea.setEditable(false);
		
		LocalDateTime localDateTime = LocalDateTime.now();
		textArea.setText(localDateTime+"> command mode start...\n");
	
		j2t.setTextArea(textArea);
		x2t.setTextArea(textArea);
		
		JScrollPane jsp=new JScrollPane(textArea);

		Container contents = getContentPane();
		contents.add(jsp);
		setVisible(true);
	}

	public static void main(String args[]) {
		CommonUtil.setMySkin(3);
		new AppApplication();
	}
	

}
