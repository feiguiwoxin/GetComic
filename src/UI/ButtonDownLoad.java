package UI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import DownLoad.DLControl;

@SuppressWarnings("serial")
public class ButtonDownLoad extends JButton{
	private boolean DLState = false;
	private PanelControl pc= null;
	
	public void setControl(PanelControl pc)
	{
		this.pc = pc;
	}
	
	public ButtonDownLoad()
	{
		this.setText("下载");
		this.setEnabled(false);
		this.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if(ButtonDownLoad.this.isEnabled() && ButtonDownLoad.this.getText().equals("下载"))
				{
					ButtonDownLoad.this.setText("中断");
					DLState = true;
					DLControl.RunThread = true;
					boolean result = pc.StartDL(ButtonDownLoad.this);
					if(false == result)
					{
						ButtonDownLoad.this.setEnabled(true);
						ButtonDownLoad.this.setText("下载");
						DLState = false;
					}					
				}
				else if(!ButtonDownLoad.this.isEnabled() && ButtonDownLoad.this.getText().equals("下载"))
				{
					JOptionPane.showMessageDialog(null, "请先分析以获取章节信息", "错误说明", JOptionPane.CLOSED_OPTION);
				}
				else if(!ButtonDownLoad.this.isEnabled() && ButtonDownLoad.this.getText().equals("下载中"))
				{
					JOptionPane.showMessageDialog(null, "就算你再怎么点，也没办法加快下载速度呀-_-!", "错误说明", JOptionPane.CLOSED_OPTION);
				}
				else if(ButtonDownLoad.this.getText().equals("中断"))
				{			
					pc.InterrputDL();
				}	
			}
		});
	}

	public boolean getDLState() {
		return DLState;
	}
	
	public void resetDLStat()
	{
		this.DLState = false;
	}
}
