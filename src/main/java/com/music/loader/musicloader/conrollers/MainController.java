package com.music.loader.musicloader.conrollers;

import com.music.loader.musicloader.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainController {
    public TextField urlTextField;
    public Button downloadBtn;
    public CheckBox videoCheckBox;
    public CheckBox onlySoundCheckBox;
    private ProgressController progressController;

    public void downloadBtnAction(ActionEvent actionEvent) throws IOException {
        String url = this.urlTextField.getText();
        if (
                !this.onlySoundCheckBox.isSelected() &&
                !this.videoCheckBox.isSelected() &&
                !this.checkYTUrl(url)
        )
            this.createMessageView("Error","Please, fill in all fields!");

        else if (!this.onlySoundCheckBox.isSelected() && !this.videoCheckBox.isSelected())
            this.createMessageView("Error", "Please, choose checkBox!");

        else if (!this.checkYTUrl(url))
            this.createMessageView("Error", "Wrong url!");

        else {
            this.download(this.getVideoId(url));
        }
    }

    public void onlySoundCheckBoxAction(ActionEvent actionEvent) {
        if (this.videoCheckBox.isSelected()) this.videoCheckBox.setSelected(false);
    }

    public void videoCheckBoxAction(ActionEvent actionEvent) {
        if (this.onlySoundCheckBox.isSelected()) this.onlySoundCheckBox.setSelected(false);
    }

    private void createMessageView(String title, String messageText) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("message-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(scene);

        MessageController messageController = fxmlLoader.getController();
        messageController.setMessageText(messageText);

        stage.show();
    }

    private void createProgressView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("progress-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Downloading");
        stage.setResizable(false);
        stage.setScene(scene);

        this.progressController = fxmlLoader.getController();

        stage.show();
    }

    private boolean checkYTUrl(String url) {
        return url.matches("^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+");
    }

    private String getVideoId(String url) {
        if (url.contains("https://www.youtube.com/watch?v="))
            return url.replace("https://www.youtube.com/watch?v=", "");

        if (url.contains("https://youtu.be/"))
            return url.replace("https://youtu.be/", "");

        return "";
    }

    private double getDownloadProgress(String dataLine) {
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?%");
        Matcher matcher = matcher = pattern.matcher(dataLine);

        if (matcher.find()) {
            return Double.parseDouble(matcher.group().replace("%", ""));
        }
        return 0;
    }

    private boolean download(String videoId) {
        String format = this.onlySoundCheckBox.isSelected() ? "mp3" : "mp4";

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose directory");

        File file = directoryChooser.showDialog(new Stage());

        if (file == null) return false;

        String saveUrl = Objects.equals(format, "mp3") ? file + "\\%(title)s" : file + "\\%(title)s.%(ext)s";

        try {
            ProcessBuilder processBuilder = new
                    ProcessBuilder(
                    "src/main/java/com/music/loader/musicloader/yt-dlp.exe",
                    "--ffmpeg-location", "src/main/java/com/music/loader/musicloader/ffmpeg.exe",
                    Objects.equals(format, "mp3") ? "--extract-audio" : "",
                    Objects.equals(format, "mp3") ? "--audio-format" : "--format", format,
                    "--output", saveUrl,
                    "http://www.youtube.com/watch?v=" + videoId
            );
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(process.getInputStream())
            );

            this.createProgressView();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("has already been downloaded")) System.out.println("FILE IS ALREADY EXISTS");
                if (line.contains("%")) {
                    String finalLine = line;
                    Platform.runLater(() -> this.progressController.updateBar(this.getDownloadProgress(finalLine)));
                }
            }

            process.waitFor();
            process.destroy(); // Error!!! Progress bar updates only after it!
            this.urlTextField.setText("");
            return true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
