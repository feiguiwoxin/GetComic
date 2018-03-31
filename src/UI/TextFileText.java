package UI;

import java.io.File;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class TextFileText extends JTextField{
	
	public TextFileText()
	{
		this.setBounds(0, 0, 387, 30);
		this.setText((new File(".")).getAbsolutePath());
	}
}
