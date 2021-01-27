/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mariotestgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Rafael Perez
 */
public class playerCharacter implements Runnable {
    private double xMove;
    private double yMove;
    private int jumpState;
    private int jumpMomentum;
    private int jumpCap;
    
    static tile blockA = new tile(400, 544);
    static tile floor = new tile(0, 544, 800, 48);
    
    Rectangle playChar;
    Rectangle feetBox;
    Rectangle headBox;
    public playerCharacter(int xPos, int yPos){
        playChar = new Rectangle(xPos, yPos, 33, 51);
        feetBox = new Rectangle(xPos, yPos + 60, 33, 9);
        headBox = new Rectangle(xPos, yPos - 9, 36, 33);
    }
    
    
    protected void setXmovement(double x){
        xMove = x;
    }
    
    protected void setYmovement(double y){
        yMove = y;
    }
    
    protected double getXmovement(){
        return xMove;
    }
    
    protected double getYmovement(){
        return yMove;
    }
    
    private void setXposition(int x){
        playChar.x = x;
    }
    
    private void setYposition(int y){
        playChar.y = y;
    }
    
    private int getXposition(){
        return playChar.x;
    }
    
    private int getYposition(){
        return playChar.y;
    }
    
    private void collisionCheck(){
        if(feetBox.intersects(floor.block)){
            //System.out.println("intersection!");
            //jumpState = 0;
            playChar.y = 476;
            jumpCap = 0;
            
            if (jumpState == -1){
            jumpState = 0;
            jumpMomentum = 0;
            }
        }
    }
    
    protected void isJumping(int i){
        if (i == 1 && jumpMomentum < 100 && jumpCap == 0){
            jumpState = 1;
        jumpMomentum = 1;
        jumpCap = 1;
        }
        else if (i == -1){
            jumpState = -1;
            
        }
    }
    
    protected int getJumpstate(){
        return jumpState;
    }
    
    protected int getYmomentum(){
        return jumpMomentum;
    }
    
    private void setYmomentum(){
        if (jumpState == 1 && jumpMomentum < 100){
            //yMove = -1;
           yMove = -.5 / (.0002 *(jumpMomentum * jumpMomentum));
           if (yMove < -3)
           yMove = -3;
            /*switch(jumpMomentum){
                case 0:
                    yMove = -1;
                    break;
                case 10:
                    yMove = -1.5;
                    break;
                case 20:
                    yMove = -2;
                    break;
                case 50:
                    yMove = -2;
                    break;
                case 60:
                    yMove = .21;
                    break;
                case 65:
                    yMove = -.05;
                    break;
                case 75:
                    yMove = -.01;
                    break;
            }
            */
            jumpMomentum++;
            //System.out.printf("jump momentum is %d\n", jumpMomentum);
        }
        else if (jumpState != 0){
            jumpCap = 1;
           // yMove = 2;
           yMove = .5 / (.0002 *(jumpMomentum * jumpMomentum));
           if (yMove > 3)
           yMove = 3;
            jumpState = -1;
            /*switch(jumpMomentum){
                case 75:
                    yMove = .1;
                    break;
                case 65:
                    yMove = .5;
                    break;
                case 60:
                    yMove = 1;
                    break;
                case 50:
                    yMove = 2;
                    break;
                case 20:
                    yMove = 2;
                    break;
                case 10:
                    yMove = 2;
                    break;
                case 0:
                    yMove = 2;
                    break;
                default: yMove = 2;
            }
*/
            jumpMomentum--;
            //System.out.println("jumpcap active");
        }
        else yMove = 0;
    }
    
    protected int getWalkState(){
        int walkState = 0;
        if (jumpState != 0)
                return 4;
        if (xMove == 0)
                return 0;
        switch(playChar.x % 3){
            case 0:
                walkState = 3;
                break;
            default:
                 if (playChar.x % 2 == 0)
                     walkState = 2;
                 else walkState = 1;
                 break;
        }
        return walkState;
    }
    
    protected void draw(Graphics g){
        Color transparent = new Color(51, 204, 255, 0);
        g.setColor(Color.RED);
        //g.fillRect(playChar.x, playChar.y, playChar.width, playChar.height);
        g.fillRect(feetBox.x, feetBox.y, feetBox.width, feetBox.height);
        g.fillRect(headBox.x, headBox.y, headBox.width, headBox.height);
    }
    
    private void move(){
        collisionCheck();
        setYmomentum();
        playChar.x += getXmovement();
        playChar.y += getYmovement();
        feetBox.x = headBox.x = playChar.x;
        //(feetBox.y + 60) = (headBox.y - 9) = playChar.y;
        feetBox.y += getYmovement();
        
        headBox.y += getYmovement();
        
        if (playChar.x < -25)
            playChar.x = 800;
        else if (playChar.x > 800)
            playChar.x = -25;
        
        
    }
    
    @Override
    public void run(){
        for(;;){
            move();
            //System.out.printf("xpos = %d\n", getXposition());
            //System.out.printf("walkstate = %d\n", getWalkState());
            //System.out.printf("dir = %f\n", xMove);
            System.out.printf("jumpmomentum = %d\n", jumpMomentum);
            System.out.printf("yMove = %f\n", yMove);
            
            try{
                Thread.sleep(4);
            }
            catch (InterruptedException ex){
                Logger.getLogger(playerCharacter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
