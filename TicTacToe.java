import java.util.Scanner;

// tic-tac-toe

public class TicTacToe{
    public static void main(String[] args){

        char[][] grid = new char[3][3];

        for (int moves = 0; moves < 100000; moves += 2) {
            displayGrid(grid);

            // player move
            readyPlayerOne(grid);
            if(winCondition(grid)){
                System.out.println("Player 1 wins");
                break;
            }

            // tie check
            if(moves >= 8){
                System.out.println("It's a tie");
                break;
            }

            // computer move
            comMove(grid, moves);
            if(winCondition(grid)){
                System.out.println("Computer wins");
                break;
            }
        }
        

        displayGrid(grid);
    } 

    // Display the grid
	public static void displayGrid(char[][] grid){

        final String INSERTNEWLINE = "\n   ";
        final String VERTICALSEPERATOR = "| ";
        final String HORIZONTALSEPARATOR = "  ----------";
        final String SEPARATORTWOCHAR = "| ";
        final String SEPARATORTHREECHAR = "|  ";
        String output;

		// Display the col numbers
		System.out.print(INSERTNEWLINE);
		for(int i = 0; i < grid[0].length; i++){
			System.out.printf(" %d ", i);
		}
		System.out.println();
		
		for(int i = 0; i < grid.length; i++){
			System.out.println(HORIZONTALSEPARATOR);
			
			// Display the row number
			System.out.print(i + " ");
			
			// Display the squares
			for(int j = 0; j < grid[i].length; j++)
			{	
				output = (grid[i][j] == 'x' || grid[i][j] == 'o')? (SEPARATORTWOCHAR + grid[i][j]): SEPARATORTHREECHAR;
				System.out.print(output);
			}
			System.out.println(VERTICALSEPERATOR);
		}
		
		System.out.println(HORIZONTALSEPARATOR);
	}

    public static char[][] readyPlayerOne(char[][] grid){
        Scanner input = new Scanner(System.in);
        int row;
        int col;

        do{
            System.out.println("(Player 1) Enter the row where you would like to mark.");
            row = input.nextInt();

            System.out.println("(Player 1) Enter the col where you would like to mark.");
            col = input.nextInt();
            if((row >= 0 && row < 3) || (col >= 0 && col < 3)){

                // prevents invalid moves breaking
                if (grid[row][col] == '\u0000') {
                    grid[row][col] = 'x';
                    break;
                }
            }
            System.out.println("(Player 1) INVALID MOVE");

        }while(true);


        return grid;
    }

    public static boolean winCondition(char[][] grid){
        char topLeft = grid[0][0];
        char topRight = grid[0][2];
        boolean majorDiagonal = true;
        boolean minorDiagonal = true;

        for (int i = 0; i < grid.length; i++) {

            // if rows are the same and (contain a mark)
            if(grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2] && grid[i][1] != '\u0000'){
                return true;
            }

            // if cols are the same and (contain a mark)
            if(grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i] && grid[1][i] != '\u0000'){
                return true;
            }

            // if everything along the major Diag isn't the same
            if(grid[i][i] != topLeft || grid[i][i] == '\u0000'){
                majorDiagonal = false;
            }

