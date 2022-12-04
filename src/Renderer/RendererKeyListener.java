package Renderer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RendererKeyListener implements KeyListener {
    private final Renderer renderer;
    public RendererKeyListener(Renderer renderer) {
        this.renderer = renderer;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            if(renderer.adapter.isGameOver()) {
                renderer.adapter.reset();
                renderer.repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
