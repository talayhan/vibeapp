package net.talayhan.android.vibeproject.Model;

/**
 * Created by root on 4/30/15.
 */
public class Coordinate {

    private int x;
    private int y;
    private String frame;

    public Coordinate() {
    }

    public Coordinate(int x, int y, String frame) {
        this.x = x;
        this.y = y;
        this.frame = frame;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getFrame() {
        return frame;
    }
}
