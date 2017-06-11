package com.sdmx.framework.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class Utility 
{

	private static Logger accessLogger = null;
	private static Logger exceptionLogger = null;
	
	private static Boolean initializedLog = false;
	public static Logger getAccessLogger()
	{
		InitLog();
		return accessLogger;
	}
	
	public  static Logger getExceptionLogger()
	{
		InitLog();
		return exceptionLogger;
	}
	
	public static Logger getLogger(){
		InitLog();
		return exceptionLogger;
	}
	
	private static void InitLog()
	{
		if (!initializedLog)
		{
			String fileVirtualPath = "log4j.properties";
			String configFile = getFilePath(fileVirtualPath);
			String logRootPath = configFile.substring(0,configFile.length()- fileVirtualPath.length());
			if ('/' != logRootPath.charAt(logRootPath.length()-1))
			{
				logRootPath = logRootPath + "/";
			}
			logRootPath = logRootPath + "../";
			
			synchronized(initializedLog)
			{
				if (!initializedLog)
				{
					System.setProperty("context-root", logRootPath);
					PropertyConfigurator.configure(configFile);
					accessLogger = LogManager.getLogger("serviceLogger");
					exceptionLogger = LogManager.getLogger("serviceLogger");
					initializedLog = true;
				}
			}
		}
    }
	
	public static final int VMS_TEXT_HEIGHT = 65;
	public static final String LINE_SEPARATER = System.getProperty("line.separator").toString();
	
	public static String getFilePath(String fileName)
	{
		return Utility.class.getResource("/"+fileName).getFile().replace("%20", " ");
	}
	
	private static Properties properties = null;
	public static Properties getConfigProperties()
	{
		if (null == properties)
		{
			try
			{
				properties = new Properties();
				FileInputStream fileStream = new FileInputStream(getFilePath("config.properties"));
				properties.load(fileStream);
			}
			catch(Exception ex)
			{
				String message = "serviceName=getConfigProperties";
				getExceptionLogger().error(message, ex);
			}	
		}
		return properties;
	}
	public static String getFileContent(String url) throws Exception
	{
		URL urlObj = new URL(url);
		InputStream stream = urlObj.openStream();
		InputStreamReader streamReader = new InputStreamReader(stream, "UTF-8"); 
		BufferedReader reader = new BufferedReader(streamReader);

		StringBuffer sb = new StringBuffer();
		String line = reader.readLine();
		while (null != line)
		{
			sb.append(line).append(LINE_SEPARATER);
			line = reader.readLine();
		}
		
		return sb.toString();
	}
	
	public static BufferedImage getImageFromUrl( String url ) throws Exception
	{
		URL urlObj = new URL(url);
		InputStream stream = urlObj.openStream();
		BufferedImage image = ImageIO.read(stream);
		
		return image;
	}	

	

}
