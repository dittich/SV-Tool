package dv;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import svt.Einstellungen;

public class Dateimanager {
	
	private Einstellungen einstellungen;
	
	public Dateimanager()
	{
		
	}
	
	public boolean speichern(Object speicherObjekt, String dateinamen)
	{
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(dateinamen);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(speicherObjekt);
			out.close();
			fileOut.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			System.out.println(dateinamen);
			return false;
		}
	}
	
	public Object laden(String dateinamen)
	{
		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream(dateinamen);
			ObjectInputStream in = new ObjectInputStream(fileIn);
	        Object obj = in.readObject();
	        in.close();
	        fileIn.close();
	        
	        return obj;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
