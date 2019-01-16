package pl.edu.agh.fridgeapp.model;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;

import pl.edu.agh.fridgeapp.model.IncorrectLoginDataException;
import pl.edu.agh.fridgeapp.model.Refrigerator;


public class FridgeClient implements Callable<Refrigerator> {

    private String hostName;
    private int portNumber;
    private int loginHash;
    private int passHash;
    private Socket socket;


    public FridgeClient(String hostName, int portNumber, int loginHash, int passHash) {
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.loginHash = loginHash;
        this.passHash = passHash;
    }

    @Override
    public Refrigerator call() throws IOException,IncorrectLoginDataException {
        if(!login(loginHash,passHash))
            throw new IncorrectLoginDataException();
        return getFridge();
    }

    public boolean login(int loginHash, int passwordHash) throws IOException {
        if(loginHash==0 && passwordHash==0){
            throw new IOException();
        }
        return false;
    }

    public Refrigerator getFridge() {
        return null;
    }


}


