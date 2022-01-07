package Serveur;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
public class Main {
    static HashSet<ServerThread> listeThreads = new HashSet<>();
    static HashSet<String> listeMessages = new HashSet<>();
    static int cptClients = 0;
    public static void main(String[] args) {
        try (ServerSocket serversocket = new ServerSocket(5555)){
            System.out.println("En attente de client...");
            while(true) {
                Socket socket = serversocket.accept();
                cptClients++;
                if(socket != null)
                    System.out.println("Nouveau client connecte");
                //On commence le thread
                ServerThread serverThread = new ServerThread(socket);
                listeThreads.add(serverThread);
                serverThread.start();
            }
        } catch (Exception e) {
            System.out.println("Error occurred in main: " + Arrays.toString(e.getStackTrace()));
        }
    }
}