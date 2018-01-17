package UI;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class TextComicID extends JTextField{
	
	public TextComicID()
	{
		this.setText("请输入漫画ID");
		this.setForeground(Color.BLUE);
		this.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if(TextComicID.this.getText().equals("请输入漫画ID"))
				{
					TextComicID.this.setText("");
					TextComicID.this.setForeground(Color.BLACK);
				}				
			}
		});
		
		this.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				if(TextComicID.this.getText().equals("请输入漫画ID"))
				{
					TextComicID.this.setText("");
					TextComicID.this.setForeground(Color.BLACK);
				}
				int keychar = e.getKeyChar();
				if(!(keychar >= KeyEvent.VK_0 && keychar <= KeyEvent.VK_9))
				{
					e.consume();
				}
			}
		});
	}
}
