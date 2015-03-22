/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

/**
 *
 * @author Ryan
 */
public abstract class Drawing implements Cloneable {
    
    int x1, y1, x2, y2;
    boolean isSelected = false;
    Color color = Color.black;
    Shape shape;
    int pasteNum = 15;
    
    public Drawing(int x, int y, int width, int height, Color color) {
        x1 = x;
        y1 = y;
        y2 = height;
        x2 = width;
        this.color = color;
    }
    
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        if (isSelected) g2.setColor(Color.CYAN);
        else g2.setColor(color);
        
        g2.draw(shape);
    }
    
    public void move(int x, int y) {
        x1 += x;
        y1 += y;
    }
    
    public Drawing duplicate() throws CloneNotSupportedException {
        Drawing duplicate = (Drawing) this.clone();
        duplicate.isSelected = false;
        duplicate.move(pasteNum, pasteNum);
        pasteNum += 15;
        return duplicate;
    }
    
    public Drawing createCopy() throws CloneNotSupportedException {
        Drawing duplicate = (Drawing) this.clone();
        return duplicate;
    }
    
    public void resetPasteNum() {
        pasteNum = 15;
    }
    
    public boolean intersects(int x, int y) {
        return shape.intersects(x-2, y-2, 4, 4);
    }
    
    @Override
    public String toString() {
        return x1 + "," + y1 + "," + x2 + "," + y2 + "," + color.toString().substring(15, color.toString().length() - 1);
    }
    
}
