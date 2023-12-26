import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Pattern;

class TCPClient {
    public static void main(String argv[]) throws Exception {
        String sentence;
        String modifiedSentence;
        Scanner inFromUser = null;
        Socket clientSocket = null;
        DataOutputStream outToServer = null;
        Scanner inFromServer = null;

        String TextFromServer;
        try {
            inFromUser = new Scanner(System.in);
            clientSocket = new Socket("localhost", 1667);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new Scanner(clientSocket.getInputStream());

            while (true) {
                TextFromServer = inFromServer.nextLine();
                System.out.print(TextFromServer);

                sentence = inFromUser.nextLine();
                outToServer.writeBytes(sentence + '\n');
                if (sentence.isBlank()) {
                    break;
                }
                if (TextFromServer.contains("enter number 2") && Pattern.matches("[0-9]*", sentence)) {
                    TextFromServer = inFromServer.nextLine();
                    System.out.println(TextFromServer);
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred: Closing the connection");
        } finally {
            try {
                if (inFromServer != null)
                    inFromServer.close();
                if (outToServer != null)
                    outToServer.close();
                if (clientSocket != null)
                    clientSocket.close();
                System.out.println("All closed!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}