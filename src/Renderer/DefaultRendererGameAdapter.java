package Renderer;
import Logic.*;
public class DefaultRendererGameAdapter implements RendererGameAdapter {
    public Game game;
    public DefaultRendererGameAdapter(Game game) {
        this.game = game;
    }

    @Override
    public boolean reveal(int x, int y) {
        return this.game.reveal(x,y);
    }

    @Override
    public boolean toggleFlag(int x, int y) {
        return this.game.toggleFlag(x,y);
    }

    @Override
    public boolean isGameOver() {
        return this.game.isGameOver;
    }

    @Override
    public void reset() {
        this.game.reset();
    }

    @Override
    public Tile getTile(int x, int y) {
        Square square = this.game.getSquare(x,y);
        if(square == Square.HIDDEN) return Tile.EMPTY;
        else if(square == Square.FLAGGED) return Tile.FLAG;
        else {
            if(this.game.isMine(x,y)) return Tile.MINE;
            switch(this.game.getAdjacentMineCount(x,y)) {
                case 0:
                    return Tile.ZERO;
                case 1:
                    return Tile.ONE;
                case 2:
                    return Tile.TWO;
                case 3:
                    return Tile.THREE;
                case 4:
                    return Tile.FOUR;
                case 5:
                    return Tile.FIVE;
                case 6:
                    return Tile.SIX;
                case 7:
                    return Tile.SEVEN;
                case 8:
                    return Tile.EIGHT;
                default:
                    return Tile.EMPTY;
            }
        }
    }

    public String toString() {
        return this.game.toString(true);
    }
}
