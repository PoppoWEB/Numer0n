package sys;

import java.io.IOException;

public interface Connect {
    void send(String str);
    String receive() throws IOException;
    void close();
}
