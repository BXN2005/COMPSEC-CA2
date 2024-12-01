
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Scanner;

public class EncryptionUtil {
    private static final String SECRET_KEY = "12345678910";
    private static final String SALT = "xoxoxoxoxo";

    private static SecretKeySpec generateSecretKey() throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    public static void encryptFile(String inputFile, String outputFile) {
        try {


            // Initialization Vector (IV)
            byte[] iv = new byte[16];
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            // Generate secret key
            SecretKeySpec secretKey = generateSecretKey();
            System.out.println("The secret key used was:"+SECRET_KEY);
            System.out.println("You will need the secret key to decrypt");


            // Configure cipher for encryption
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);


            // Read file and encrypt
            FileInputStream fis = new FileInputStream(inputFile);
            FileOutputStream fos = new FileOutputStream(outputFile);
            byte[] buffer = new byte[64];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);

                if (output != null) {
                    fos.write(output);
                }
            }
            byte[] output = cipher.doFinal();
            if (output != null) {
                fos.write(output);
            }

            fis.close();
            fos.close();

            System.out.println("File encrypted successfully!");
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.getMessage());
        }
    }

    public static void decryptFile(String inputFile, String outputFile) {
        try{
            Scanner keyboard = new Scanner(System.in);

                System.out.println("Please enter the secret key to decrypt the file");
                String userSecretKey = keyboard.next();
                if(userSecretKey.equals(SECRET_KEY)) {
                // Initialization Vector (IV)
                byte[] iv = new byte[16];
                IvParameterSpec ivspec = new IvParameterSpec(iv);
                // Generate secret key
                SecretKeySpec secretKey = generateSecretKey();
                // Configure cipher for decryption
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);

                // Read file and decrypt
                FileInputStream fis = new FileInputStream(inputFile);
                FileOutputStream fos = new FileOutputStream(outputFile);
                byte[] buffer = new byte[64];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    byte[] output = cipher.update(buffer, 0, bytesRead);
                    if (output != null) {
                        fos.write(output);
                    }
                }
                byte[] output = cipher.doFinal();
                if (output != null) {
                    fos.write(output);
                }

                fis.close();
                fos.close();

                System.out.println("File decrypted successfully!");
            }
                else{
                    System.out.println("The secret key was in valid");
                }
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.getMessage());
        }
    }
}

