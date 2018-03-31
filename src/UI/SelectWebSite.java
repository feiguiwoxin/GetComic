package UI;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class SelectWebSite extends JComboBox<String>{
	public SelectWebSite()
	{
		this.addItem("漫画柜");
		//this.addItem("你想取的名字");
		//如上添加后就可以在界面的网址按钮中添加一个网址
		this.setSelectedIndex(0);
	}
}
