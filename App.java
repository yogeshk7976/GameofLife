final class GameOfLife {

    // The value representing a dead cell
    public final static int DEAD    = 0x00;

    // The value representing a live cell
    public final static int LIVE    = 0x01;


    public final static void main(String[] args) {


    GameOfLife gof = new GameOfLife();
    gof.test(5);
    }

    private void test(int nrIterations) {

    // the starting playing board with life and dead cells
    int[][] board = {{DEAD, DEAD, DEAD, DEAD, DEAD},
                        {DEAD, DEAD, DEAD, LIVE, DEAD},
                        {DEAD, DEAD, LIVE, LIVE, DEAD},
                        {DEAD, DEAD, DEAD, LIVE, DEAD},
                        {DEAD, DEAD, DEAD, DEAD, DEAD}}; 

    System.out.println("Conway's GameOfLife");
    printBoard(board);

    for (int i = 0 ; i < nrIterations ; i++) {
        System.out.println();
        board = getNextBoard(board);
        printBoard(board);
    }
    }

    private void printBoard(int[][] board) {

    for (int i = 0, e = board.length ; i < e ; i++) {

        for (int j = 0, f = board[i].length ; j < f ; j++) {
            System.out.print(Integer.toString(board[i][j]) + ",");
        } 
        System.out.println();
    }
    }


    public int[][] getNextBoard(int[][] board) {

    if (board.length == 0 || board[0].length == 0) {
        throw new IllegalArgumentException("Board must have a positive amount of rows and/or columns");
    }

    int nrRows = board.length;
    int nrCols = board[0].length;

    // temporary board to store new values
    int[][] buf = new int[nrRows][nrCols];

    for (int row = 0 ; row < nrRows ; row++) {

        for (int col = 0 ; col < nrCols ; col++) {
            buf[row][col] = getNewCellState(board[row][col], getLiveNeighbours(row, col, board));
        }
    }   
    return buf;
    }


    private int getLiveNeighbours(int cellRow, int cellCol, int[][] board) {

    int liveNeighbours = 0;
    int rowEnd = Math.min(board.length , cellRow + 2);
    int colEnd = Math.min(board[0].length, cellCol + 2);

    for (int row = Math.max(0, cellRow - 1) ; row < rowEnd ; row++) {
        
        for (int col = Math.max(0, cellCol - 1) ; col < colEnd ; col++) {
            
            // make sure to exclude the cell itself from calculation
            if ((row != cellRow || col != cellCol) && board[row][col] == LIVE) {
                liveNeighbours++;
            }
        }
    }
    return liveNeighbours;
    }



    private int getNewCellState(int curState, int liveNeighbours) {

    int newState = curState;

    switch (curState) {
    case LIVE:

        // Any live cell with fewer than two 
        // live neighbours dies
        if (liveNeighbours < 2) {
            newState = DEAD;
        }

        // Any live cell with two or three live   
        // neighbours lives on to the next generation.
        if (liveNeighbours == 2 || liveNeighbours == 3) {
            newState = LIVE;
        }

        // Any live cell with more than three live neighbours
        // dies, as if by overcrowding.
        if (liveNeighbours > 3) {
            newState = DEAD;
        }
        break;

    case DEAD:
        // Any dead cell with exactly three live neighbours becomes a 
        // live cell, as if by reproduction.
        if (liveNeighbours == 3) {
            newState = LIVE;
        }
        break;

    default:
        throw new IllegalArgumentException("State of cell must be either LIVE or DEAD");
    }			
    return newState;
    }
    }
