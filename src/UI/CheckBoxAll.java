package UI;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class CheckBoxAll extends JCheckBox{
	public CheckBoxAll(String text, FrameComic fc)
	{
		this.setText(text);
		this.setSelected(true);
		
		this.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(CheckBoxAll.this.getText().equals("全选"))
				{
					if(ItemEvent.SELECTED == e.getStateChange())
					{
						fc.SelectAll();
					}
					else
					{
						fc.DeSelectAll();
					}
				}		
			}
			
		});
	}
	
	
}
