
import java.util.HashMap;
import java.util.Scanner;
import java.time.LocalDate;

public class Transaction_Tracker_hashmap {

    public static void main(String[] args) {
        
        HashMap<Integer, String> Transaction = new HashMap<>();
        
        Scanner scanner = new Scanner(System.in);
        int TransactionID = 1;

        while(true) {   
            
            System.out.println("1. Add Transaction");
            System.out.println("2. Yesterdays Transactions");
            System.out.println("3. Total income and expenses");
            System.out.println("4. Exit");
            System.out.println("5. Print all Transactions");

            System.out.print("Enter your choice: ");
          int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch(choice) {
                case 1:
                    System.out.println("Enter transaction type");
                    String Type = scanner.nextLine();
                    System.out.println("Enter transaction amount");
                    double Amount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    LocalDate date = LocalDate.now();
                    Transaction.put(TransactionID++, Type + "," + Amount + "," + date);
                    System.out.println("Transaction added successfully.");
                    break;


                case 2:
                    LocalDate yesterday = LocalDate.now().minusDays(1);
                   int count = 0 ;
                   for (String transaction : Transaction.values()) {
                        String [] parts = transaction.split(",");
                        if(parts[2].equals(yesterday.toString())){

                            count ++;
                        }
                    }
                    System.out.println("Yesterdays Transactions: " + count);
                    break;  
                        
                case 3:
                    double income = 0;
                    double expense = 0;
                
                    for (String transaction : Transaction.values()) {
                        String [] parts = transaction.split(",");
                        String type = parts[0];
                        double amount = Double.parseDouble(parts[1]);
                        if(type.equalsIgnoreCase("Sale")){
                            income += amount;
                        } else if(type.equalsIgnoreCase("Purchase")){
                            expense += amount;
                        }

                        
                    }
                    System.out.println("Total Income: " + income);
                    System.out.println("Total Expenses: " + expense);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                case 5:
                default:
                    System.out.println("Invalid choice. Please try again.");
            }


        }
        



     }




}
