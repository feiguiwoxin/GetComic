package UI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class ButtonAnaly extends JButton{
	private PanelControl pc= null;
	
	public void setControl(PanelControl pc)
	{
		this.pc = pc;
	}
	
	public ButtonAnaly()
	{
		this.setText("获取章节");
		this.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if(ButtonAnaly.this.isEnabled())
				{	
					ButtonAnaly.this.setText("获取中....");
					ButtonAnaly.this.setEnabled(false);
					ButtonAnaly.this.paintImmediately(0, 0, ButtonAnaly.this.getWidth(), ButtonAnaly.this.getHeight());
					pc.StartAnaly(ButtonAnaly.this);
				}
			}
		});
	}
}
