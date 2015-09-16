package gui;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BarcodeEAN;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

public class PDFDienste {
	private Utilities util;
	private ResultSet rs;
	
	public PDFDienste(File pdfFile, ResultSet rs){
		this.rs = rs;
		util = new Utilities();
	}
	
	public void viewPDF(File pdfFile, JPanel pdfPanel){
		try {
			//InputStream inputStream = new FileInputStream(pdfFile.toString());
			BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(pdfFile.toString()));
			SwingController controller = new SwingController();
			SwingViewBuilder factory = new SwingViewBuilder(controller);
			JPanel viewerComponentPanel = factory.buildViewerPanel();
			viewerComponentPanel.setPreferredSize(new Dimension(760, 460));;
			pdfPanel.removeAll();
			pdfPanel.add(viewerComponentPanel);
			pdfPanel.revalidate();
			pdfPanel.repaint();
			
			controller.openDocument(inputStream, "", "");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void makePDF(File pdfFile){
		Document document = new Document();
		
		BaseColor[] bc = {
				new BaseColor(/*Red*/ 255, /*Green*/255, /*Blue*/ 255),
				new BaseColor(/*Red*/ 168, /*Green*/169, /*Blue*/ 173),
				new BaseColor(/*Red*/ 0, /*Green*/0, /*Blue*/ 0)
		};
		
		BaseFont[] bf = new BaseFont[3];
		try {
			bf[0] = BaseFont.createFont("src/fonts/arial.ttf", BaseFont.WINANSI, false);
			bf[1] = BaseFont.createFont("src/fonts/arialbd.ttf", BaseFont.WINANSI, false);
			bf[2] = BaseFont.createFont("src/fonts/ariali.ttf", BaseFont.WINANSI, false);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		Rectangle rect = new Rectangle(dot(86),dot(54));
		rect.setBackgroundColor(new BaseColor(0, 91, 127));
		document.setPageSize(rect);
		
		try{
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFile.toString()));
			
			document.open();
			
			PdfContentByte cb = writer.getDirectContent();
			
			while(rs.next()){
				rect.setBackgroundColor(new BaseColor(0, 91, 127));
				document.setPageSize(rect);
				document.newPage();
				
				this.setText(cb, "SCHÜLERAUSWEIS", 10, 18, 3, bf[1], bc[0]);
	            this.setText(cb, "/Student Identity Card/Pièce d\'identité scolaire", 6, 18, 8, bf[0], bc[1]);
	            this.setText(cb, "Albrecht-Dürer-Gymnasium", 8, 18, 14.5f, bf[0], bc[1]);
	            this.setText(cb, "Heinitzstr. 73 | 58097 Hagen", 6, 18, 19, bf[0], bc[1]);
	            this.setText(cb, "http://www.ad-hagen.de", 6, 18, 21.2f, bf[0], bc[1]);
	            this.setText(cb, "Name", 8, 4, 25.4f, bf[0], bc[1]);
	            this.setText(cb, "/Name/Nom", 5, 12, 26.4f, bf[0], bc[1]);
	            this.setText(cb, "Geburtsdatum", 8, 4, 34.6f, bf[0], bc[1]);
	            this.setText(cb, "/date of birth/Date de naissance", 5, 22.4f, 35.7f, bf[0], bc[1]);
	            this.setText(cb, "Gültig bis ", 8, 4, 43.8f, bf[0], bc[1]);
	            this.setText(cb, "/valid till/Durée de validité", 5, 16.3f, 44.8f, bf[0], bc[1]);
	            this.setText(cb, "siehe Rückseite ", 10, 4, 47, bf[0], bc[0]);
	            this.setText(cb, "/see back/voir au verso", 8, 29.9f, 47.7f, bf[0], bc[0]);
	            
	            String name = rs.getString("vorname") + " " + rs.getString("name");
	            this.setText(cb, name, 10, 4, 28.5f, bf[0], bc[0]);
	            
	            String gebDatum = rs.getString("gebdatum");
	            this.setText(cb, gebDatum, 10, 4, 37.5f, bf[0], bc[0]);
	            
	            this.setImage(cb, "sv_nrw_logo.png", 14.2f, 3, 3);
	            this.setImage(cb, "sv_ad_logo.png", 20, 63, 3);
	            
	            BufferedImage buffImg;
	            try{
	            	buffImg = ImageIO.read(rs.getBinaryStream("bild"));
	            } catch(IllegalArgumentException iae){
	            	File img = new File("src/img/sv_nofoto.png");
	            	buffImg = ImageIO.read(img);
	            }
	            this.setImage(cb, buffImg, 20, 63, 24.5f);
	            
	            rect.setBackgroundColor(new BaseColor(255, 255, 255));
	            document.setPageSize(rect);
	            document.newPage();
	            
	            this.setText(cb, "nur mit Siegelaufkleber gültig bis", 8, 4, 3.3f, bf[2], bc[2]);
	            this.setText(cb, "/valid till/Durée de validité", 6, 45.5f, 3.9f, bf[2], bc[2]);
	            
	            int susID = rs.getInt("schueler_id");
	            this.setEAN13(cb, susID, 4, 37, bc[0], bc[2]);
			}
			
			document.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void setEAN13(PdfContentByte cb, int susID, float x, float y, BaseColor bc1, BaseColor bc2){
		BarcodeEAN ean = new BarcodeEAN();
		ean.setCode(this.generateENA13(susID));
		ean.setCodeType(BarcodeEAN.EAN13);

		Image iImage = ean.createImageWithBarcode(cb, null, null);
		setImage(cb, iImage, 35, x, y);
	}
	
	private String generateENA13(int wert){
		String susID = ""+wert;
		
		while(susID.length()<12){
			susID = "0"+susID;
		}
		
		int pruefZiffer = this.ean13Pruefziffer(susID);
		susID = susID+pruefZiffer;
		return ""+susID;
	}
	
	private int ean13Pruefziffer(String wert){
		int pruefziffersumme = 0;
		for(int i=0; i<wert.length(); i++){
			String ziffer = wert.substring(i, i+1);
			int zifferInt = Integer.parseInt(ziffer);
			if(i%2==0)
				pruefziffersumme=pruefziffersumme + 1*zifferInt;
			else 
				pruefziffersumme=pruefziffersumme + 3*zifferInt;
		}
		
		return (10-pruefziffersumme%10)%10;
	}
	
	private void setImage(PdfContentByte cb, String pfad, float widthMM, float x, float y){
		try {
			BufferedImage buffImg = ImageIO.read(Main.class.getResource(pfad));
			this.setImage(cb, buffImg, widthMM, x, y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setImage(PdfContentByte cb, BufferedImage buffImg, float widthMM, float x, float y){
		try {
			Image iImage = Image.getInstance(buffImg, new Color(0, 91, 127));
			setImage(cb,iImage,widthMM,x,y);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setImage(PdfContentByte cb, Image iImage, float widthMM, float x, float y){
		int width = (int)dot(widthMM);
		try {
			float wImg = iImage.getPlainWidth();
			float hImg = iImage.getPlainHeight();
			iImage.scaleToFit(width, hImg*width/wImg);
			iImage.setAbsolutePosition(dotx(x),doty(y)-iImage.getPlainHeight());
			cb.addImage(iImage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private float dot(float mm){
		return util.millimetersToPoints(mm);
	}
	
	private float dotx(float mm){
		return util.millimetersToPoints(mm);
	}
	
	private float doty(float mm){
		return dot(54)-util.millimetersToPoints(mm);
	}
	
	private void setText(PdfContentByte cb, String text, int size, float x, float y, BaseFont bf, BaseColor bc){
		cb.beginText();
		cb.setFontAndSize(bf, size);
		cb.setColorFill(bc);
        cb.setTextMatrix(dotx(x), doty(y)-size);
        cb.showText(text);
        cb.endText();
	}
}
