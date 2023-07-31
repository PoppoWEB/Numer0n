package sys.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import sys.Connect;

public class Server implements Connect{
    private Socket socket;
    protected PrintWriter send;
    protected BufferedReader receive;

    public Server(Socket socket) {
        this.socket = socket;

        try {
            send = new PrintWriter(socket.getOutputStream(), true);
            receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Server: send or receive instance is null");
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

    public void send(String str) {
        send.println(str);
    }

    public String receive() throws IOException {
        return receive.readLine();
    }
}
