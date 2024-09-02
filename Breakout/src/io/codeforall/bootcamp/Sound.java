package io.codeforall.bootcamp;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    private Clip clip;

    private AudioInputStream audioInputStream;

    public void playSound(String soundFilePath){
        try{

            File soundFile = new File(soundFilePath);

            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopSound() throws IOException{
        clip.stop();
        audioInputStream.close();
    }
    public void loopSound(int count){
        if(clip != null){
            clip.loop(count);
        }
    }


}
