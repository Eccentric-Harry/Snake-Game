package JavaDev;

import javax.swing.*;

public class SnakeGame extends JFrame {

    SnakeGame()
    {
        super("Snake Game");// calls the parent class also sets title for the frame. it must be the first statement of the constructor.
        add(new Board());
        pack(); // refresh the frame so as to implement all the changes that are made.
        //setSize(400,400);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true); // initiall, at thtop left corner(0,0)

    }       
    
    public static void main(String[] args)
    {
        new SnakeGame().setVisible(true); // generally, we store object in a variabale, but here we don't need any variable.
        // class ka object banate hi class ka constructor call hota hai?
    }
}
