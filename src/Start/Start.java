package Start;

import java.util.Timer;
import java.util.TimerTask;

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
		Timer logtime = new Timer();
		logtime.schedule(new TimerTask()
		{
			@Override
			public void run() {
				LOG.logintofile();;
			}			
		},
		1, 2 * 1000);
	}
}

