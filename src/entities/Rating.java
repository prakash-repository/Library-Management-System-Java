package entities;

public class Rating {
	int userId;
	int bookId;
	float rating;
	
	//Constructor
	public Rating() {
		super();
	}
	
	public Rating(int userId, int bookId, float rating) {
		super();
		this.userId = userId;
		this.bookId = bookId;
		this.rating = rating;
	}
	
	//Getters and Setters
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	
}
