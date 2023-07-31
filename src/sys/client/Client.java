package sys.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import game.level.Human;
import sys.Connect;

public class Client implements Connect{
    private Socket socket;
    private PrintWriter send;
    private BufferedReader receive;
    private Human enemyHuman;
    
    public Client(String ip, int port, Human myHuman) throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(ip, port));
        
        InetAddress inet = socket.getInetAddress();
        if (inet == null) {
            System.out.println("Connection failed.");
            close();
            return;
        } else {
            System.out.println("Connect to " + inet);
        }

        
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            enemyHuman = (Human) ois.readObject();

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(myHuman);

            send = new PrintWriter(socket.getOutputStream(), true);
            receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (send != null) send.close();
            if (receive != null) receive.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Human getEnemyHuman() {
        return enemyHuman;
    }

    public void send(String str) {
        send.println(str);
    }

    public String receive() throws IOException {
        return receive.readLine();
    }
}