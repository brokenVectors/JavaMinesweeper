package Logic;

public class Mine {
    public final int x;
    public final int y;
    public Mine(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public String toString() {
        return String.format("(x:%s, y:%s)", this.x, this.y);
    }
}
