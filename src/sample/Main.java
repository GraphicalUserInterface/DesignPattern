package sample;

import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;


import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Final.fxml"));
        primaryStage.setTitle("Update the Item");


//        ChoiceBox choiceBoxBuy = new ChoiceBox();
//        choiceBoxBuy.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//
//            }
//        });


        Scene scene = new Scene(root, 1250, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {

        launch(args);
    }
}
/*https://github.com/GraphicalUserInterface/DesignPattern.git*/