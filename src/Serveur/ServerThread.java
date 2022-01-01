package Serveur;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ServerThread extends Thread {
    final private Socket socket;
    private PrintWriter output;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //On lit ce que le client ecrit
            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            output = new PrintWriter(socket.getOutputStream(),true);
            //inifite loop for server
            while(true) {
                String outputString = reader.readLine();
                // On separe le pseudo et le message du client
                String [] msg = outputString.split(": ", 2);
                // Si l'utilisateur écrit "exit" ou bien envoie un message déja envoyé, il sera déconnecté
                if(Main.listeMessages.contains(msg[1]) || msg[1].contains("exit")){
                    System.out.println(msg[0] + " a ete deconnecte :(");
                    if (Main.listeMessages.contains(msg[1]))
                        output.println("Votre message a déja été envoyé, vous allez être déconnecté du serveur");
                    else
                        output.println("Robot9000 vous dit au revoir");
                    //On envoie un message qui quand le client va le recevoir va etre deconnecte
                    output.println(" Goodbye");
                    socket.close();
                    //On lui retire de la liste des threads;
                    Main.listeThread.remove(this);
                    break;
                }
                //On envoie un message a tous les clients
                printToALlClients(outputString);
                //On print dans la console du serveur ce que les clients ont envoye
                System.out.println("Server received "  + outputString);
                //On ajoute le message qu'a envoye un client dans le Hashset du main
                Main.listeMessages.add(msg[1]);
            }
        } catch (Exception e) {
            System.out.println("Error occured " +e.getStackTrace());
        }
    }
    private void printToALlClients(String outputString) {
        System.out.println(Main.listeMessages);
        System.out.println(Main.listeThread);
        for( ServerThread sT: Main.listeThread) {
            sT.output.println(outputString);
        }
    }
}