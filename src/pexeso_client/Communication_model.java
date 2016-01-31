/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pexeso_client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Morbeavus
 */
public class Communication_model implements Runnable
{
    private int port;
    private InetAddress hostIP;
    private String nick;
    private int msgID;
    private String lastMsg;
    public String toSend = null;
    public boolean msgsent = false;
    public boolean exit = false;
    private DatagramSocket socket = null ;
    
    private final static int PACKETSIZE = 75;
    
    public Communication_model(int port, InetAddress ip, String nick)
    {
        this.port = port;
        this.hostIP = ip;
        this.nick = nick;
        this.msgID = 0;
        try 
        {
            socket = new DatagramSocket() ;
        } catch (SocketException ex) {
            Logger.getLogger(Communication_model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() 
    {   
        int temp;
        while(exit == false)
        {                
            if(toSend != null)
            {
                temp = msgSender(toSend);
                if(temp == 0)
                {
                    toSend = null;
                    msgsent = true;
                }
                
            }
        }
        
    }
    
    private String listen(int time)
    {  
        String msg = "";
        try
            {
                 // Construct the datagram packets

                 byte [] recvData = new byte[PACKETSIZE];

                 DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length) ;

                 // Set a receive timeout, to int time milliseconds
                 socket.setSoTimeout(time) ;

                 // Prepare the packet for receive
                 recvPacket.setData(new byte[PACKETSIZE]) ;

                 // Wait for a response from the server
                 socket.receive(recvPacket) ;

                 // Print the response
                 msg = (new String(recvPacket.getData()));
                 System.out.println(msg);
                 
                 if((int)msg.charAt(0)-48 < 2)return "wrong msg ID";
            }
            catch( Exception e )
            {
               System.out.println(e) ;
               return "timeout";
            }

        
        return msg;
    }
    
    public int listenToTurns()
    {
        String msg = "";
        
        while(msg.charAt(1) != 'T')
        {
            msg = listen(2000);
        }
        return 0;
    }
    
    public String [] recieveGames(int count)
    {
        String [] games = new String [count];
                
        for(int i = 0 ; i < count; i++)
        {
            games[i] = listen(2000);
            
            if(games[i].equals("wrong msg ID")) 
            {
                i--;
            }
            
            else if (games[i].equals("timeout"))
            {
                
            }
        }    
        return games;
    }
    
    private int sendMsg(String msg)
    { 
        String tmsg;
        tmsg = msgID + msg;
        System.out.println("sendMsg("+tmsg+")");
        
        try
        {
             // Construct the datagram packets
             byte [] data = tmsg.getBytes() ;
             byte [] recvData = msg.getBytes() ;

             DatagramPacket packet = new DatagramPacket(data, data.length, hostIP, port) ;
             DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length) ;
             // Send it
             socket.send(packet) ;

             // Set a receive timeout, 2000 milliseconds
             socket.setSoTimeout(2000) ;

             // Prepare the packet for receive
             recvPacket.setData(new byte[PACKETSIZE]) ;

             // Wait for a response from the server
             socket.receive(recvPacket) ;

             // Print the response
             this.setLastMsg(new String(recvPacket.getData()));
             System.out.println("Server: |"+getLastMsg()+"|" );
             
             if((int)this.getLastMsg().charAt(0)-48 != msgID)return 1;   
        }
        catch(Exception e)
        {
           System.out.println(e) ;
           return 2;
        }
        
        return 0;
    }
    
    /**
     *
     * @param msg
     * @return 1 for failed connection
     */
    public synchronized int msgSender(String msg)
    {
        for(int i = 0; i < 3; i++)
        {
            if(sendMsg(msg) == 0)
            {
                changeMsgID();
                return 0;
            }
            
        }
        System.out.println("Connection terminated");
        return 1;
    }
    
    private void changeMsgID()
    {
        if(this.msgID == 0 )this.msgID++;
        else this.msgID--;
    }
    
    public void closeSocket()
    {
        if(this.socket != null)
        this.socket.close();
    }
    /*getters*/
    public InetAddress getIP()
    {
        return this.hostIP;
    }
    
    public int getPort()
    {
        return this.port;
    }
    
    public String getNick()
    {
        return this.nick;
    }
    
    /*setters*/
    public void setPort(int port)
    {
        this.port = port;
    }
    public void setNick(String nick)
    {
        this.nick = nick;
    }
    public void setIP(InetAddress ip)
    {
        this.hostIP = ip;
    }

    /**
     * @return the lastMsg
     */
    public String getLastMsg() {
        return lastMsg;
    }

    /**
     * @param LastMsg the lastMsg to set
     */
    public void setLastMsg(String LastMsg) {
        this.lastMsg = LastMsg;
    }
}
