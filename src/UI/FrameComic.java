package UI;

import Config.ValidConfig;
import GetComic.Chapter;

import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class FrameComic extends JFrame{
	private JPanel JpScroll = null;
	private LinkedHashMap<Chapter, JCheckBox> Boxs = new LinkedHashMap<Chapter, JCheckBox>();
	CheckBoxAll CheckAll = null;
	private int TaskNum = 0;
	PanelControl pc = null;
	
	public FrameComic(PanelControl pc)
	{
		this.pc = pc;
		initLayout();
	}
	
	public void initLayout()
	{
		this.setTitle("一键获取漫画工具");
		this.setResizable(false);
		this.setIconImage(ValidConfig.Icon);
		this.setSize(ValidConfig.ComicW, ValidConfig.ComicH);
		this.setLocation((ValidConfig.WinW - ValidConfig.ComicW) / 2, (ValidConfig.WinH - ValidConfig.ComicH) / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container con = this.getContentPane();
		con.setBackground(new Color(144, 238, 144));
		con.setLayout(null);
		con.add(pc);
		
		setScrollUI(con);
	}	
	
	private void setScrollUI(Container con)
	{
		JpScroll = new JPanel();
		JpScroll.setLayout(new BoxLayout(JpScroll, BoxLayout.Y_AXIS));
		JScrollPane scrollpane = new JScrollPane(JpScroll);
		scrollpane.getVerticalScrollBar().setUnitIncrement(15);
		scrollpane.setBounds(5, 45, 430, 400);				
		con.add(scrollpane);		
	}
	
	public void addChapters(ArrayList<Chapter> Chapters)
	{
		JpScroll.removeAll();
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
	
	public void FinishDl(Chapter chapter)
	{
		TaskNum --;
		JCheckBox box = Boxs.get(chapter);
		String currtext = box.getText();
		int index = currtext.indexOf("任务进展:");
		if(-1 != index)
		{
			currtext = currtext.substring(0, index - 1);
		}
		
		box.setText(currtext + " 下载完成喽~");
		box.updateUI();
		box.setForeground(Color.RED);
		if(TaskNum == 0)
		{
			pc.ResetCompnent();
			ActiveAllbox();
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
}

