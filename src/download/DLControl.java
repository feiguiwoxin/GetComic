package download;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import GetComic.Chapter;
import GetComic.GetChapter;

public class DLControl {
		public static boolean ThreadControl(String ComicNum, int PoolSize) throws InterruptedException
		{
			ArrayList<Chapter> Chapters = new GetChapter(ComicNum).getChapter();
			if(Chapters.isEmpty())
			{
				JOptionPane.showConfirmDialog(null, "分解章节失败，需要重新适配网站", "错误说明", JOptionPane.CLOSED_OPTION);
				//TODO 后续写入到日志当中
				System.out.println("分解章节失败，需要重新适配");
				return false;
			}
			
			File HeadDir = new File("/Comic/" + ComicNum);
			
			if(HeadDir.exists() && HeadDir.isDirectory())
			{
				HeadDir.delete();
			}
			
			if(!HeadDir.mkdirs())
			{
				JOptionPane.showConfirmDialog(null, "创建初始文件夹失败，请检查磁盘是否已满/地址错误/文件夹已存在", "错误说明", JOptionPane.CLOSED_OPTION);
				//TODO 后续写入到日志当中
				System.out.println("创建初始文件夹失败，请检查磁盘是否已满/地址错误/文件夹已存在");
				return false;
			}
			
			ExecutorService fixpool = Executors.newFixedThreadPool(PoolSize);
			
			for(Chapter c : Chapters)
			{	
				DownLoad dl = new DownLoad(c.getHtml(), c.getTitle(), HeadDir.getAbsolutePath());
				fixpool.execute(dl);
			}
			
			fixpool.shutdown();
			
			while(true)
			{
				if(fixpool.isTerminated())
				{
					return true;
				}
				Thread.sleep(2 * 1000);
			}
		}
}
