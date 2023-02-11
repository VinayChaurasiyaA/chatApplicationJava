import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
class Server{
    // server is the server socket, which is revieving
    ServerSocket server;
    Socket socket;

    BufferedReader br; // it is used reading for taking the inputs from the client
    PrintWriter out;  // it is used to print
    // constructor for creating the server for main server
    public Server() {
        try {
            server = new ServerSocket(7777); // which will require the ports to communitate with the client
            System.out.println("Server is waiting for the connections");
            socket = server.accept(); // socket is the client base server which is making the connection
            br = new BufferedReader(new InputStreamReader( socket.getInputStream()));
            // what this will do is ki.. {what br will get is socket.getInput will take the Binary Inputs
            // and inputstreamReader will make the bit to character or String and give the Bufferreader a proper string }

            out = new PrintWriter((socket.getOutputStream())); // same as br will be done here just the output/wrinting will be stored

            startReading();
            startWritinig();
        } catch (Exception e) {
            // TODO: handle exception
            e.getStackTrace();
        }
    }
// multithreading is used below because we have to do the read and write simultaneously
    public void startReading() {
// this will the first thread : it'll read and provide us
        Runnable r1 = () -> {
            System.out.println(" Reading started ...");
            while(true) {
                try {
                    String msg = br.readLine();
                    if(msg == "exit") {
                        System.out.println("Client wants to exit the server");
                        break;
                    }
                    System.out.println("Client : " + msg);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
// this expression is know as lamda expression
        };
        new Thread(r1).start(); // new object of thread class is created with r1 class and start will run it!
    }
    public void startWritinig() {
// this will be the second thread : it'll take the data from user and then send it to the client server
        Runnable w1 = () -> {
// this expression is know as lamda expression
// writer started
            while(true) {
                try {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in)); // here we'll take the messages from the keyboard
                    String content = br1.readLine();
                    out.println(content);
                    out.flush(); // sometimes data doesn't go on client server so flush will force it to do it

                } catch (Exception e) {
                    e.printStackTrace();
                    // TODO: handle exception
                }
            }
        };
        new Thread(w1).start();
    }
    public static void main(String[] args) {
        System.out.println("This is the new server");
        new Server();
    }
    
}