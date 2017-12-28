package Config;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import Start.Start;

public class ValidConfig {
	public static final int WinH = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static final int WinW = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int ComicH = 480;
	public static final int ComicW = 450;
	public static final Image Icon = new ImageIcon(Start.class.getResource("/Comic.png").getPath()).getImage();
	public static final String JSPath = Start.class.getResource("/config.js").getPath();
}
