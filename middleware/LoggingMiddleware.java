package middleware;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;

public class LoggingMiddleware implements Filter {

    // Replace with your actual token from Authentication API
    private static final String ACCESS_TOKEN =" eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJNYXBDbGFpbXMiOnsiYXVkIjoiaHR0cDovLzIwLjI0NC41Ni4xNDQvZXZhbHVhdGlvbi1zZXJ2aWNlIiwiZW1haWwiOiJzYjE1MjVAc3JtaXN0LmVkdS5pbiIsImV4cCI6MTc3NzcwMjc1MCwiaWF0IjoxNzc3NzAxODUwLCJpc3MiOiJBZmZvcmQgTWVkaWNhbCBUZWNobm9sb2dpZXMgUHJpdmF0ZSBMaW1pdGVkIiwianRpIjoiYzE3M2M5MmYtNzYyOS00MWFmLTliOGMtYjE4ODY4YmJiMTQxIiwibG9jYWxlIjoiZW4tSU4iLCJuYW1lIjoiYi5zcmlrcmlzaGEiLCJzdWIiOiIyNzdmNjNmYi0xYmM5LTQzOTctYmFjOS1iZmE0ODkzYjc0OTkifSwiZW1haWwiOiJzYjE1MjVAc3JtaXN0LmVkdS5pbiIsIm5hbWUiOiJiLnNyaWtyaXNoYSIsInJvbGxObyI6InJhMjMxMTAwMzA1MDM0NCIsImFjY2Vzc0NvZGUiOiJRa2JweEgiLCJjbGllbnRJRCI6IjI3N2Y2M2ZiLTFiYzktNDM5Ny1iYWM5LWJmYTQ4OTNiNzQ5OSIsImNsaWVudFNlY3JldCI6IlJaanN2V3F3R25ncUNTY2YifQ.WpzREeIsMeW0qSOA69j07hTGLMbiPxElZih5VxuQMdY";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // BEFORE API
        logToAPI("info", "api",
                String.format("Incoming %s %s from %s",
                        req.getMethod(),
                        req.getRequestURI(),
                        req.getRemoteAddr()));

        chain.doFilter(request, response);

        // AFTER API
        logToAPI("info", "api",
                String.format("Response status: %d", res.getStatus()));
    }

    private void logToAPI(String level, String pkg, String message) {
        try {
            // Use the same endpoint that worked in Postman
            URL url = new URL("http://20.244.56.144/evaluation-service/logs");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String json = String.format(
                "{ \"stack\": \"backend\", " +
                "\"level\": \"%s\", " +
                "\"package\": \"%s\", " +
                "\"message\": \"%s\", " +
                "\"timestamp\": \"%s\" }",
                level, pkg, message, Instant.now().toString()
            );

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes("utf-8"));
            }

            int status = conn.getResponseCode();
            System.out.println("Log API response: " + status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
