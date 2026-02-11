

public class TRiangle {
    public static  void main(String[] args){
        for (int i=10; i>=1; i--){
            String stars ="*".repeat(i);
            String spaces = " ".repeat(10-i);

            System.out.println(stars+ spaces.repeat(2)+stars);
        }
        for (int i=1; i<=10; i++){
            String stars ="*".repeat(i);
            String spaces = " ".repeat(10-i);

            System.out.println(stars+ spaces.repeat(2)+stars);
        }
    }
}