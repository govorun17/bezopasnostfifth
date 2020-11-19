import helper.Helper;

import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class Algorithm {
    private KeyPair keyPair;

    public String registerKeys() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
            keyPairGenerator.initialize(1024);
            keyPair = keyPairGenerator.generateKeyPair();
            return Helper.bytesToHex(keyPair.getPublic().getEncoded());
        }
        catch (Exception ignore) {}
        return null;
    }

    public String create(String s) throws IOException {
        if (s == null || s.isEmpty()) {
            throw new IOException("Введите сообщение");
        }
        try {
            Signature signature = Signature.getInstance("SHA256WithDSA");

            signature.initSign(keyPair.getPrivate());

            signature.update(s.getBytes());

            byte[] digitalSignature = signature.sign();
            return Helper.bytesToHex(digitalSignature);
        }
        catch (Exception ignore) {}
        return null;
    }

    public Boolean validate(String key, String pass, String msg) throws IOException {
        if (pass == null || pass.isEmpty()) {
            throw new IOException("Введите номер подписи");
        }
        if (key == null || key.isEmpty()) {
            throw new IOException("Введите номер подписи");
        }
        if (msg == null || msg.isEmpty()) {
            throw new IOException("Введите номер подписи");
        }

        try {
            KeyFactory kf = KeyFactory.getInstance("DSA");
            byte[] publicKeyBytes = Helper.hexStringToByteArray(key);
            PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

            System.out.println(this.keyPair.getPublic().equals(publicKey));

            Signature signature = Signature.getInstance("SHA256WithDSA");

            byte[] b = Helper.hexStringToByteArray(pass);

            signature.initVerify(publicKey);
            signature.update(msg.getBytes());
            return signature.verify(b);
        }
        catch (Exception ignore) {}
        return false;
    }
}
