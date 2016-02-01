/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pexeso_client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Morbeavus
 */
public class Game implements Runnable{
    private int p1Score;
    private int p2Score;
    public Card[] gameCards;
    private int state;/*0 for new game waiting for player 1 for being played 2 for saved 3 for finished*/
    private String nick1; /*nick of host*/
    private String nick2; /*nick of guest*/
    private int ID;
    public int turnCounter; 
    
    final static int GAME_SIZE = 64;
    
    /**
     * Constructor for new game
     * @param nick 
     */
    public Game(String nick)
    {
        this.turnCounter = 0;
        this.state = 0;
        this.nick1 = nick;
        this.p1Score = 0;
        this.p2Score = 0;
        generateCards();
    }
    
    /**
     * Constructor for loaded game
     * @param nick1
     * @param nick2
     * @param p1score
     * @param p2score
     * @param GameCards
     * @param state 
     */
    public Game(int id, String nick1,String nick2, int p1score, int p2score, Card[] GameCards, int state )
    {
        this.ID = id;
        this.turnCounter = 0;
        this.state = state;
        this.nick1 = nick1;
        this.nick2 = nick2;
        this.p1Score = p1score;
        this.p2Score = p2score;
        this.gameCards = GameCards;        
    }
    
    @Override
    public void run() 
    {
        while(state != 3)
        {
            
        }
    }
    
    public static Game[] loadGames(String player) throws FileNotFoundException
    {
        Game games[];
        ArrayList<String> data = new ArrayList<>();
        String one_game[];
        Card[] cards;
        
        try{
            System.out.println("Loading saved games for player: "+player+"..." );
            FileReader file = new FileReader("./saves/"+player+"/games");
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine())
            {
                data.add(sc.nextLine());
            }
            sc.close();
            
            games = new Game[data.size()];
            
            for(int i = 0; i < data.size(); i++)
            {
                one_game = data.get(i).split("\\|");
                 
                cards = Card.generateCardsFromLoad((one_game[7]), one_game[8],GAME_SIZE);
                
                games[i] = new Game(Integer.parseInt(one_game[0]),one_game[1],one_game[2],Integer.parseInt(one_game[3]),Integer.parseInt(one_game[4]),cards,Integer.parseInt(one_game[5]));
            }
            System.out.println("Loaded "+games.length+" games");
        }
        catch(Exception e)
        {
            System.out.println(e);
            System.out.println("No saved games found for this player");
            return null;
        }
        
        return games;
    }
    public void saveGame(Game to_save, String player) throws IOException
    {
        File file = new File("./saves/"+player+"/games");
        File path = new File("./saves/"+player);
        if(false == new File("./saves/"+player+"/games").exists())
        {
            path.mkdir();
            file.createNewFile();
        }
        
        FileWriter writer = new FileWriter(file,true);
        
        /*writing game info*/
        writer.write(""+to_save.getID()+"|"+to_save.getNick1()+"|"+to_save.getNick2()+"|"+to_save.getP1Score()+"|"+to_save.getP2Score()+"|"+to_save.turnCounter+"|"+to_save.getState()+"|");
        System.out.println(""+to_save.getID()+"|"+to_save.getNick1()+"|"+to_save.getNick2()+"|"+to_save.getP1Score()+"|"+to_save.getP2Score()+"|"+to_save.turnCounter+"|"+to_save.getState()+"|");
        for(int i = 0; i < GAME_SIZE; i++)
        {
            /*writing cards*/
            writer.write((char)(to_save.gameCards[i].getPic_id()+'0'));
        }
        
        writer.write("|");
        
        for(int i = 0; i < GAME_SIZE; i++)
        {
            /*writing card states*/
            writer.write((to_save.gameCards[i].getState()+'0'));
        }    
        writer.write("\n");
        
        writer.close();
    }
    private void generateCards()
    {
        Card temp[] = new Card[64];
        
        for(int i = 0; i < (temp.length/2); i++)
        {
            temp[i] = new Card(i);
            temp[i+32] = new Card(i);
        }
        
        temp = ShuffleCards(temp);
        
        this.gameCards = temp;
    }
    /**
     * 
     * @param cards
     * @return 
     */
    private Card[] ShuffleCards(Card [] cards)
    {
        int index;
        Card temp;
        Random random = new Random();
        for (int i = cards.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i)
            {
                temp = cards[index];
                cards[index] = cards[i];
                cards[i] = temp;
                
            }
        }
        return cards;
    }
    
    String ConstructNGMsg(int PID) 
    {
       String msg = "n"+(char)(PID+'0');
       for(int i = 0; i < gameCards.length; i++)
       {
           msg += (char)(gameCards[i].getPic_id()+'0');
       }
       
       return msg;
    }
    
    public int checkPairs(int card[] )
    {
        if((gameCards[card[0]].getPic_id() == gameCards[card[1]].getPic_id()) && (card[0] != card[1])) 
        {
            gameCards[card[0]].cardLabel.setEnabled(false);
            gameCards[card[0]].cardLabel.setEnabled(false);
                    
            return 1; 
        }
        
        return 0;
    }
    
    int getCard(int card_id) {
        return this.gameCards[card_id].getPic_id();
    }
    
    public Card[] getCards()
    {
        return this.gameCards;
    }
    
    public int getID()
    {
        return this.ID;
    }
    
    public int getP1Score()
    {
        return this.p1Score;
    }
    
    public int getP2Score()
    {
        return this.p1Score;
    }
    
    public int getState()
    {
        return this.state;
    }
    
    public void setp1Score(int p1Score)
    {
        this.p1Score = p1Score;
    }
    
    public void setp2Score(int p2Score)
    {
        this.p2Score = p2Score;
    }
    
    public void setState(int state)
    {
        this.state = state;
    }
    
    public void incrP1Score()
    {
        this.p1Score++;
    }
    
    public void incrP2Score()
    {
        this.p2Score++;
    }

    public void setCards(Card[] temp) {
        this.gameCards = temp;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return the nick2
     */
    public String getNick2() {
        return nick2;
    }

    /**
     * @param nick2 the nick2 to set
     */
    public void setNick2(String nick2) {
        this.nick2 = nick2;
    }

    /**
     * @return the nick1
     */
    public String getNick1() {
        return nick1;
    }

    /**
     * @param nick1 the nick1 to set
     */
    public void setNick1(String nick1) {
        this.nick1 = nick1;
    }

}
