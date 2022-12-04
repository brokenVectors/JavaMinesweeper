package Logic;

import java.util.ArrayList;
import java.util.Optional;

public class Game {
    private final Square[][] grid;
    private final ArrayList<Mine> mines;
    private final int width;
    private final int height;
    public boolean isGameOver;
    public Game(int width, int height, int mineQuantity) {
        this.width = width;
        this.height = height;
        this.grid = new Square[width][height];
        this.mines = new ArrayList<>();
        this.reset(mineQuantity);
    }

    private void initializeMines(int quantity) {
        // Initializes a given quantity of mines.
        mines.clear();
        for(int i = 0; i < quantity; i++) {
            // Subtract by 1 because arrays are zero-indexed.
            int x = (int)(Math.random() * (this.width-1));
            int y = (int)(Math.random() * (this.height-1));
            if(!this.isMine(x,y)) {
                // If a mine isn't already present at this square...
                this.mines.add(new Mine(x,y));
            }
        }
    }
    public void reset(int mineQuantity) {
        this.initializeGrid();
        this.initializeMines(mineQuantity);
        this.isGameOver = false;
    }
    public void reset() {
        this.reset(10);
    }
    public boolean isMine(int x, int y) {
        Optional<Mine> optional = this.mines.stream()
                .filter(mine -> mine.x == x && mine.y == y)
                .findFirst();
        return optional.isPresent();
    }
    public int getAdjacentMineCount(int x, int y) {
        int count = 0;
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                // This condition is to ensure only adjacent squares are taken into account, not the input square.
                if(!(i == 0 && j == 0)) {
                    if(isMine(x+i, y+j)) count++;
                }
            }
        }
        return count;
    }
    private boolean detectGameOver() {
        boolean friendlySquaresRemaining = false;
        for(int y = 0; y < this.height; y++) {
            for(int x = 0; x < this.width; x++) {
                if(this.grid[y][x] == Square.REVEALED && this.isMine(x,y)) {
                    // If a square is revealed, and it's a mine, the game is over and lost.
                    gameOver(false);
                    return true;
                }
                if(this.grid[y][x] != Square.REVEALED && !this.isMine(x,y)) {
                    // If square isn't revealed and square is not a mine...
                    friendlySquaresRemaining = true;
                }
            }
        }
        if(!friendlySquaresRemaining) {
            // If no friendly squares are remaining and no mines were stepped on, the game is over and won.
            System.out.println(this.toString(true));
            gameOver(true);
        }
        return !friendlySquaresRemaining;
    }
    private void initializeGrid() {
        for(int y = 0; y < this.height; y++) {
            for(int x = 0; x < this.width; x++) {
                this.grid[y][x] = Square.HIDDEN;
            }
        }
    }
    private boolean inBounds(int x, int y) {
        return x > -1 && x < this.width && y > -1 && y < this.height;
    }
    public boolean reveal(int x, int y) {
        // Reveals a square at a given coordinate. Returns false if revelation could not be done.
        if(this.isGameOver) return false;
        if(this.inBounds(x,y)) {
            boolean canReveal = this.grid[y][x] == Square.HIDDEN;
            if(canReveal) {
                this.grid[y][x] = Square.REVEALED;
                this.detectGameOver();
            }
            if(this.getAdjacentMineCount(x,y) == 0 && !this.isMine(x,y)) {
                for(int i = -1; i < 2; i++) {
                    for(int j = -1; j < 2; j++) {
                        // This condition is to ensure only adjacent squares are taken into account, not the input square.
                        if(!(i == 0 && j == 0) && this.inBounds(x+i, y+j) && this.grid[y+j][x+i] != Square.REVEALED && !this.isMine(x+i, y+j)) {
                            this.reveal(x+i, y+j);
                        }
                    }
                }
            }

            return true;
        }
        return false;
    }
    public boolean toggleFlag(int x, int y) {
        if(this.isGameOver) return false;
        if(this.inBounds(x,y)) {
            if(this.grid[y][x] == Square.HIDDEN) {
                this.grid[y][x] = Square.FLAGGED;
                return true;
            }
            else if(this.grid[y][x] == Square.FLAGGED) {
                this.grid[y][x] = Square.HIDDEN;
                return true;
            }
        }
        return false;
    }
    private void gameOver(boolean won) {
        this.isGameOver = true;
        if(won) System.out.println("You won!");
        else {
            System.out.println("You lost!");
            //System.out.println(this.toString(true));
        }
    }
    public String toString(boolean showMines) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int y = 0; y < this.height; y++) {
            for(int x = 0; x < this.width; x++) {
                if(showMines && this.isMine(x,y)) {
                    stringBuilder.append("# ");
                }
                else {
                    switch(this.grid[y][x]) {
                        case HIDDEN:
                            stringBuilder.append("_ ");
                            break;
                        case REVEALED:
                            stringBuilder.append(this.getAdjacentMineCount(x, y)).append(" ");
                            break;
                        case FLAGGED:
                            stringBuilder.append("+ ");
                            break;
                    }
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public Square getSquare(int x, int y) {
        return this.grid[y][x];
    }
}
