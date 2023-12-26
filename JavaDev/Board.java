package JavaDev;

import java.awt.Color; //Abstract Window Toolkit
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener { // panel is similar to div in CSS it divides the frame to small parts.
    private int dots;
    
    private Image apple;
    private Image head;
    private Image dot;

    private final int ALL_DOTS = 900;
    private final int DOT_SIZE = 10;
    private final int RANDOM_POS = 29;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    private int apple_x;
    private int apple_y;

    private Timer timer;

    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];

    private boolean inGame = true;

    Board()
    {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(300,300));
        loadImages();
        initGame();
    }

    public void loadImages()
    {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("JavaDev/icons/apple.png"));
        apple = i1.getImage();
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("JavaDev/icons/head.png"));
        head = i3.getImage();
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("JavaDev/icons/dot.png"));
        dot = i2.getImage();
    }

    public void initGame()
    {
        dots = 3;

        for(int i = 0; i< dots; i++)
        {
            y[i] = 50;
            x[i] = 50 - i*DOT_SIZE;
        }
        locateApple();

        timer = new Timer(140, this);
        timer.start();
    }

    private void locateApple() {
        int r = (int) (Math.random()*RANDOM_POS); // we are multiplying because random() give in (0,1);
        apple_x = r*DOT_SIZE;

        r = (int) (Math.random()*RANDOM_POS);
        apple_y = r * DOT_SIZE;  

    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
    if(inGame)
    {
        g.drawImage(apple, apple_x, apple_y, this);

        for(int i = 0; i < dots; i++)
        {
            if(i == 0)
            {
                g.drawImage(head, x[i], y[i], this);
            }
            else
            {
                g.drawImage(dot, x[i], y[i], this);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }
    else
    {
        gameOver(g);
        score(g);
    } 
    }

    public void gameOver(Graphics g)
    {
        String msg = "GameOver!";
        Font font = new Font("SAN_SERIF" , Font.BOLD, 14);
        FontMetrics metrics = getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (300 - metrics.stringWidth(msg)) / 2, 300/2);
        
    }
        public void score(Graphics g)
    {
        int score = dots - 3;
        //String score_String  = String.valueOf(score);
        String score_String = " Your Score - " + score;
        Font font = new Font("SAN_SERIF" , Font.BOLD, 14);
        //FontMetrics metrics = getFontMetrics(font);
        g.setColor(Color.GREEN);
        g.setFont(font);
        g.drawString(score_String, 105, 170);
    }

    public void checkApple()
    {
        if((x[0] == apple_x) && (y[0] == apple_y))
        {
            dots++;
            locateApple();
        }
    }

    public void checkCollision()
    {
        // agar snake kudh se takra jaata hai;
        for(int i = dots; i > 0; i--)
        {
            if((i>4) && (x[0] == x[i]) && (y[0] == y[i]))
            {
                inGame = false;
            }
        }

        // agar snake boundaries se takara jaata hai;

        if(x[0] >=300)
        {
            inGame = false;
        }
        if(y[0] >=300)
        {
            inGame = false;
        }
        if(x[0] < 0)
        {
            inGame = false;
        }        
        if(y[0] < 0)
        {
            inGame = false;
        }

        if(!inGame)
        {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
       if(inGame)
       {
        checkApple();
        checkCollision();
        move();
       }
       repaint();

    }

    private void move() {
        for(int i = dots; i>0; i--)
        {
            x[i] = x[i-1];
            y[i] = y[i-1]; // peeche waale ko move kar rahe hai
        }

        if(leftDirection)
        {
            x[0] -=DOT_SIZE;
        }
        if(rightDirection)
        {
            x[0] +=DOT_SIZE;
        }
        if(upDirection)
        {
            y[0] -=DOT_SIZE;
        }
        if(downDirection)
        {
            y[0] +=DOT_SIZE;
        }


         // head ko move kar rahe hai

    }

    public class TAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            int key = e.getKeyCode();

            if(key == KeyEvent.VK_LEFT && (!rightDirection))
            {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_RIGHT && (!leftDirection))
            {
                rightDirection = true;
                //leftDirection = true;
                upDirection = false;
                downDirection = false;

            }
            if(key == KeyEvent.VK_UP && (!downDirection))
            {
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
                
            }
            if(key == KeyEvent.VK_DOWN && (!upDirection))
            {
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
        }
    }
}
