package se.gafw.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author alexander
 *
 */

public class FileHandler implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private File file;
	
	/**
	 * 
	 * @param path
	 * @param fileName
	 * @param fileFormat
	 */
	public FileHandler(String path, String fileName, String fileFormat){
		file = new File(path);
		file.mkdirs();
		file = new File(path + fileName + fileFormat);
		if(!file.exists()){
			try{
				file.createNewFile();
			}catch(IOException e) {logError(e);}
		}
	}
	/**
	 * 
	 * @param filePath the path to the file
	 * @return true of the file exists
	 */
	public static boolean fileExists(String filePath)
	{
		return new File(filePath).exists();
	}
	/**
	 * 
	 * @param path
	 * @param fileName
	 * @param fileFormat
	 * @return
	 */
	public FileHandler set(String path, String fileName, String fileFormat){
		file = new File(path);
		file.mkdirs();
		file = new File(path + fileName + "." + fileFormat);
		if(!file.exists()){
			try{
				file.createNewFile();
			}catch(IOException e) {logError(e);}
		}
		return this;
	}
	/**
	 * 
	 * @param text
	 */
	public FileHandler write(String text){
		if(text == null)text = "" + '\0';
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(text);
			writer.close();
		}catch(IOException e){logError(e);}
		return this;
	}
	/**
	 * 
	 * @return
	 */
	public boolean deleteFile()
	{
		return file.delete();
	}
	/**
	 * 
	 * @param lines
	 */
	public FileHandler write(String[] lines){
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i < lines.length; i++)
				writer.write(lines[i]+"\n");
			writer.close();
		}catch(IOException e){logError(e);}
		return this;
	}
	/**
	 * 
	 * @param o
	 * @return
	 */
	public FileHandler writeObject(Object o){
		if(o == null)
		{
			logError("error trying to write a null object");
			return null;
		}
		try{
			ObjectOutputStream stream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			stream.writeObject(o);
			stream.close();
		}catch(IOException e){logError(e);}
		return this;
	}
	/**
	 * 
	 * @param subfolder
	 * @param fileNames
	 * @param toWrite
	 * @return
	 */
	public FileHandler writeObjects(String subfolder, String fileNames, Object[] toWrite){
		long timer = System.currentTimeMillis();
		File dir = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - file.getName().length())+subfolder);
		if(!dir.exists())dir.mkdirs();
		try{
			for(int i = 0; i < toWrite.length; i++){
				File f = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - file.getName().length())+subfolder+fileNames+i+".ser");
				f.createNewFile();
				ObjectOutputStream stream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
				stream.writeObject(toWrite[i]);
				stream.close();
			}
		}catch(Exception e){logError(e);}
		System.out.println("done writing "+toWrite.length+" objects from "+file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - file.getName().length())+subfolder+
				"\nin: " + (System.currentTimeMillis() - timer) / 1000.0 + "s");
		return this;
	}
	/**
	 * 
	 * @param subfolder
	 * @param fileNames
	 * @return
	 */
	public List<Object> readObjects(String subfolder, String fileNames){
		long timer = System.currentTimeMillis();
		List<Object> result = new ArrayList<>();
		try{
			int ammountOfFiles = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - file.getName().length())+subfolder).listFiles().length;
			for(int i = 0; i < ammountOfFiles; i++){
				ObjectInputStream stream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - file.getName().length())+subfolder+fileNames+i+".ser"))));
				result.add(stream.readObject());
				stream.close();
			}
		}catch(Exception e){e.printStackTrace();}
		System.out.println("done reading "+result.size()+" objects from "+file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - file.getName().length())+subfolder+
				"\nin: " + (System.currentTimeMillis() - timer) / 1000.0 + "s");
		return result;
	}
	/**
	 * 
	 * @return
	 */
	public Object readObject(){
		Object result = null;
		if(!file.exists()) logError("file does not exist");
		if(!file.isFile()) logError("path does not lead to a readable file");
		if(file.length() == 0) 
		{
			logError("file is null");
			return null;
		}
		try{
			FileInputStream fIn = new FileInputStream(file);
			BufferedInputStream bIn = new BufferedInputStream(fIn);
			ObjectInputStream stream = new ObjectInputStream(bIn);
			result = stream.readObject();
			stream.close();
		}catch(ClassNotFoundException e){logError(e);}catch(IOException e){logError(e);}
		return result;
	}
	/**
	 * 
	 * @return
	 */
	public String read(){
		String result = "";
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = reader.readLine()) != null)result += line+"\n";
			reader.close();
		}catch(IOException e){logError(e);}
		return result;
	}
	
	public String toString()
	{
		return file.getPath();
	}
	
	public String getCurrentDirectory()
	{
		return file.getPath();
	}
	
	private void logError(Exception e){logError(e.getMessage());}
	private void logError(String message)
	{
		System.err.println("Error while dealing with file"
							+ "\n[" + file.getPath() + "]\n" 
							+ message);
	}
}
