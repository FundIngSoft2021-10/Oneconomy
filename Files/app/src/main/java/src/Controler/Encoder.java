package src.Controler;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encoder {
    private static Cipher cipher;
    public static String SHA512(String text){
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(text.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String EncryptKey(String text,String key){
        String value="";
        byte[] valuebytes = key.getBytes();
        SecretKey keySecret = new SecretKeySpec( Arrays.copyOf( valuebytes, 16 ) , "AES" );

        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySecret);
            byte[] textbytes = text.getBytes();
            byte[] cipherbytes =cipher.doFinal(textbytes) ;
            value = Base64.getEncoder().encodeToString(cipherbytes);
        } catch (Exception ex) {
            System.err.println( ex.getMessage() );
        }
        return value;
    }

    public static String DecryptKey(String text,String key){
        String str="";
        byte[] valuebytes = key.getBytes();
        SecretKey keySecret = new SecretKeySpec( Arrays.copyOf( valuebytes, 16 ) , "AES" );
        try {
            byte[] value = Base64.getDecoder().decode(text);
            cipher = Cipher.getInstance("AES");
            cipher.init( Cipher.DECRYPT_MODE, keySecret );
            byte[] cipherbytes = cipher.doFinal( value );
            str = new String( cipherbytes );
        } catch (Exception ex) {
            System.err.println( ex.getMessage() );
        }
        return str;
    }
}
