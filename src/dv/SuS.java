package dv;

import java.sql.Blob;
import java.sql.Timestamp;

public class SuS {
	private int id, schueler_id;
	private String name, vorname, geschlecht, klasse, typ;
	private Blob bild;
	private Timestamp gebdatum;
	private boolean geloescht, selektiert;
	
	public SuS()
	{
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSchueler_id() {
		return schueler_id;
	}

	public void setSchueler_id(int schueler_id) {
		this.schueler_id = schueler_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getGeschlecht() {
		return geschlecht;
	}

	public void setGeschlecht(String geschlecht) {
		this.geschlecht = geschlecht;
	}

	public String getKlasse() {
		return klasse;
	}

	public void setKlasse(String klasse) {
		this.klasse = klasse;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public Blob getBild() {
		return bild;
	}

	public void setBild(Blob bild) {
		this.bild = bild;
	}

	public Timestamp getGebdatum() {
		return gebdatum;
	}

	public void setGebdatum(Timestamp gebdatum) {
		this.gebdatum = gebdatum;
	}

	public boolean isGeloescht() {
		return geloescht;
	}

	public void setGeloescht(boolean geloescht) {
		this.geloescht = geloescht;
	}

	public boolean isSelektiert() {
		return selektiert;
	}

	public void setSelektiert(boolean selektiert) {
		this.selektiert = selektiert;
	}
}
