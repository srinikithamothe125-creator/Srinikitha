import java.util.*;

class Event {

	String name;
	String date;
	String guests;
	int budget;

	Event(String name, String date, String guests, int budget) {

		this.name = name;
		this.date = date;
		this.guests = guests;
		this.budget = budget;

	}

}

public class EventSphere {

	static Scanner sc = new Scanner(System.in);

	// HashMap for users
	static HashMap<String,String> users = new HashMap<>();

	// ArrayList for event packages
	static ArrayList<Event> packages = new ArrayList<>();

	// Queue for bookings
	static Queue<Event> bookingQueue = new LinkedList<>();

	// Stack for history
	static Stack<Event> bookingHistory = new Stack<>();

	public static void main(String[] args) {

		initializePackages();

		while(true){

			System.out.println("\n===== EventSphere =====");
			System.out.println("1 Signup");
			System.out.println("2 Login");
			System.out.println("3 Exit");

			int choice = sc.nextInt();
			sc.nextLine();

			switch(choice){

				case 1:
					signup();
					break;

				case 2:
					if(login()){
						userMenu();
					}
					break;

				case 3:
					System.exit(0);

			}

		}

	}

	// SIGNUP

	static void signup(){

		System.out.print("Enter Email: ");
		String email = sc.nextLine();

		System.out.print("Enter Password: ");
		String pass = sc.nextLine();

		users.put(email,pass);

		System.out.println("Signup Successful");

	}

	// LOGIN

	static boolean login(){

		System.out.print("Enter Email: ");
		String email = sc.nextLine();

		System.out.print("Enter Password: ");
		String pass = sc.nextLine();

		if(users.containsKey(email) && users.get(email).equals(pass)){

			System.out.println("Login Successful");
			return true;

		}

		System.out.println("Invalid Login");
		return false;

	}

	// USER MENU

	static void userMenu(){

		while(true){

			System.out.println("\n===== Home =====");
			System.out.println("1 View Event Packages");
			System.out.println("2 Create Custom Event");
			System.out.println("3 Dashboard");
			System.out.println("4 Admin Panel");
			System.out.println("5 Logout");

			int ch = sc.nextInt();
			sc.nextLine();

			switch(ch){

				case 1:
					viewPackages();
					break;

				case 2:
					customEvent();
					break;

				case 3:
					showDashboard();
					break;

				case 4:
					adminPanel();
					break;

				case 5:
					return;

			}

		}

	}

	// INITIALIZE PACKAGES (ArrayList)

	static void initializePackages(){

		packages.add(new Event("Wedding Package","-", "Package",500000));
		packages.add(new Event("Birthday Party","-", "Package",150000));
		packages.add(new Event("Corporate Event","-", "Package",300000));

	}

	// VIEW PACKAGES

	static void viewPackages(){

		System.out.println("\nAvailable Packages:");

		for(int i=0;i<packages.size();i++){

			Event e = packages.get(i);

			System.out.println((i+1)+". "+e.name+" Budget ₹"+e.budget);

		}

		System.out.print("Select Package: ");
		int choice = sc.nextInt();

		Event selected = packages.get(choice-1);

		Event booking = new Event(selected.name,new Date().toString(),"Package",selected.budget);

		saveBooking(booking);

	}

	// CUSTOM EVENT

	static void customEvent(){

		System.out.print("Event Name: ");
		String name = sc.nextLine();

		System.out.print("Guests: ");
		int guests = sc.nextInt();

		System.out.print("Budget: ");
		int budget = sc.nextInt();
		sc.nextLine();

		System.out.print("Event Date: ");
		String date = sc.nextLine();

		int baseCost = guests * 500;
		int decorCost = 50000;
		int seatCost = 20000;

		int total = baseCost + decorCost + seatCost;

		if(budget < total){

			System.out.println("Budget too low. Minimum required ₹"+total);
			return;

		}

		Event booking = new Event(name,date,String.valueOf(guests),budget);

		saveBooking(booking);

	}

	// SAVE BOOKING (Queue + Stack)

	static void saveBooking(Event e){

		bookingQueue.add(e);

		bookingHistory.push(e);

		System.out.println("Booking Confirmed");

	}

	// DASHBOARD

	static void showDashboard(){

		System.out.println("\n===== Dashboard =====");

		System.out.println("Total Bookings: "+bookingQueue.size());

		int totalBudget = 0;

		for(Event e : bookingQueue){

			totalBudget += e.budget;

		}

		System.out.println("Total Budget Used: ₹"+totalBudget);

		System.out.println("\nBooking History (Latest First)");

		Stack<Event> temp = (Stack<Event>) bookingHistory.clone();

		while(!temp.isEmpty()){

			Event e = temp.pop();

			System.out.println(e.name+" | "+e.date+" | Guests:"+e.guests+" | ₹"+e.budget);

		}

	}

	// ADMIN PANEL

	static void adminPanel(){

		System.out.println("\n===== Admin Panel =====");

		for(Event e : bookingQueue){

			System.out.println(e.name+" | "+e.date+" | Guests:"+e.guests+" | ₹"+e.budget);

		}

	}

}
