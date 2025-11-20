package protocol;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Provides a simple protocol for sending
 * and receiving messages over a socket.
 */
public final class Protocol implements Closeable {
  private final Socket socket;
  private final BufferedReader in;
  private final BufferedWriter out;

  /**
   * Constructor for Protocol.
   *
   * @param socket the socket to use for communication
   * @throws IOException if an I/O error occurs when creating the input/output streams
   */
  public Protocol(Socket socket) throws IOException {
    this.socket = socket;
    this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
    this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
  }

  /**
   * Sends a message over the socket.
   *
   * @param message the message to send
   * @throws IOException if an I/O error occurs when sending the message
   */
  public void sendMessage(String message) throws IOException {
    out.write(message);
    out.write('\n');
    out.flush();
  }

  /**
   * Reads a message from the socket.
   *
   * @return the message read from the socket
   * @throws IOException if an I/O error occurs when reading the message
   */
  public String readMessage() throws IOException {
    return in.readLine();
  }

  /**
   * Closes the protocol and the underlying socket.
   *
   * @throws IOException if an I/O error occurs when closing the socket
   */
  @Override public void close() throws IOException {
    socket.close();
  }
}