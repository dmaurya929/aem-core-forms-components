// package com.adobe.cq.forms.core.components.internal.cybersource.service;
//
// import java.math.BigInteger;
// import java.security.KeyFactory;
// import java.security.interfaces.RSAPublicKey;
// import java.security.spec.RSAPublicKeySpec;
// import java.util.Base64;
// import java.util.Base64.Decoder;
//
// import org.apache.http.HttpResponse;
// import org.apache.http.client.HttpClient;
// import org.apache.http.client.methods.HttpGet;
// import org.apache.http.impl.client.HttpClients;
// import org.apache.http.util.EntityUtils;
// import org.osgi.service.component.annotations.Component;
// import org.osgi.service.component.annotations.Reference;
//
// import com.adobe.cq.forms.core.components.internal.cybersource.config.MerchantCredentials;
// import com.adobe.cq.forms.core.components.internal.cybersource.dto.CaptureContextResponseBody;
// import com.adobe.cq.forms.core.components.internal.cybersource.dto.CaptureContextResponseHeader;
// import com.adobe.cq.forms.core.components.internal.cybersource.dto.JWK;
// import com.auth0.jwt.JWT;
// import com.auth0.jwt.JWTVerifier;
// import com.auth0.jwt.algorithms.Algorithm;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import lombok.RequiredArgsConstructor;
// import lombok.SneakyThrows;
//
// @RequiredArgsConstructor
// @Component
// public class JwtProcessorService {
//
// @Reference
// private MerchantCredentials merchantCredentials;
//
// @SneakyThrows
// public String verifyJwtAndGetDecodedBody(final String jwt) {
// // Parse the JWT response into header, payload, and signature
// final String[] jwtChunks = jwt.split("\\.");
// final Decoder decoder = Base64.getUrlDecoder();
// final String header = new String(decoder.decode(jwtChunks[0]));
// final String body = new String(decoder.decode(jwtChunks[1]));
//
// // Normally you'd want to cache the header and JWK, and only hit /flex/v2/public-keys/{kid} when the key rotates.
// // For simplicity and demonstration's sake let's retrieve it every time
// final JWK publicKeyJWK = getPublicKeyFromHeader(header);
//
// // Construct an RSA Key out of the response we got from the /public-keys endpoint
// final BigInteger modulus = new BigInteger(1, decoder.decode(publicKeyJWK.getN()));
// final BigInteger exponent = new BigInteger(1, decoder.decode(publicKeyJWK.getE()));
// final RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus,
// exponent));
//
// // Verify the JWT's signature using the public key
// final Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, null);
// final JWTVerifier verifier = JWT.require(algorithm).build();
//
// // This will throw a runtime exception if there's a signature mismatch.
// verifier.verify(jwt);
//
// return body;
// }
//
// @SneakyThrows
// public String getClientVersionFromDecodedBody(final String body) {
// // We've verified the response is from Cybersource, so we can safely pass the client library to our frontend
// CaptureContextResponseBody mappedCaptureContextResponse = new ObjectMapper().readValue(body, CaptureContextResponseBody.class);
//
// // Dynamically retrieve the client library
// return mappedCaptureContextResponse.getCtx().stream().findFirst()
// .map(wrapper -> wrapper.getData().getClientLibrary())
// .orElseThrow();
//
// }
//
// @SneakyThrows
// private JWK getPublicKeyFromHeader(final String jwtHeader) {
// // Again, this process should be cached so you don't need to hit /public-keys
// // You'd want to look for a difference in the header's value (e.g. new key id [kid]) to refresh your cache
// CaptureContextResponseHeader mappedJwtHeader = new ObjectMapper().readValue(jwtHeader, CaptureContextResponseHeader.class);
//
// HttpClient httpClient = HttpClients.createDefault();
// String url = "https://" + merchantCredentials.getRequestHost() + "/flex/v2/public-keys/" + mappedJwtHeader.getKid();
// HttpGet httpGet = new HttpGet(url);
//
// try {
// HttpResponse httpResponse = httpClient.execute(httpGet);
// String responseBody = EntityUtils.toString(httpResponse.getEntity());
//
// ObjectMapper objectMapper = new ObjectMapper();
// return objectMapper.readValue(responseBody, JWK.class);
// } catch (Exception e) {
// e.printStackTrace();
// return null;
// }
// }
// }
