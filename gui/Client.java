package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;


public class Client extends JFrame{

    Socket socket;
    BufferedReader br; // it is used reading for taking the inputs from the client
    PrintWriter out;  // it is used to print

    //GUI...Declaring Components
    private JLabel heading = new JLabel("Client");
    private JTextArea msgArea = new JTextArea();
    private JTextField msgInput = new JTextField();
    private Font font = new Font("Roboto", Font.PLAIN, 20);


    public Client() {
        try {
            System.out.println("Sending requests to the server");
            socket = new Socket("127.0.0.1" , 7777); // host port and ip address
            System.out.println("Connection done");

            br = new BufferedReader(new InputStreamReader( socket.getInputStream()));
            // what this will do is ki.. {what br will get is socket.getInput will take the Binary Inputs
            // and inputstreamReader will make the bit to character or String and give the Bufferreader a proper string }

            out = new PrintWriter((socket.getOutputStream())); // same as br will be done here just the output/wrinting will be stored

            createGUI();
            handleEvents();
            startReading();
            // startWriting(); //After creating gui writing will be done by the startreading only, this method is for wriitng from the console

        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    // method to handle "enter" key click event 
    private void handleEvents() {
        msgInput.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyReleased(KeyEvent e) { // Method used as when enter key is released
                // TODO Auto-generated method stub
                // System.out.println("Key released: " + e.getKeyCode());

                if(e.getKeyCode() == 10){ // '10' bcoz "Enter" key has this key code 
                    // System.out.println("Enter key pressed.");

                    // Does the writing work here
                    String msgToSend = msgInput.getText();
                    msgArea.append("Me: "+ msgToSend + "\n");
                    out.println(msgToSend);
                    out.flush();
                    msgInput.setText("");// To again set the msg input field to blank after the msg is sent
                    msgInput.requestFocus();
                }
            }
        
        
        });
    }


    private void createGUI(){
        //gui code..
        this.setTitle("Client Messenger");//"this" refers to the window over here
        this.setSize(700,600);
        this.setLocationRelativeTo(null);//It centers the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//when clicked on cross closes window
        this.setVisible(true);//makes our window visible, by default its value is false

        
        //Coding Component

        //1) Setting the fonts for all areas
        heading.setFont(font);
        msgArea.setFont(font);
        msgInput.setFont(font);

        //2) Alignments, borders and icons
        heading.setHorizontalAlignment(SwingConstants.CENTER);//aligning the "Client" to center in north area
        heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));// similar to adding padding from all sides
        heading.setIcon(new ImageIcon("clogo50.png"));

        //Setting the layout of our frame
        this.setLayout(new BorderLayout()); 
        // BorderLayout -> 
        // 1)Creates a layout which is divided into 5 parts -> North,east,west,south and center.
        //2)We will be using north->heading, center->msgArea, south->msgInput

        
        //Adding the components to frame
        this.add(heading, BorderLayout.NORTH);
        this.add(msgArea, BorderLayout.CENTER);
        this.add(msgInput,BorderLayout.SOUTH);

    }


    // multithreading is used below because we have to do the read and write simultaneously
    public void startReading() {
        // this will the first thread : it'll read and provide us
                Runnable r1 = () -> {
                    System.out.println("Reading started...");
                    while(true) {
                        try {
                            String msg = br.readLine();
                            if(msg.equals("exit")) {
                                System.out.println("Server wants to exit the server");
                                JOptionPane.showMessageDialog(this, "Server terminated the chat");
                                msgInput.setEnabled(false);// to disable input field afer connection is closed
                        
                                break;
                            }
                            // System.out.println("Server : " + msg); => this used to print msg on the console
                            msgArea.append("Server : " + msg + "\n"); // => this prints msg on the window

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            // e.printStackTrace();
                            System.out.println("The Connection is closed.");
                        }
                    }
        // this expression is know as lamda expression
                };
                new Thread(r1).start(); // new object of thread class is created with r1 class and start will run it!
            }
            public void startWriting() {
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
        System.out.println("this is the client server");
        new Client();
    }
    
}