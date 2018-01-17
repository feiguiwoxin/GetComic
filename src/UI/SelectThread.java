package UI;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class SelectThread extends JComboBox<String>{
	
	public SelectThread()
	{
		this.addItem("4线程");
		this.addItem("8线程");
		this.addItem("16线程(推荐)");
		this.addItem("32线程(慎用)");
		this.setSelectedIndex(2);
	}
}
