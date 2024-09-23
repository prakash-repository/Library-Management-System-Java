package customExceptions;

public class RatingIsNotValidException extends Exception{

	public static float ratingIsValidOrNot(float rating) {
		if(rating<1 || rating>5) {
		return 0;
	    }	
		else {
		return rating;
		}
	}

}
