import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A network client for asynchronous request/response network i/o.
 * manage multiple connection with other nodes.
 * Will try to buffer all requests till TCP packet size is reached
 * otherwise send it as the user demands.
 *
 */

public class MyNetworkClient {
    /* the selector used to perform network i/o */
    private Selector selector ;

    private List<Request> completedSend;

    private List<Response> completedReceive;

    /* the socket send buffer size in bytes */
    private int socketSendBuffer;

    /* the socket receive size buffer in bytes */
    private int socketReceiveBuffer;

    /*
     * stores request to send them in future.
     */
    private Map<String,List<Request>> requestBufferPerNode;

    /*ask selector if node is ready or not*/
    public boolean ready(Node node, long now){
        return false;
    }


    public SelectionKey connect(Node node, long now)throws  IOException{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Socket socket = socketChannel.socket();
        socket.setKeepAlive(true);
        socket.setSendBufferSize(socketSendBuffer);
        socket.setReceiveBufferSize(socketReceiveBuffer);
        socket.setTcpNoDelay(true);
        try {
            socketChannel.connect(new InetSocketAddress(node.getHostName(), node.getPort()));
        } catch (UnresolvedAddressException e) {
            socketChannel.close();
            throw new IOException("Can't resolve address: " + node.getHostName(), e);
        } catch (IOException e) {
            socketChannel.close();
            throw e;
        }
        SelectionKey key = socketChannel.register(selector, SelectionKey.OP_CONNECT);
        return key;
    }

    /*
    * does not actually send the data.
    * add current request in send queue but dont send it.
    * check inflightRequest limit reached if not queue the request else
    */
    public void send(Node node, Request request){

        requestBufferPerNode.get(node.getHostName()).add(request);
    }

    /*
     *   check recieveBuffer if no data then create fetch requests. and then wait for reply
     *   actually send the data block till the timeout
     */
    public void poll(long timeout,long now)throws IOException{
        int keys = selector.select(timeout);
        if(keys>0){
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if(key.isWritable()){
                }
                if(key.isReadable()){

                }
                if (!key.isValid()) {

                }
            }
        }
    }

}
