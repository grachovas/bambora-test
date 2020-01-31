package com.bambora.server.security;

import com.bambora.server.commons.exceptions.TrustlyAPIException;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.KeyException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
class KeyChain {

    private static final String TEST_TRUSTLY_PUBLIC_KEY_PATH = "keys/test_trustly_public.pem";
    private static final String PRIVATE_KEY_PATH = "keys/private.pem";
    private static final String PRIVATE_KEY_PASSWORD = "";

    private PrivateKey merchantPrivateKey;
    private PublicKey trustlyPublicKey;

    KeyChain() throws KeyException {
        loadTrustlyPublicKey();
        loadMerchantPrivateKey();
    }

    private void loadMerchantPrivateKey() throws KeyException {
        try {

            InputStream initialStream = new ClassPathResource(PRIVATE_KEY_PATH).getInputStream();
            Reader targetReader = new InputStreamReader(initialStream);
            final PEMParser pemParser = new PEMParser(targetReader);
            final Object object = pemParser.readObject();

            final JcaPEMKeyConverter converter = new JcaPEMKeyConverter();

            final KeyPair kp;
            if (object instanceof PEMEncryptedKeyPair) { // Password required
                final PEMDecryptorProvider decProv = new JcePEMDecryptorProviderBuilder().build(PRIVATE_KEY_PASSWORD.toCharArray());
                kp = converter.getKeyPair(((PEMEncryptedKeyPair) object).decryptKeyPair(decProv));
            } else {
                kp = converter.getKeyPair((PEMKeyPair) object);
            }

            merchantPrivateKey = kp.getPrivate();
        }
        catch (final IOException e) {
            throw new KeyException("Failed to load private key", e);
        }
    }

    private void loadTrustlyPublicKey() {
        try {
            InputStream initialStream = new ClassPathResource(TEST_TRUSTLY_PUBLIC_KEY_PATH).getInputStream();
            Reader targetReader = new InputStreamReader(initialStream);
            final PEMParser pemParser1 = new PEMParser(targetReader);
            final PemObject object = pemParser1.readPemObject();

            final JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider(new BouncyCastleProvider());

            final byte[] encoded = object.getContent();
            final SubjectPublicKeyInfo subjectPublicKeyInfo = new SubjectPublicKeyInfo(
                    ASN1Sequence.getInstance(encoded));

            trustlyPublicKey = converter.getPublicKey(subjectPublicKeyInfo);
        }
        catch (final IOException e) {
            throw new TrustlyAPIException("Failed to load Trustly public key", e);
        }
    }

    PrivateKey getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    PublicKey getTrustlyPublicKey() {
        return trustlyPublicKey;
    }
}
