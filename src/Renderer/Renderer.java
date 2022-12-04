package Renderer;

import Logic.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Renderer extends JFrame {
    private BufferedImage spritesheet;
    private final Image[] sprites;
    public RendererGameAdapter adapter;
    public final int yOffset = 25;
    private final int width;
    private final int height;
    private final int rows;
    private final int cols;

    public Renderer(int width, int height, int rows, int cols) {
        // TODO: implement interactions between game and renderer
        super("Minesweeper!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.cols = cols;
        this.adapter = new DefaultRendererGameAdapter(new Game(rows, cols, 10));
        this.addMouseListener(new RendererMouseListener(this, width, height, rows, cols));
        this.addKeyListener(new RendererKeyListener(this));
        this.setSize(new Dimension(width, height));
        this.sprites = new Image[16];
        try {
            this.spritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResource("/minesweeper_sprites.png")));
        }
        catch(IOException ignored) {
            System.out.println("could not load image");
        }
        // TODO: move spritesheet code out of here!!!
        int spritesheetRows = 2;
        int spriteSheetCols = 8;
        int spriteSize = 16;
        for(int i = 0; i < spritesheetRows; i++) {
            for(int j = 0; j < spriteSheetCols; j++) {
                sprites[i*8+j] = this.spritesheet.getSubimage(
                        j* spriteSize,
                        i * spriteSize,
                        spriteSize,
                        spriteSize)
                        .getScaledInstance(width/this.cols, (height-this.yOffset)/this.rows, Image.SCALE_DEFAULT);
            }
        }
        this.repaint();
    }
    public void paint(Graphics g){
        for(int y = 0; y < this.rows; y++) {
            for(int x = 0; x < this.cols; x++) {
                Tile tile = this.adapter.getTile(x,y);
                Image img;
                switch(tile) {
                    case EMPTY:
                        img = this.sprites[0];
                        break;
                    case ZERO:
                        img = this.sprites[1];
                        break;
                    case FLAG:
                        img = this.sprites[2];
                        break;
                    case MINE:
                        img = this.sprites[5];
                        break;
                    case ONE:
                        img = this.sprites[8];
                        break;
                    case TWO:
                        img = this.sprites[9];
                        break;
                    case THREE:
                        img = this.sprites[10];
                        break;
                    case FOUR:
                        img = this.sprites[11];
                        break;
                    case FIVE:
                        img = this.sprites[12];
                        break;
                    case SIX:
                        img = this.sprites[13];
                        break;
                    case SEVEN:
                        img = this.sprites[14];
                        break;
                    case EIGHT:
                        img = this.sprites[15];
                        break;
                    default:
                        img = this.sprites[3];
                        break;
                }
                g.drawImage(img, x*this.width/this.rows, yOffset+y*(this.height-yOffset)/this.cols, null);
            }
        }

    }
}
