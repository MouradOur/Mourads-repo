    public class factorial_recursion{

    public static void main(String[] args) {
        int number = 4;

        if (number < 0) {
            System.out.println("Factorial is not defined for negative numbers.");
        } else {
            int result = factorial(number);
            System.out.println(number + "! = " + result);
        }
    }

    public static int factorial(int n) {
        if (n == 0 || n == 1) {
            return 1; 
        }
        return n * factorial(n - 1); 
    }
}
