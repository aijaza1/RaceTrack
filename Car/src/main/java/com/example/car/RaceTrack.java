package com.example.car;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class RaceTrack extends Application {
    // path for the car image
    String carImage = "/Users/azeem/IdeaProjects/Car/src/racecar.png";

    // set max and min of the random numbers
    int max = 100;
    int min = 50;

    // keep track of how far each car has moved
    private static int finished1 = 0;
    private static int finished2 = 0;
    private static int finished3 = 0;

    @Override
    public void start(Stage stage) throws IOException {
        Random r = new Random();

        // create the three buttons and set the positions
        Button button1 = new Button("Start");
        Button button2 = new Button("Pause");
        Button button3 = new Button("Reset");
        button1.setLayoutX(150);
        button2.setLayoutX(220);
        button3.setLayoutX(300);
        button1.setLayoutY(5);
        button2.setLayoutY(5);
        button3.setLayoutY(5);


        // get the image of the car, make the three cars and their positions
        InputStream stream = new FileInputStream(carImage);
        Image image = new Image(stream);
        ImageView car = new ImageView(image);
        car.setX(0);
        car.setY(50);
        car.setFitWidth(50);
        car.setPreserveRatio(true);

        ImageView car2 = new ImageView(image);
        car2.setX(0);
        car2.setY(80);
        car2.setFitWidth(50);
        car2.setPreserveRatio(true);

        ImageView car3 = new ImageView(image);
        car3.setX(0);
        car3.setY(110);
        car3.setFitWidth(50);
        car3.setPreserveRatio(true);


        // make the three tracks for each car and their positions
        Rectangle track1 = new Rectangle();
        Rectangle track2 = new Rectangle();
        Rectangle track3 = new Rectangle();
        track1.setFill(Color.GRAY);
        track1.setX(50);
        track1.setY(72);
        track1.setWidth(400);
        track1.setHeight(10);

        track2.setFill(Color.GRAY);
        track2.setX(50);
        track2.setY(102);
        track2.setWidth(400);
        track2.setHeight(10);

        track3.setFill(Color.GRAY);
        track3.setX(50);
        track3.setY(132);
        track3.setWidth(400);
        track3.setHeight(10);


        // start button action
        EventHandler<ActionEvent> start = new EventHandler<ActionEvent>() {
            @Override
            public synchronized void handle(ActionEvent e) {

                // make the transitions for each car
                TranslateTransition translate = new TranslateTransition();
                translate.setNode(car);
                TranslateTransition translate2 = new TranslateTransition();
                translate2.setNode(car2);
                TranslateTransition translate3 = new TranslateTransition();
                translate3.setNode(car3);

                // could not get to run indefinitely,
                // it would not show the cars moving and just show the winner
                // the stage that shows up continues to have a loading circle and doesn't work correctly
                //while (RaceTrack.finished1 < 400 && RaceTrack.finished2 < 400 && RaceTrack.finished3 < 400) {

                // generate a random 1-10 number of pixels to move the cars by
                int pix1 = r.nextInt(max - min) + 1;
                translate.setByX(pix1);
                RaceTrack.finished1 += pix1;

                int pix2 = r.nextInt(max - min) + 1;
                translate2.setByX(pix2);
                RaceTrack.finished2 += pix2;

                int pix3 = r.nextInt(max - min) + 1;
                translate3.setByX(pix3);
                RaceTrack.finished3 += pix3;


                // find out which car wins first and call the winner method
                if (RaceTrack.finished1 >= 400) {
                    try {
                        winner("One");
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                } else if (RaceTrack.finished2 >= 400) {
                    try {
                        winner("Two");
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                } else if (RaceTrack.finished3 >= 400) {
                    try {
                        winner("Three");
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }


                // play the animation of car moving
                translate.play();
                translate2.play();
                translate3.play();


                // sleep for 50 milliseconds after the animation
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            //}
        };


        // pause button action
        EventHandler<ActionEvent> pause = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                new PauseTransition(Duration.INDEFINITE);

            }
        };


        // reset button action
        EventHandler<ActionEvent> reset = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // make the transitions for each of the cars
                TranslateTransition reset1 = new TranslateTransition();
                reset1.setNode(car);
                TranslateTransition reset2 = new TranslateTransition();
                reset2.setNode(car2);
                TranslateTransition reset3 = new TranslateTransition();
                reset3.setNode(car3);


                // reset the tracker of how far the cars have gone
                RaceTrack.finished1 = 0;
                RaceTrack.finished2 = 0;
                RaceTrack.finished3 = 0;


                // reset the position of each of the cars
                reset1.setFromX(0);
                reset2.setFromX(0);
                reset3.setFromX(0);


                // do the animation
                reset1.play();
                reset2.play();
                reset3.play();
            }
        };


        // actions for the buttons
        button1.setOnAction(start);
        button2.setOnAction(pause);
        button3.setOnAction(reset);


        // group all the nodes together
        Group group = new Group();
        group.getChildren().add(button1);
        group.getChildren().add(button2);
        group.getChildren().add(button3);
        group.getChildren().add(track1);
        group.getChildren().add(track2);
        group.getChildren().add(track3);
        group.getChildren().add(car);
        group.getChildren().add(car2);
        group.getChildren().add(car3);


        // set scene with the group and set dimensions
        Scene scene = new Scene(group, 500, 200);
        stage.setResizable(false);
        stage.setTitle("Richmond Raceway");
        stage.setScene(scene);
        stage.show();
    }

    // winner method after someone wins for alert message
    public void winner(String num) throws FileNotFoundException {
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Car " + num + " Wins!");
        a.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}