import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        int op = -1 ;

        System.out.println("\nEnter password : ");
        String password = keyboard.nextLine();
        int[] encryption = new int[password.length()];

        while(op != 0) {

            System.out.println("\n1. Encrypt password and check encrypted array.\n2. Decrypt password using the output of the encrypted array.\n0. Terminate program");
            System.out.println("Choose option : ");
            op = keyboard.nextInt();

            if(op == 1){
                Vulnerability.encryptionMethod(password, encryption);
            }
            else if (op == 2) {
                Vulnerability.decryptionMethod(encryption);
            }
            else if (op == 0) {
                System.out.println("Program terminated.");
            }
            else{
                System.out.println("Option not found, retry.");
            }
        }
    }
}