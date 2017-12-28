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
		this.setText("·ÖÎö");
		this.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				pc.StartAnaly(ButtonAnaly.this);
			}
		});
	}
}
