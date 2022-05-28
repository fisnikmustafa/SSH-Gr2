package ChatServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private static Map<String, Socket> usersList = new ConcurrentHashMap<>();
    private static Set<String> activeUsersSet = new HashSet<>();

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void startServer(){
        try {
            System.out.println("Server started listening on port 8818...");
            new ClientAccept().start();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8818);
        Server server = new Server(serverSocket);
        server.startServer();
    }

    class ClientAccept extends Thread{
        @Override
        public void run(){
            while (true){
                try{
                    Socket clientSocket = serverSocket.accept();
                    String name = new DataInputStream(clientSocket.getInputStream()).readUTF();
                    /*DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
                    outputStream.writeUTF("");*/

                    usersList.put(name, clientSocket);
                    activeUsersSet.add(name);

                    System.out.println("[NEW CONNECTION] Client " + name + " connected!");

                    new MsgRead(clientSocket, name).start();
                } catch (IOException ioex){
                    ioex.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    class MsgRead extends Thread{
        Socket sock;
        String name;

        private MsgRead(Socket sock, String name){
            this.sock = sock;
            this.name = name;
        }

        @Override
        public void run(){
            while(!activeUsersSet.isEmpty()){
                try{
                    // Serveri duhet me rujt mesazhin ne chat file;
                    if(sock.isConnected()){
                        String message = new DataInputStream(sock.getInputStream()).readUTF();
                        System.out.println("Message read: " + message);
                        String[] msgList = message.split(":");
                        //msgList = chat_file_name:client_for_receiving_msg:message

                        if(msgList[0].equalsIgnoreCase("exit")){
                            System.out.println("[CLOSED CONNECTION] Client " + name + " disconnected!");
                            activeUsersSet.remove(name);
                        } else {
                            //Send message to user:
                            String receiver = msgList[1];
                            try {
                                if (activeUsersSet.contains(receiver)){
                                    new DataOutputStream(((Socket) usersList.get(receiver)).getOutputStream())
                                            .writeUTF(msgList[2]);
                                }
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }


                    }

                } catch (Exception e){
                    System.out.println("[ERR] Client " + name + " is not active anymore!");
                    break;
                }
            }
        }
    }

}