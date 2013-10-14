package snakegame;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class SnakeWorld extends Frame implements KeyListener, Runnable,WindowListener,AudioClip {

    private int apple_x, apple_y, time_delay = 300;
    private final int B_WIDTH = 500, B_HEIGHT = 500;
    private boolean stop = true, draw_apple = true, go_left = false, go_right = false, go_up = false, go_down = false;
    AudioClip au;
    static int x[], y[], LENGTH;
    float speed;
    Random rand;
    Thread t;
    TextField txt;
    Image head,body,apple;
    ImageIcon h,b,a;
    public static void main(String args[]) {
        SnakeWorld game = new SnakeWorld();
    }

    public void check_collision() {
        for (int i = 1; i <= LENGTH; i++) {
            if (Math.abs(x[0] - x[i]) < 15 && Math.abs(y[0] - y[i]) < 15) {
                JOptionPane.showMessageDialog(this, "GameOver,Your score is: " + LENGTH);
                System.exit(0);
                stop = true;
                break;
            } else if (x[0] >= B_WIDTH || x[0] < 0 || y[0] >= B_HEIGHT || y[0] < 0) {
                JOptionPane.showMessageDialog(this, "GameOver,Your score is: " + LENGTH);
                System.exit(0);
                stop = true;
                break;
            }
        }
    }

    SnakeWorld() {
        rand = new Random();
        apple_x = rand.nextInt(100) + 250;
        apple_y = rand.nextInt(100) + 250;
        setTitle("TheVintageSnakeGame");
        setSize(B_WIDTH, B_HEIGHT);
        setVisible(true);
        x = new int[10000];
        y = new int[10000];
        stop = false;
        LENGTH = 5;
        x[0] = 200;
        y[0] = 200;
        for (int i = 1; i <=LENGTH; i++) {
            x[i] = 20+x[i-1];
            y[i] = y[i-1];
        }
        
        h = new ImageIcon(this.getClass().getResource("head1.png"));
        head = h.getImage();
        b = new ImageIcon(this.getClass().getResource("body1.png"));
        body = b.getImage();
        a = new ImageIcon(this.getClass().getResource("apple.png"));
        apple = a.getImage();
        
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        addWindowListener(this);
        txt = new TextField();
        txt.setText("Base score = 5");
        add(txt,BorderLayout.NORTH);
        this.validate();
        t = new Thread(this);
        t.start();
        
       // au = new AudioClip(this.getClass().getResource("devd.mp3")) 
       // au.play();
    }

    public void got_apple() {
        if (Math.abs(x[0] - apple_x) < 20 && Math.abs(y[0] - apple_y) < 20) {
            draw_apple = true;
            apple_x = rand.nextInt(400) + 50;
            apple_y = rand.nextInt(400) + 50;
            LENGTH++;
            if (time_delay >= 100) {
                time_delay -= 25;
            }
            speed = (float)20/time_delay;
            txt.setText("Your current score =" + LENGTH + "   Speed of snake = " + speed +" pixels per millisecond");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        stop = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            go_right = true;
            go_left = false;
            go_up = false;
            go_down = false;


        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            go_left = true;
            go_right = false;
            go_up = false;
            go_down = false;

        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            go_up = true;
            go_right = false;
            go_left = false;
            go_down = false;



        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            go_down = true;
            go_right = false;
            go_left = false;
            go_up = false;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void start() {
    }

    @Override
    public void run() {

        while (stop == false) {
            if (go_down == true || go_up == true || go_right == true || go_left == true) {
                for (int i = LENGTH; i > 0; i--) {
                    x[i] = x[i - 1];
                    y[i] = y[i - 1];
                }

                if (go_down == true) {
                    y[0] += 20;
                } else if (go_up == true) {
                    y[0] -= 20;
                } else if (go_left == true) {
                    x[0] -= 20;
                } else if (go_right == true) {
                    x[0] += 20;
                }

                repaint();

                got_apple();
                check_collision();
                try {
                    Thread.sleep(time_delay);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.white);
        if (draw_apple == true) {
            g.drawImage(apple, apple_x, apple_y,this);
        }

        g.drawImage(head, x[0], y[0],this);

        for (int i = 1; i <= LENGTH; i++) {
            g.drawImage(body, x[i], y[i],this);
        }

    }

    @Override
    public void windowOpened(WindowEvent we) {        
    }

    @Override
    public void windowClosing(WindowEvent we) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent we) {        
    }

    @Override
    public void windowIconified(WindowEvent we) {        
    }

    @Override
    public void windowDeiconified(WindowEvent we) {        
    }

    @Override
    public void windowActivated(WindowEvent we) {        
    }

    @Override
    public void windowDeactivated(WindowEvent we) {        
    }

    @Override
    public void play() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
