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
