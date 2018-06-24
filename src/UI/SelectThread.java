package UI;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class SelectThread extends JComboBox<String>{
	
	public SelectThread()
	{
		this.addItem("4线程");
		this.addItem("8线程");
		this.setSelectedIndex(0);
	}
}
