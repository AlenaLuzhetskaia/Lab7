import org.example.Other.ClientNetwork;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        for (var i = 0; i < 5; i++)
        {        ClientNetwork.start();
            Thread.sleep(5000);
    }}
}