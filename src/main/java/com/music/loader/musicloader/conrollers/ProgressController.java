package com.music.loader.musicloader.conrollers;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgressController implements Initializable {
    public ProgressBar progressBar;
    private final DoubleProperty barUpdater = new SimpleDoubleProperty(.0);

    public void updateBar(double value) {
        barUpdater.set(value / 100);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.progressBar.progressProperty().bind(this.barUpdater);
    }
}
