package com.antonio.jamal.gameOfLife.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.antonio.jamal.gameOfLife.Generation;


public class GridPane extends JPanel {
	int rows = 100;
	int cols = 100;

	boolean clickable;
	boolean isSetup;

	DeadOrAlive[][] cells;
	Generation gen;

	public GridPane() {
		clickable = true;
		isSetup = false;

		cells = new DeadOrAlive[rows][cols];
		gen = new Generation();

		this.setBackground(Color.DARK_GRAY);

		addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e) {
				if (clickable) {
					int width = getWidth();
					int height = getHeight();

					int cellWidth = width / cols;
					int cellHeight = height / rows;

					int col = e.getX() / cellWidth;
					int row = e.getY() / cellHeight;

					cells[row][col].changeState();

					repaint();
				}
			}
		});

		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (clickable && e.getX() <= (getWidth() - 1) 
						&& e.getY() <= (getHeight() - 1) 
						&& e.getX() >= 0 && e.getY() >= 0) {
					int width = getWidth();
					int height = getHeight();

					int cellWidth = width / cols;
					int cellHeight = height / rows;

					int col = e.getX() / cellWidth;
					int row = e.getY() / cellHeight;

					cells[row][col].makeAlive();

					repaint();
				}
			}
		});
	}

	public void update() {
		cells = gen.nextGeneration(cells, rows, cols);
	}

	public void clear() {
		for (int row = 0 ; row < rows ; row++) {
			for (int col = 0; col < cols ; col++) {
				cells[row][col] = null;
			}
		}

		isSetup = false;

		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}

	@Override
	public void invalidate() {
		clear();
		super.invalidate();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		int width = this.getWidth();
		int height = this.getHeight();

		int cellWidth = width / cols;
		int cellHeight = height / rows;

		int xOffset = 0;
		int yOffset = 0;

		if(!isSetup) {
			for (int row = 0 ; row < rows ; row++) {
				for (int col = 0; col < cols ; col++) {
					xOffset = col * cellWidth;
					yOffset = row * cellHeight;
					cells[row][col] = new DeadOrAlive(xOffset, yOffset, cellWidth, cellHeight);
				}
			}

			isSetup = true;
		}

		for (int row = 0 ; row < rows ; row++) {
			for (int col = 0; col < cols ; col++) {
				DeadOrAlive cell = cells[row][col];

				if (cell.isAlive()) {
					g2.setPaint(Color.ORANGE);
					g2.fill(cell);
				} else {
					g2.setPaint(Color.GRAY);
					g2.draw(cell);
				}
			}
		}
	}
}
