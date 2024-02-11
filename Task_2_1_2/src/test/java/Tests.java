import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Tests {
    @Test
    public void test() throws IOException {
        Server.main("server");
        Client.main("client");
        Client.main("client");
    }
}
