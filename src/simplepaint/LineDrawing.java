/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 *
 * @author Ryan
 */
public class LineDrawing extends Drawing {

    public LineDrawing(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        shape = new Line2D.Double(x,y,width,height);
    }
    
    public void paint(Graphics g, boolean selected) {
       
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(Color.CYAN);
        
        g2.draw(shape);
    }
    
    @Override
    public boolean intersects(int x, int y) {
        return shape.intersects(x, y, 10, 10);
    }
    
    @Override
    public void move(int x, int y) {
        super.move(x,y);
        x2 += x;
        y2 += y;
        shape = new Line2D.Double(x1,y1,x2,y2);
    }
    
    @Override
    public String toString() {
        return "l:" + super.toString();
    }
}
