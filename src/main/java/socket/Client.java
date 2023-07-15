package socket;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends Thread{
    private ArrayList<Client> clients;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public Client(Socket socket, ArrayList<Client> clients) {
        try {
            this.socket = socket;
            this.clients = clients;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run(){
        try {
            String msg;
            while ((msg = reader.readLine()) != null){
                if (msg.equalsIgnoreCase("String")){
                    break;
                }
                for (Client client : clients){
                    client.writer.println(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
                writer.close();
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
