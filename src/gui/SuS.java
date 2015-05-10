package gui;

public class SuS {

	private int schueler_id;
	private String name;
	private String vorname;
	private String gebdatum;
	private String klasse;
	private boolean geloescht;
	private String geschlecht;
	
	public SuS(){
		
	}
	
	public SuS(int schueler_id, String name, String vorname, String gebdatum, String klasse, boolean geloescht, String geschlecht){
		this.schueler_id=schueler_id;
		this.name = name;
		this.vorname = vorname;
		this.gebdatum = gebdatum;
		this.klasse = klasse;
		this.geloescht = geloescht;
		this.geschlecht = geschlecht;
	}
	
	public String toString(){
		return "SuS[susID = "+schueler_id+", name = "+name+", vorname = "+vorname+", gebdatum = "+gebdatum+", klasse = "+klasse+", geloescht = "+geloescht+", geschlecht = "+geschlecht+"]";
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

	public String getGebdatum() {
		return gebdatum;
	}

	public void setGebdatum(String gebdatum) {
		this.gebdatum = gebdatum;
	}

	public String getKlasse() {
		return klasse;
	}

	public void setKlasse(String klasse) {
		this.klasse = klasse;
	}

	public boolean isGeloescht() {
		return geloescht;
	}

	public void setGeloescht(boolean geloescht) {
		this.geloescht = geloescht;
	}

	public String getGeschlecht() {
		return geschlecht;
	}

	public void setGeschlecht(String geschlecht) {
		this.geschlecht = geschlecht;
	}
}
