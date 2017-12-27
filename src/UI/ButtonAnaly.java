package UI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class ButtonAnaly extends JButton{
	private TextComicID ComicId = new TextComicID();
	private ButtonDownLoad buttonDl = new ButtonDownLoad();
	
	public ButtonAnaly()
	{
		this.setText("分析");
		this.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(ComicId.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "请输入漫画ID", "错误说明", JOptionPane.CLOSED_OPTION);
				}
				
				if(buttonDl.getDLState())
				{
					JOptionPane.showMessageDialog(null, "正在下载中，暂时无法操作", "错误说明", JOptionPane.CLOSED_OPTION);
					return;
				}
				
				buttonDl.getDlc().setComicNum(ComicId.getText());
				if(buttonDl.getDlc().AnalyChapter())
				{
					buttonDl.setEnabled(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "漫画ID错误，请检查", "错误说明", JOptionPane.CLOSED_OPTION);
				}
			}
		});
	}
	
	public TextComicID getComicId()
	{
		return ComicId;
	}

	public ButtonDownLoad getButtonDl() {
		return buttonDl;
	}
}
