package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class MandelBrot extends Application implements EventHandler<ActionEvent> {

	int iterations = 0;
	int width = 600;
	int height = 600;
	int maxIteration = 100;
	int moveX = 0;
	int moveY = 0;
	int SCALE = 200;
	// state 0 is mandel state 1 is julia
	int state = 0;

	Button up;
	Button down;
	Button left;
	Button right;
	Button zoomIn;
	Button zoomOut;
	Button showMandel;
	Button showJulia;

	PixelWriter pw;

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Mandelbrot");

		Group root = new Group();
		HBox box = new HBox();
		Canvas c = new Canvas(width, height);
		c.setLayoutY(50);

		up = new Button("up");
		down = new Button("down");
		left = new Button("left");
		right = new Button("right");
		zoomIn = new Button("zoom in");
		zoomOut = new Button("zoom out");
		showMandel = new Button("show Mandel");
		showJulia = new Button("show Julia");

		zoomIn.setOnAction(this);
		up.setOnAction(this);
		down.setOnAction(this);
		left.setOnAction(this);
		right.setOnAction(this);
		zoomOut.setOnAction(this);
		showJulia.setOnAction(this);
		showMandel.setOnAction(this);

		box.getChildren().add(showJulia);
		box.getChildren().add(showMandel);
		box.getChildren().add(up);
		box.getChildren().add(down);
		box.getChildren().add(left);
		box.getChildren().add(right);
		box.getChildren().add(zoomIn);
		box.getChildren().add(zoomOut);

		GraphicsContext gc = c.getGraphicsContext2D();
		pw = gc.getPixelWriter();

		// first preview when started
		drawMandelbrot(pw);
//		drawJulia(pw);

		box.setMinSize(600, 50);
		box.setAlignment(Pos.TOP_CENTER);
		box.setStyle("-fx-background-color: black;-fx-padding: 10;-fx-spacing : 5");

		root.getChildren().add(box);
		root.getChildren().add(c);

		Scene scene = new Scene(root, width, height);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void drawMandelbrot(PixelWriter pw) {

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				double first = x - ((width + moveX) / 2.0);
				double second = (y - (height - moveY) / 2.0);
				Color color = computeMandel(first / (SCALE), second / (SCALE));

				pw.setColor(x, y, color);

			}
		}

	}

	public void drawJulia(PixelWriter pw) {

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				double first = x - ((width + moveX) / 2.0);
				double second = (y - (height - moveY) / 2.0);
				Color color = computeJulia(first / (SCALE), second / (SCALE));

				pw.setColor(x, y, color);

			}
		}
	}

	public Color computeMandel(double x, double y) {

		double comX = x;
		double comY = y;

		int i = 0;
		boolean doThis = true;

		while (i < maxIteration && doThis) {
			double x2 = x * x - y * y + comX;
			double y2 = x * 2 * y + comY;
			x = x2;
			y = y2;
			i++;

			if (x * x - y * y > 2) {

				doThis = false;
			}

		}

		if (i == maxIteration)
			return Color.BLACK;

		if (i < 25 && i > 10)
			return Color.rgb(0, 7, 100);
		if (i < 50 && i > 10)
			return Color.rgb(32, 107, 203);
		if (i < 75 && i > 10)
			return Color.rgb(237, 255, 255);
		if (i < 100 && i > 10)
			return Color.rgb(255, 170, 0);
		if (i < 125 && i > 10)
			return Color.rgb(0, 2, 0);

		return Color.BLACK;

	}

	public Color computeJulia(double x, double y) {

		int i = 0;
		boolean doThis = true;
		double comX = -0.70176f;
		double comY = -0.3842f;
		while (i < maxIteration && doThis) {

			double x2 = x * x - y * y + comX;
			double y2 = 2 * x * y + comY;
			i++;
			x = x2;
			y = y2;

			if (x2 * x2 + y2 * y2 > 2) {

				doThis = false;
			}

		}

		if (i == maxIteration)
			return Color.BLACK;

		if (i < 25 && i > 10)
			return Color.rgb(0, 7, 100);
		if (i < 50 && i > 10)
			return Color.rgb(32, 107, 203);
		if (i < 75 && i > 10)
			return Color.rgb(237, 255, 255);
		if (i < 100 && i > 10)
			return Color.rgb(255, 170, 0);
		if (i < 125 && i > 10)
			return Color.rgb(0, 2, 0);

		return Color.BLACK;

	}

	@Override
	public void handle(ActionEvent arg0) {

		if (arg0.getSource() == showMandel) {
			state = 0;
			// reset numbers
			moveX = 0;
			moveY = 0;
			SCALE = 200;
			drawMandelbrot(pw);
		}

		if (arg0.getSource() == showJulia) {
			state = 1;
			// reset numbers
			moveX = 0;
			moveY = 0;
			SCALE = 200;
			drawJulia(pw);
		}

		//Mandelbrot buttons
		if (arg0.getSource() == up && state == 0) {
			moveY -= 200;
			drawMandelbrot(pw);

		}
		if (arg0.getSource() == down && state == 0) {
			moveY += 200;
			drawMandelbrot(pw);

		}
		if (arg0.getSource() == left && state == 0) {
			moveX += 200;
			drawMandelbrot(pw);

		}
		if (arg0.getSource() == right && state == 0) {
			moveX -= 200;
			drawMandelbrot(pw);

		}
		if (arg0.getSource() == zoomIn && state == 0) {
			SCALE += 400;
			drawMandelbrot(pw);
		}
		// sometimes buggy
		if (arg0.getSource() == zoomOut && state == 0) {
			SCALE -= 400;
			drawMandelbrot(pw);
		}

		//Julia buttons 
		if (arg0.getSource() == up && state == 1) {
			moveY -= 200;
			drawJulia(pw);

		}
		if (arg0.getSource() == down && state == 1) {
			moveY += 200;
			drawJulia(pw);

		}
		if (arg0.getSource() == left && state == 1) {
			moveX += 200;
			drawJulia(pw);

		}
		if (arg0.getSource() == right && state == 1) {
			moveX -= 200;
			drawJulia(pw);

		}
		if (arg0.getSource() == zoomIn && state == 1) {
			SCALE += 400;
			drawJulia(pw);
		}
		// sometimes buggy
		if (arg0.getSource() == zoomOut && state == 1) {
			SCALE -= 400;
			drawJulia(pw);
		}

	}

}
