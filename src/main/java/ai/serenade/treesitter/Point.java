package ai.serenade.treesitter;

public class Point{

    private static final Point ORIGIN = new Point(0, 0);
    private int row;
    private int column;

    public Point(int row, int column){
        this.row = row;
        this.column = column;
    }

    public static Point getOrigin(){
        return ORIGIN;
    }


    @Override
    public String toString(){
        return this.row + " : " + this.column;
    }

    public int getRow() {
        return this.row;
    }
    public int getColumn(){
        return this.column;
    }
}
