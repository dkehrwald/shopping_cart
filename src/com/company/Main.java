package com.company;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        double fullApplesDiscount = 0;

        initialOutput();
        String choices = readInput();
        String[] ChoicesArray = parseInput( choices );
        int tempSize = ChoicesArray.length;
        int numSoup = findQuantity( ChoicesArray, "Soup");
        int numBread = findQuantity( ChoicesArray, "Bread");
        int numMilk = findQuantity( ChoicesArray, "Milk");
        int numApples = findQuantity( ChoicesArray, "Apples");

        Soup soup = new Soup();
        soup.setQuantity( numSoup );
        soup.setPrice(.65);

        Bread bread = new Bread();
        bread.setQuantity( numBread );
        bread.setPrice(.80);
        bread.setDiscount(0.40);

        Milk milk = new Milk();
        milk.setQuantity( numMilk );
        milk.setPrice(1.30);

        Apples apples = new Apples();
        apples.setQuantity( numApples );
        apples.setPrice(1.00);
        apples.setDiscount(0.10);

        setApplesDiscount( numApples, fullApplesDiscount, apples );
        setBreadDiscount( soup, bread );
        setOutput( soup, bread, milk, apples );
    }


    /** Initial output to user
     *
     */
    public static void initialOutput() {
        System.out.println( "Choose from Soup, Bread, Milk or Apples." );
        System.out.println( "Soup costs 65p per Tin" );
        System.out.println( "Bread costs 80p per Loaf" );
        System.out.println( "Milk costs £1.30 per Bottle" );
        System.out.println( "Apples costs £1.00 per Bag" );
        System.out.println();
        System.out.println( "Special offers this week are:" );
        System.out.println( "Apples are 10% off" );
        System.out.println( "Buy 2 tins of Soup and get a loaf of Bread 50% off" );
        System.out.println();
        System.out.println( "Enter your choices below in the form:" );
        System.out.println( "PriceBasket item1 item2 item3" );
        System.out.println();
    }

    /** Read input from user...ex: PriceBasket item1 item2 item3 item4 ...
     *
     * @return
     */
    public static String readInput() {
        Scanner keyboard = new Scanner ( System.in );

        String choices = keyboard.nextLine();

        keyboard.close();
        return choices;
    }

    /**
     * Parse the input from user and determine quantity of items and sorts alphabetically
     * @param choices String input from user
     * @return Array of strings of Goods
     */
    public static String[] parseInput(String choices ) {
        String[] ChoicesArray = choices.split( "\\s+" );

        for ( int i = 0; i < ChoicesArray.length; i++ ) {
            if ( i == 0 && !ChoicesArray[i].equalsIgnoreCase("PriceBasket") ) {
                System.out.println( "Invalid Entry. Try again" );
                System.exit( 0 );
            }
        }

        Arrays.sort(ChoicesArray);
        return ChoicesArray;
    }

    /**
     * goes through the array to find the quantity of each item.
     * @param ChoicesArray is the array from the input
     * @param keyword String is which item u want to count
     * @return int of how many times that item was selected
     */
    public static int findQuantity( String[] ChoicesArray, String keyword ) {
        int count = 0;

        for ( int i = 0; i < ChoicesArray.length; i++ ) {
            if ( ChoicesArray[i].equalsIgnoreCase( keyword ) ) {
                count++;
            }
        }
        return count;
    }

    /**
     * Sets the Apples Discount
     * @param numApples
     * @param fullApplesDiscount
     * @param apples
     */
    public static double setApplesDiscount( int numApples, double fullApplesDiscount, Apples apples ) {

        if ( numApples > 0 ) {
            fullApplesDiscount = numApples * apples.getDiscount();
            apples.setDiscountApplied(true);
        }
        apples.setDiscount( fullApplesDiscount );
        return fullApplesDiscount;
    }

    /**
     * Sets the Bread Discount
     * @param soup
     * @param bread
     */
    public static int setBreadDiscount( Soup soup, Bread bread ) {
        int soupQty = soup.getQuantity();
        int breadQty = bread.getQuantity();
        int breadDiscountQty = 0;
        for( int i = 0; i < bread.getQuantity(); i++ ) {
            if( breadQty == 0 || soupQty < 2 ) {
                break;
            }
            breadDiscountQty++;
            soupQty = soupQty - 2;
            breadQty = breadQty - 1;
        }
        bread.setNumDiscounts( breadDiscountQty );
        if( breadDiscountQty > 0 ) {
            bread.setDiscountApplied( true );
        }
        return breadDiscountQty;

    }

    /**Calculates Totals and Outputs to user.
     *
     * @param soup
     * @param bread
     * @param milk
     * @param apples
     */
    public static boolean setOutput( Soup soup, Bread bread, Milk milk, Apples apples ) {
        DecimalFormat df2 = new DecimalFormat( "#0.00" );
        double soupCost = ( soup.getQuantity() * soup.getPrice() );
        double breadCost = ( bread.getQuantity() * bread.getPrice() );
        double milkCost = ( milk.getQuantity() * milk.getPrice() );
        double applesCost = ( apples.getQuantity() * apples.getPrice() );
        double Total = 0;
        boolean isDiscount = false;

        double TempTotal = soupCost + breadCost + milkCost + applesCost;

        System.out.println( "Subtotal: £" + df2.format(TempTotal) );


        if ( apples.isDiscountApplied() ) {
            for (int i = 0; i < apples.getQuantity(); i++) {
                System.out.println( "Apples 10% off: -10p" );
            }
            Total = TempTotal - (apples.getQuantity() * apples.getDiscount());
            isDiscount = true;
            System.out.println("apples" + isDiscount);
        }
        if ( bread.isDiscountApplied() ) {
            for ( int i = 0; i < bread.getNumDiscounts(); i++ ) {
                System.out.println( "Bread half price: -40p" );
            }
            Total = TempTotal - ( bread.getNumDiscounts() * bread.getDiscount() );
            isDiscount = true;
            System.out.println("bread" + isDiscount);
        }
        if ( apples.isDiscountApplied() && bread.isDiscountApplied() ) {
            Total = TempTotal - ( apples.getQuantity() * apples.getDiscount() +
                    (bread.getNumDiscounts() * bread.getDiscount()) );
            isDiscount = true;
            System.out.println("both" + isDiscount);
        }
        else if ( !bread.isDiscountApplied() && !apples.isDiscountApplied() ) {
            System.out.println( "(No offers available)" );
            Total = TempTotal;
            System.out.println("nothing" + isDiscount);
        }
        System.out.println( "Total Price: £" + df2.format(Total) );
        System.out.println("end" + isDiscount);
        return isDiscount;
    }
}

