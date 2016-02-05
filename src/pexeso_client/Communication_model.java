/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pexeso_client;

import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

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
    private String toSend = null;
    private boolean msgsent = false;
    private boolean exit = false;
    private boolean opponent_connected = false;
    private DatagramSocket socket = null ;
    Thread game_thread;
    
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
        } 
        catch (SocketException ex) 
        {
            Logger.getLogger(Communication_model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void terminateGame()
    {

        Pexeso_client.CurrentGame = null;
        setExit(true);
        game_thread.interrupt();

        Pexeso_client.mygui.LobbyStatus.setText("Your game ended: server was not responding!");
        Pexeso_client.mygui.LobbyStatus.setVisible(true);        
        Pexeso_client.mygui.panel2.setVisible(true);
        Pexeso_client.mygui.panel3.setVisible(false);
             
    }
    
    @Override
    public void run() 
    {   
            game_thread = new Thread(Pexeso_client.CurrentGame);
            game_thread.start();
            int temp;
            try 
            {
                while(isExit() == false)
                { 
                    if(getToSend() != null)
                    {   
                        System.out.println("Comm thread going to send: "+ toSend);
                        if(toSend.charAt(0) == 't')
                        {
                            sleep(2000);
                            Pexeso_client.mygui.turnCardBack(Pexeso_client.mygui.turns[0]);
                            Pexeso_client.mygui.turnCardBack(Pexeso_client.mygui.turns[1]);
                            
                        }
                        
                        
                        temp = msgSender(getToSend());
                        
                        if(temp == 0)
                        {
                            setToSend(null);
                            setMsgsent(true);
                        }
                        else
                        {
                            sleep(10000);
                            temp = msgSender("Hello");
                            if(temp != 0)
                            {
                                terminateGame();
                            }
                        }
                    }
                    else if(Pexeso_client.CurrentPlayer.isTurning() == false) /*asks for opponent's turns*/
                    {
                        temp = msgSender("M"+(char)(Pexeso_client.CurrentGame.getID()+'0')+""+(char)(Pexeso_client.CurrentPlayer.getPosition()+'0'));
                        
                        if(temp == 0)
                        {
                            if(lastMsg.charAt(2) == '1')
                            {
                                if((1 + Pexeso_client.CurrentGame.turnCounter) == (int)(lastMsg.charAt(3)-'0'))
                                {
                                    Pexeso_client.mygui.setIcon(lastMsg.charAt(1)-'0');
                                    Pexeso_client.mygui.turns[Pexeso_client.CurrentGame.turnCounter] = lastMsg.charAt(1)-'0';
                                    Pexeso_client.CurrentGame.turnCounter++;

                                    if(Pexeso_client.CurrentGame.turnCounter == 2)
                                    {
                                        if(Pexeso_client.CurrentGame.checkPairs(Pexeso_client.mygui.turns) == 1)
                                        {
                                            Pexeso_client.mygui.GameStatus.setText("Opponent scored!");
                                            Pexeso_client.CurrentGame.turnCounter = 0;
                                            int opponentPos = Pexeso_client.CurrentPlayer.getPosition();
                                            if(opponentPos == 1 )
                                            {
                                                opponentPos = 2;
                                                Pexeso_client.mygui.increaseScore(opponentPos);
                                            }
                                            else
                                            {
                                                opponentPos = 1;
                                                Pexeso_client.mygui.increaseScore(opponentPos);
                                            }
                                        }
                                        else
                                        {
                                            SwingUtilities.invokeLater(new Runnable() {
                                            @Override
                                            public void run() {

                                              Pexeso_client.mygui.GameStatus.setText("Your turn!");
                                            }
                                            });
                                            
                                            sleep(4000);
                                            Pexeso_client.mygui.turnCardBack(Pexeso_client.mygui.turns[0]);
                                            Pexeso_client.mygui.turnCardBack(Pexeso_client.mygui.turns[1]);                                          
                                            
                                            
                                            Pexeso_client.CurrentGame.turnCounter = 0;
                                            Pexeso_client.CurrentPlayer.setTurning(true);                  
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            sleep(10000);
                            temp = msgSender("Hello");
                            if(temp != 0)
                            {
                                terminateGame();
                            }
                        }
                    }
                    else if(Pexeso_client.CurrentGame.getNick2() == null) /*tries to find opponent */
                    {
                        temp = msgSender("s"+(char)(Pexeso_client.CurrentGame.getID()+'0'));
                        
                        if(temp == 0)
                        {
                            if( 47 < lastMsg.charAt(1) &&  lastMsg.charAt(1) < 58  ) /*basically asks if its number*/
                            {
                                Pexeso_client.CurrentGame.setNick2(lastMsg.substring(2,2+lastMsg.charAt(1)-'0'));
                                Pexeso_client.CurrentGame.setState(1);
                            }
                            
                        }
                    }
                    else /*connection refreshing*/
                    { 
                        temp = msgSender("Hello");
                        
                        if(temp != 0)
                        {
                            sleep(10000);
                            temp = msgSender("Hello");
                            if(temp != 0)
                            {
                                terminateGame();
                            }
                        }
                    }
                    sleep(2000);
                }
            } 
            catch (InterruptedException ex) 
            {
                    Logger.getLogger(Communication_model.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private String listen(int time)
    {  
        String msg;
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

    /**
     * @return the toSend
     */
    public String getToSend() {
        return toSend;
    }

    /**
     * @return the msgsent
     */
    public boolean isMsgsent() {
        return msgsent;
    }

    /**
     * @return the exit
     */
    public boolean isExit() {
        return exit;
    }

    /**
     * @param toSend the toSend to set
     */
    public synchronized void setToSend(String toSend) {
        this.toSend = toSend;
    }

    /**
     * @param msgsent the msgsent to set
     */
    public synchronized void setMsgsent(boolean msgsent) {
        this.msgsent = msgsent;
    }

    /**
     * @param exit the exit to set
     */
    public synchronized void setExit(boolean exit) {
        this.exit = exit;
    }

    /**
     * @return the opponent_connected
     */
    public boolean isOpponent_connected() {
        return opponent_connected;
    }

    /**
     * @param opponent_connected the opponent_connected to set
     */
    public synchronized void setOpponent_connected(boolean opponent_connected) {
        this.opponent_connected = opponent_connected;
    }
}
