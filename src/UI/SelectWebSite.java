package UI;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class SelectWebSite extends JComboBox<String>{
	public SelectWebSite()
	{
		for(ComicWebInfo cw : UIConfig.ComicWebList)
		{
			this.addItem(cw.getname());
		}
		this.setSelectedIndex(0);
	}
}
