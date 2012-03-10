//  Refactoring, a First Example, step2, (~p11)

import java.util.*;

public class Movie {
  public static final int CHILDRENS = 2;
  public static final int REGULAR = 0;
  public static final int NEW_RELEASE = 1;

  private String _title;        //
  private int _priceCode;       //

  public Movie(String title, int priceCode){
      _title = title;
      _priceCode = priceCode;
  }

   public int getPriceCode() {
      return _priceCode;
  }

  public void setPriceCode(int arg) {
      _priceCode = arg;
  }

  public String getTitle() {
      return _title;
  }

  // jjhou add
  public static void main(String[] args) {
      System.out.println("Refactoring, a First Example, step1");

      Movie m1 = new Movie("Seven", Movie.NEW_RELEASE);
      Movie m2 = new Movie("Terminator", Movie.REGULAR);
      Movie m3 = new Movie("Star Trek", Movie.CHILDRENS);

      Rental r1 = new Rental(m1, 4);
      Rental r2 = new Rental(m1, 2);
      Rental r3 = new Rental(m3, 7);
      Rental r4 = new Rental(m2, 5);
      Rental r5 = new Rental(m3, 3);

      Customer c1 = new Customer("jjhou");
      c1.addRental(r1);
      c1.addRental(r4);

      Customer c2 = new Customer("gigix");
      c2.addRental(r1);
      c2.addRental(r3);
      c2.addRental(r2);

      Customer c3 = new Customer("jiangtao");
      c3.addRental(r3);
      c3.addRental(r5);

      Customer c4 = new Customer("yeka");
      c4.addRental(r2);
      c4.addRental(r3);
      c4.addRental(r5);

      System.out.println(c1.statement());
      System.out.println(c2.statement());
      System.out.println(c3.statement());
      System.out.println(c4.statement());
  }
}

class Rental {
  private Movie _movie;         //
  private int _daysRented;              //

  public Rental(Movie movie, int daysRented) {
    _movie = movie;
    _daysRented = daysRented;
  }

  public int getDaysRented() {
    return _daysRented;
  }

  public Movie getMovie() {
    return _movie;
  }
  
  double getCharge(){
	double result = 0;
    switch(getMovie().getPriceCode()){   //®æ
        case Movie.REGULAR:                     //
          result += 2;
          if(getDaysRented()>2)
            result += (getDaysRented()-2)*1.5;
          break;

        case Movie.NEW_RELEASE:         //
          result += getDaysRented()*3;
          break;

        case Movie.CHILDRENS:           //
          result += 1.5;
          if(getDaysRented()>3)
            result += (getDaysRented()-3)*1.5;
          break;
      }
	return result;
  }
  int getFrequentRenterPoints(){
      if ((getMovie().getPriceCode() == Movie.NEW_RELEASE) &&
         getDaysRented() > 1){
        return 2;
	  }
	  else{
		return 1;
	  }
  }
}

class Customer {
  private String _name;                                 //
  private Vector _rentals = new Vector();               //

  public Customer(String name) {
      _name = name;
  }

  public void addRental(Rental arg) {
      _rentals.addElement(arg);
  }

  public String getName() {
      return _name;
  }

  public String statement() {
    Enumeration rentals = _rentals.elements();
    String result = "Rental Record for " + getName() + "\n";

    while(rentals.hasMoreElements()){
      Rental each = (Rental) rentals.nextElement(); //

      // show figures for this rental®Æ¡^
      result += "\t" + each.getMovie().getTitle() + "\t" +
          String.valueOf(each.getCharge()) + "\n";
    }

    // add footer lines¡^
    result += "Amount owed is " + String.valueOf(getTotalCharge()) + "\n";
    result += "You earned " + String.valueOf(getTotalFrequentRenterPoints()) +
            " frequent renter points";
    return result;
  }
  private int getTotalFrequentRenterPoints(){
	int result = 0;
	Enumeration rentals = _rentals.elements();
	while(rentals.hasMoreElements()){
		Rental each = (Rental) rentals.nextElement();
		result += each.getFrequentRenterPoints();
	}
	return result;
  }
  private double getTotalCharge(){
	double result = 0;
	Enumeration rentals = _rentals.elements();
	while(rentals.hasMoreElements()){
		Rental each = (Rental) rentals.nextElement();
		result += each.getCharge();
	}
	return result;
  }
}