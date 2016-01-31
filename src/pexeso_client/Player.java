/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pexeso_client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Morbeavus
 */
public class Player implements Serializable{
    private int ID;
    private String nick;
    private int lastMsgID;
    private boolean turning;
    public Player(String ID, String nick) 
    {
        this.turning = false;
       this.ID = Integer.parseInt(ID);
       this.nick = nick;
    }

    public Player(String nick) 
    {
        this.turning = false;
        this.ID = -1;
        this.nick = nick;
    }

    Player(String id, String nick, String lastMsgID) 
    {
        this.turning = false;
        this.ID = Integer.parseInt(id);
        this.nick = nick;
        this.lastMsgID = Integer.parseInt(lastMsgID);
    }
    
    private void savePlayers(Player [] toSave) throws IOException
    {
        File file = new File("./saves/users");
        File path = new File("./saves/");
        if(false == new File("./saves/users").exists())
        {
            path.mkdir();
            file.createNewFile();
        }
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(toSave.length+"\n");
        for(int i = 0; i < toSave.length; i++)
        {
            writer.write(""+toSave[i].getID()+":"+toSave[i].getNick()+":"+toSave[i].getLastMsgID()+"\n");
        }
        
        writer.close();
        
        
    }
    
    /**
     * increases size of array holding players and adds a new player
     * @param oldArray
     * @param newPlayer
     * @return newArray with new player
     */
    public Player [] newPlayer(Player[] oldArray, Player newPlayer)
    {
        int i;
        Player newArray[] = new Player[(oldArray.length+1)];
        for(i = 0; i < oldArray.length; i++)
        {
            newArray[i] = oldArray[i];
        }
        
        newArray[i] = newPlayer;
        try 
        {
            savePlayers(newArray);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return newArray;
    }
    
    /**
     * @return the nick
     */
    public String getNick() 
    {
        return nick;
    }

    /**
     * @param nick the nick to set
     */
    public void setNick(String nick) 
    {
        this.nick = nick;
    }

    /**
     * @return the ID
     */
    public int getID() 
    {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) 
    {
        this.ID = ID;
    }

    /**
     * @return the lastMsgID
     */
    public int getLastMsgID() {
        return lastMsgID;
    }

    /**
     * @param lastMsgID the lastMsgID to set
     */
    public void setLastMsgID(int lastMsgID) {
        this.lastMsgID = lastMsgID;
    }

    /**
     * @return the turning
     */
    public boolean isTurning() {
        return turning;
    }

    /**
     * @param turning the turning to set
     */
    public void setTurning(boolean turning) {
        this.turning = turning;
    }
    
}
