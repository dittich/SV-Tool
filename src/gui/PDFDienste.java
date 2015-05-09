package gui;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Font;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.Image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class PDFDienste {
	private Utilities util;
	private File pdfFile;
	private ResultSet rs;
	
	public PDFDienste(File pdfFile, ResultSet rs){
		this.pdfFile = pdfFile;
		this.rs = rs;
		util = new Utilities();
	}
	
	public void makePDF(){
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
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("d:/doc.pdf"));
			
			document.open();
			
			PdfContentByte cb = writer.getDirectContent();
			
			while(rs.next()){
				this.setText(cb, "SCHÜLERAUSWEIS", 10, 17, 2, bf[1], bc[0]);
	            this.setText(cb, "/Student Identity Card/Pièce d\'identité scolaire", 6, 17, 7, bf[0], bc[1]);
	            this.setText(cb, "Albrecht-Dürer-Gymnasium", 8, 17, 14, bf[0], bc[1]);
	            this.setText(cb, "Heinitzstr. 73 | 58097 Hagen", 6, 17, 18, bf[0], bc[1]);
	            this.setText(cb, "http://www.ad-hagen.de", 6, 17, 20.8f, bf[0], bc[1]);
	            this.setText(cb, "Name", 8, 3, 24.3f, bf[0], bc[1]);
	            this.setText(cb, "/Name/Nom", 5, 11, 25.3f, bf[0], bc[1]);
	            this.setText(cb, "Geburtsdatum", 8, 3, 33.3f, bf[0], bc[1]);
	            this.setText(cb, "/date of birth/Date de naissance", 5, 21.5f, 34.3f, bf[0], bc[1]);
	            this.setText(cb, "Gültig bis ", 8, 3, 42.3f, bf[0], bc[1]);
	            this.setText(cb, "/valid till/Durée de validité", 5, 15.5f, 43.3f, bf[0], bc[1]);
	            this.setText(cb, "siehe Rückseite ", 10, 3, 47, bf[0], bc[0]);
	            this.setText(cb, "/see back/voir au verso", 8, 29, 47.3f, bf[0], bc[0]);
	            
	            String name = rs.getString("vorname") + " " + rs.getString("name");
	            this.setText(cb, name, 10, 3, 27, bf[0], bc[0]);
	            
	            String gebDatum = rs.getString("gebdatum");
	            this.setText(cb, gebDatum, 10, 3, 36, bf[0], bc[0]);
	            
	            this.setImage(cb, "../IMG/sv_nrw_logo.png", 13, 3, 3);
	            this.setImage(cb, "../IMG/sv_ad_logo.png", 20, 63, 3);
	            
	            BufferedImage buffImg = ImageIO.read(rs.getBinaryStream("bild"));
	            this.setImage(cb, buffImg, 20, 63, 24.5f);
			}
			
			document.close();
		} catch(Exception e){
			e.printStackTrace();
		}
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
		int width = (int)dot(widthMM);
		try {
			Image iImage = Image.getInstance(buffImg, new Color(0, 91, 127));
			float wImg = iImage.getPlainWidth();
			float hImg = iImage.getPlainHeight();
			iImage.scaleToFit(width, hImg*width/wImg);
			System.out.println(iImage.getPlainHeight());
			iImage.setAbsolutePosition(dotx(x),doty(y)-iImage.getPlainHeight());
			System.out.println(iImage.toString());
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
