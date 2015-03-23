/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.LinkedList;

/**
 *
 * @author Ryan
 */
public class PolygonDrawing extends Drawing {

    LinkedList<LineDrawing> lines = new LinkedList<>();
    Rectangle endingPoint;
    
    public PolygonDrawing(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        lines.add(new LineDrawing(x, y, width, height, color));
        endingPoint = new Rectangle(x-3, y-3, x+3, y-3);
    }
    
    public void modifyLastLine(int x2, int y2) {
        lines.getLast().x2 = x2;
        lines.getLast().y2 = y2;
        lines.getLast().shape = new Line2D.Double(lines.getLast().x1, lines.getLast().y1, x2, y2);
    }
    
    public void addNewLine(int x1, int y1, int x2, int y2) {
        lines.add(new LineDrawing(x1, y1, x2, y2, this.color));
    }
    
    @Override
    public void move(int x, int y) {
        for (int i = 0; i < lines.size(); i++)
            lines.get(i).move(x,y);
    }
    
    @Override
    public boolean intersects(int x, int y) {
        
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).intersects(x, y))
                return true;
        }
        
        return false;
    }
    
    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < lines.size(); i++) {
            if (this.isSelected)
                lines.get(i).paint(g,true);
            else lines.get(i).paint(g);
        }
    }
    
    @Override
    public PolygonDrawing duplicate() throws CloneNotSupportedException {
        
        Drawing duplicate = (Drawing) this.clone();
        duplicate.isSelected = false;
        
        PolygonDrawing tmp = (PolygonDrawing) duplicate;
        
        tmp.lines = new LinkedList<>();
        
        for (int i = 0; i < this.lines.size(); i++) {
            tmp.lines.add((LineDrawing)this.lines.get(i).duplicate());
        }

        pasteNum += 15;
        
        return tmp;
    }
    
    @Override
    public String toString() {
        
        String output = "p:";
        
        for (int i = 0; i < lines.size(); i++) {
            String[] tmp = lines.get(i).toString().split(":")[1].split(",");
            output += tmp[0] + "," + tmp[1] + "," + tmp[2] + "," + tmp[3] + "," + color.toString().substring(15, color.toString().length() -1 ) + ":";
        }
        
        output = output.substring(0, output.length()-1);
        
        return output;
    }
    
}
