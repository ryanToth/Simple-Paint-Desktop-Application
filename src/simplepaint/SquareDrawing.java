/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import java.awt.Color;
import java.awt.Rectangle;

/**
 *
 * @author Ryan
 */
public class SquareDrawing extends Drawing {

    public SquareDrawing(int x, int y, int width, int height, Color color, boolean fromLoad) {
        super(x, y, width, height, color);
        shape = new Rectangle(x1,y1,width,height);
    }
    
    public SquareDrawing(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        
        int corner = 0;
        int X = x1;
        int Y = y1;
        
        if (x2 - x1 > 0 && y2 - y1 > 0) {

            if (x2 - x1 < y2 - y1)
                corner = x2 - x1;
            else
                corner = y2 - y1;
            
        } else if (x2 - x1 > 0 && y2 - y1 < 0) //Negative height
        {
            Y = y2;
            
            if (x2 - x1 < y1 - y2) {
                corner = x2 - x1;
                Y = y1 - corner;
            }
            else
                corner = y1 - y2;
            
        } else if (x2 - x1 < 0 && y2 - y1 > 0) //Negative X
        {
            X = x2;
            
            if (x1 - x2 < y2 - y1)
                corner = x1 - x2;
            else {
                corner = y2 - y1;
                X = x1 - corner;
            }
            
        } else { //Negative X and Y 
            
            X = x2;
            Y = y2;
            
            if (x1 - x2 < y1 - y2) {
                corner = x1 - x2;
                Y = y1 - corner;
            }
            else {
                corner = y1 - y2;
                X = x1 - corner;
            }
        }
        shape = new Rectangle(X, Y, corner, corner);
        
        this.x1 = X;
        this.x2 = corner;
        this.y1 = Y;
        this.y2 = corner;
    }
    
    @Override
    public void move(int x, int y) {       
        super.move(x,y);
        shape = new Rectangle(x1,y1,x2,y2);
    }
    
    @Override
    public String toString() {
        return "s:" + super.toString();
    }
}
