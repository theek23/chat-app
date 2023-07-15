package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static ArrayList<Client> clients = new ArrayList<>();
    public static void main(String[] args) throws IOException{
        boolean status = true;
        ServerSocket serverSocket = new ServerSocket(3000);
        Socket socket;

        while (status){
            System.out.println("Waiting for client ....");
            socket = serverSocket.accept();
            System.out.println("Client Connected");
            Client clientThred = new Client(socket, clients);
            clients.add(clientThred);
            clientThred.start();
        }
    }
}
