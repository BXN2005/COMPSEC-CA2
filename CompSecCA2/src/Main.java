import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args){
        Scanner keyboard = new Scanner(System.in);
        String[] menuOptions = {
                "1. Encrypt your file",
                "2. Decrypt a file",
                "3. Quit the application"
        };

        int menuChoice = -1;
        do {
            MenuUtil.displayMenu(menuOptions, "Encryption Menu");
            try {
                menuChoice = MenuUtil.getMenuChoice(menuOptions.length);
                switch (menuChoice) {
                    case 1:
                        System.out.println("Please enter a file name to encrypt");
                        String inputFile= keyboard.next();
                        inputFile = ValidationUtil.validateFileName(inputFile);
                        String encryptedFile = "ciphertext.txt";
                        // Encrypt file
                        EncryptionUtil.encryptFile(inputFile, encryptedFile);
                        System.out.println("Your encrypted file has been printed to: "+encryptedFile);
                        break;
                    case 2:
                        System.out.println("Please enter a file name to decrypt");
                        String encryptedFile2= keyboard.next();
                        encryptedFile2 = ValidationUtil.validateFileName(encryptedFile2);
                        String decryptedFile = "plaintext.txt";
                        // Decrypt file
                        EncryptionUtil.decryptFile(encryptedFile2, decryptedFile);
                        System.out.println("Your decrypted file has been printed to: "+decryptedFile);

                   default:
                        break;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid - Please enter a valid option");
            }
        }
        while(menuChoice != 3);

    }}