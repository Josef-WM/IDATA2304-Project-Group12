package Protocol;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public final class Protocol implements Closeable {
  private final Socket socket;
  private final BufferedReader in;
  private final BufferedWriter out;

  public Protocol(Socket socket) throws IOException {
    this.socket = socket;
    this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
    this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
  }

  public void sendMessage(String message) throws IOException{
    out.write(message);
    out.write('\n');
    out.flush();
  }

  public String readMessage() throws IOException{
    return in.readLine();
  }

  @Override public void close() throws IOException {
    socket.close();
  }
}