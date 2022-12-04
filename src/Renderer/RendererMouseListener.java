package Renderer;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RendererMouseListener implements MouseListener {
    private final int width;
    private final int height;
    private final int rows;
    private final int cols;
    private final Renderer renderer;
    public RendererMouseListener(Renderer renderer, int width, int height, int rows, int cols) {
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.cols = cols;
        this.renderer = renderer;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouse_x = e.getX();
        int mouse_y = e.getY();
        int game_x = mouse_x / (this.width/this.cols);
        int game_y = (mouse_y - this.renderer.yOffset) / (this.height/this.rows);
        if(SwingUtilities.isLeftMouseButton(e))
            this.renderer.adapter.reveal(game_x, game_y);
        else
            this.renderer.adapter.toggleFlag(game_x, game_y);
        //System.out.println(this.renderer.adapter.toString());
        this.renderer.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
