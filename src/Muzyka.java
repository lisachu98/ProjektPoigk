import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

//Klasa która pozwala na wykorzystanie dźwięków w grze
public class Muzyka {
    File muzyczki[] = {new File("muzyka/punkt.wav"), new File("muzyka/koniec.wav"), new File("muzyka/blad.wav")};
    public void mPunkt(int wybor){
        File mPath = null;
        if(wybor==1) mPath = muzyczki[0];
        if(wybor==2) mPath = muzyczki[1];
        if(wybor==3) mPath = muzyczki[2];
        try{
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(mPath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            clip.start();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}