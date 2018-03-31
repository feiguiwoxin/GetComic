package UI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class ButtonFilePath extends JButton{
	private PanelControl pc= null;
	
	public void setControl(PanelControl pc)
	{
		this.pc = pc;
	}
	
	public ButtonFilePath()
	{
		this.setText("选择");
		this.setBounds(392, 0, 93, 30); 
		this.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (!ButtonFilePath.this.isEnabled()) return;
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(pc.getFilePath()));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showOpenDialog(null);
				if(chooser.getSelectedFile() != null)
				{
					String SelectedFile = chooser.getSelectedFile().getAbsolutePath();
					pc.setFilePath(SelectedFile);
				}				
			}
		});
	}
}
