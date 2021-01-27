/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mariotestgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Rafael Perez
 */
public class tile {
    
    Rectangle block;
    public tile(int xPos, int yPos){
        block = new Rectangle(xPos, yPos, 48, 48);
    }
    public tile(int xPos, int yPos, int width, int height){
        block = new Rectangle(xPos, yPos, width, height);
    }
    
    protected void draw(Graphics g){
        Color transparent = new Color(51, 204, 255, 0);
        g.setColor(Color.BLUE);
        g.fillRect(block.x, block.y, block.width, block.height);
    }
}
