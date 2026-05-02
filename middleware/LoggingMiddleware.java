package middleware;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoggingMiddleware implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        // BEFORE API
        logToAPI("info", "api", "Incoming request: " + req.getRequestURI());

        chain.doFilter(request, response);

        // AFTER API (optional)
    }

    private void logToAPI(String level, String pkg, String message) {
        try {
            java.net.URL url = new java.net.URL("http://20.207.122.201/evaluation-service/logs");
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer YOUR_ACCESS_TOKEN");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String json = String.format(
                "{ \"stack\": \"backend\", \"level\": \"%s\", \"package\": \"%s\", \"message\": \"%s\" }",
                level, pkg, message
            );

            try (java.io.OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
            }

            conn.getResponseCode(); // trigger request

        } catch (Exception e) {
            System.out.println("Logging failed");
        }
    }
}