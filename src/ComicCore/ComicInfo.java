package ComicCore;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import Start.LOG;
/******************************
 * 在抽象类中增加2个公共方法：
 * 1）获取网页信息的方法，这在获取章节和图片地址时必然会用到
 * 2）保存图片的方法，这在下载图片的时候也必然会用到
 ******************************/
public abstract class ComicInfo implements Comic{
	protected String BookName = null;
	
	public String GetBookName()
	{
		return BookName; 
	}
	
	public String GetHtmlInfo(String UrlAdd)
	{
		String HttpInfo = null;
		if(null == UrlAdd) return null;
		String line;
		URL url;
		int trytime = 5;
		
		while(trytime > 0)
		{
			try {
				url = new URL(UrlAdd);
				URLConnection urlcon = url.openConnection();
				if(UrlAdd.startsWith("https"))
				{
					trustall((HttpsURLConnection) urlcon);
				}
				
				urlcon.setRequestProperty("accept", "*/*");
				urlcon.setRequestProperty("connection", "Keep-Alive");
				urlcon.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0");
				urlcon.setConnectTimeout(10 * 1000);
				urlcon.setReadTimeout(10 * 1000);
				urlcon.connect();
				BufferedReader urlRead = new BufferedReader(new InputStreamReader(urlcon.getInputStream(), "utf-8"));
				while((line = urlRead.readLine()) != null)
				{
					HttpInfo += line;
				}
				urlRead.close();
				return HttpInfo;
			} catch (Exception e) {
				trytime --;		
				LOG.log(String.format("%s倒数第%d次重连，信息%s", UrlAdd, trytime, e.getMessage()));
				continue;
			}
		}
		
		if(HttpInfo == null)
		{
			LOG.log("获取网址信息错误:" + UrlAdd);
		}
		
		return HttpInfo;
	}
	
	private static void trustall(HttpsURLConnection con)
	{
		TrustManager[] trustallcert = {new X509TrustManager()
		{
			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}}};
		SSLContext ssl;
		try {
			ssl = SSLContext.getInstance("SSL");
			ssl.init(null, trustallcert, new SecureRandom());
			con.setSSLSocketFactory(ssl.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static int SavePicture(String UrlAdd, String Path, String FileName, String refer)
	{
		if(null == Path || null == FileName) return 0;
		//提前判断文件是否存在，如果存在直接跳过下载，加快续传速度
		File file = new File(Path + FileName);
		if(file.exists())
		{
			return 1;
		}
		
		byte[] imgData = getImgData(UrlAdd, refer);
		if(null == imgData) return 0;
		
		FileOutputStream fop = null;
		
		try {
			try {
				fop = new FileOutputStream(file);
				fop.write(imgData);
				fop.flush();
			} catch (Exception e) {
				e.printStackTrace();
				LOG.log(e.getMessage());
				return 0;
			}
			finally
			{
				fop.close();
			}
		}
		catch(Exception e)
		{
			LOG.log(e.getMessage());
			e.printStackTrace();
		}
		
		return 1;
	}
	//获取图片的二进制流
	private static byte[] getImgData(String UrlAdd, String refer)
	{
		byte[] ImgData = null;
		//如果连接失败，有5次重连机会
		int ConnectTime = 5;
		if(null == UrlAdd) return null;
		
		while(ConnectTime > 0)
		{
			try {
				UrlAdd = ConvertURLAddToChar(UrlAdd, "utf-8");
				URL url = new URL(UrlAdd);
				URLConnection urlcon = url.openConnection();
				if(UrlAdd.startsWith("https"))
				{
					trustall((HttpsURLConnection)urlcon);
				}				
				urlcon.setRequestProperty("Referer", refer);
				urlcon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0");
				urlcon.setConnectTimeout(15 * 1000);
				urlcon.setReadTimeout(15 * 1000);
				
				ImgData = GetbyteFromStream(urlcon.getInputStream());
				return ImgData;
			} catch (Exception e) {
				e.printStackTrace();
				LOG.log(String.format("%s倒数第%d次重连，信息%s", UrlAdd, ConnectTime, e.getMessage()));
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
	//将链接中可能的中文进行编码
	private static String ConvertURLAddToChar(String UrlAdd,String Charset)
	{
		try 
		{  	      
		     Matcher matcher = Pattern.compile("[\\u4e00-\\u9fa5]").matcher(UrlAdd);  
		     while (matcher.find()) 
		     {  
		       String CHNWord=matcher.group();  
		       UrlAdd=UrlAdd.replaceAll(CHNWord, URLEncoder.encode(CHNWord,Charset));  
		     }  
		} 
		catch (Exception e) 
		{  
		    e.printStackTrace();  
		    LOG.log(String.format("encode string[%s] fail with[%s]", UrlAdd, Charset));
		}  
		  
		return UrlAdd; 
	}
}
