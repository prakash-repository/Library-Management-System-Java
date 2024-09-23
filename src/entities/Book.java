package entities;

public class Book{
	int bookId;
	String bookName;
	int languageId;
	int authorId;
	float rating;
	String keyWords;
	
	
	
	public Book() {
		super();
	}
	public Book(int bookId, String bookName, int languageId, int authorId, String keyWords,float rating) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.languageId = languageId;
		this.authorId = authorId;
		this.keyWords = keyWords;
		this.rating=rating;
	}
	public Book(String bookName, int languageId, int authorId, String keyWords) {
		super();
		this.bookName = bookName;
		this.languageId = languageId;
		this.authorId = authorId;
		this.keyWords = keyWords;
	}
	//Getters and Setters
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public int getLanguageId() {
		return languageId;
	}
	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	
}
