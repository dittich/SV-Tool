package de.dittich.sv.basic;

import java.util.prefs.Preferences;

import javax.swing.JFileChooser;

public class UserPreferences {
	
	private static final UserPreferences OBJ = new UserPreferences();
	
	private Preferences userRoot;
	private String subNodeName = "sv_ausweise";
	
	private UserPreferences(){
		userRoot = Preferences.userRoot();
		if(!isSetSubNode("sv_cmd")){
			setSubNode("sv_cmd", "cmd.exe");
		}
		if(!isSetSubNode("sv_mysqldump")){
			setSubNode("sv_mysqldump", getFile("mysqldump.exe"));
		}
		if(!isSetSubNode("sv_mysql")){
			setSubNode("sv_mysql", getFile("mysql.exe"));
		}
		if(!isSetSubNode("sv_mysqlimport")){
			setSubNode("sv_mysqlimport", getFile("mysqlimport.exe"));
		}
		
	}
	
	public static UserPreferences getInstance(){
		return OBJ;
	}
	
	private String getFile(String dateiname){
		JFileChooser chooser = new JFileChooser();
        int rueckgabeWert = chooser.showDialog(null, dateiname);
        
        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
        {
            return chooser.getSelectedFile().getAbsolutePath();
        }
		return "leer";
	}
	
	public void setSubNode(String pathName, String value){
		Preferences subnode = userRoot.node(subNodeName);
		subnode.put(pathName, value);
	}
	
	public String getSubNode(String key){
		Preferences subnode = userRoot.node(subNodeName);
		return subnode.get(key, null);
	}
	
	public boolean isSetSubNode(String pathName){
		if(getSubNode(pathName)!=null){
			return true;
		}
		else{
			return false;
		}
	}
}
