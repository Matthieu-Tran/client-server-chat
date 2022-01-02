package Serveur;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
public class Main {
    static HashSet<ServerThread> listeThread = new HashSet<>();
    static HashSet<String> listeMessages = new HashSet<>();
    public static void main(String[] args) {
        try (ServerSocket serversocket = new ServerSocket(5555)){
            System.out.println("En attente de client...");
            while(true) {
                Socket socket = serversocket.accept();
                if(socket != null)
                    System.out.println("Nouveau client connecte");
                //ON commence le thread
                ServerThread serverThread = new ServerThread(socket);
                listeThread.add(serverThread);
                serverThread.start();
            }
        } catch (Exception e) {
            System.out.println("Error occurred in main: " + Arrays.toString(e.getStackTrace()));
        }
    }
}