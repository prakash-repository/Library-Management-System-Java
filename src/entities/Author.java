package entities;

public class Author {
	int authorId;
	String authorName;
	//Constructor
	public Author() {
	}
	//Constructor
		public Author(String authorName) {
		this.authorName = authorName;
		}
	//Constructor
	public Author(int authorId, String authorName) {
		this.authorId = authorId;
		this.authorName = authorName;
	}
	//Getters and Setters
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	
	
}
