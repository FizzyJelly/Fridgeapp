package pl.edu.agh.fridgeapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class FridgeClient {

    private Socket socket;
    private PrintWriter outStream;
    private BufferedReader inStream;


    private FridgeClient(String hostName, int portNumber) throws IOException {

        this.socket = new Socket(hostName, portNumber);
        this.outStream = new PrintWriter(socket.getOutputStream(), true);
        this.inStream = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));


    }

    public static FridgeClient startListening(String hostName, int portNumber) {
        try {
            final FridgeClient client = new FridgeClient(hostName, portNumber);
            Thread listeningThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        client.listen();
                    } catch (IOException exception){
                        Toaster.toast(exception.getMessage());
                    }
                }
            });
            listeningThread.start();
            return client;
        } catch (IOException exception) {
            Toaster.toast(exception.getMessage());
            return null;
        }
    }


    private void listen() throws IOException {
        Toaster.toast("Listening");
        String serverResponse;
        while ((serverResponse = inStream.readLine()) != null && !serverResponse.equals("Stop listening!!!")) {
            Toaster.toast("Server: " + serverResponse);
        }
    }

    public void saySomething(String message) {
        Toaster.toast("Client: " + message);
        this.outStream.println(message);
    }


}


