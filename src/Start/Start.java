package Start;

import java.io.IOException;

import javax.swing.JOptionPane;

import Log.LOG;
import download.DLControl;

public class Start {

	public static void main(String[] args) throws InterruptedException
	{
		//TODO 做成有UI画面的输入框
		String ComicNum = "456465465465";
		boolean result = DLControl.ThreadControl(ComicNum, 16);
		boolean logRst = LOG.LogIntoFile("/Comic/" + "log.txt");
		if(!logRst)
		{
			JOptionPane.showConfirmDialog(null, "创建日志文件错误，请检查路径是否配置成功", "错误说明", JOptionPane.CLOSED_OPTION);
		}
		if(result)
		{
			JOptionPane.showConfirmDialog(null, "下载完成^_^", "说明", JOptionPane.CLOSED_OPTION);
		}
		else
		{
			int Select = JOptionPane.showConfirmDialog(null, "下载出现问题-_-!\n是否要打开错误说明日志", "说明", JOptionPane.OK_CANCEL_OPTION);
			if(Select == JOptionPane.OK_OPTION)
			{
				try {
					Runtime.getRuntime().exec(new String[] {"cmd.exe", "/c", "/Comic/" + "log.txt"});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
}

