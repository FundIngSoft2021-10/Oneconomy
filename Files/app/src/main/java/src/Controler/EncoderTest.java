package src.Controler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EncoderTest {

    @org.junit.jupiter.api.Test
    void SHA512() {
        String expected = "d6e28be88a1a636c34cc905c92bee1691f8ff68b653bf8744f146062432fc928962385f1c707e9362cfc5aa13abb1177eb7dac792bd2bf2b099d4ee86171d99b";
        String received = Encoder.SHA512("sha512 testing");
        assertEquals(expected, received);
    }

    @Test
    void encryptKey() {
        String expected = "WKrB1Z35tZR4BiMtVqEHephADZoP7KFlsFL065wuHbs=";
        String received = Encoder.EncryptKey("this is a test string", "this is a secret key");
        assertEquals(expected, received);
    }

    @Test
    void decryptKey() {
        String expected = "this is a test string";
        String received = Encoder.DecryptKey("WKrB1Z35tZR4BiMtVqEHephADZoP7KFlsFL065wuHbs=", "this is a secret key");
        assertEquals(expected, received);
    }
}