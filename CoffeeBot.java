/*
INFO1103 - Assignment 1
Craig Russell Tiu - ctiu6442

Yes, I did copy and paste this. This was code that I have written myself
on Atom. I promise. :)

Reminders:
- Assume that the program only needs to handle integer arguments.
*/

import java.util.Scanner;

public class CoffeeBot {

	public static void main(String[] args) {

		// Initialize the scanner.
		Scanner keyboard = new Scanner(System.in);

		// Testing the number of command line arguments.
		testLength(args);

		// Store the arguments into variables for ease of use.
		int cups = Integer.parseInt(args[0]);
		int shots = Integer. parseInt(args[1]);

		// Testing whether the input arguments are negative.
		testNegative(cups, shots);

		// Asks for the user's name and stores it in a variable.
		System.out.print("Hello, what's your name? ");
		String name = keyboard.next();

		// Asks the user whether they would like some coffee.
		while(true){
			System.out.print("Would you like to order some coffee, " + name + "? (y/n) ");
			String response = keyboard.next();

			if(response.equals("n")){
				System.out.println("Come back next time, " + name + ".");
				System.exit(1);
			} else if(response.equals("y")){
				System.out.println("Great! Let's get started.");
				break;
			} else {
				System.out.println("Invalid response. Try again.");
			}
		}

		divider("Order selection");

		double cupCost = 2.00;
		double shotCost = 1.00;

		// Display appropriate conjunctions for plural and singular.
		System.out.println("There " + isAre(cups) + " " + cups + " coffee " +
			testPlural("cup", cups) + " in stock and each costs " + displayMoney(cupCost) + ".");
		System.out.println("There " + isAre(shots) + " " + shots + " coffee " +
			testPlural("shot", shots) + " in stock and each costs " + displayMoney(shotCost) + ".");

		// Asking the user for the amount of coffee and shots they would like.
		System.out.print("\nHow many cups of coffee would you like? ");
		int cupsWant = keyboard.nextInt();

		// Terminates if the coffee cups is either zero, negative, or more than the stock.
		if(cupsWant == 0){
			System.out.println("No cups, no coffee. Goodbye.");
			System.exit(1);
		} else if(cupsWant < 0){
			System.out.println("Does not compute. System terminating.");
			System.exit(1);
		} else if(cupsWant > cups){
			System.out.println("Not enough stock. Come back later.");
			System.exit(1);
		}

		System.out.println("");

		// Initialize array to remember all the shots wanted.
		int[] shotsWant = new int[cups];

		// For each cup, ask how many shots the user wants.
		for(int i = 0; i < cupsWant; i++){
			System.out.print("How many coffee shots in cup " + (i + 1) + "? ");
			int shot = keyboard.nextInt();

			if(shot < 0){
				System.out.println("Does not compute. Try again.");
				i--;
			} else if(shot > shots){
				System.out.println("There " + isAre(shots) + " only " + shots + " coffee " +
					testPlural("shot", shots) + " left. Try again.");
				i--;
			} else {
				shotsWant[i] = shot;
			}
		}

		divider("Order summary");

		double totalCost = 0;
		double totalPaid = 0;

		// Display all the cups and their respective shots and cost.
		for(int i = 0; i < cupsWant; i++){
			double currentCost = shotsWant[i] * shotCost + cupCost;
			System.out.println("Cup " + (i + 1) + " has " + shotsWant[i] + " " +
				testPlural("shot", shotsWant[i]) + " and will cost " + displayMoney(currentCost));
			totalCost += currentCost;
		}

		System.out.println("\n" + cupsWant + " " + testPlural("coffee", cupsWant) + " to purchase.");
		System.out.println("Purchase price is " + displayMoney(totalCost));
		System.out.print("Proceed to payment? (y/n) ");
		String response = keyboard.next();

		if(response.equals("n")){
			System.out.println("Come back next time, " + name + ".");
			System.exit(1);
		}

		divider("Order payment");

		// Initialize an array that contains all valid payment.
		double[] valid = {100.00, 50.00, 20.00, 10.00, 5.00, 2.00, 1.00, 0.50, 0.20, 0.10, 0.05};
		boolean validity = false;
		int amount = 0;

		// Keep looping until no amount is due, or change is due.
		while(totalCost > 0.00){
			System.out.print(displayMoney(totalCost) + " remains to be paid. Enter coin or note: ");
			String payment = keyboard.next();

			// Goes through all the valid notes and coins and tests.
			for(int i = 0; i < valid.length; i++){
				if(payment.equals(displayMoney(valid[i]))){
					validity = true;
				}
			}

			// Tries again if invalid. Otherwise does math.
			if(validity == false){
				System.out.println("Invalid coin or note. Try again.");
			} else {
				totalCost -= Double.parseDouble(payment.substring(1));
				totalPaid += Double.parseDouble(payment.substring(1));
			}
		}

		if(totalCost == 0.00){
			System.out.println("\nYou gave " + displayMoney(totalPaid));
			System.out.println("Perfect! No change given.");
		} else if(totalCost < 0.00){
			System.out.println("\nYou gave " + displayMoney(totalPaid));
			System.out.println("Your change:");

			// Here we test for less than 0.001 because of the error in storing doubles.
			for(int i = 0; i < valid.length; i++){
				if(totalCost + valid[i] < 0.001){
					totalCost += valid[i];
					amount++;
					i--;
				} else if(amount > 0){
					System.out.println(amount + " x " + displayMoney(valid[i]));
					amount = 0;
				}
			}
		}

		System.out.println("\nThank you, " + name + ".");
		System.out.println("See you next time.");
}

	public static void testLength(String[] args){
		if(args.length == 0){
			System.out.println("No arguments. System terminating.");
		} else if(args.length < 2){
			System.out.println("Not enough arguments. System terminating.");
		} else if(args.length > 2){
			System.out.println("Too many arguments. System terminating.");
		}

		// Terminate if any of the above were true.
		if(args.length == 0 || args.length < 2 || args.length > 2){
			System.exit(1);
		}
	}

	public static void testNegative(int cups, int shots){
		if(cups < 0 && shots < 0){
			System.out.println("Negative supply chain. System terminating.");
		} else if(cups < 0){
			System.out.println("Negative supply of coffee cups. System terminating.");
		} else if(shots < 0){
			System.out.println("Negative supply of coffee shots. System terminating.");
		}

		// If any of the above is true, then terminate the program.
		if(cups < 0 || shots < 0){
			System.exit(1);
		}
	}

	// Determines whether it is right to use "is" or "are".
	public static String isAre(int amount){
		if(amount > 1){
			return "are";
		} else {
			return "is";
		}
	}

	// Determines whether the object needs to be pluralized.
	public static String testPlural(String object, int amount){
		String result;
		if(amount > 1){
			return (object + "s");
		} else {
			return object;
		}
	}

	// Turns double amount to display as money.
	public static String displayMoney(double amount){
		return (String.format("$%.2f", amount));
	}

	// Creates a divider for order selection/summary/payment.
	public static void divider(String display){
		System.out.println("\n" + display);
		for(int i = 0; i < display.length(); i++){
			System.out.print("-");
		}
		System.out.println("\n");
	}

}
