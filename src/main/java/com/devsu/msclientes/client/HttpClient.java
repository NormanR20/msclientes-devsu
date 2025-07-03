package com.devsu.msclientes.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hguzman
 */
public class HttpClient {

    // @Value("${app.http.read.timeout}")
    private final int readTimeout = 180;

    //@Value("${app.http.connect.timeout}")
    private final int connectTimeout = 180;

    private static final Map<Integer, String> ERROR_MESSAGES = new HashMap<>();

    private static final String DEFAULT_MESSAGE = "";

    static {
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_ACCEPTED, "HTTP_ACCEPTED");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_BAD_GATEWAY, "HTTP_BAD_GATEWAY");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_BAD_METHOD, "HTTP_BAD_METHOD");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_BAD_REQUEST, "HTTP_BAD_REQUEST");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_CLIENT_TIMEOUT, "HTTP_CLIENT_TIMEOUT");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_CONFLICT, "HTTP_CONFLICT");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_CREATED, "HTTP_CREATED");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_ENTITY_TOO_LARGE, "HTTP_ENTITY_TOO_LARGE");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_FORBIDDEN, "HTTP_FORBIDDEN");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_GATEWAY_TIMEOUT, "HTTP_GATEWAY_TIMEOUT");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_GONE, "HTTP_GONE");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_INTERNAL_ERROR, "HTTP_INTERNAL_ERROR");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_LENGTH_REQUIRED, "HTTP_ACCEPTED");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_MOVED_PERM, "HTTP_MOVED_PERM");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_MULT_CHOICE, "HTTP_MULT_CHOICE");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_NOT_ACCEPTABLE, "HTTP_NOT_ACCEPTABLE");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_NOT_AUTHORITATIVE, "HTTP_NOT_AUTHORITATIVE");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_NOT_FOUND, "HTTP_NOT_FOUND");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_NOT_IMPLEMENTED, "HTTP_NOT_IMPLEMENTED");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_NOT_MODIFIED, "HTTP_NOT_MODIFIED");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_NO_CONTENT, "HTTP_ACCEPTED");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_PARTIAL, "HTTP_PARTIAL");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_PAYMENT_REQUIRED, "HTTP_PAYMENT_REQUIRED");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_PRECON_FAILED, "HTTP_PRECON_FAILED");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_PROXY_AUTH, "HTTP_PROXY_AUTH");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_REQ_TOO_LONG, "HTTP_REQ_TOO_LONG");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_RESET, "HTTP_RESET");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_SEE_OTHER, "HTTP_SEE_OTHER");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_UNAUTHORIZED, "HTTP_UNAUTHORIZED");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_UNAVAILABLE, "HTTP_UNAVAILABLE");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_UNSUPPORTED_TYPE, "HTTP_UNSUPPORTED_TYPE");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_USE_PROXY, "HTTP_USE_PROXY");
        ERROR_MESSAGES.put(HttpURLConnection.HTTP_VERSION, "HTTP_VERSION");
    }

    /**
     * @param <T>
     * @param req
     * @param url
     * @return
     * @throws Exception
     */
    public <T> String post(T req, String url) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String request = objectMapper.writeValueAsString(req);
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            if (conn instanceof HttpsURLConnection httpsConnection) {
                httpsConnection.setSSLSocketFactory(createNonSSLContext().getSocketFactory());
                httpsConnection.setHostnameVerifier((hostname, session) -> true);
            }
            conn.setDoOutput(true);
            conn.setConnectTimeout(readTimeout * 1000);
            conn.setReadTimeout(connectTimeout * 1000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestMethod("POST");

            byte[] inputBytes = request.getBytes(StandardCharsets.UTF_8);
            conn.setRequestProperty("Content-Length", String.valueOf(inputBytes.length));
            try (OutputStream os = conn.getOutputStream()) {
                os.write(inputBytes);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder response = new StringBuilder();
                String line;
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                }
                conn.disconnect();
                return response.toString();
            }
            throw new IllegalArgumentException(codeError(responseCode));
        } catch (IOException | RuntimeException | KeyManagementException | NoSuchAlgorithmException e) {
            throw (e);
        }
    }

    private static SSLContext createNonSSLContext() throws RuntimeException, KeyManagementException, NoSuchAlgorithmException {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509TrustManager = null;
                return x509TrustManager;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
                // comment explaining why the method is empty
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
                // comment explaining why the method is empty
            }
        }};

        SSLContext sc = SSLContext.getInstance("TLSv1.2");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        return sc;

    }

    /**
     * @param code
     * @return
     */
    public static String codeError(int code) {
        return ERROR_MESSAGES.getOrDefault(code, DEFAULT_MESSAGE);
    }

}
