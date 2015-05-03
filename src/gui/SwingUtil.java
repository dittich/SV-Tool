package gui;

/*
 * Enhydra Java Application Server Project
 *
 * The contents of this file are subject to the Enhydra Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License on
 * the gEnhydra web site ( http://www.enhydra.org/ ).
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific terms governing rights and limitations
 * under the License.
 *
 * The Initial Developer of the Enhydra Application Server is Lutris
 * Technologies, Inc. The Enhydra Application Server and portions created
 * by Lutris Technologies, Inc. are Copyright Lutris Technologies, Inc.
 * All Rights Reserved.
 *
 * Contributor(s):
 * Paul Mahar
 * Thomas Wessell
 *
 */

//
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import java.util.ResourceBundle;

/**
 * SwingUtil contains static utility methods for working with Swing
 * classes.
 *
 */
public class SwingUtil {

    /**
     * Hidden default constructor
     */
    private SwingUtil() {}

    /**
     * Open a JFileChooser dialog for selecting a directory and return the
     * selected directory.
     *
     * @param owner
     * The frame or dialog that controls the invokation of this dialog.
     * @param defaultDir
     * A string representation of the directory to show when the
     * dialog opens.
     * @param title
     * Tile for the dialog.
     *
     * @return
     * The selected directory as a File. Null if user cancels dialog without
     * a selection.
     *
     */
    public static File getDirectoryChoice(Component owner, String defaultDir,
                                          String title) {
        return getDirectoryChoice(owner, new File(defaultDir), title);
    }

    /**
     * Open a JFileChooser dialog for selecting a directory and return the
     * selected directory.
     *
     *
     * @param owner
     * The frame or dialog that controls the invokation of this dialog.
     * @param defaultDir
     * The directory to show when the dialog opens.
     * @param title
     * Tile for the dialog.
     *
     * @return
     * The selected directory as a File. Null if user cancels dialog without
     * a selection.
     *
     */
    public static File getDirectoryChoice(Component owner, File defaultDir,
                                          String title) {
        //
        // There is apparently a bug in the native Windows FileSystem class that
        // occurs when you use a file chooser and there is a security manager
        // active. An error dialog is displayed indicating there is no disk in
        // Drive A:. To avoid this, the security manager is temporarily set to
        // null and then reset after the file chooser is closed.
        //
        SecurityManager sm = null;
        JFileChooser chooser = null;
        File         choice = null;

        sm = System.getSecurityManager();
        System.setSecurityManager(null);
        chooser = new JFileChooser();
        //chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if ((defaultDir != null) && defaultDir.exists()
                && defaultDir.isDirectory()) {
            chooser.setCurrentDirectory(defaultDir);
            chooser.setSelectedFile(defaultDir);
        }
        chooser.setDialogTitle(title);
        chooser.setApproveButtonText("OK");
        int v = chooser.showOpenDialog(owner);

        owner.requestFocus();
        switch (v) {
        case JFileChooser.APPROVE_OPTION:
            if (chooser.getSelectedFile() != null) {
                if (chooser.getSelectedFile().exists()) {
                    choice = chooser.getSelectedFile();
                } else {
                    File parentFile =
                        new File(chooser.getSelectedFile().getParent());

                    choice = parentFile;
                }
            }
            break;
        case JFileChooser.CANCEL_OPTION:
        case JFileChooser.ERROR_OPTION:
        }
        chooser.removeAll();
        chooser = null;
        System.setSecurityManager(sm);
        return choice;
    }

    /**
     * Get a file selection using the FileChooser dialog.
     *
     * @param owner
     * The parent of this modal dialog.
     * @param defaultSelection
     * The default file selection as a string.
     * @param filter
     * An extension filter
     * @param title
     * The caption for the dialog.
     *
     * @return
     * A selected file or null if no selection is made.
     */
    public static File getFileChoice(Component owner,
                                     String defaultSelection,
                                     FileFilter filter, String title) {
        return SwingUtil.getFileChoice(owner, new File(defaultSelection),
                                       filter, title);
    }

    /**
     * Get a file selection using the FileChooser dialog.
     *
     * @param owner
     * The parent of this modal dialog.
     * @param defaultSelection
     * The default file selection as a file.
     * @param filter
     * An extension filter
     * @param title
     * The caption for the dialog.
     *
     * @return
     * A selected file or null if no selection is made.
     */
    public static File getFileChoice(Component owner, File defaultSelection,
                                     FileFilter filter, String title) {
        //
        // There is apparently a bug in the native Windows FileSystem class that
        // occurs when you use a file chooser and there is a security manager
        // active. An error dialog is displayed indicating there is no disk in
        // Drive A:. To avoid this, the security manager is temporarily set to
        // null and then reset after the file chooser is closed.
        //
        SecurityManager sm = null;
        File         choice = null;
        JFileChooser chooser = null;

        sm = System.getSecurityManager();
        System.setSecurityManager(null);

        chooser = new JFileChooser();
        if (defaultSelection.isDirectory()) {
            chooser.setCurrentDirectory(defaultSelection);
        } else {
            chooser.setSelectedFile(defaultSelection);
        }
        chooser.setFileFilter(filter);
        chooser.setDialogTitle(title);
        chooser.setApproveButtonText("OK");
        int v = chooser.showOpenDialog(owner);

        owner.requestFocus();
        switch (v) {
        case JFileChooser.APPROVE_OPTION:
            if (chooser.getSelectedFile() != null) {
                choice = chooser.getSelectedFile();
            }
            break;
        case JFileChooser.CANCEL_OPTION:
        case JFileChooser.ERROR_OPTION:
        }
        chooser.removeAll();
        chooser = null;
        System.setSecurityManager(sm);
        return choice;
    }

    /**
     * Get the point on point on the screen at which to open a dialog
     * or window for it to appear centered. This point is the top right hand
     * corner of the container you want to position.
     *
     *
     * @param size
     * The demensions of the dialog or window to position.
     *
     * @return
     * The top left hand point at which to position the container
     * for it to appear centered.
     *
     */
    public static Point getCenteringPoint(Dimension size) {
        Point     centeringPoint = new Point();
        Dimension screenSize;

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (size.height > screenSize.height) {
            size.height = screenSize.height;
        }
        if (size.width > screenSize.width) {
            size.width = screenSize.width;
        }
        centeringPoint.x = (screenSize.width - size.width) / 2;
        centeringPoint.y = (screenSize.height - size.height) / 2;
        return centeringPoint;
    }


}