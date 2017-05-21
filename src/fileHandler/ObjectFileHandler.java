package fileHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectFileHandler {
	private File file = null;
	/*
	 * Constructor
	 */
	public ObjectFileHandler(String path){
		new File(path).mkdirs();
	}

	public void delete(String path){
		new File(path).delete();
	}
	
	public void createFile(String path){
		file = new File(path);
		if(!file.exists()){
			try{
				file.createNewFile();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		
	}

	public void write(Object o, String path) {
		try{


			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(o);
			out.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public Object read(String filename){
		Object o = null;
		File file = new File(filename);
		try{
			FileInputStream fi = new FileInputStream(file);
			ObjectInputStream oi = new ObjectInputStream(fi);
			o = oi.readObject();
			fi.close();

		}catch(IOException e){
			e.printStackTrace();
		}
		catch(ClassNotFoundException ce){
			ce.printStackTrace();
		}
		return o;
	}
}




