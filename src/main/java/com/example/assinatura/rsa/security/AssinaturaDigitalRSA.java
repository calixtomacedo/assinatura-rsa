package com.example.assinatura.rsa.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class AssinaturaDigitalRSA {

    @Value("${api.security.rsa.privatekey}")
    private String base64PrivateKey;

    public boolean validaAssinatura(String base64PublicKey) throws Exception {

        System.out.println(base64PrivateKey);

        String msg = "Calixto Rodrigues Macedo";

        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        KeyPair pair = keyPairGen.generateKeyPair();
        PrivateKey privateKeyInvalid = pair.getPrivate();


        PrivateKey privateKey = getPrivateKey(base64PrivateKey);
        PublicKey publicKey = getPublicKey(base64PublicKey);

        Signature signaturePrivate = Signature.getInstance("SHA256withRSA");
        //signaturePrivate.initSign(privateKey);
        signaturePrivate.initSign(privateKeyInvalid);
        signaturePrivate.update(msg.getBytes(StandardCharsets.UTF_8));
        byte[] signatureBytes = signaturePrivate.sign();

        Signature signaturePublic = Signature.getInstance("SHA256withRSA");
        signaturePublic.initVerify(publicKey);
        signaturePublic.update(msg.getBytes(StandardCharsets.UTF_8));
        boolean isValid = signaturePublic.verify(signatureBytes);

        return isValid;
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey) {
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }


    public static PublicKey getPublicKey(String base64PublicKey) {
        PublicKey publicKey = null;
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }
}
