package UI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import DownLoad.DLControl;

@SuppressWarnings("serial")
public class ButtonDownLoad extends JButton{

	private DLControl dlc = new DLControl();
	private SelectThread select = new SelectThread();
	
	private boolean DLState = false;

	public ButtonDownLoad()
	{
		this.setText("下载");
		this.setEnabled(false);
		this.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(ButtonDownLoad.this.isEnabled() && ButtonDownLoad.this.getText().equals("下载"))
				{
					dlc.setPoolSize((int)Math.pow(2, select.getSelectedIndex()) * 4);
					ButtonDownLoad.this.setEnabled(false);
					ButtonDownLoad.this.setText("下载中");
					DLState = true;
					//dlc.ThreadControl();
				}
				else if(!ButtonDownLoad.this.isEnabled() && ButtonDownLoad.this.getText().equals("下载"))
				{
					JOptionPane.showMessageDialog(null, "请先分析以获取章节信息", "错误说明", JOptionPane.CLOSED_OPTION);
				}
				else if(!ButtonDownLoad.this.isEnabled() && ButtonDownLoad.this.getText().equals("下载中"))
				{
					JOptionPane.showMessageDialog(null, "就算你再怎么点，也没办法加快下载速度呀-_-!", "错误说明", JOptionPane.CLOSED_OPTION);
				}
			}
		});
	}

	public DLControl getDlc() {
		return dlc;
	}

	public boolean getDLState() {
		return DLState;
	}

	public SelectThread getSelect() {
		return select;
	}
}
