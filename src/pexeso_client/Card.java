/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pexeso_client;

import java.io.Serializable;

/**
 *
 * @author Morbeavus
 */
public class Card implements Serializable{
    private int state = 0; // 0 not turned 1 turned 2 locked
//    private int card_id;
    private int picID;
    public javax.swing.JLabel cardLabel;
    
    
    public Card(int pic_id)
    {
        this.picID = pic_id;
    }

    public Card() 
    {
        
    }
    
    public Card(int pic_id, int card_state)
    {
        this.picID = pic_id;
        this.state = card_state;
    }
    public static Card[] generateCardsFromLoad(String card_ids, String card_states, int GAME_SIZE)
    {
        Card cards[] = new Card[GAME_SIZE];
        
        for(int i = 0; i < GAME_SIZE; i++)
        {
            cards[i] = new Card(card_ids.charAt(i)-'0',card_states.charAt(i)-'0');
            
        }
        
        return cards;
    }
    public void setState(int value)
    {
        this.state = value;
    }
//    public void setCard_id(int value)
//    {
//        this.card_id = value;
//    }
    public void setPic_id(int value)
    {
        this.picID = value;
    }
    
    public void setLabel(javax.swing.JLabel CardLabel)
    {
        this.cardLabel = CardLabel;
    }
    public int getState()
    {
        return this.state;
    }
//    public int getCard_id()
//    {
//        return this.card_id;
//    }
    public int getPic_id()
    {
        return this.picID;
    }
    
}
