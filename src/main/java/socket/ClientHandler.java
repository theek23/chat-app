package socket;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread{
    private ArrayList<ClientHandler> clientHandlers;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientHandler(Socket socket, ArrayList<ClientHandler> clientHandlers) {
        try {
            this.socket = socket;
            this.clientHandlers = clientHandlers;
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
                for (ClientHandler clientHandler : clientHandlers){
                    clientHandler.writer.println(msg);
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
