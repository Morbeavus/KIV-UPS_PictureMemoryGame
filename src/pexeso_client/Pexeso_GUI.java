/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pexeso_client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;


/**
 *
 * @author Morbeavus
 */
public class Pexeso_GUI extends javax.swing.JFrame {
    
    private Player users[];
    private Communication_model comm;
    Thread t1;   
    public Game loaded_games[];
    public int turns[] = new int[2];
    private String [] gamesList;
    
    final static int GAME_SIZE = 64;
    /**
     * Creates new form NewJFrame
     */
    public Pexeso_GUI() {
        initComponents();
    }
    
    public void setIcon(int card_id)
    {
        int card = Pexeso_client.CurrentGame.getCard(card_id);
        
        SwingUtilities.invokeLater(new Runnable() {
        public void run() {

          Pexeso_client.CurrentGame.gameCards[card_id].cardLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/"+card+".jpg")));
        }
      });
        
        
        
    }
    public void turnCardBack(int card_id)
    {
        
        SwingUtilities.invokeLater(new Runnable() {
        public void run() {

          Pexeso_client.CurrentGame.gameCards[card_id].cardLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg")));
        }
      });
        
        
    }
    public void increaseScore(int player_position)
    {
        
        if(player_position == 1 )
        {
            Pexeso_client.CurrentGame.setp1Score(1 + Pexeso_client.CurrentGame.getP1Score());
            
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {

                    p1Value.setText(Pexeso_client.CurrentGame.getP1Score()+"");
                }
              });
        }
        else if(player_position == 2 )
        {
            Pexeso_client.CurrentGame.setp2Score(1 + Pexeso_client.CurrentGame.getP2Score());
            
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {

                    p2Value.setText(Pexeso_client.CurrentGame.getP2Score()+"");
                }
              });
        }
        
    }
    
    private void GameCardClicked(int card_id)
    {
        int temp;
        
        
        if(Pexeso_client.CurrentGame.getState() == 1 && Pexeso_client.CurrentPlayer.isTurning() == true)
        {
            if(Pexeso_client.CurrentGame.turnCounter != 2)
            {
                try 
                {
                    turns[Pexeso_client.CurrentGame.turnCounter] = card_id;
                    Pexeso_client.CurrentGame.turnCounter++;
                    comm.setToSend("m"+(char)(card_id + '0')+""+(char)(Pexeso_client.CurrentGame.getID()+'0')+""+Pexeso_client.CurrentPlayer.getPosition()+""+Pexeso_client.CurrentGame.turnCounter);
                    if(Pexeso_client.CurrentGame.turnCounter == 1)sleep(1500);
                    
                    if(comm.isMsgsent() == true)
                    {
                        setIcon(card_id);
                        
                        if(Pexeso_client.CurrentGame.turnCounter == 2)
                        {
                            if(Pexeso_client.CurrentGame.checkPairs(turns) == 1) 
                            {
                                GameStatus.setText("Nice pair! Play again!");
                                Pexeso_client.CurrentGame.turnCounter = 0;
                                increaseScore(Pexeso_client.CurrentPlayer.getPosition());
                                
                            }
                            else
                            {
                                
                                Pexeso_client.CurrentPlayer.setTurning(false);
                                
                                
                                SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {

                                      GameStatus.setText("Opponents turn!");

                                    }
                                  });
                                
                                sleep(2000);
                                
                                
                                Pexeso_client.CurrentGame.turnCounter = 0;
                                
                                comm.setToSend("t"+(char)(Pexeso_client.CurrentGame.getID()+'0')+""+(Pexeso_client.CurrentPlayer.getPosition()));
                                comm.setMsgsent(false);
                                
                                
                            }
                        }
                    }
                    else
                    {
                        GameStatus.setText("Server is not responding!");
                        Pexeso_client.CurrentGame.turnCounter--;
                    }
                } 
                catch (InterruptedException ex) {
                    Logger.getLogger(Pexeso_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
    
    public int loadUsers() throws FileNotFoundException
    {
        String temp [];
        int x;
        int i = 0;
        String data;
        FileReader file = new FileReader("./saves/users");
        Scanner sc = new Scanner(file);
        if(sc.hasNextInt())
        {
            x = sc.nextInt();
            sc.nextLine();
            this.users = new Player[x];
            System.out.println("Loaded: "+this.users.length+" users");
        }
        else return 2;
        
        for(int j = 0; j < this.users.length ; j++)
        {
            data = sc.nextLine();
            temp = data.split(":");
            this.users[i] = new Player(temp[0],temp[1],temp[2]);
            i++;
        }       
        return 0;
    }
    /**
     * HOPE NOONE EVER SEES THIS AWFULL METHOD
     */
    private void FillCards() 
    {
        Card temp [];
        temp = Pexeso_client.CurrentGame.getCards();
        temp[0].setLabel(jLabel0);
        temp[1].setLabel(jLabel1);
        temp[2].setLabel(jLabel2);
        temp[3].setLabel(jLabel3);
        temp[4].setLabel(jLabel4);
        temp[5].setLabel(jLabel5);
        temp[6].setLabel(jLabel6);
        temp[7].setLabel(jLabel7);
        temp[8].setLabel(jLabel8);
        temp[9].setLabel(jLabel9);
        temp[10].setLabel(jLabel10);
        temp[11].setLabel(jLabel11);
        temp[12].setLabel(jLabel12);
        temp[13].setLabel(jLabel13);
        temp[14].setLabel(jLabel14);
        temp[15].setLabel(jLabel15);
        temp[16].setLabel(jLabel16);
        temp[17].setLabel(jLabel17);
        temp[18].setLabel(jLabel18);
        temp[19].setLabel(jLabel19);
        temp[20].setLabel(jLabel20);
        temp[21].setLabel(jLabel21);
        temp[22].setLabel(jLabel22);
        temp[23].setLabel(jLabel23);
        temp[24].setLabel(jLabel24);
        temp[25].setLabel(jLabel25);
        temp[26].setLabel(jLabel26);
        temp[27].setLabel(jLabel27);
        temp[28].setLabel(jLabel28);
        temp[29].setLabel(jLabel29);
        temp[30].setLabel(jLabel30);
        temp[31].setLabel(jLabel31);
        temp[32].setLabel(jLabel32);
        temp[33].setLabel(jLabel33);
        temp[34].setLabel(jLabel34);
        temp[35].setLabel(jLabel35);
        temp[36].setLabel(jLabel36);
        temp[37].setLabel(jLabel37);
        temp[38].setLabel(jLabel38);
        temp[39].setLabel(jLabel39);
        temp[40].setLabel(jLabel40);
        temp[41].setLabel(jLabel41);
        temp[42].setLabel(jLabel42);
        temp[43].setLabel(jLabel43);
        temp[44].setLabel(jLabel44);
        temp[45].setLabel(jLabel45);
        temp[46].setLabel(jLabel46);
        temp[47].setLabel(jLabel47);
        temp[48].setLabel(jLabel48);
        temp[49].setLabel(jLabel49);
        temp[50].setLabel(jLabel50);
        temp[51].setLabel(jLabel51);
        temp[52].setLabel(jLabel52); 
        temp[53].setLabel(jLabel53);
        temp[54].setLabel(jLabel54);
        temp[55].setLabel(jLabel55);
        temp[56].setLabel(jLabel56);
        temp[57].setLabel(jLabel57);
        temp[58].setLabel(jLabel58);
        temp[59].setLabel(jLabel59);
        temp[60].setLabel(jLabel60);
        temp[61].setLabel(jLabel61);
        temp[62].setLabel(jLabel62);
        temp[63].setLabel(jLabel63);
        for(int i = 0; i < temp.length ; i++)
        {
            Pexeso_client.CurrentGame.gameCards[i].cardLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg")));
        }
        
        Pexeso_client.CurrentGame.setCards(temp);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        try{
            loadUsers();
        }catch( Exception e )
        {
            System.out.println(e) ;
        }
        panel1 = new javax.swing.JPanel();
        welcomePanel = new javax.swing.JPanel();
        welcome = new javax.swing.JLabel();
        info1 = new javax.swing.JLabel();
        Login = new javax.swing.JPanel();
        nick = new javax.swing.JLabel();
        player_name = new javax.swing.JTextField();
        server = new javax.swing.JLabel();
        server_ip = new javax.swing.JTextField();
        port_label = new javax.swing.JLabel();
        port_in = new javax.swing.JTextField();
        login = new javax.swing.JButton();
        exit = new javax.swing.JButton();
        status_label = new javax.swing.JLabel();
        portHint = new javax.swing.JLabel();
        nickHint = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        lobbyMPanel = new javax.swing.JPanel();
        youarelogged = new javax.swing.JLabel();
        exit1 = new javax.swing.JButton();
        lobbyNick = new javax.swing.JLabel();
        backToLogin = new javax.swing.JButton();
        NewGame = new javax.swing.JButton();
        LoadGame = new javax.swing.JButton();
        JoinGame = new javax.swing.JButton();
        Refreshlist = new javax.swing.JButton();
        LobbyStatus = new javax.swing.JLabel();
        loadGameBox = new javax.swing.JComboBox<>();
        joinGameBox = new javax.swing.JComboBox<>();
        panel3 = new javax.swing.JPanel();
        gameboard = new javax.swing.JPanel();
        jLabel0 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        p1Name = new javax.swing.JLabel();
        p1Score = new javax.swing.JLabel();
        p1Value = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        p2Name = new javax.swing.JLabel();
        p2Score = new javax.swing.JLabel();
        p2Value = new javax.swing.JLabel();
        GameExit = new javax.swing.JButton();
        GameSave = new javax.swing.JButton();
        LeaveGame = new javax.swing.JButton();
        GameStatus = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        statusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));
        setResizable(false);

        panel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        welcomePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        welcomePanel.setName("Nechapem"); // NOI18N

        welcome.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        welcome.setText("Welcome to PEXESO");
        welcome.setName("welcome"); // NOI18N

        info1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        info1.setText("Memory game made for KIV/UPS");

        javax.swing.GroupLayout welcomePanelLayout = new javax.swing.GroupLayout(welcomePanel);
        welcomePanel.setLayout(welcomePanelLayout);
        welcomePanelLayout.setHorizontalGroup(
            welcomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(welcomePanelLayout.createSequentialGroup()
                .addContainerGap(320, Short.MAX_VALUE)
                .addGroup(welcomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(info1)
                    .addComponent(welcome))
                .addGap(286, 286, 286))
        );
        welcomePanelLayout.setVerticalGroup(
            welcomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(welcomePanelLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(welcome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(info1)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        Login.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Login", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        nick.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nick.setText("Nick:");

        player_name.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        player_name.setText("Nickinson");

        server.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        server.setText("Server IP:");

        server_ip.setFont(new java.awt.Font("Times New Roman", 0, 10)); // NOI18N
        server_ip.setText("192.168.0.25");

        port_label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        port_label.setText("Port:");

        port_in.setFont(new java.awt.Font("Times New Roman", 0, 10)); // NOI18N
        port_in.setText("35625");
        port_in.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                port_inActionPerformed(evt);
            }
        });

        login.setText("LOG-IN");
        login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginMouseClicked(evt);
            }
        });

        exit.setText("EXIT");
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseClicked(evt);
            }
        });
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        status_label.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        status_label.setText("Failed to connect!");
        status_label.setEnabled(false);
        status_label.setVisible(false);

        portHint.setText("1025-65534");

        nickHint.setText("Maximum of 10 not special characters");

        javax.swing.GroupLayout LoginLayout = new javax.swing.GroupLayout(Login);
        Login.setLayout(LoginLayout);
        LoginLayout.setHorizontalGroup(
            LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exit))
            .addGroup(LoginLayout.createSequentialGroup()
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LoginLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(LoginLayout.createSequentialGroup()
                                .addComponent(port_label)
                                .addGap(48, 48, 48)
                                .addComponent(port_in, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(portHint))
                            .addGroup(LoginLayout.createSequentialGroup()
                                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(server)
                                    .addComponent(nick))
                                .addGap(18, 18, 18)
                                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(player_name)
                                    .addComponent(server_ip, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(nickHint))))
                    .addGroup(LoginLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(login)
                        .addGap(131, 131, 131)
                        .addComponent(status_label)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        LoginLayout.setVerticalGroup(
            LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nick)
                    .addComponent(player_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nickHint))
                .addGap(18, 18, 18)
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(server)
                    .addComponent(server_ip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(port_label)
                    .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(port_in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(portHint)))
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LoginLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(status_label)
                        .addGap(52, 52, 52))
                    .addGroup(LoginLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(login)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(exit))
        );

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(welcomePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Login, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(welcomePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 230, Short.MAX_VALUE)
                .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel2.setPreferredSize(new java.awt.Dimension(828, 670));
        panel2.setVisible(false);

        lobbyMPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Server lobby"));

        youarelogged.setText("You are logged in as:");

        exit1.setText("EXIT");
        exit1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exit1MouseClicked(evt);
            }
        });
        exit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exit1ActionPerformed(evt);
            }
        });

        lobbyNick.setText("jLabel2");
        lobbyNick.setToolTipText("");

        backToLogin.setText("Back");
        backToLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backToLoginMouseClicked(evt);
            }
        });

        NewGame.setText("New Game");
        NewGame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NewGameMouseClicked(evt);
            }
        });

        LoadGame.setText("Load game");

        JoinGame.setText("Join game");
        JoinGame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JoinGameMouseClicked(evt);
            }
        });

        Refreshlist.setText("Refresh");
        Refreshlist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RefreshlistMouseClicked(evt);
            }
        });

        LobbyStatus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LobbyStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LobbyStatus.setText("STATUS LABEL TEXT HERE");
        LobbyStatus.setVisible(false);

        loadGameBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Game 1", "Game 2", "Game 3", "Game 4" }));

        joinGameBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Game 1", "Game 2", "Game 3", "Game 4" }));

        javax.swing.GroupLayout lobbyMPanelLayout = new javax.swing.GroupLayout(lobbyMPanel);
        lobbyMPanel.setLayout(lobbyMPanelLayout);
        lobbyMPanelLayout.setHorizontalGroup(
            lobbyMPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lobbyMPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lobbyMPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lobbyMPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(backToLogin)
                        .addGap(18, 18, 18)
                        .addComponent(exit1))
                    .addGroup(lobbyMPanelLayout.createSequentialGroup()
                        .addComponent(youarelogged)
                        .addGap(18, 18, 18)
                        .addGroup(lobbyMPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lobbyNick)
                            .addComponent(LobbyStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lobbyMPanelLayout.createSequentialGroup()
                .addContainerGap(362, Short.MAX_VALUE)
                .addGroup(lobbyMPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(loadGameBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LoadGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(joinGameBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JoinGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Refreshlist, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(NewGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(344, 344, 344))
        );
        lobbyMPanelLayout.setVerticalGroup(
            lobbyMPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lobbyMPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lobbyMPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(youarelogged)
                    .addComponent(lobbyNick))
                .addGap(71, 71, 71)
                .addComponent(NewGame)
                .addGap(67, 67, 67)
                .addComponent(loadGameBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LoadGame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addComponent(joinGameBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JoinGame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Refreshlist)
                .addGap(71, 71, 71)
                .addComponent(LobbyStatus)
                .addGap(44, 44, 44)
                .addGroup(lobbyMPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exit1)
                    .addComponent(backToLogin))
                .addContainerGap())
        );

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lobbyMPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lobbyMPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel3.setPreferredSize(new java.awt.Dimension(826, 670));
        panel3.setVisible(false);

        gameboard.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel0.setText("jLabel1");
        jLabel0.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel0MouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel2.setText("jLabel1");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel3.setText("jLabel1");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel4.setText("jLabel1");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel5.setText("jLabel1");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel6.setText("jLabel1");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel7.setText("jLabel1");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel8.setText("jLabel1");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel9.setText("jLabel1");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel10.setText("jLabel1");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel11.setText("jLabel1");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel12.setText("jLabel1");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel13.setText("jLabel1");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel14.setText("jLabel1");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel15.setText("jLabel1");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel16.setText("jLabel1");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel17.setText("jLabel1");
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel18.setText("jLabel1");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel19.setText("jLabel1");
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel20.setText("jLabel1");
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel21.setText("jLabel1");
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel22.setText("jLabel1");
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel23.setText("jLabel1");
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel24.setText("jLabel1");
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel24MouseClicked(evt);
            }
        });

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel25.setText("jLabel1");
        jLabel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel25MouseClicked(evt);
            }
        });

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel26.setText("jLabel1");
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel27.setText("jLabel1");
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
        });

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel28.setText("jLabel1");
        jLabel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel28MouseClicked(evt);
            }
        });

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel29.setText("jLabel1");
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel29MouseClicked(evt);
            }
        });

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel30.setText("jLabel1");
        jLabel30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel30MouseClicked(evt);
            }
        });

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel31.setText("jLabel1");
        jLabel31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel31MouseClicked(evt);
            }
        });

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel32.setText("jLabel1");
        jLabel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel32MouseClicked(evt);
            }
        });

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel33.setText("jLabel1");
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
        });

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel34.setText("jLabel1");
        jLabel34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel34MouseClicked(evt);
            }
        });

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel35.setText("jLabel1");
        jLabel35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel35MouseClicked(evt);
            }
        });

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel36.setText("jLabel1");
        jLabel36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel36MouseClicked(evt);
            }
        });

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel37.setText("jLabel1");
        jLabel37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel37MouseClicked(evt);
            }
        });

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel38.setText("jLabel1");
        jLabel38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel38MouseClicked(evt);
            }
        });

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel39.setText("jLabel1");
        jLabel39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel39MouseClicked(evt);
            }
        });

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel40.setText("jLabel1");
        jLabel40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel40MouseClicked(evt);
            }
        });

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel41.setText("jLabel1");
        jLabel41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel41MouseClicked(evt);
            }
        });

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel42.setText("jLabel1");
        jLabel42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel42MouseClicked(evt);
            }
        });

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel43.setText("jLabel1");
        jLabel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel43MouseClicked(evt);
            }
        });

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel44.setText("jLabel1");
        jLabel44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel44MouseClicked(evt);
            }
        });

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel45.setText("jLabel1");
        jLabel45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel45MouseClicked(evt);
            }
        });

        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel46.setText("jLabel1");
        jLabel46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel46MouseClicked(evt);
            }
        });

        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel47.setText("jLabel1");
        jLabel47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel47MouseClicked(evt);
            }
        });

        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel48.setText("jLabel1");
        jLabel48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel48MouseClicked(evt);
            }
        });

        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel49.setText("jLabel1");
        jLabel49.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel49MouseClicked(evt);
            }
        });

        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel50.setText("jLabel1");
        jLabel50.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel50MouseClicked(evt);
            }
        });

        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel51.setText("jLabel1");
        jLabel51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel51MouseClicked(evt);
            }
        });

        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel52.setText("jLabel1");
        jLabel52.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel52MouseClicked(evt);
            }
        });

        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel53.setText("jLabel1");
        jLabel53.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel53MouseClicked(evt);
            }
        });

        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel54.setText("jLabel1");
        jLabel54.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel54MouseClicked(evt);
            }
        });

        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel55.setText("jLabel1");
        jLabel55.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel55MouseClicked(evt);
            }
        });

        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel56.setText("jLabel1");
        jLabel56.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel56MouseClicked(evt);
            }
        });

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel57.setText("jLabel1");
        jLabel57.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel57MouseClicked(evt);
            }
        });

        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel58.setText("jLabel1");
        jLabel58.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel58MouseClicked(evt);
            }
        });

        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel59.setText("jLabel1");
        jLabel59.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel59MouseClicked(evt);
            }
        });

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel60.setText("jLabel1");
        jLabel60.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel60MouseClicked(evt);
            }
        });

        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel61.setText("jLabel1");
        jLabel61.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel61MouseClicked(evt);
            }
        });

        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel62.setText("jLabel1");
        jLabel62.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel62MouseClicked(evt);
            }
        });

        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/backend.jpg"))); // NOI18N
        jLabel63.setText("jLabel1");
        jLabel63.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel63MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout gameboardLayout = new javax.swing.GroupLayout(gameboard);
        gameboard.setLayout(gameboardLayout);
        gameboardLayout.setHorizontalGroup(
            gameboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameboardLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(gameboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(gameboardLayout.createSequentialGroup()
                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(gameboardLayout.createSequentialGroup()
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(gameboardLayout.createSequentialGroup()
                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(gameboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(gameboardLayout.createSequentialGroup()
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(gameboardLayout.createSequentialGroup()
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(gameboardLayout.createSequentialGroup()
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(gameboardLayout.createSequentialGroup()
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(gameboardLayout.createSequentialGroup()
                            .addComponent(jLabel0, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        gameboardLayout.setVerticalGroup(
            gameboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameboardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gameboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel0)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(gameboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(gameboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(gameboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel26)
                    .addComponent(jLabel25)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(gameboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel34)
                    .addComponent(jLabel33)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37)
                    .addComponent(jLabel38)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(gameboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jLabel42)
                    .addComponent(jLabel41)
                    .addComponent(jLabel43)
                    .addComponent(jLabel44)
                    .addComponent(jLabel45)
                    .addComponent(jLabel46)
                    .addComponent(jLabel47))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(gameboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(jLabel50)
                    .addComponent(jLabel49)
                    .addComponent(jLabel51)
                    .addComponent(jLabel52)
                    .addComponent(jLabel53)
                    .addComponent(jLabel54)
                    .addComponent(jLabel55))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(gameboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(jLabel58)
                    .addComponent(jLabel57)
                    .addComponent(jLabel59)
                    .addComponent(jLabel60)
                    .addComponent(jLabel61)
                    .addComponent(jLabel62)
                    .addComponent(jLabel63))
                .addContainerGap(72, Short.MAX_VALUE))
        );

        jLabel0.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel1.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel2.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel3.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel4.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel5.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel6.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel7.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel8.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel9.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel10.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel11.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel12.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel13.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel14.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel15.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel16.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel17.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel18.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel19.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel20.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel21.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel22.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel23.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel24.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel25.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel26.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel27.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel28.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel29.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel30.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel31.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel32.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel33.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel34.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel35.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel36.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel37.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel38.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel39.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel40.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel41.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel42.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel43.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel44.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel45.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel46.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel47.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel48.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel49.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel50.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel51.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel52.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel53.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel54.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel55.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel56.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel57.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel58.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel59.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel60.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel61.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel62.getAccessibleContext().setAccessibleName("jLabel0");
        jLabel63.getAccessibleContext().setAccessibleName("jLabel0");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Game status"));

        p1Name.setText("Player 1 name");

        p1Score.setText("P1 score:");

        p1Value.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p1Value.setText("0");

        p2Name.setText("Player 2 name");

        p2Score.setText("P2 score:");

        p2Value.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p2Value.setText("0");

        GameExit.setText("Exit");
        GameExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GameExitMouseClicked(evt);
            }
        });

        GameSave.setText("Save");
        GameSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GameSaveMouseClicked(evt);
            }
        });

        LeaveGame.setText("Leave");
        LeaveGame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LeaveGameMouseClicked(evt);
            }
        });

        GameStatus.setText("Waiting for opponent!");

        statusLabel.setText("Game status:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator2)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(p2Name)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(p1Score)
                        .addGap(18, 18, 18)
                        .addComponent(p1Value))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(p2Score)
                        .addGap(18, 18, 18)
                        .addComponent(p2Value))
                    .addComponent(p1Name)
                    .addComponent(statusLabel)
                    .addComponent(GameStatus)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(GameSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LeaveGame)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GameExit)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(p1Name)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p1Score)
                    .addComponent(p1Value))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 202, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusLabel)
                .addGap(18, 18, 18)
                .addComponent(GameStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p2Name)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p2Score)
                    .addComponent(p2Value))
                .addGap(220, 220, 220)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GameExit)
                    .addComponent(GameSave)
                    .addComponent(LeaveGame))
                .addContainerGap())
        );

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gameboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(gameboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE)
                    .addGap(22, 22, 22)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, 851, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(24, 24, 24)
                    .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
                    .addGap(24, 24, 24)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void port_inActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_port_inActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_port_inActionPerformed

    private void loginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginMouseClicked
        
        status_label.setVisible(true);
        status_label.setText("Connecting...");
        
        
        String nick;
        InetAddress ip; 
        int port;
        char nick_l;
        int temp;
        int id;
        nick = player_name.getText();
        nick_l = (char)(nick.length()+ '0'); /*nick length*/ 
        
        for(int i = 0; i < users.length; i++)
        {
            if (users[i].getNick().equals(nick)) 
            {
                Pexeso_client.CurrentPlayer = users[i];
            }
        }        
        
        System.out.println("Number of saved players:"+users.length);
        
        if( Pexeso_client.CurrentPlayer == null) 
        {
            Pexeso_client.CurrentPlayer = new Player(nick);
        }
        
        port = Integer.parseInt(port_in.getText());
        try
        {
            ip = InetAddress.getByName(server_ip.getText());
            
            comm = new Communication_model(port, ip, nick);
            
            
            if(Pexeso_client.CurrentPlayer.getID() == -1)//new user
            {
                LobbyStatus.setText("WELCOME NEW USER!");
                
                temp = comm.msgSender("N"+nick_l+nick);
                if(temp == 0)
                { 
                    id = (comm.getLastMsg().charAt(1)-'0');
                    System.out.println("new player IDchar: "+comm.getLastMsg().charAt(1)+""+(comm.getLastMsg().charAt(1)-'0'));
                    Pexeso_client.CurrentPlayer.setID(id);
                    users = Pexeso_client.CurrentPlayer.newPlayer(users,Pexeso_client.CurrentPlayer);
                    
                    File dir = new File("/saves/"+Pexeso_client.CurrentPlayer.getNick());
                    dir.mkdir();
                    loadGameBox.removeAllItems();
                    LoadGame.setVisible(false);
                }
                    
                
            }
            else // old user
            {
                LobbyStatus.setText("WELCOME BACK "+Pexeso_client.CurrentPlayer.getNick()+"!");
                temp = comm.msgSender("C"+(char)(Pexeso_client.CurrentPlayer.getID()+'0'));
                
                
            }
                    
            
            if(temp == 0)
            {   
                System.out.println("Succefull login as: "+nick+" on server IP: "+ip+" Port: "+port+ " PID: "+Pexeso_client.CurrentPlayer.getID());
                
                try
                {
                    loaded_games = Game.loadGames(Pexeso_client.CurrentPlayer.getNick());
                    if(loaded_games != null)
                    {
                        loadGameBox.removeAllItems();
                        for(int i = 0; i < loaded_games.length ; i++)
                        {
                           loadGameBox.addItem(loaded_games[i].getID()+""+loaded_games[i].getNick1());
                           LoadGame.setVisible(true);
                        }
                    }
                }catch( Exception e )
                {
                    LoadGame.setVisible(false);
                    System.out.println(e) ;
                }
                
                lobbyNick.setText(nick);
                lobbyNick.setVisible(true);
                LobbyStatus.setVisible(true);
                panel1.setVisible(false);
                panel2.setVisible(true);
                status_label.setVisible(false);
            }
            else
            {
                status_label.setText("Failed to connect!");
                status_label.setVisible(true);
            }
        }
        catch( UnknownHostException | NumberFormatException e )
        {
           System.out.println( e ) ;
        }
          
        
        
    }//GEN-LAST:event_loginMouseClicked

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_exitMouseClicked

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exitActionPerformed

    private void exit1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exit1MouseClicked
        comm.closeSocket();
        System.exit(0);
    }//GEN-LAST:event_exit1MouseClicked

    private void exit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exit1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exit1ActionPerformed

    private void backToLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backToLoginMouseClicked
        
        status_label.setText("Disconnected...");
        status_label.setVisible(true);
        Pexeso_client.CurrentPlayer = null;
        loaded_games = null;
        loadGameBox.removeAllItems();
        LoadGame.setVisible(false);
        comm.setExit(true);
        comm.closeSocket();
        
        panel1.setVisible(true);
        panel2.setVisible(false);
        
    }//GEN-LAST:event_backToLoginMouseClicked

    private void jLabel0MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel0MouseClicked
        GameCardClicked(0);
    }//GEN-LAST:event_jLabel0MouseClicked

    private void NewGameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NewGameMouseClicked
        
        int temp;
        String msg;
        Pexeso_client.CurrentGame = new Game(comm.getNick());
        
        msg = Pexeso_client.CurrentGame.ConstructNGMsg(Pexeso_client.CurrentPlayer.getID());
        temp = comm.msgSender(msg);
        if(temp == 0)
        {   
            Pexeso_client.CurrentGame.setID(comm.getLastMsg().charAt(1)-'0');
            FillCards();
            Pexeso_client.CurrentPlayer.setTurning(true);
            Pexeso_client.CurrentPlayer.setPosition(1);
            
            
            comm.setExit(false);
            t1 = new Thread(comm); 
            t1.start();
            

            try 
            {
                sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Pexeso_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(Pexeso_client.CurrentGame.getState() != 1)
            {
                comm.setExit(true);
                comm.game_thread.interrupt();
                panel2.setVisible(true);
                panel3.setVisible(false);
                Pexeso_client.CurrentGame = null;
                LobbyStatus.setText("Game ended no opponent found!");
                LobbyStatus.setVisible(true);
            }
            else if(Pexeso_client.CurrentGame.getState() == 1)
            {
                p1Name.setText(Pexeso_client.CurrentGame.getNick1());
                p2Name.setText(Pexeso_client.CurrentGame.getNick2());
                GameStatus.setText("Opponent found! Make your turn!");
                
                panel2.setVisible(false);
                panel3.setVisible(true);
            }
        }
        else
        {
           LobbyStatus.setText("Failed to create new game!");
           LobbyStatus.setVisible(true);
        }
    }//GEN-LAST:event_NewGameMouseClicked

    private void GameExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GameExitMouseClicked
        comm.closeSocket();
        comm.setExit(true);
        comm.game_thread.interrupt();
        
        System.exit(0);
    }//GEN-LAST:event_GameExitMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        GameCardClicked(1);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        GameCardClicked(2);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
       GameCardClicked(3);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
       GameCardClicked(4);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        GameCardClicked(5);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        GameCardClicked(6);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        GameCardClicked(7);
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        GameCardClicked(8);
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        GameCardClicked(9);
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        GameCardClicked(10);
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        GameCardClicked(11);
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        GameCardClicked(12);
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        GameCardClicked(13);
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        GameCardClicked(14);
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        GameCardClicked(15);
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        GameCardClicked(16);
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        GameCardClicked(17);
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        GameCardClicked(18);
    }//GEN-LAST:event_jLabel18MouseClicked

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        GameCardClicked(19);
    }//GEN-LAST:event_jLabel19MouseClicked

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        GameCardClicked(20);
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        GameCardClicked(21);
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        GameCardClicked(22);
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        GameCardClicked(23);
    }//GEN-LAST:event_jLabel23MouseClicked

    private void jLabel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseClicked
        GameCardClicked(24);
    }//GEN-LAST:event_jLabel24MouseClicked

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked
        GameCardClicked(25);
    }//GEN-LAST:event_jLabel25MouseClicked

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        GameCardClicked(26);
    }//GEN-LAST:event_jLabel26MouseClicked

    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseClicked
        GameCardClicked(27);
    }//GEN-LAST:event_jLabel27MouseClicked

    private void jLabel28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel28MouseClicked
        GameCardClicked(28);
    }//GEN-LAST:event_jLabel28MouseClicked

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseClicked
        GameCardClicked(29);
    }//GEN-LAST:event_jLabel29MouseClicked

    private void jLabel30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MouseClicked
       GameCardClicked(30);
    }//GEN-LAST:event_jLabel30MouseClicked

    private void jLabel31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MouseClicked
        GameCardClicked(31);
    }//GEN-LAST:event_jLabel31MouseClicked

    private void jLabel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseClicked
        GameCardClicked(32);
    }//GEN-LAST:event_jLabel32MouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        GameCardClicked(33);
    }//GEN-LAST:event_jLabel33MouseClicked

    private void jLabel34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel34MouseClicked
        GameCardClicked(34);
    }//GEN-LAST:event_jLabel34MouseClicked

    private void jLabel35MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel35MouseClicked
        GameCardClicked(35);
    }//GEN-LAST:event_jLabel35MouseClicked

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked
        GameCardClicked(36);
    }//GEN-LAST:event_jLabel36MouseClicked

    private void jLabel37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel37MouseClicked
        GameCardClicked(37);
    }//GEN-LAST:event_jLabel37MouseClicked

    private void jLabel38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel38MouseClicked
        GameCardClicked(38);
    }//GEN-LAST:event_jLabel38MouseClicked

    private void jLabel39MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MouseClicked
        GameCardClicked(39);
    }//GEN-LAST:event_jLabel39MouseClicked

    private void jLabel40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseClicked
        GameCardClicked(40);
    }//GEN-LAST:event_jLabel40MouseClicked

    private void jLabel41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel41MouseClicked
        GameCardClicked(41);
    }//GEN-LAST:event_jLabel41MouseClicked

    private void jLabel42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel42MouseClicked
        GameCardClicked(42);
    }//GEN-LAST:event_jLabel42MouseClicked

    private void jLabel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseClicked
        GameCardClicked(43);
    }//GEN-LAST:event_jLabel43MouseClicked

    private void jLabel44MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseClicked
        GameCardClicked(44);
    }//GEN-LAST:event_jLabel44MouseClicked

    private void jLabel45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseClicked
        GameCardClicked(45);
    }//GEN-LAST:event_jLabel45MouseClicked

    private void jLabel46MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseClicked
        GameCardClicked(46);
    }//GEN-LAST:event_jLabel46MouseClicked

    private void jLabel47MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseClicked
        GameCardClicked(47);
    }//GEN-LAST:event_jLabel47MouseClicked

    private void jLabel48MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseClicked
        GameCardClicked(48);
    }//GEN-LAST:event_jLabel48MouseClicked

    private void jLabel49MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel49MouseClicked
        GameCardClicked(49);
    }//GEN-LAST:event_jLabel49MouseClicked

    private void jLabel50MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel50MouseClicked
        GameCardClicked(50);
    }//GEN-LAST:event_jLabel50MouseClicked

    private void jLabel51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseClicked
        GameCardClicked(51);
    }//GEN-LAST:event_jLabel51MouseClicked

    private void jLabel52MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseClicked
        GameCardClicked(52);
    }//GEN-LAST:event_jLabel52MouseClicked

    private void jLabel53MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseClicked
        GameCardClicked(53);
    }//GEN-LAST:event_jLabel53MouseClicked

    private void jLabel54MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseClicked
        GameCardClicked(54);
    }//GEN-LAST:event_jLabel54MouseClicked

    private void jLabel55MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseClicked
        GameCardClicked(55);
    }//GEN-LAST:event_jLabel55MouseClicked

    private void jLabel56MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel56MouseClicked
        GameCardClicked(56);
    }//GEN-LAST:event_jLabel56MouseClicked

    private void jLabel57MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel57MouseClicked
        GameCardClicked(57);
    }//GEN-LAST:event_jLabel57MouseClicked

    private void jLabel58MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel58MouseClicked
        GameCardClicked(58);
    }//GEN-LAST:event_jLabel58MouseClicked

    private void jLabel59MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel59MouseClicked
        GameCardClicked(59);
    }//GEN-LAST:event_jLabel59MouseClicked

    private void jLabel60MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel60MouseClicked
        GameCardClicked(60);
    }//GEN-LAST:event_jLabel60MouseClicked

    private void jLabel61MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel61MouseClicked
        GameCardClicked(61);
    }//GEN-LAST:event_jLabel61MouseClicked

    private void jLabel62MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel62MouseClicked
        GameCardClicked(62);
    }//GEN-LAST:event_jLabel62MouseClicked

    private void jLabel63MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel63MouseClicked
        GameCardClicked(63);
    }//GEN-LAST:event_jLabel63MouseClicked

    private void LeaveGameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LeaveGameMouseClicked
        Pexeso_client.CurrentGame = null;
        comm.setExit(true);
        comm.game_thread.interrupt();
        
        LobbyStatus.setText("You left your current game");
        LobbyStatus.setVisible(true);        
        panel2.setVisible(true);
        panel3.setVisible(false);
    }//GEN-LAST:event_LeaveGameMouseClicked
    
    private static Object makeObj( final String title ) 
    {
        
        return new Object() 
        { 
            @Override
            public String toString()
            {
                return title; 
            }
        };
    }

    private void RefreshlistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RefreshlistMouseClicked
        
        joinGameBox.removeAllItems();
        
        int x;
        String msg = "r";
        int temp;
        temp = comm.msgSender(msg);
        
        if(temp == 0)
        {
            x = comm.getLastMsg().charAt(1)-'0';
            System.out.println("Going to receive "+x+" games");  
            gamesList = comm.recieveGames(x);        

            for(int i = 0; i < x ; i++)
            {

               msg = gamesList[i].trim();
               joinGameBox.addItem(msg);
            }
        }
        else
        {
            LobbyStatus.setText("Failed to refresh, please try again later!");
            LobbyStatus.setVisible(true);
        }
    }//GEN-LAST:event_RefreshlistMouseClicked

    private void JoinGameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JoinGameMouseClicked
       
        int i, temp;
        Card [] cards = new Card[GAME_SIZE];
        
        
        
        int p1score = 0, p2score = 0;
        String player1, player2 = Pexeso_client.CurrentPlayer.getNick();
        Pexeso_client.CurrentPlayer.setTurning(false);
        Pexeso_client.CurrentPlayer.setPosition(2);
        String selected = joinGameBox.getSelectedItem().toString();
        System.out.println("Selected for connection: "+selected);
        player1 = selected.substring(2);
        
        String msg = "j";
        msg += selected.charAt(1);
        msg += (char)(Pexeso_client.CurrentPlayer.getID()+'0');
        
        temp = comm.msgSender(msg);
        msg = comm.getLastMsg();
        
        if(temp == 0)
        {
            for(i = 0; i < GAME_SIZE; i++)
            {
                cards[i] = new Card();
                cards[i].setPic_id(msg.charAt(1 + i)-'0');
            }

            msg = "i" + selected.charAt(1);
            comm.msgSender(msg);
            msg = comm.getLastMsg();

            for(i = 0; i < GAME_SIZE; i++)
            {            
                cards[i].setState(msg.charAt(1 + i)-'0');
            }

            Pexeso_client.CurrentGame = new Game(selected.charAt(1), player1, player2, p1score, p2score, cards, 1);
            Pexeso_client.CurrentGame.setID(selected.charAt(1)-'0');
            FillCards();
            
            p1Name.setText(Pexeso_client.CurrentGame.getNick1());
            p2Name.setText(Pexeso_client.CurrentGame.getNick2());
            p1Value.setText(Pexeso_client.CurrentGame.getP1Score()+"");
            p2Value.setText(Pexeso_client.CurrentGame.getP2Score()+"");
            Pexeso_client.CurrentGame.setState(1);
            panel2.setVisible(false);
            panel3.setVisible(true);
            
            /*thread ralated stuff*/
            comm.setExit(false);
            t1 = new Thread(comm); 
            t1.start();
        }
        else
        {
            LobbyStatus.setText("Failed to connect to selected game!");
            LobbyStatus.setVisible(true);
        }
    }//GEN-LAST:event_JoinGameMouseClicked

    private void GameSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GameSaveMouseClicked
        
        try 
        {
            Pexeso_client.CurrentGame.saveGame(Pexeso_client.CurrentGame, Pexeso_client.CurrentPlayer.getNick());
        } 
        catch (IOException ex) 
        {
            System.out.println("Failed to save your game");
        }
    }//GEN-LAST:event_GameSaveMouseClicked

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Pexeso_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Pexeso_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Pexeso_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Pexeso_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton GameExit;
    public javax.swing.JButton GameSave;
    public javax.swing.JLabel GameStatus;
    public javax.swing.JButton JoinGame;
    public javax.swing.JButton LeaveGame;
    public javax.swing.JButton LoadGame;
    public javax.swing.JLabel LobbyStatus;
    private javax.swing.JPanel Login;
    public javax.swing.JButton NewGame;
    public javax.swing.JButton Refreshlist;
    public javax.swing.JButton backToLogin;
    private javax.swing.JButton exit;
    public javax.swing.JButton exit1;
    private javax.swing.JPanel gameboard;
    private javax.swing.JLabel info1;
    private javax.swing.JLabel jLabel0;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JSeparator jSeparator1;
    public javax.swing.JSeparator jSeparator2;
    public javax.swing.JComboBox<String> joinGameBox;
    public javax.swing.JComboBox<String> loadGameBox;
    private javax.swing.JPanel lobbyMPanel;
    public javax.swing.JLabel lobbyNick;
    private javax.swing.JButton login;
    private javax.swing.JLabel nick;
    private javax.swing.JLabel nickHint;
    public javax.swing.JLabel p1Name;
    public javax.swing.JLabel p1Score;
    public javax.swing.JLabel p1Value;
    public javax.swing.JLabel p2Name;
    public javax.swing.JLabel p2Score;
    public javax.swing.JLabel p2Value;
    public javax.swing.JPanel panel1;
    public javax.swing.JPanel panel2;
    public javax.swing.JPanel panel3;
    private javax.swing.JTextField player_name;
    private javax.swing.JLabel portHint;
    private javax.swing.JTextField port_in;
    private javax.swing.JLabel port_label;
    private javax.swing.JLabel server;
    private javax.swing.JTextField server_ip;
    public javax.swing.JLabel statusLabel;
    private javax.swing.JLabel status_label;
    private javax.swing.JLabel welcome;
    private javax.swing.JPanel welcomePanel;
    public javax.swing.JLabel youarelogged;
    // End of variables declaration//GEN-END:variables

    
}
