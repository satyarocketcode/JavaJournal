package com.pingpong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PingPongGame extends JPanel implements KeyListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private int paddleY = 250;
	private final int paddleHeight = 100;
	private final int paddleWidth = 20;

	private int ballX = 400, ballY = 400;
	private int ballDiameter = 20;
	private int ballXSpeed = -3, ballYSpeed = 3;

	private Timer timer;
	private int score = 0;
	private boolean gameOver = false;

	public PingPongGame() {
		this.setFocusable(true);
		this.addKeyListener(this);
		timer = new Timer(10, this);
		timer.start();
	}

	public void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D) g1;
		// background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.GREEN);
		g.fillRect(30, paddleY, paddleWidth, paddleHeight);

		g.setColor(Color.WHITE);
		g.fillOval(ballX, ballY, ballDiameter, ballDiameter);

		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("SCORE :" + score, 10, 20);

		if (gameOver) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 40));
			g.drawString("Game Over", getWidth() / 2 - 100, getHeight() / 2);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (!gameOver) {
			ballX += ballXSpeed;
			ballY += ballYSpeed;

			if (ballY <= 0 || ballY >= getHeight() - ballDiameter) {
				ballYSpeed = -ballYSpeed;
			}

			if (ballX <= 50 && ballY + ballDiameter >= paddleY && ballY <= paddleY + paddleHeight) {
				ballXSpeed = -ballXSpeed;
				score++;
			}

			if (ballX >= getWidth() - ballDiameter) {
				ballXSpeed = -ballXSpeed;
			}
			
			if(ballX < 0) {
				gameOver = true;
			}
		}

		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_UP && paddleY > 0) {
			paddleY -= 40;
		}
		if (key == KeyEvent.VK_DOWN && paddleY < getHeight() - paddleHeight) {
			paddleY += 40;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	
	public void resetGame() {
			paddleY = 250;
			ballX = 350;
			ballY = 350;
			ballXSpeed = -3;
			ballYSpeed = 3;
			score = 0;
			gameOver = false;
			repaint();
		
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Ping Pong - Single Player");

		PingPongGame game = new PingPongGame();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game options");
		
		JMenuItem newGameItem = new JMenuItem("New Game");
		JMenuItem exitItem = new JMenuItem("Exit");
		
		
		newGameItem.addActionListener(e -> {game.resetGame();});
		exitItem.addActionListener(e -> {System.exit(0);});
		
		gameMenu.add(newGameItem);
		gameMenu.addSeparator();
		gameMenu.add(exitItem);
		menuBar.add(gameMenu);
		
		frame.setJMenuBar(menuBar);
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.setVisible(true);
	}

}
