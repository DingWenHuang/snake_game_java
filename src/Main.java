import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

class Fruit {
    int x;
    int y;
    ImageIcon img;

    public Fruit() {
        this.x = (int)Math.floor(Math.random() * Main.COLUMN) * Main.CELL_SIZE;
        this.y = (int)Math.floor(Math.random() * Main.ROW) * Main.CELL_SIZE;
        img = new ImageIcon("fruit.png");
    }

    public void drawFruit(Graphics g) {
        img.paintIcon(null, g, this.x, this.y);
    }

}

class Node {
    int x;
    int y;

    public Node (int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Snake {
    public ArrayList<Node> getSnakeBody() {
        return snakeBody;
    }

    ArrayList<Node> snakeBody = new ArrayList<>();
    public Snake() {
        snakeBody.add(new Node(80, 0));
        snakeBody.add(new Node(60, 0));
        snakeBody.add(new Node(40, 0));
        snakeBody.add(new Node(20, 0));
    }

    public void drawSnake(Graphics g) {
        for (Node n : snakeBody) {
            if (n == snakeBody.get(0)) {
                g.setColor(Color.MAGENTA);
            } else {
                g.setColor(Color.GREEN);
            }
            g.fillOval(n.x, n.y, Main.CELL_SIZE, Main.CELL_SIZE);
        }
    }
}

public class Main extends JPanel implements KeyListener {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    public static final int CELL_SIZE = 20;
    public static final int COLUMN = WIDTH / CELL_SIZE;
    public static final int ROW = HEIGHT / CELL_SIZE;

    Snake snake;
    Fruit fruit;
    Timer timer;
    String direction;

    public Main() {
        snake = new Snake();
        fruit = new Fruit();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0, 100);
        direction = "RIGHT";
        addKeyListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        fruit.drawFruit(g);
        snake.drawSnake(g);

        int snakeX = snake.getSnakeBody().get(0).x;
        int snakeY = snake.getSnakeBody().get(0).y;

        if (direction.equals("RIGHT")) {
            snakeX += CELL_SIZE;
        } else if (direction.equals("LEFT")) {
            snakeX -= CELL_SIZE;
        } else if (direction.equals("UP")) {
            snakeY -= CELL_SIZE;
        } else if (direction.equals("DOWN")) {
            snakeY += CELL_SIZE;
        }

        Node newHead = new Node(snakeX, snakeY);

        snake.getSnakeBody().add(0, newHead);
        snake.getSnakeBody().remove(snake.getSnakeBody().size() - 1);

        requestFocusInWindow();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Snake Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new Main());
        window.pack();
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 37 && !direction.equals("RIGHT")) {
            direction = "LEFT";
        } else if (e.getKeyCode() == 38 && !direction.equals("DOWN")) {
            direction = "UP";
        } else if (e.getKeyCode() == 39 && !direction.equals("LEFT")) {
            direction = "RIGHT";
        } else if (e.getKeyCode() == 40 && !direction.equals("UP")) {
            direction = "DOWN";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}