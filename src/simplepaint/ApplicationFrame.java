/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Ryan
 */
public class ApplicationFrame extends JPanel implements MouseMotionListener, MouseListener, ActionListener, KeyListener {
    
    private Point p = null;
    private Drawing d = null;
    private LinkedList<Drawing> drawings = new LinkedList<>();
    private Stack<LinkedList<Drawing>> undo = new Stack<>();
    private Stack<LinkedList<Drawing>> redo = new Stack<>();
    private LinkedList<Drawing> selected = new LinkedList<>();
    private LinkedList<Drawing> clipboard = new LinkedList<>();
    boolean control = false;
    private char mode = 'l';
    private Color color = Color.black;
    
    public ApplicationFrame() {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
        switch(mode) {
         
            case 'r':
                d = new RectangleDrawing(p.x, p.y, e.getX(), e.getY(), color);
                break;
            case 's':
                d = new SquareDrawing(p.x, p.y, e.getX(), e.getY(), color);
                break;
            case 'l':
                d = new LineDrawing(p.x, p.y, e.getX(), e.getY(), color);
                break;
            case 'o':
                d = new OvalDrawing(p.x, p.y, e.getX(), e.getY(), color);
                break;
            case 'c':
                d = new CircleDrawing(p.x, p.y, e.getX(), e.getY(), color);
                break;
            case 'f':
                FreeHandDrawing h = (FreeHandDrawing) d;
                h.addNewLine(p.x, p.y, e.getX(), e.getY());
                p = e.getPoint();
                break;
            case 'q':
                for (int i = 0 ; i < selected.size(); i++)
                    selected.get(i).move(e.getX() - p.x, e.getY() - p.y);
                p = new Point(e.getX(), e.getY());
                break;
            case 'p':
                break;
                
            default: System.out.println("Error: no mode selected");
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
        if (mode == 'p' && p != null) {
            PolygonDrawing tmp = (PolygonDrawing) d;

            tmp.modifyLastLine(e.getX(), e.getY());
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (mode == 'p') {
            
            p = new Point(e.getX(), e.getY());
            PolygonDrawing tmp = (PolygonDrawing) d;
            
            if (tmp.endingPoint.contains(p) && tmp.lines.size() > 1) {
                tmp.modifyLastLine(tmp.x1, tmp.y1);
                drawings.add(d);
                p = null;
                d = null;
            }
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
       
        p = new Point(e.getX(), e.getY());
        
        switch(mode) {
            
            case 'f':
                d = new FreeHandDrawing(p.x, p.y, e.getX(), e.getY(), color);
                break;
            case 'q': //Select mode
                boolean found = false;
                for (int i = drawings.size() - 1; i >= 0; i--) {
                    if (drawings.get(i) != null && drawings.get(i).intersects(p.x, p.y)) {
                        
                        if (!control) clearSelected();
                        
                        if (drawings.get(i).isSelected) selected.remove(drawings.get(i));
                        
                        drawings.get(i).isSelected = true;
                        selected.add(drawings.get(i));
                        found = true;
                        break;
                    }
                }
                if (!found) clearSelected();
                    
                break;
        }
        
        if (mode != 'q') clearSelected();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        try {
            undo.push(this.duplicateScreen());
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ApplicationFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mode != 'q') {
            drawings.add(d);
            p = null;
            d = null;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    
    @Override
    public void paint(Graphics g) {

        repaint();
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setStroke(new BasicStroke(4));
        
        g2.setColor(Color.white);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        g2.setColor(Color.black);
        
        for (int i = 0; i < drawings.size(); i++) {
            if (drawings.get(i) != null)
                drawings.get(i).paint(g);
        }
        
        if (d != null)
            d.paint(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if (e.getKeyCode() == e.VK_X && control)
            cut();
        else if (e.getKeyCode() == e.VK_C && control)
            copy();
        else if (e.getKeyCode() == e.VK_V && control)
            try {
                paste();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ApplicationFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        else if (e.getKeyCode() == e.VK_L)
            mode = 'l';
        else if (e.getKeyCode() == e.VK_R)
            mode = 'r';
        else if (e.getKeyCode() == e.VK_O)
            mode = 'o';
        else if (e.getKeyCode() == e.VK_D) 
            deleteAll();
        else if (e.getKeyCode() == e.VK_F)
            mode = 'f';
        else if (e.getKeyCode() == e.VK_Z)
            try {
                undo();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ApplicationFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        else if (e.getKeyCode() == e.VK_X)
            try {
                redo();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ApplicationFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        else if (e.getKeyCode() == e.VK_S)
            mode = 's';
        else if (e.getKeyCode() == e.VK_C)
            mode = 'c';
        else if (e.getKeyCode() == e.VK_1)
            color = Color.black;
        else if (e.getKeyCode() == e.VK_2)
            color = Color.red;
        else if (e.getKeyCode() == e.VK_3)
            color = Color.yellow;
        else if (e.getKeyCode() == e.VK_4)
            color = Color.blue;
        else if (e.getKeyCode() == e.VK_5)
            color = Color.green;
        else if (e.getKeyCode() == e.VK_6)
            color = Color.MAGENTA;
        else if (e.getKeyCode() == e.VK_Q)
            mode = 'q';
        else if (e.getKeyCode() == e.VK_W)
            try {
                save();
        } catch (IOException ex) {
            Logger.getLogger(ApplicationFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        else if (e.getKeyCode() == e.VK_E)
            try {
                load();
        } catch (IOException ex) {
            Logger.getLogger(ApplicationFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ApplicationFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (e.getKeyCode() == e.VK_CONTROL)
            control = true;
        
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == e.VK_CONTROL)
            control = false;
    }
    
    public void undo() throws CloneNotSupportedException {
        if (!undo.isEmpty()) {
            redo.push(this.duplicateScreen());
            drawings = undo.pop();
        }
    }
    
    public void redo() throws CloneNotSupportedException {
        if (!redo.isEmpty()) {
            undo.push(this.duplicateScreen());
            drawings = redo.pop();
        }
    }
    
    public void clearSelected() {  
        for (int i = 0; i < selected.size(); i++) {
            Drawing tmp = selected.remove(i);
            tmp.isSelected = false;
            tmp.resetPasteNum();
            i--;
        }
    }
    
    public void cut() {
        
        if (!selected.isEmpty()) {
            clipboard = new LinkedList<>();
            undo.push(drawings);
        
            for (int i = 0; i < selected.size(); i++) {
                drawings.remove(selected.get(i));
                clipboard.add(selected.get(i));
            }
        }

    }
    
    public void copy() {
        
        if (!selected.isEmpty())
            clipboard = new LinkedList<>();
        
        for (int i = 0; i < selected.size(); i++) {
            clipboard.add(selected.get(i));
        }
    }
    
    public void paste() throws CloneNotSupportedException {
        
        if (!selected.isEmpty()) {
            undo.push(drawings);

            for (int i = 0; i < clipboard.size(); i++) {
                Drawing tmpDrawing = clipboard.get(i).duplicate();
                drawings.add(tmpDrawing);
            }
        }
    }
    
    public void deleteAll() {

        undo.push(drawings);
        
        drawings = new LinkedList<>();
    }
    
    public void changeMode(char change) {
        mode = change;
    }
    
    public LinkedList<Drawing> duplicateScreen() throws CloneNotSupportedException {
        
        LinkedList<Drawing> tmp = new LinkedList<>();
        
        for (int i = 0; i < drawings.size(); i++)
            tmp.add(drawings.get(i).createCopy());
        
        return tmp;
    }
    
    public void save() throws IOException {
        
        String name = JOptionPane.showInputDialog(null, "Name File", "Save", 1);
        
        if (name == null) return;
        
        FileInputStream input = new FileInputStream("files/list_of_files.txt");
        BufferedReader configReader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
        
        boolean fileAlreadyExists = false;
        
        while(configReader.ready()) {
            String tmp = configReader.readLine();
            if ((name).equals(tmp))
                fileAlreadyExists = true;
        }
        
        if (!name.equals("") && !fileAlreadyExists) {
            
            BufferedWriter master = new BufferedWriter(new FileWriter("files/list_of_files.txt", true));
            master.append(name + "\n");
            master.close();
            
            PrintWriter writer = new PrintWriter(new File("files/" + name + ".txt"));
            
            String output = "";
            
            for (int i = 0; i < drawings.size(); i++)
                output += drawings.get(i) + "\n";
            
            if (!output.equals(""))
                output = output.substring(0,output.length()-1);
            
            writer.print(output);
            writer.close();
        }
        else
            JOptionPane.showMessageDialog(null, "Invalid File Name", "Error", 1);
    }
    
    public void load() throws FileNotFoundException, UnsupportedEncodingException, IOException, CloneNotSupportedException {
        
        FileInputStream input = new FileInputStream("files/list_of_files.txt");
        BufferedReader configReader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
        
        LinkedList<String> fileNames = new LinkedList<>();
        
        while(configReader.ready()) {
            String tmp = configReader.readLine();
            fileNames.add(tmp);
        }
        
        input.close();
        configReader.close();
        
        if (fileNames.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No saved files found", "Error", 1);
            return;
        }
        
        String name = (String)JOptionPane.showInputDialog(null, "Select a File", "Load Drawing",
                        JOptionPane.INFORMATION_MESSAGE,
                        null, fileNames.toArray(), fileNames.toArray()[0]);
        
        if (name != null) {
            
            undo.push(this.duplicateScreen());
            
            drawings = new LinkedList<>();
            
            input = new FileInputStream("files/" + name + ".txt");
            configReader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            
            while(configReader.ready()) {
                String tmp = configReader.readLine();
                
                String[] drawing = tmp.split(":"); 
                String[] coordinates = null;
                
                switch(drawing[0].charAt(0)) {
                    
                    case 'f':
                        coordinates = drawing[1].split(",");
                        d = new FreeHandDrawing(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), 
                                Integer.parseInt(coordinates[2]), Integer.parseInt(coordinates[3]), 
                                new Color(Integer.parseInt(coordinates[4].split("=")[1]),Integer.parseInt(coordinates[5].split("=")[1]),Integer.parseInt(coordinates[6].split("=")[1])));
                        
                        FreeHandDrawing h = (FreeHandDrawing) d;
                        
                        for (int i = 2; i < drawing.length; i++) {
                            coordinates = drawing[i].split(",");
                            h.addNewLine(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), 
                                Integer.parseInt(coordinates[2]), Integer.parseInt(coordinates[3]));
                        }
                        drawings.add(d);
                        d = null;
                        break;    
                    case 'r':
                        coordinates = drawing[1].split(",");
                        d = new RectangleDrawing(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), 
                                Integer.parseInt(coordinates[2]), Integer.parseInt(coordinates[3]), 
                                new Color(Integer.parseInt(coordinates[4].split("=")[1]),Integer.parseInt(coordinates[5].split("=")[1]),Integer.parseInt(coordinates[6].split("=")[1])), true);
                        drawings.add(d);
                        d = null;
                        break;
                    case 's':
                        coordinates = drawing[1].split(",");
                        d = new SquareDrawing(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), 
                                Integer.parseInt(coordinates[2]), Integer.parseInt(coordinates[3]), 
                                new Color(Integer.parseInt(coordinates[4].split("=")[1]),Integer.parseInt(coordinates[5].split("=")[1]),Integer.parseInt(coordinates[6].split("=")[1])), true);
                        drawings.add(d);
                        d = null;
                        break;
                    case 'l':
                        coordinates = drawing[1].split(",");
                        d = new LineDrawing(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), 
                                Integer.parseInt(coordinates[2]), Integer.parseInt(coordinates[3]), 
                                new Color(Integer.parseInt(coordinates[4].split("=")[1]),Integer.parseInt(coordinates[5].split("=")[1]),Integer.parseInt(coordinates[6].split("=")[1])));
                        drawings.add(d);
                        d = null;
                        break;
                    case 'o':
                        coordinates = drawing[1].split(",");
                        d = new OvalDrawing(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), 
                                Integer.parseInt(coordinates[2]), Integer.parseInt(coordinates[3]), 
                                new Color(Integer.parseInt(coordinates[4].split("=")[1]),Integer.parseInt(coordinates[5].split("=")[1]),Integer.parseInt(coordinates[6].split("=")[1])), true);
                        drawings.add(d);
                        d = null;
                        break;
                    case 'c':
                        coordinates = drawing[1].split(",");
                        d = new CircleDrawing(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), 
                                Integer.parseInt(coordinates[2]), Integer.parseInt(coordinates[3]), 
                                new Color(Integer.parseInt(coordinates[4].split("=")[1]),Integer.parseInt(coordinates[5].split("=")[1]),Integer.parseInt(coordinates[6].split("=")[1])), true);
                        drawings.add(d);
                        d = null;
                        break;

                }
            }
        }
        
    }
}
