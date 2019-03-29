import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends JFrame {

    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;

    public Server(){
        super("Instant Messenger");
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage(e.getActionCommand());
                userText.setText("");
            }
        });
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow));
        setSize(300,150);
        setVisible(true);
    }

public void startRUnning(){
        try{
            server = new ServerSocket(8080, 100);
            while(true){
                try{
                    waitForConnection();
                    setupStreams();
                    whileChatting();
                }catch(EOFException eofException){
                    showMessage("\nServer ended the connection!");
                }finally {
                    closeCrap();
                }
            }

        }catch (IOException ioException){
            ioException.printStackTrace();
        }
}

    private void waitForConnection() throws IOException{
        showMessage("Waiting for someone to connect...\n");
        connection = server.accept();
        showMessage(" Now connected to " + connection.getInetAddress().getHostName());
    }

    private void setupStreams() throws  IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush(); //cleans potentially remaining data
        input = new ObjectInputStream(connection.getInputStream());
        showMessage("\n Streams are now setup! \n");
    }

    private void whileChatting() throws IOException{
        String message = " You are now connected! ";
        sendMessage(message);
        ableToType(true);
        do{
            try{
                message = (String) input.readObject();
                showMessage("\n" + message);

            }catch(ClassNotFoundException classNotFoundException){
                showMessage("\n Object not found!");
            }

        }while (!message.equals("CLIENT - END"));
    }

    private void closeCrap(){
        showMessage("\n Closing connections...\n");
        ableToType(false);
        try{
            output.close();
            input.close();
            connection.close();
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    private void sendMessage(String message){
        try{
            output.writeObject("SERVER - " + message);
            output.flush();
            showMessage("\n SERVER - " + message);
        }catch (IOException ioException){
            chatWindow.append("\n Error sending message!");
        }
    }

    private void showMessage(final String text){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                chatWindow.append(text);
            }
        });
    }

    private void ableToType(final boolean tof){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                userText.setEditable(tof);
            }
        });
    }
}