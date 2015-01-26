package com.antonio.jamal.gameOfLife;

import com.antonio.jamal.gameOfLife.gui.DeadOrAlive;

public class Generation {

	public DeadOrAlive[][] nextGeneration(DeadOrAlive[][] grid, int rows, int cols) {

		DeadOrAlive[][] newGrid = copyGrid(grid, rows, cols);

		for (int row = 0 ; row < rows ; row++) {
			for (int col = 0; col < cols ; col++) {

				DeadOrAlive cell = grid[row][col];
				DeadOrAlive newCell = newGrid[row][col];

				int neighbours = calculateNeighbours(grid, rows, cols, row, col);
				
				if (cell.isAlive() && neighbours > 3) newCell.makeDead();
				if (cell.isAlive() && neighbours < 2) newCell.makeDead();
				if (!cell.isAlive() && neighbours == 3) newCell.makeAlive();

				/*if (cell.isAlive()) {

					if (neighbours > 3 || neighbours < 2) newCell.changeState();

				} else {

					if (neighbours == 3) newCell.changeState();

				}*/

			}
		}

		return newGrid;

	}
	
	public DeadOrAlive[][] copyGrid(DeadOrAlive[][] grid, int rows, int cols) {
		DeadOrAlive[][] gridCopy = new DeadOrAlive[rows][cols];
		
		for (int row = 0 ; row < rows ; row++) {
			for (int col = 0; col < cols ; col++) {
				
				gridCopy[row][col] = new DeadOrAlive(grid[row][col]);
				
			}
		}	
		
		return gridCopy;
	}

	public int calculateNeighbours(DeadOrAlive[][] grid, int rows, int cols, int row, int col) {
		int rowEnd = row + 1;
		int colEnd = col + 1;
		int neighbours = 0;

		for (int currentRow = row - 1 ; currentRow <= rowEnd ; currentRow++) {
			for (int currentCol = col - 1 ; currentCol <= colEnd ; currentCol++) {

				if (currentRow >= 0 && currentCol >= 0 
						&& !(currentRow == row && currentCol == col) 
						&& currentRow < rows && currentCol < cols) { 
					
					if (grid[currentRow][currentCol].isAlive()) {
						
						neighbours++; 
						
					}
				}
			}
		}

		return neighbours;

	}

}
