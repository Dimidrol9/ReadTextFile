package com.example.readtextfile;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.Voice;
import java.io.*;
import java.util.Locale;

public class ControllerRTF {

    @FXML
    private ChoiceBox<String> voice;

    @FXML
    private TextArea TextArea;
    public Synthesizer synthesizer;
    private boolean isSpeaking = false;


    public ControllerRTF(){
        try {
            System.setProperty("freetts.voices",
                    "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

            synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();
            synthesizer.resume();



        } catch (Exception e) {
            System.out.println("Error-Controller"+e.getMessage());
        }
    }

    @FXML
    public void initialize(){
        voice.getItems().addAll("kevin16","kevin");
        voice.setValue("kevin16");

    }

    @FXML
    private void OpenFile(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files","*.txt"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null){
            try (BufferedReader br = new BufferedReader(new FileReader(file))){
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine())!=null){
                    sb.append(line).append("\n");
                }
                TextArea.setText(sb.toString());
            }catch (IOException e){
                System.out.println("Errora-OpenFile"+ e.getMessage());
            }
        }
    }

    @FXML
    private void Speak(){
        try {
            String text = TextArea.getText();
            if (text != null && !text.isEmpty()) {
                if (!isSpeaking) {
                    Voice selectedVoice = new Voice(voice.getValue(), Voice.GENDER_DONT_CARE, Voice.AGE_DONT_CARE, null);
                    synthesizer.getSynthesizerProperties().setVoice(selectedVoice);

                    synthesizer.speakPlainText(text, null);
                    isSpeaking = true;
                } else {
                    synthesizer.cancelAll();
                    isSpeaking = false;
                }
            }
        } catch (Exception e) {
            System.out.println("Error-Speak" + e.getMessage());
        }
    }

    }



