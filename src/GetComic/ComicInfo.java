package GetComic;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import Config.LOG;
/******************************
 * 在抽象类中增加2个公共方法：
 * 1）获取网页信息的方法，这在获取章节和图片地址时必然会用到
 * 2）保存图片的方法，这在下载图片的时候也必然会用到
 * 3) 获取书名，这在设置保存文件夹的时候可以被用到
 ******************************/
public abstract class ComicInfo implements Comic{
	protected String BookName = null;
	
	public String getBookName()
	{
		return BookName; 
	}
	
	public String GetHtmlInfo(String UrlAdd)
	{
		String HttpInfo = null;
		if(null == UrlAdd) return null;
		String line;
		URL url;
		
		try {
			url = new URL(UrlAdd);
			URLConnection urlcon = url.openConnection();
			urlcon.setRequestProperty("accept", "*/*");
			urlcon.setRequestProperty("connection", "Keep-Alive");
			urlcon.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; …) Gecko/20100101 Firefox/57.0");
			urlcon.setConnectTimeout(3 * 1000);
			urlcon.setReadTimeout(3 * 1000);
			urlcon.connect();
			BufferedReader urlRead = new BufferedReader(new InputStreamReader(urlcon.getInputStream(), "utf-8"));
			while((line = urlRead.readLine()) != null)
			{
				HttpInfo += line;
			}
			urlRead.close();
		} catch (Exception e) {
			LOG.log("获取网址信息错误:" + UrlAdd + " e:" + e.getMessage(), LOG.NormalType);
			e.printStackTrace();
			return null;
		}
		return HttpInfo;
	}
	
	public static int SavePicture(String UrlAdd, String Path, String FileName)
	{
		if(null == Path || null == FileName) return 0;
		//提前判断文件是否存在，如果存在直接跳过下载，加快续传速度
		File file = new File(Path + FileName);
		if(file.exists())
		{
			return 1;
		}
		
		byte[] imgData = getImgData(UrlAdd);
		if(null == imgData) return 0;
		
		FileOutputStream fop = null;
		
		try {
			try {
				fop = new FileOutputStream(file);
				fop.write(imgData);
				fop.flush();
			} catch (Exception e) {
				e.printStackTrace();
				LOG.log(e.getMessage(), LOG.NormalType);
				return 0;
			}
			finally
			{
				fop.close();
			}
		}
		catch(Exception e)
		{
			LOG.log(e.getMessage(), LOG.NormalType);
			e.printStackTrace();
		}
		
		return 1;
	}
	//获取图片的二进制流
	private static byte[] getImgData(String UrlAdd)
	{
		byte[] ImgData = null;
		//如果连接失败，有3次重连机会
		int ConnectTime = 3;
		if(null == UrlAdd) return null;
		
		while(ConnectTime > 0)
		{
			try {
				URL url = new URL(UrlAdd);
				HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
				urlcon.setRequestProperty("Referer", "http://www.manhuagui.com/");
				urlcon.setRequestMethod("GET");
				urlcon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; …) Gecko/20100101 Firefox/57.0");
				urlcon.setConnectTimeout(5 * 1000);
				urlcon.setReadTimeout(8 * 1000);
				
				ImgData = GetbyteFromStream(urlcon.getInputStream());
				return ImgData;
			} catch (Exception e) {
				e.printStackTrace();
				LOG.log(e.getMessage() + "ConnectTime:" + ConnectTime, LOG.NormalType);
				ConnectTime --;
				continue;
			}
		}
		
		return null;
	}
	//将图片的Http流转化为二进制流
	private static byte[] GetbyteFromStream(InputStream in) throws IOException
	{
		int len;
		
		byte[] buffer = new byte[3 * 100 * 1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while((len = in.read(buffer)) != -1)
		{
			out.write(buffer, 0 ,len);
		}
	
		byte[] imgData = out.toByteArray();
		in.close();
		out.close();
		
		return imgData;
	}
}
