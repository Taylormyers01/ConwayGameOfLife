package com.zipcodeconway;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConwayGameOfLife {
    //private static final Logger logger =  Logger.getLogger("MyLog.log");
    static SimpleWindow displayWindow;
    int[][] matrix;
    int[][] nextMatrix;
    int dimension;

    public ConwayGameOfLife(Integer dimension) {
        displayWindow = new SimpleWindow(dimension);
        matrix = new int[dimension][dimension];
        matrix = createRandomStart(dimension);
        this.dimension = dimension;
        nextMatrix = new int[dimension][dimension];
     }

    public ConwayGameOfLife(Integer dimension, int[][] startmatrix) {
        displayWindow = new SimpleWindow(dimension);
        this.matrix = startmatrix;
        this.dimension = dimension;
        nextMatrix = new int[dimension][dimension];

    }

    public static void main(String[] args) {
        //logger.setLevel(Level.OFF);

        int[][] hold = {
                {0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0}};
        ConwayGameOfLife sim = new ConwayGameOfLife(50);
        sim.simulate(50);
        //logger.info("outcome: " + outcome);
//        int[][] endingWorld = sim.simulate(50);
        //displayWindow.display(sim.createRandomStart(10), 1);
    }

    // Contains the logic for the starting scenario.
    // Which cells are alive or dead in generation 0.
    // allocates and returns the starting matrix of size 'dimension'
    private int[][] createRandomStart(Integer dimension) {
        for(int x = 0; x < dimension; x++){
            for(int y = 0; y < dimension; y++){
                matrix[x][y] = (int)Math.round(Math.random());
            }
        }
        return matrix;
    }

    public int[][] simulate(Integer maxGenerations) {
        for(int i = 0; i <= maxGenerations; i++){
            displayWindow.display(matrix, i);
            displayWindow.sleep(125);
            for(int x = 0; x < dimension; x++){
                for(int y = 0; y < dimension; y++){
                    nextMatrix[x][y] = isAlive(x, y, matrix);
                }
            }
            copyAndZeroOut(nextMatrix, matrix);
        }
        return matrix;
    }

    // copy the values of 'next' matrix to 'current' matrix,
    // and then zero out the contents of 'next' matrix
    public void copyAndZeroOut(int [][] next, int[][] current) {
        for(int x = 0; x < dimension; x++){
            for(int y = 0; y < dimension; y++){
                current[x][y] = next[x][y];
                next[x][y] = 0;
            }
        }
    }

    // Calculate if an individual cell should be alive in the next generation.
    // Based on the game logic:
	/*
		Any live cell with fewer than two live neighbours dies, as if by needs caused by underpopulation.
		Any live cell with more than three live neighbours dies, as if by overcrowding.
		Any live cell with two or three live neighbours lives, unchanged, to the next generation.
		Any dead cell with exactly three live neighbours cells will come to life.
	*/
    private int isAlive(int row, int col, int[][] world) { // this thing is nasty and needs re-written
        int maxNum = world.length;
        //logger.info("Max num: " + maxNum);
        int numOfAlive = 0;
        int outerRun = 0;
        int innerRun = 0;
        int x = row - 1;
        int y = col - 1;

        while(outerRun < 3){
            //logger.info("Pass: " + outerRun);
            if(x == -1){
                x = maxNum - 1;
            }
            if(x == maxNum){
                x = 0;
            }
            while(innerRun < 3){

                if(y == -1){
                    y = maxNum - 1;
                }

                if(y == maxNum){
                    y = 0;
                }

                //logger.info(world[x][y] + " at position " +x + " " + y);
                if(world[x][y] == 1){
                    numOfAlive++;

                }
                innerRun++;
                y++;

            }
            innerRun = 0;
            y = col - 1;
            outerRun++;
            x++;
        }

        if(world[row][col] == 1){
            numOfAlive--;
        }
        //logger.info("Number of alive " + numOfAlive);
        if(world[row][col] == 1){
            if(numOfAlive > 3 || numOfAlive < 2){
                return 0;
            }
            return 1;
        }
        else if(numOfAlive == 3){
            return 1;
        }
        return 0;
    }
}


/**
*        5       3
 * int[rows][columns]
 *      [0] [1] [2]
 *      [1] [1] [2]
 *      [2] [1] [2]
 *      [2] [1] [2]
 *      [3] [1] [2]
 */
