/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mariotestgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JTextField;


/**
 *
 * @author Rafael Perez
 */
public class MarioTestGame extends JFrame {

   int width = 800;
    int height = 600;
    Dimension resolution = new Dimension(width, height);
    Image dbImage;
    Graphics dbGraphics;
    
    static playerCharacter player1 = new playerCharacter(400, 476);
    
    
    public MarioTestGame(){
        this.getContentPane().setBackground(Color.BLACK);
        this.setTitle("Mario Test Game (in development)");
        this.setSize(resolution);
        this.setResizable(false);
        this.setVisible(true);
        this.setBackground(Color.CYAN);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextField textField = new JTextField();
        textField.addKeyListener(new Input());
        this.add(textField);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        
        MarioTestGame game = new MarioTestGame();
        Thread mario = new Thread(player1);
        mario.start();
        
    }
    @Override
    public void paint(Graphics g) { //draw graphics
		dbImage = createImage(getWidth(), getHeight());
		dbGraphics = dbImage.getGraphics();
		draw(dbGraphics);
		g.drawImage(dbImage, 0, 0, this);
	}
    public void draw(Graphics g) {
		g.setColor(Color.WHITE);
                player1.draw(g);
                player1.floor.draw(g);
                //blockA.draw(g);
                
                Image p1stand = Toolkit.getDefaultToolkit().getImage("gpx/p1stand.png");
                Image p1walk2 = Toolkit.getDefaultToolkit().getImage("gpx/p1walk2.png");
                Image p1walk3 = Toolkit.getDefaultToolkit().getImage("gpx/p1walk3.png");
                Image tile = Toolkit.getDefaultToolkit().getImage("gpx/tile.png");
                Image jump = Toolkit.getDefaultToolkit().getImage("gpx/jump.png");
                Image background = Toolkit.getDefaultToolkit().getImage("gpx/bg.png");
                
                g.drawImage(background, 0, 0, this);
                
                int p1direction;
                if (player1.getXmovement() < 0)
                    p1direction = -1;
                 if (player1.getXmovement() > 0)
                    p1direction = 1;
                else  p1direction = -1;
                
                switch(player1.getWalkState()){
                    case 0:
                            //if (player1.getXmovement() == 1)
                            g.drawImage(p1stand, player1.playChar.x - 6, player1.playChar.y - 27, this);    
                            //else if (player1.getXmovement() == -1)
                            //g.drawImage(p1stand, (player1.playChar.x - 6) + 48, player1.playChar.y - 27, -48, 96, this);
                            break;
                    case 1:
                            if (player1.getXmovement() >= 1)
                            g.drawImage(p1stand, player1.playChar.x - 6, player1.playChar.y - 27, this);    
                            else if (player1.getXmovement() <= -1)
                            g.drawImage(p1stand, (player1.playChar.x - 6) + 48, player1.playChar.y - 27, -48, 96, this);  
                            playSfx(1);
                            break;
                    case 2:
                            if (player1.getXmovement() >= 1)
                            g.drawImage(p1walk2, player1.playChar.x - 6, player1.playChar.y - 27, this);    
                            else if (player1.getXmovement() <= -1)
                            g.drawImage(p1walk2, (player1.playChar.x - 6) + 48, player1.playChar.y - 27, -48, 96, this); 
                            playSfx(2);
                            break;
                    case 3:
                            if (player1.getXmovement() >= 1)
                            g.drawImage(p1walk3, player1.playChar.x - 6, player1.playChar.y - 27, this);    
                            else if (player1.getXmovement() <= -1)
                            g.drawImage(p1walk3, (player1.playChar.x - 6) + 48, player1.playChar.y - 27, -48, 96, this); 
                            playSfx(3);
                            break;
                    case 4:
                            //if (player1.getXmovement() >= 1)
                            //g.drawImage(jump, player1.playChar.x - 6, player1.playChar.y - 27, this);    
                            if (player1.getXmovement() <= -1)
                            g.drawImage(jump, (player1.playChar.x - 6) + 48, player1.playChar.y - 27, -48, 96, this); 
                            else g.drawImage(jump, player1.playChar.x - 6, player1.playChar.y - 27, this);
                            //playSfx(4);
                            
                            break;
                    default:
                            break;
                            
            }
                //g.drawImage(image, x + width, y, -width, height, null);
                
                //g.drawImage(player1Sprite, player1.playChar.x - 6, player1.playChar.y - 27, this);
                //g.drawImage(player1Sprite, (player1.playChar.x - 6) + 48, player1.playChar.y - 27, -48, 96, this); //flips the sprite
		
                
                
                
                for(int i = 8; i < 800; i += 48)
                    g.drawImage(tile, player1.floor.block.x + i, player1.floor.block.y, this);
                
                repaint();
	}
    
        public void playSfx(int i) {
        try {
            Clip jump = AudioSystem.getClip();
            Clip step1 = AudioSystem.getClip();
            Clip step2 = AudioSystem.getClip();
            Clip step3 = AudioSystem.getClip();
            
            jump.open(AudioSystem.getAudioInputStream(new File("sfx/jump.wav")));
            step1.open(AudioSystem.getAudioInputStream(new File("sfx/step1.wav")));
            step2.open(AudioSystem.getAudioInputStream(new File("sfx/step2.wav")));
            step3.open(AudioSystem.getAudioInputStream(new File("sfx/step3.wav")));
            
            switch(i){
                case 1:
                    step1.start();
                    
                    break;
                case 2:
                    step2.start();
                    break;
                case 3:
                    step3.start();
                    break;
                case 4:
                    if (player1.getWalkState() == 4 && player1.getYmomentum() == 2)
                    jump.start();
                    System.out.println("jump sound!!!");
                    break;
                default: break;
            }

            //sound.start();

        } catch (Exception e) {
            System.out.println("Whatever" + e);
        }
    }

    
    class Input extends KeyAdapter{
                double baseSpeed = 1;
            @Override
            public void keyPressed(KeyEvent event){
                
                if(event.getKeyCode() == KeyEvent.VK_W){
                    player1.isJumping(1);
                    playSfx(4);
                    
                }
                      //player1.setYmovement(baseSpeed * -1);
                if(event.getKeyCode() == KeyEvent.VK_A)
                    player1.setXmovement(baseSpeed * -1);
                 //if(event.getKeyCode() == KeyEvent.VK_S)
                    //player1.setYmovement(baseSpeed);
                if(event.getKeyCode() == KeyEvent.VK_D)
                    player1.setXmovement(baseSpeed);
            }

            @Override
            public void keyReleased(KeyEvent event){
                if(event.getKeyCode() == event.VK_W) 
                    player1.isJumping(-1);
			//player1.setYmovement(0);
                if(event.getKeyCode() == event.VK_A) 
			player1.setXmovement(0);
                //if(event.getKeyCode() == event.VK_S) 
			//player1.setYmovement(0);
                if(event.getKeyCode() == event.VK_D) 
			player1.setXmovement(0);
		} 
        }
}
