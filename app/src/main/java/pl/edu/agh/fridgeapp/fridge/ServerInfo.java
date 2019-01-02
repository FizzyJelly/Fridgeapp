package pl.edu.agh.fridgeapp.fridge;

public class ServerInfo {

    private String hostName;
    private int portNumber;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public ServerInfo(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }
}
