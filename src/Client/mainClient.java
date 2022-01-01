package Client;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
public class mainClient {
    public static void main(String[] args){
        Socket socket = null;
        try {
            socket = new Socket("localhost", 5555);
            System.out.println("Connecte au serveur!");
            // Permet d'envoyer les saisies clavier de l'utilisateur au serveur
            PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
            //On cree un scanner pour obtenir les donnees du client
            Scanner scanner = new Scanner(System.in);
            String userInput = "";
            //On prends le pseudo de l'utilisateur
            String clientName = "empty";
            //On cree un thread qui va permettre de gerer les donnees du serveur
            ClientRunnable clientRun = new ClientRunnable(socket);
            Thread t = new Thread(clientRun);
            t.start();
            //On fait une loop jusqu'a ce que l'utilisateur envoie "exit"
            //C'est la boucle qui permet d'envoyer des donnes au serveur
            while(!(userInput.equals("exit"))) {
                //Si le thread est mort on quitte la boucle
                if(!t.isAlive())
                    break;
                if (clientName.equals("empty")) {
                    System.out.println("Veuillez saisir votre prenom ");
                    userInput = scanner.nextLine();
                    clientName = userInput;
                }
                else {
                    String message = ( "(" + clientName + ")" + ": " );
                    System.out.println(message);
                    userInput = scanner.nextLine();
                    writer.println(message + " " + userInput);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Déconnecté");
    }
}