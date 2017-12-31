package UI;

import Config.ValidConfig;
import GetComic.Chapter;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class FrameComic extends JFrame{
	private JPanel JpScroll = null;
	private LinkedHashMap<Chapter, JCheckBox> Boxs = new LinkedHashMap<Chapter, JCheckBox>();
	CheckBoxAll CheckAll = null;
	private int TaskNum = 0;
	PanelControl pc = null;
	JScrollPane scrollpane = null;
	
	public FrameComic(PanelControl pc)
	{
		this.pc = pc;
		initLayout();
	}
	
	private void initLayout()
	{
		this.setTitle("一键获取漫画工具");
		this.setResizable(false);
		//采取如此奇怪的读取方式是为了在JAR中能读取到img
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("Comic.png")));
		this.setSize(ValidConfig.ComicW, ValidConfig.ComicH);
		this.setLocation((ValidConfig.WinW - ValidConfig.ComicW) / 2, (ValidConfig.WinH - ValidConfig.ComicH) / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container con = this.getContentPane();
		con.setBackground(new Color(144, 238, 144));
		con.setLayout(null);
		con.add(pc);
		
		setScrollUI(con);
		setFileUI(con);
		setHelpUI(con);
	}
	
	private void setHelpUI(Container con)
	{	
		JTextArea ta = new JTextArea();
		ta.setEditable(false);
		ta.setLineWrap(true);
		ta.setFont(new Font("宋体", Font.PLAIN, 15));
		ta.setDisabledTextColor(Color.BLACK);
		ta.setText("本软件只能抓取到http://www.manhuagui.com/该网站的漫画数据(此文本框内可以用CTRL+C进行复制)。\n" + 
				"1.漫画ID是什么？\n" + 
				"进入http://www.manhuagui.com/网站后，搜索到你想要的漫画，点击进入漫画页面。比如http://www.manhuagui.com/comic/1626/这个漫画，则漫画ID就是1626。\n"
				+ "另外即使是网页显示因版权而被屏蔽的漫画也可以试一下哦，但切记不要一天之内下载过多此类被禁漫画，很容易被网站后台发现而封IP。\n" + 
				"2.下载失败后，如何判断是不是被封IP了？如何解决？\n" + 
				"可以尝试在浏览器上打开http://www.manhuagui.com/，如果发现无法进入，则可以使用你的手机（注意请不要使用和电脑一样的wifi网络，使用你的无线网络或其他网络）尝试登录该网站，如果手机可以进入而电脑不行，则说明确实被封IP了。\n" + 
				"封IP不要紧张，如果你是拨号上网的用户，那么重新拨号就可以获取到新IP。如果你是光纤用户，则需要断电重启一下光钎盒子。\n" + 
				"3.其他说明\n" + 
				"1）如果出现下载不动的情况（进度一直没动），可以中断一下然后继续点下载，这并不会重新下载已下载文件，而是会重新建立连接下载（就像用迅雷等软件下载没速度重连一下可能会好一样的道理），可能会加快下载速度\n" + 
				"2）如果中断后发现选择章节界面是灰色的，这时候可以直接关闭程序，然后重新打开重新分析下载，只要你不改动保存位置也不删除已下载文件，那么对于已下载内容同样会直接跳过，不会重新下载。");
		
		JScrollPane sp = new JScrollPane(ta);
		sp.setBounds(5, 450, 430, 115);
		con.add(sp);
	}
	
	private void setFileUI(Container con)
	{
		JPanel filepanel = new JPanel();
		filepanel.setBounds(5, 45, 430, 30);
		filepanel.setBackground(new Color(144, 238, 144));
		filepanel.setLayout(null);
		
		filepanel.add(pc.getTextFileText());
		filepanel.add(pc.getButtonFilePath());
			
		con.add(filepanel);
		
	}
	
	private void setScrollUI(Container con)
	{
		JpScroll = new JPanel();
		JpScroll.setLayout(new BoxLayout(JpScroll, BoxLayout.Y_AXIS));
		scrollpane = new JScrollPane(JpScroll);
		scrollpane.getVerticalScrollBar().setUnitIncrement(15);
		scrollpane.setBounds(5, 80, 430, 365);				
		con.add(scrollpane);		
	}
	
	public void addChapters(ArrayList<Chapter> Chapters)
	{
		JpScroll.removeAll();
		scrollpane.getVerticalScrollBar().setValue(0);
		Boxs.clear();
		CheckAll = new CheckBoxAll("全选", this);
		JpScroll.add(CheckAll);
		for(Chapter chapter : Chapters)
		{
			JCheckBox tmp = new JCheckBox(chapter.getTitle());
			tmp.setSelected(true);
			JpScroll.add(tmp);
			Boxs.put(chapter, tmp);
		}
		JpScroll.updateUI();
	}
	
	public ArrayList<Chapter> getDLInfo()
	{
		this.TaskNum = 0;
		ArrayList<Chapter> info = new ArrayList<Chapter>();
		Set<Chapter> keys = Boxs.keySet();
		for(Chapter key : keys)
		{
			JCheckBox cb = Boxs.get(key);
			if(cb.isSelected() && cb.isEnabled())
			{
				info.add(key);
				TaskNum ++;
			}
		}
		
		return info;
	}
	
	public void SelectAll()
	{
		Set<Chapter> keys = Boxs.keySet();
		for(Chapter key : keys)
		{
			Boxs.get(key).setSelected(true);
		}
		
		JpScroll.updateUI();
		return;
	}
	
	public void DeSelectAll()
	{
		Set<Chapter> keys = Boxs.keySet();
		for(Chapter key : keys)
		{
			Boxs.get(key).setSelected(false);
		}
		
		JpScroll.updateUI();
		return;
	}
	
	public void disableAllbox()
	{
		Set<Chapter> keys = Boxs.keySet();
		CheckAll.setEnabled(false);
		for(Chapter key : keys)
		{
			Boxs.get(key).setEnabled(false);;
		}
		
		JpScroll.updateUI();
		return;
	}
	
	public void UpdateDLinfo(Chapter chapter,int curr,int all)
	{
		JCheckBox box = Boxs.get(chapter);
		String currtext = box.getText();
		int index = currtext.indexOf("任务进展:");
		if(-1 != index)
		{
			currtext = currtext.substring(0, index - 1);
		}

		box.setText(currtext + " 任务进展:" + curr + "/" + all);
		box.updateUI();
	}
	
	public void FinishDl(Chapter chapter, int type)
	{
		TaskNum --;
		JCheckBox box = Boxs.get(chapter);
		String currtext = box.getText();
		int index = currtext.indexOf("任务进展:");
		if(-1 != index)
		{
			currtext = currtext.substring(0, index - 1);
		}
		
		if(type == 1)
		{
			box.setText(currtext + " 下载完成喽~");
			box.setForeground(Color.RED);
		}
		else if(type == 0)
		{
			box.setText(currtext + " 任务进展:任务失败,可以尝试再次下载，不会影响已下图片" );
		}
		
		box.updateUI();
		if(TaskNum == 0)
		{
			pc.ResetCompnent();
			ActiveAllbox();
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "下载完成^_^", "说明", JOptionPane.CLOSED_OPTION);
		}
	}
	
	public void ActiveAllbox()
	{
		Set<Chapter> keys = Boxs.keySet();
		CheckAll.setEnabled(true);
		for(Chapter key : keys)
		{
			if(Boxs.get(key).getForeground() == Color.RED)
			{
				continue;
			}
			
			Boxs.get(key).setEnabled(true);
		}
		
		JpScroll.updateUI();
		return;
	}
	
	public void InterrputDL()
	{
		ActiveAllbox();
		this.TaskNum = 0;
	}
}

