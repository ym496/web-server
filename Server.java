import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  private static BufferedWriter writer;
  private static BufferedReader reader;
  private static String basedir = "./serve/"; 

  private static void parse(String line) {
    try {
      if (line.startsWith("GET")){
        String[] l = line.split(" ");
        String path = l[1];
        File file = new File(basedir+path);
        if (!file.exists()){
          writer.write("HTTP/1.1 404 Not Found");
          writer.newLine();
          writer.write("Content-Type: " + getMimeType(path));
          writer.newLine();
          writer.newLine();
          writer.write("<h1>404 Not Found</h1>");
          writer.flush();
        }else{
          serve(path);
        }
        System.out.println(path);
      }
      if (line.startsWith("POST")){
        int contentLength = 0;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
          if (line.startsWith("Content-Length")) {
            contentLength = Integer.parseInt(line.split(" ")[1]);
            System.out.println(contentLength);
          }
        }
        char[] body = new char[contentLength];
        reader.read(body);
        String requestBody = new String(body);
        System.out.println("Received POST data: " + requestBody);
        writer.write("HTTP/1.1 200 OK");
        writer.newLine();
        writer.write("Content-Type: text/html");
        writer.newLine();
        writer.write("Content-Length: " + "<h1>POST request received</h1>".length());
        writer.newLine();
        writer.write("Connection: close");
        writer.newLine();
        writer.newLine();
        writer.write("<h1>POST request received</h1>");
        writer.write("****************************");
        writer.flush();
      }
      if (line.startsWith("PUT")){
        System.out.println("heyyy it's a put request!!");
      }
      // imagine other CRUD methods below ok thx
    } catch (IOException e){
       System.err.println("Error occurred: " + e.getMessage());
    }
  }
  private static String getMimeType(String filePath) {
      if (filePath.endsWith(".html")) return "text/html";
      if (filePath.endsWith(".css")) return "text/css";
      if (filePath.endsWith(".js")) return "application/javascript";
      if (filePath.endsWith(".png")) return "image/png";
      if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) return "image/jpeg";
      if (filePath.endsWith(".gif")) return "image/gif";
      if (filePath.endsWith(".svg")) return "image/svg+xml";
      return "text/html";
  }
  private static void serve(String path){
    try {
      writer.write("HTTP/1.1 200 OK");
      writer.newLine();
      writer.write("Content-Type: " + getMimeType(path));
      writer.newLine();
      writer.newLine();
      if (path.equals("/")){
        path = "/index.html";
      }
      BufferedReader file = new BufferedReader(new FileReader(basedir + path));
      String line;
      while ((line = file.readLine()) != null) {
          writer.write(line);
      }
      writer.flush();
      System.out.println("Response sent.");
    } catch (IOException e){
       System.err.println("Error occurred: " + e.getMessage());
    }
  }
 public static void main(String[] args) {
   try {
     ServerSocket serverSocket = new ServerSocket(1234);
     System.out.println("Server listening");
     while (true){
         Socket connectionSocket = serverSocket.accept();
         System.out.println("Client connected.");
         reader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
         writer = new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));
         connectionSocket.setKeepAlive(true);
         String line;
         while ((line = reader.readLine()) != null && !line.isEmpty()) {
           parse(line);
           System.out.println(line);
         }
          reader.close();
          writer.close();
          connectionSocket.close();
     }
   } catch (IOException e) {
     System.err.println("Error occurred: " + e.getMessage());
   }
 }
}
