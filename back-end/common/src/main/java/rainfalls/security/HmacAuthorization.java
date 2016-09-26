package rainfalls.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.DigestUtils;
import org.springframework.util.SerializationUtils;

/**
 * Common class to extends by filters which uses Hash-based Message Authentication (HMAC) to sign the requests with a private key.
 *
 * TODO: at the moment only md5 of content is part of authorization string, so maybe date and nonce should be added to improve it
 */
abstract class HmacAuthorization {
    protected static final String HMAC_ALGORITHM = "HmacSHA256";
    protected static final String AUTHORIZATION_PROPERTY = "Authorization";
    
    // common format for Authorization: <type> <credentials>
    private static final String AUTHHORIZATION_FORMAT = "RAINFALLS %s"; 
	
    /**
     * Build a signature string by encryption of entity's MD5 hash
     */
    protected String buildSignature(Object entity) throws Exception { 	
    	byte[] md5StationHash = DigestUtils.md5Digest(SerializationUtils.serialize(entity));
		
    	// TODO move the key into property file so it can be configured externally
    	return String.format(AUTHHORIZATION_FORMAT, encrypt(md5StationHash, "secretAPIKey"));
    }
	
    protected String encrypt(byte[] data, String key) throws Exception {
	     Mac mac = Mac.getInstance(HMAC_ALGORITHM);
	     mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM));
	     
	     return Base64.getEncoder().encodeToString(mac.doFinal(data));
	}
}
