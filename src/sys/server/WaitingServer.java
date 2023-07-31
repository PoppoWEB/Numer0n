package sys.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import game.level.Human;

public class WaitingServer {
    private Server cs;
    private Human enemyHuman;
    public WaitingServer(int port, int timeoutMillis, Human myHuman) throws IOException {
        try (ServerSocket server = new ServerSocket(port)) {
            server.setSoTimeout(timeoutMillis);
    
            Socket socket = server.accept();
            cs = new Server(socket);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(myHuman);
            
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            enemyHuman = (Human) ois.readObject();
            
        } catch (SocketTimeoutException e) {
            throw new SocketTimeoutException("Timeout occurred while waiting for connection.");
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("An error occurred while waiting for connection.", e);
        }
    }

    public Server getServer() {
        return cs;
    }

    public Human getEnemyHuman() {
        return enemyHuman;
    }
}
