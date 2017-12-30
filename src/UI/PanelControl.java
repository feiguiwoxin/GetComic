package UI;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DownLoad.DLControl;

@SuppressWarnings("serial")
public class PanelControl extends JPanel{
	private TextComicID ComicId = new TextComicID();
	private ButtonDownLoad buttonDl = new ButtonDownLoad();
	private ButtonAnaly buttonanaly = new ButtonAnaly();
	private SelectThread select = new SelectThread();
	private DLControl dlc = null;
	private ButtonFilePath filepath = new ButtonFilePath();
	private TextFileText filetext = new TextFileText();
	
	public PanelControl()
	{
		initComponent();
		initLayout();
	}
	
	public void setDlc(DLControl dlc)
	{
		this.dlc = dlc;
	}
	
	private void initComponent()
	{
		buttonDl.setControl(this);
		buttonanaly.setControl(this);
		filepath.setControl(this);
	}
	
	private void initLayout()
	{
		this.add(ComicId);
		this.add(select);
		this.add(buttonanaly);
		this.add(buttonDl);
		this.setBounds(5, 10, 430, 30);
		this.setBackground(new Color(144, 238, 144));
		this.setLayout(new GridLayout(1, 4, 5, 0));
	}

	public void StartAnaly(ButtonAnaly buttonanaly)
	{
		//安全性考虑，这样不是由这个PanelControl本身创建的ButtonAnaly对象无法调用此方法。
		if(buttonanaly != this.buttonanaly) return;
		if(ComicId.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "请输入漫画ID", "错误说明", JOptionPane.CLOSED_OPTION);
			return;
		}
		
		if(buttonDl.getDLState())
		{
			JOptionPane.showMessageDialog(null, "正在下载中，暂时无法操作", "错误说明", JOptionPane.CLOSED_OPTION);
			return;
		}
		
		dlc.setComicNum(ComicId.getText());
		if(dlc.AnalyChapter())
		{
			buttonDl.setEnabled(true);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "漫画ID错误或网站已禁止该漫画，请检查", "错误说明", JOptionPane.CLOSED_OPTION);
		}
	}
	
	public boolean StartDL(ButtonDownLoad buttonDl)
	{
		if(buttonDl != this.buttonDl) return false;
		buttonanaly.setEnabled(false);
		select.setEnabled(false);
		dlc.setFilePath(filetext.getText());
		boolean result = dlc.StartDL((int)Math.pow(2, select.getSelectedIndex()) * 4);
		if(false == result)
		{
			buttonanaly.setEnabled(true);
			select.setEnabled(true);
		}
		
		return result;
	}
	
	public void ResetCompnent()
	{
		buttonDl.setEnabled(true);
		buttonDl.setText("下载");
		buttonDl.resetDLStat();
		buttonanaly.setEnabled(true);
		select.setEnabled(true);
	}
	
	public void InterrputDL()
	{
		ResetCompnent();
		dlc.InterrputDL();
	}
	
	public String getFilePath()
	{
		return filetext.getText();
	}
	
	public void setFilePath(String filepath)
	{
		filetext.setText(filepath);
	}

	public ButtonFilePath getButtonFilePath() {
		return filepath;
	}

	public TextFileText getTextFileText() {
		return filetext;
	}
}