            // if everything along the minor Diag isn't the same
            if(grid[i][2-i] != topRight || grid[i][2-i] == '\u0000'){
                minorDiagonal = false;
            }
        }

        return (majorDiagonal || minorDiagonal);
    }

    public static char[][] comMove(char[][] grid, int moves){

        // and yes this is basically a useless method :kek: just using it as a shell for the name
        // COM looks for moves in this order
        // 1. A Winning move for the bot
        // 2. A defence against a Player win
        // 3. Center Control
        // 4. Control of a single corner
        // 5. Moves in a cardinal direction around the corner
        // 6. Random move

        lookForAWin(grid, moves);

        return grid;
    }

    public static char[][] lookForAWin(char[][] grid, int moves){

        // tries to find a winning move for the COM
        for (int i = 0; i < grid.length; i++) {

            // checks rows
            if (grid[i][0] == 'o' && grid[i][1] == 'o' && grid[i][2] == '\u0000') {
                grid[i][2] = 'o';
                return grid;
            }
            if (grid[i][0] == 'o' && grid[i][2] == 'o' && grid[i][1] == '\u0000') {
                grid[i][1] = 'o';
                return grid;
            }
            if (grid[i][1] == 'o' && grid[i][2] == 'o' && grid[i][0] == '\u0000') {
                grid[i][0] = 'o';
                return grid;
            }

            // checks cols
            if (grid[0][i] == 'o' && grid[1][i] == 'o' && grid[2][i] == '\u0000') {
                grid[2][i] = 'o';
                return grid;
            }
            if (grid[0][i] == 'o' && grid[2][i] == 'o' && grid[1][i] == '\u0000') {
                grid[1][i] = 'o';
                return grid;
            }
            if (grid[1][i] == 'o' && grid[2][i] == 'o' && grid[0][i] == '\u0000') {
                grid[0][i] = 'o';
                return grid;
            }

            // Diag checks 
            // makes sure it doesn't ArrayOutofIndex
            if(i <= 1){

                // Major check i -> 0 [2][2] i -> 1 [0][0]
                if(grid[i][i] == 'o' && grid[i+1][i+1] == 'o' && grid[2*(1-i)][2*(1-i)] == '\u0000'){
                    grid[2*(1-i)][2*(1-i)] = 'o';
                    return grid;
                }

                // Minor check i -> 0 [2][0], i -> 1 [0][2]
                if(grid[i][2-i] == 'o' && grid[i+1][1-i] == 'o' && grid[2*(1-i)][2*(i)] == '\u0000'){
                    grid[2*(1-i)][2*(i)] = 'o';
                    return grid;
                }
            }
        }

        // remaining diags
        if ((grid[2][0] == 'o' && grid[0][2] == 'o' ||
            grid[0][0] == 'o' && grid[2][2] == 'o') && grid[1][1] == '\u0000') {
            grid[1][1] = 'o';
            return grid;
        }

        // no winning move? DEFEND!
        grid = IronDefence(grid, moves);
        return grid;
    }

    public static char[][] IronDefence(char[][] grid, int moves){

        // Defends flawlessly against Player wins
        for (int i = 0; i < grid.length; i++) {

            // checks rows
            if (grid[i][0] == 'x' && grid[i][1] == 'x' && grid[i][2] == '\u0000') {
                grid[i][2] = 'o';
                return grid;
            }
            if (grid[i][0] == 'x' && grid[i][2] == 'x' && grid[i][1] == '\u0000') {
                grid[i][1] = 'o';
                return grid;
            }
            if (grid[i][1] == 'x' && grid[i][2] == 'x' && grid[i][0] == '\u0000') {
                grid[i][0] = 'o';
                return grid;
            }

            // checks cols
            if (grid[0][i] == 'x' && grid[1][i] == 'x' && grid[2][i] == '\u0000') {
                grid[2][i] = 'o';
                return grid;
            }
            if (grid[0][i] == 'x' && grid[2][i] == 'x' && grid[1][i] == '\u0000') {
                grid[1][i] = 'o';
                return grid;
            }
            if (grid[1][i] == 'x' && grid[2][i] == 'x' && grid[0][i] == '\u0000') {
                grid[0][i] = 'o';
                return grid;
            }

            // Diag checks 
            // makes sure it doesn't ArrayOutofIndex
            if(i <= 1){

                // Major check i -> 0 [2][2] i -> 1 [0][0]
                if(grid[i][i] == 'x' && grid[i+1][i+1] == 'x' && grid[2*(1-i)][2*(1-i)] == '\u0000'){
                    grid[2*(1-i)][2*(1-i)] = 'o';
                    return grid;
                }

                // Minor check i -> 0 [2][0], i -> 1 [0][2]
                if(grid[i][2-i] == 'x' && grid[i+1][1-i] == 'x' && grid[2*(1-i)][2*(i)] == '\u0000'){
                    grid[2*(1-i)][2*(i)] = 'o';
                    return grid;
                }
            }
        }
        // remaining diags
        if ((grid[2][0] == 'x' && grid[0][2] == 'x' ||
            grid[0][0] == 'x' && grid[2][2] == 'x') && grid[1][1] == '\u0000') {
            grid[1][1] = 'o';
            return grid;
        }

        // no Defence needed? Take the Center!
        grid = centerControl(grid, moves);
        
        return grid;
    }
    
    public static char[][] centerControl(char[][] grid, int moves){

        // because centerControl is superior.. at least thats what chess taught me
        if (grid[1][1] == '\u0000') {
            grid[1][1] = 'o';
            return grid;
        }

        // no Center not open? Take a corner!
        grid = conquerTheCorners(grid, moves);
        return grid;
    }

    public static char[][] conquerTheCorners(char[][] grid, int moves){
        // next best thing to take control of

        // only marks one corner
        boolean isACornerConquered = false;

        if (grid[0][0] == 'o' ||
            grid[0][2] == 'o' ||
            grid[2][0] == 'o' ||
            grid[2][2] == 'o') {
            
            isACornerConquered = true;
        }

        if (!isACornerConquered && grid[0][0] == '\u0000'){
            grid[0][0] = 'o';
            return grid;
        }
        if (!isACornerConquered && grid[0][2] == '\u0000'){
            grid[0][2] = 'o';
            return grid;
        }
        if (!isACornerConquered && grid[2][0] == '\u0000'){
            grid[2][0] = 'o';
            return grid;
        }
        if (!isACornerConquered && grid[2][2] == '\u0000'){
            grid[2][2] = 'o';
            return grid;
        }

        grid = markInCardinalDirection(grid, moves);
        return grid;
    }

    public static char[][] markInCardinalDirection(char[][] grid, int moves){

        // once a corner is controlled, try to build pairs
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid.length; col++) {
                if (grid[row][col]=='o'){

                    // can go up
                    if(row-1 >= 0 && grid[row-1][col] == '\u0000'){
                        grid[row-1][col] = 'o';
                        return grid;
                    }

                    // can go right
                    if(col + 1 < 3 && grid[row][col+1] == '\u0000'){
                        grid[row][col+1] = 'o';
                        return grid;
                    }

                    // can go down
                    if(row + 1 < 3 && grid[row+1][col] == '\u0000'){
                        grid[row+1][col] = 'o';
                        return grid;
                    }

                    // can go left
                    if(col - 1 >= 0 && grid[row][col-1] == '\u0000'){
                        grid[row][col-1] = 'o';
                        return grid;
                    }
                }
            }
        }

        // no more reasonable moves? Random time
        grid = pickRandom(grid, moves);
        return grid;
    }

    public static char[][] pickRandom(char[][] grid, int moves){
        int randRow;
        int randCol;

        while(moves != 9){
            randRow = (int)(Math.random() * 3);
            randCol = (int)(Math.random() * 3);

            if(grid[randRow][randCol] == '\u0000' ){
                grid[randRow][randCol] = 'o';
                break;
            }
        }

        return grid;
    }
}