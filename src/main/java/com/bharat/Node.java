/**
 * Created by bharat on 2/6/16.
 */
public class Node {
    private String hostName;

    private String ipAddr;

    public int getPort() {
        return port;
    }

    private int port;

    public Node(String hostName,String ipAddr,int port) {
        this.hostName = hostName;
        this.ipAddr = ipAddr;
        this.port = port;
    }

    public String getHostName() {
        return hostName;
    }

    public String getIpAddr() {
        return ipAddr;
    }
}
