package Start;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import Config.LOG;
import DownLoad.DLControl;
import UI.FrameComic;
import UI.PanelControl;

public class Start {

	public static void main(String[] args) throws InterruptedException
	{
		LOG.log(new Date().toString(), LOG.NormalType);
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
				LOG.log("", LOG.PeriodType);
			}			
		},
		1, 2 * 1000);
	}
}

