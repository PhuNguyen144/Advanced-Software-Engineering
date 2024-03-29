import java.util.Enumeration;
import java.util.Vector;

class Customer {
	private String name;
	private Vector<Rental> rentals = new Vector<>();

	public Customer(String name) {
		this.name = name;
	}

	public void addRental(Rental rental) {
		rentals.addElement(rental);
	}

	public String getName() {
		return name;
	}

	public String statement() {
		double totalAmount = 0;
		int frequentRenterPoints = 0;
		Enumeration<Rental> rentalsEnum = rentals.elements();
		String result = "Rental Record for " + getName() + "\n";
		while (rentalsEnum.hasMoreElements()) {
			Rental each = rentalsEnum.nextElement();
			double thisAmount = calculateAmount(each);
			frequentRenterPoints += calculateFrequentRenterPoints(each);
			result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(thisAmount) + " \n";
			totalAmount += thisAmount;
		}
		result += "Amount owned is " + String.valueOf(totalAmount) + "\n";
		result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";
		return result;
	}

	private double calculateAmount(Rental rental) {
		double amount = 0;
		switch (rental.getMovie().getPriceCode()) {
			case Movie.REGULAR:
				amount += 2;
				if (rental.getDaysRented() > 2) {
					amount += (rental.getDaysRented() - 2) * 1.5;
				}
				break;
			case Movie.NEW_RELEASE:
				amount += rental.getDaysRented() * 3;
				break;
			case Movie.CHILDRENS:
				amount += 1.5;
				if (rental.getDaysRented() > 3) {
					amount += (rental.getDaysRented() - 3) * 1.5;
				}
				break;
		}
		return amount;
	}

	private int calculateFrequentRenterPoints(Rental rental) {
		int points = 1;
		if (rental.getMovie().getPriceCode() == Movie.NEW_RELEASE && rental.getDaysRented() > 1) {
			points++;
		}
		return points;
	}

}
