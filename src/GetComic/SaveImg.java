package GetComic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JOptionPane;
//该类传入一个图片的地址，用于保存该图片
public class SaveImg {
	private String UrlAdd = null;
	private String Path = null;
	private String FileName = null;
	
	public SaveImg(String UrlAdd, String Path, String FileName)
	{
		this.UrlAdd = UrlAdd;
		this.Path = Path;
		this.FileName = FileName;
	}
	//将图片的二进制流写入文件，返回是否保存成功
	public boolean SavePicture()
	{
		if(null == Path || null == FileName) return false;
		
		byte[] imgData = getImgData();
		if(null == imgData) return false;
		
		File file = new File(Path + FileName);
		FileOutputStream fop = null;
		if(file.exists()) file.delete();
		try {
			try {
				fop = new FileOutputStream(file);
				fop.write(imgData);
				fop.flush();
			} catch (Exception e) {
				JOptionPane.showConfirmDialog(null, "保存文件失败，请检查配置文件夹或磁盘空间是否已满！", "错误提示", 
												JOptionPane.CLOSED_OPTION);
				e.printStackTrace();
				return false;
			}
			finally
			{
				fop.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return true;
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
			urlcon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36");
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
		
		byte[] buffer = new byte[1024];
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
