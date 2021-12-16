import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;

import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

public class BasicHttpServerExample {

private static final Logger logger = LogManager.getLogger();

  public static void main(String[] args) throws IOException {
      HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
      HttpContext context = server.createContext("/");
      context.setHandler(BasicHttpServerExample::handleRequest);
      
      logger.info("Run");
      server.start();
  }

  private static void handleRequest(HttpExchange exchange) throws IOException {
    try {
      ThreadContext.put("ua", exchange.getRequestHeaders().getFirst("User-Agent"));
      logger.info("Request received");
    } catch(Exception e) {
      logger.error("Exception");
    }

    String response = "Hi there!";
    exchange.sendResponseHeaders(200, response.getBytes().length);
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }
}