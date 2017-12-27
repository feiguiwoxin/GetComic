package Start;

import javax.swing.JOptionPane;

import download.DLControl;

public class Start {

	public static void main(String[] args) throws InterruptedException
	{
		//TODO 做成有UI画面的输入框
		String ComicNum = "20671";
		boolean result = DLControl.ThreadControl(ComicNum, 16);
		if(result)
		{
			JOptionPane.showConfirmDialog(null, "下载完成^_^", "说明", JOptionPane.CLOSED_OPTION);
		}
		else
		{
			JOptionPane.showConfirmDialog(null, "下载出现问题-_-!", "说明", JOptionPane.CLOSED_OPTION);
		}
	}
}

