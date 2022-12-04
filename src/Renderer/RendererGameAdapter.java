package Renderer;

public interface RendererGameAdapter {
    public boolean reveal(int x, int y);
    public boolean toggleFlag(int x, int y);
    public Tile getTile(int x, int y);
}
