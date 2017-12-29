package GetComic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//该类传入一个图片的地址，用于保存该图片
public class SaveImg {
	private String UrlAdd = null;
	private String Path = null;
	private String FileName = null;
	private boolean interrput = false;
	
	public SaveImg(String UrlAdd, String Path, String FileName)
	{
		this.UrlAdd = UrlAdd;
		this.Path = Path;
		this.FileName = FileName;
	}
	//将图片的二进制流写入文件，返回是否保存成功
	public int SavePicture()
	{
		if(null == Path || null == FileName) return 0;
		//提前判断文件是否存在，如果存在直接跳过下载，加快续传速度
		File file = new File(Path + FileName);
		if(file.exists())
		{
			return 1;
		}
		
		byte[] imgData = getImgData();
		if(null == imgData) return 0;
		
		FileOutputStream fop = null;
		
		try {
			try {
				fop = new FileOutputStream(file);
				if(interrput) 
				{
					fop.close();
					file.delete();
				}
				
				fop.write(imgData);
				fop.flush();
			} catch (IOException e) {
				if(!interrput)
				{
					System.out.println("保存文件失败，请检查配置文件夹或磁盘空间是否已满！" + file);
					e.printStackTrace();
				}
				else
				{
					return 2;
				}
				return 0;
			}
			finally
			{
				fop.close();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return 1;
	}
	//获取图片的二进制流
	private byte[] getImgData()
	{
		byte[] ImgData = null;
		if(null == UrlAdd) return null;
		try {
			URL url = new URL(UrlAdd);
			HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
			urlcon.setRequestProperty("Referer", "http://www.manhuagui.com/");
			urlcon.setRequestMethod("GET");
			urlcon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; …) Gecko/20100101 Firefox/57.0");
			urlcon.setConnectTimeout(5*1000);
			
			ImgData = GetbyteFromStream(urlcon.getInputStream());
			return ImgData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//将图片的Http流转化为二进制流
	private byte[] GetbyteFromStream(InputStream in) throws IOException
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
	
	public void setInterrput()
	{
		interrput = true;
	}

}
