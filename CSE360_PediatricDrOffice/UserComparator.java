import java.util.Comparator;

public class UserComparator implements Comparator<User>
{	
	// first we compare the user's first name, if they are same, we then compare
	// their last names. 
	public int compare(User first, User second)
	{
		int firstNameCompareTo = first.getFirstName().compareTo(second.getFirstName());
		
		if (firstNameCompareTo == 0) {
			// user first names are the same
			// check last name
			int lastNameCompareTo = first.getLastName().compareTo(second.getLastName());

			return lastNameCompareTo;			
		} else {
			// first names are not the same
			return firstNameCompareTo;
		}
	}
}
