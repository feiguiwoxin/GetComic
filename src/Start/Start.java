package Start;

import DownLoad.DLControl;
import UI.FrameComic;
import UI.PanelControl;

public class Start {

	public static void main(String[] args) throws InterruptedException
	{
		PanelControl pc = new PanelControl();
		FrameComic fc = new FrameComic(pc);
		DLControl dlc = new DLControl(fc);
		pc.setDlc(dlc);
		fc.setVisible(true);		
	}
}

