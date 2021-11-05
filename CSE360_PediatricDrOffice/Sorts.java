import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;

public class Sorts
{
	public static void sortUsers(ArrayList<User> userList, Comparator<User> xComparator) {

		// The xCompartor can be an object of 
		// UserComparator
		// which separates the algorithm from its underline data structure
		
		// implement Selection sort
		int min;

		for (int index = 0; index < userList.size() - 1; index++) {
		   min = index;
		   for (int scan = index+1; scan < userList.size(); scan++) {
		      if (xComparator.compare(userList.get(scan), userList.get(min)) < 0) {
		         min = scan;
		      }
		   }
		   
		   swapUsers(userList, min, index);
		}

	}
	
	public static void sortAppointments(ArrayList<Appointment> appointmentList, Comparator<Appointment> xComparator) {

		// The xCompartor can be an object of 
		// UserComparator
		// which separates the algorithm from its underline data structure
		
		// implement Selection sort
		int min;

		for (int index = 0; index < appointmentList.size() - 1; index++) {
		   min = index;
		   for (int scan = index+1; scan < appointmentList.size(); scan++) {
		      if (xComparator.compare(appointmentList.get(scan), appointmentList.get(min)) < 0) {
		         min = scan;
		      }
		   }
		   
		   swapAppointments(appointmentList, min, index);
		}

	}
	
	public static void sortMessages(ArrayList<Message> messageList, Comparator<Message> xComparator) {

		// The xCompartor can be an object of 
		// UserComparator
		// which separates the algorithm from its underline data structure
		
		// implement Selection sort
		int min;

		for (int index = 0; index < messageList.size() - 1; index++) {
		   min = index;
		   for (int scan = index+1; scan < messageList.size(); scan++) {
		      if (xComparator.compare(messageList.get(scan), messageList.get(min)) < 0) {
		         min = scan;
		      }
		   }
		   
		   swapMessages(messageList, min, index);
		}

	}

	//-----------------------------------------------------------------
    //  Swaps two elements in the specified array list
    //-----------------------------------------------------------------
    private static void swapMessages(ArrayList<Message> messageList, int index1, int index2) {
        try {
            Collections.swap(messageList, index1, index2);
        } catch (IndexOutOfBoundsException e) {
        	System.out.print("Index Out of Bounds Exception when swapping during sort\n");
        }
    }

	//-----------------------------------------------------------------
    //  Swaps two elements in the specified array list
    //-----------------------------------------------------------------
    private static void swapAppointments(ArrayList<Appointment> appointmentList, int index1, int index2) {
        try {
            Collections.swap(appointmentList, index1, index2);
        } catch (IndexOutOfBoundsException e) {
        	System.out.print("Index Out of Bounds Exception when swapping during sort\n");
        }
    }

    //-----------------------------------------------------------------
    //  Swaps two elements in the specified array list
    //-----------------------------------------------------------------
    private static void swapUsers(ArrayList<User> userList, int index1, int index2) {
        try {
            Collections.swap(userList, index1, index2);
        } catch (IndexOutOfBoundsException e) {
        	System.out.print("Index Out of Bounds Exception when swapping during sort\n");
        }
    }
}