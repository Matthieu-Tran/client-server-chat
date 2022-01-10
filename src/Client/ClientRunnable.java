package Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientRunnable implements Runnable {
    private final BufferedReader input;

    public ClientRunnable(Socket s) throws IOException {
        InputStream in = s.getInputStream();
        this.input = new BufferedReader(new InputStreamReader(in));
    }
    @Override
    public void run() {
        try {
            while(true) {
                //Si il y a eu un probleme avec le serveur, on quitte la boucle
                int temp=input.read();
                if(temp<0)
                    break;
                else {
                    char tempChar = (char)temp;
                    String repTemp = input.readLine();
                    String response2 = tempChar+repTemp;
                    //Si le serveur a envoye Goodbye alors on quitte la boucle
                    if (response2.contains("Goodbye"))
                        break;
                    System.out.println(response2);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}