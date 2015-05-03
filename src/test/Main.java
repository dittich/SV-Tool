package test;

import javax.swing.JFileChooser;
import java.awt.Component;
import java.awt.Container;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.UIManager;

public class Main {
  public static void main(String s[]) {
    JFileChooser chooser = new JFileChooser();
    disableNewFolderButton(chooser);
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle("choosertitle");
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.setAcceptAllFileFilterUsed(false);

    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
    } else {
      System.out.println("No Selection ");
    }
  }
  
  public static void disableNewFolderButton(Container c) {
	    int len = c.getComponentCount();
	    for (int i = 0; i < len; i++) {
	      Component comp = c.getComponent(i);
	      if (comp instanceof JButton) {
	        JButton b = (JButton) comp;
	        Icon icon = b.getIcon();
	        if (icon != null
	            && icon == UIManager.getIcon("FileChooser.newFolderIcon"))
	          b.setEnabled(false);
	      } else if (comp instanceof Container) {
	        disableNewFolderButton((Container) comp);
	      }
	    }
  }
}