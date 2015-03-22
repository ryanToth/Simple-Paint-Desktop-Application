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
public class RectangleDrawing extends Drawing {
    
    public RectangleDrawing(int x1, int y1, int width, int height, Color color, boolean fromLoad) {
        super(x1,y1,width,height, color);
        shape = new Rectangle(x1,y1,width,height);
    }
    
    public RectangleDrawing(int x1, int y1, int x2, int y2, Color color) {
        super(x1,y1,x2,y2, color);
        
        if (x2 - x1 > 0 && y2 - y1 > 0) {
            shape = new Rectangle(x1, y1, x2 - x1, y2 - y1);
            this.x2 = x2-x1;
            this.y2 = y2 - y1;
            
        } else if (x2 - x1 > 0 && y2 - y1 < 0) //Negative height
        {
            shape = new Rectangle(x1, y2, x2 - x1, y1 - y2);
            this.y1 = y2;
            this.x2 = x2 - x1;
            this.y2 = y1 - y2;
            
        } else if (x2 - x1 < 0 && y2 - y1 > 0) //Negative X
        {
            shape = new Rectangle(x2, y1, x1 - x2, y2 - y1);
            this.x1 = x2;
            this.x2 = x1 - x2;
            this.y2 = y2 - y1;
            
        } else {//Negative X and Y
            shape = new Rectangle(x2, y2, x1 - x2, y1 - y2);
            this.x1 = x2;
            this.y1 = y2;
            this.x2 = x2 - x1;
            this.y2 = y2 - y1;
        }
    }
    
    @Override
    public void move(int x, int y) {       
        super.move(x,y);
        shape = new Rectangle(x1,y1,x2,y2);
    }
    
    @Override
    public String toString() {
        return "r:" + super.toString();
    }
}
    
