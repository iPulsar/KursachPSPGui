/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.Connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Integer.parseInt;
import java.net.Socket;
import java.util.Arrays;
import javax.sound.sampled.Port;
import javax.swing.JOptionPane;

/**
 *
 * @author Orlovskiy
 */
        public class Connection
        {
            private static String IP;
            private static String port;
            public static boolean CheckConnection(String ip, String prt) throws IOException
            {
                try{


                    Socket sock = new Socket(ip,parseInt(prt));
                    if(sock.isConnected())
                    {
                        sock.close();
                        return true;
                    }
                    else
                    {
                        sock.close();
                        return false;
                    }
                }catch(Exception e){
                    return false;
                }
            }
            public static void ServerInfo(String adress, String Port)
            {
                IP = adress;
                port = Port;
            }
            public static boolean Send(String request) throws IOException
            {
                if(!CheckConnection(IP, port)) return false;
                return true;
            }
            public static String Recive(String request) throws IOException //recive something from server with exact request
            {
                String response = "Connection failed";
                try{
                    Socket socket = new Socket(IP, parseInt(port));
                    // Create and connect the socket
                    response = "";
                    DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
                    DataInputStream dIn = new DataInputStream(socket.getInputStream());
                    String Check = dIn.readUTF();
                    dOut.writeUTF(request);
                    dOut.flush();
                    response += dIn.readUTF();
                    dOut.close();
                    dIn.close();                
                }catch(IOException | NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,ex+"\n IP = "+IP+" Port= "+port);
                }
                return response;
            }
        }
        
