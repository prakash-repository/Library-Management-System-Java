package entities;

public class Language {
	int languageId;
	String languageName;
	
	public Language() {
		
	}
	public Language(String languageName) {
		this.languageName = languageName;
	}
	public Language(int languageId, String languageName) {
		this.languageId = languageId;
		this.languageName = languageName;
	}
	
	public int getLanguageId() {
		return languageId;
	}
	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}
	public String getLanguageName() {
		return languageName;
	}
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
}
