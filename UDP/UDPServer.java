import java.net.*;
import java.util.Date;
import java.text.SimpleDateFormat;

class UDPServer {
  public static void main(String args[]) throws Exception {
    DatagramSocket serverSocket = new DatagramSocket(9876);

    while (true) {
      byte[] receiveData = new byte[1024];
      byte[] sendData = new byte[1024];
      System.out.println("The server is waiting ");
      // receive empty str
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      serverSocket.receive(receivePacket);
      InetAddress IPAddress = receivePacket.getAddress();
      int port = receivePacket.getPort();
      // process str
      Date date = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("E, MM/dd/YYYY HH:mm:ss z");
      String strDate = formatter.format(date);
      // send str
      sendData = strDate.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
      serverSocket.send(sendPacket);
    }
  }
}