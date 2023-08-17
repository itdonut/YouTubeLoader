module com.music.loader.musicloader {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.music.loader.musicloader to javafx.fxml;
    opens com.music.loader.musicloader.conrollers to javafx.fxml;
    exports com.music.loader.musicloader;
    exports com.music.loader.musicloader.conrollers;
}