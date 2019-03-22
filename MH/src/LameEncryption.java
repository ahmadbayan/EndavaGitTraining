import java.util.Random;

public class LameEncryption {


    private Random r= new Random();
    private final int constant = r.nextInt(1500);
   // private static int k = 0;
    //array for prime rand numbers
    private static int ranPrime[];

    public int calcPrime(int n)
    {
        if (n % 2 == 0 && n != 2 || n < 2){
            return 0;
        }
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return 0;
            }
        }
        return 1;
    }

    //returns a random prime number
    public int calcK(){
        int k=0;
        Random r = new Random();
        //generating a random prime number
        while(calcPrime(k)!=1) {
            k = r.nextInt(1500);
        }
        return k;
    }


    public void encryptionMethod(String inputString , int outputAscii[]) {

        //setting the size of the random array of prime integers
        ranPrime=new int[inputString.length()];
        //assigning random prime numbers to ranPrime array
        for (int i = 0 ; i < inputString.length() ; i++)
            ranPrime[i]=calcK();

        for (int i = 0 ; i < inputString.length() ; i++) {
            outputAscii[i] = (inputString.charAt(i) - constant)*ranPrime[i];
        }

        System.out.print("\nYour encrypted password is : ");
        for(int i = 0 ; i < inputString.length() ; i ++){
            System.out.print(outputAscii[i]+" ");
        }
    }


    public void decryptionMethod(int inputAscii[]){

        System.out.print("\nYour decrypted password is : ");
        for(int i=0 ; i < inputAscii.length ; i++) {
            System.out.print((char) ((inputAscii[i]/ranPrime[i])+constant));
        }
        System.out.println("\n");
    }
}