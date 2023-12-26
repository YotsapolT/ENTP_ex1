package TCP_Concurrent;

//EchoThread.java
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Pattern;

public class EchoThread extends Thread {
   private Socket connectionSocket;

   public EchoThread(Socket connectionSocket) {
      this.connectionSocket = connectionSocket;
   }

   public void run() {
      Scanner inFromClient = null;
      DataOutputStream outToClient = null;
      try {
         inFromClient = new Scanner(connectionSocket.getInputStream());
         outToClient = new DataOutputStream(connectionSocket.getOutputStream());
         LinkedList<Integer> numList = new LinkedList<>();
         int i = 1;
         while (true) {
            if (i % 2 == 1) {
               outToClient.writeBytes("enter number 1 (to end just press enter): " + '\n');
               String clientSentence = inFromClient.nextLine();
               if (clientSentence.isBlank()) {
                  break;
               } else if (!Pattern.matches("[0-9]*", clientSentence)) {
                  continue;
               }
               numList.add(Integer.parseInt(clientSentence));
               i++;
            } else {
               outToClient.writeBytes("enter number 2 (to end just press enter): " + '\n');
               String clientSentence = inFromClient.nextLine();
               if (clientSentence.isBlank()) {
                  break;
               } else if (!Pattern.matches("[0-9]*", clientSentence)) {
                  continue;
               }
               numList.add(Integer.parseInt(clientSentence));
               int result = numList.get(numList.size() - 2) + numList.get(numList.size() - 1);
               outToClient.writeBytes("The result is " + result + '\n');
               i++;
            }
         }
      } catch (IOException e) {
         System.err.println("Closing Socket connection");
      } finally {
         try {
            if (inFromClient != null)
               inFromClient.close();
            if (outToClient != null)
               outToClient.close();
            if (connectionSocket != null)
               connectionSocket.close();
            System.out.println("All closed!");
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
}
