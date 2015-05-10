package test;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

public class displayPdfExample {

public static void main(String[] args){

JFrame window = new JFrame("Example Title");
window.getContentPane().add(displayPDF("d:/ausweise.pdf"));
window.pack();
window.setVisible(true);
}

static JPanel displayPDF (String path)  {

SwingController controller = new SwingController();
SwingViewBuilder factory = new SwingViewBuilder(controller);
JPanel viewerComponentPanel = factory.buildViewerPanel();

ComponentKeyBinding.install(controller, viewerComponentPanel);

controller.getDocumentViewController().setAnnotationCallback(
new org.icepdf.ri.common.MyAnnotationCallback(
controller.getDocumentViewController()));

controller.openDocument(path);

return viewerComponentPanel;
}
}