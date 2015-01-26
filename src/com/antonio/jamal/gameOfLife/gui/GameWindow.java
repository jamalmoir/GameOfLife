package com.antonio.jamal.gameOfLife.gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GameWindow {	

	private JFrame frame;
	private GridPane gridPane;
	private boolean running;
	private boolean paused;
	private int fps;
	private int frameCount;

	public GameWindow() {

		gridPane = new GridPane();
		running = false;
		paused = false;
		fps = 60;
		frameCount = 0;

	}

	public void createAndShowGui() {

		frame = new JFrame("Game of Life");
		
		JPanel gridPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new GridLayout(0, 1));

		JButton startButton = new JButton("Start");
		JButton pauseButton = new JButton("(un)Pause");
		JButton stepButton = new JButton("Step");
		JButton clearButton = new JButton("Clear");

		startButton.setActionCommand("start");
		startButton.addActionListener(new buttonListener());

		pauseButton.setActionCommand("pause");
		pauseButton.addActionListener(new buttonListener());

		stepButton.setActionCommand("step");
		stepButton.addActionListener(new buttonListener());

		clearButton.setActionCommand("clear");
		clearButton.addActionListener(new buttonListener());

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout());

		frame.getContentPane().add(gridPanel);
		frame.getContentPane().add(buttonPanel);

		gridPanel.add(gridPane);

		buttonPanel.add(startButton);
		buttonPanel.add(pauseButton);
		buttonPanel.add(stepButton);
		buttonPanel.add(clearButton);

		frame.pack();
		frame.setVisible(true);

	}
	
	//Game loop courtesy of http://www.java-gaming.org/index.php?topic=24220.0
	public void startGameLoop() {
		
		running = true;
		
		Thread loop = new Thread() {
			
			public void run() {
				
				gameLoop();
				
			}
		};
		
		loop.start();
		
	}

	private void gameLoop() {
		
		final double GAME_HERTZ = 60.0;
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		final int MAX_UPDATES_BEFORE_RENDER = 1;
		final double TARGET_FPS = 120;
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
		
		double lastUpdateTime = System.nanoTime();
		double lastRenderTime = System.nanoTime();

		int lastSecondTime = (int) (lastUpdateTime / 1000000000);

		while (running) {
			
			double now = System.nanoTime();
			int updateCount = 0;

			if (!paused) {

				while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER ) {
					
					gridPane.update();
					lastUpdateTime += TIME_BETWEEN_UPDATES;
					updateCount++;
					
				}
				
				if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
					
					lastUpdateTime = now - TIME_BETWEEN_UPDATES;
					
				}

				frame.setTitle("Game of Life (" + fps + " fps)");
				gridPane.repaint();
				frameCount++;
				lastRenderTime = now;

				int thisSecond = (int) (lastUpdateTime / 1000000000);
				
				if (thisSecond > lastSecondTime) {
					
					fps = frameCount;
					frameCount = 0;
					lastSecondTime = thisSecond;
					
				}

				while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS 
						&& now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
					
					Thread.yield();

					try {Thread.sleep(1);} catch(Exception e) {} 

					now = System.nanoTime();
					
				}
			}
		}
	}

	public void step() {

		gridPane.update();
		gridPane.repaint();

	}

	public void pauseGame() {

		paused = !paused;

	}

	private class buttonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if ("start".equals(e.getActionCommand())) { startGameLoop(); }

			if ("pause".equals(e.getActionCommand())) { pauseGame(); }

			if ("step".equals(e.getActionCommand())) { step(); }

			if ("clear".equals(e.getActionCommand())) { gridPane.clear(); }

		}	
	}
}
