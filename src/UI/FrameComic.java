package UI;

import Config.ValidConfig;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class FrameComic extends JFrame{
	
	public FrameComic()
	{
		this.setTitle("一键获取漫画工具");
		this.setResizable(false);
		this.setIconImage(ValidConfig.Icon);
		this.setSize(ValidConfig.ComicW, ValidConfig.ComicH);
		this.setLocation((ValidConfig.WinW - ValidConfig.ComicW) / 2, (ValidConfig.WinH - ValidConfig.ComicH) / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container con = this.getContentPane();
		con.setBackground(new Color(144, 238, 144));
		
		JPanel JpUp = new JPanel();
		JpUp.setBounds(10, 10, 425, 30);
		ButtonAnaly buttonanaly = new ButtonAnaly();
		JpUp.add(buttonanaly.getComicId());
		JpUp.add(buttonanaly.getButtonDl().getSelect());
		JpUp.add(buttonanaly);
		JpUp.add(buttonanaly.getButtonDl());
		JpUp.setBackground(new Color(144, 238, 144));
		JpUp.setLayout(new GridLayout(1, 4, 10, 0));
		
		con.setLayout(null);
		con.add(JpUp);
		
		this.setVisible(true);
	}
	
}

