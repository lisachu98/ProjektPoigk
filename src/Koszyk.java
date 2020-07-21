import java.io.*;

//Klasa opisująca położenie oraz stan animacji koszyka równocześnie posiadająca metody odpowiadające za jego ruch
public class Koszyk implements Serializable{
    private int x;
    private int y;
    private boolean animacja;

    public Koszyk() {
        this.x=240;
        this.y=710;
        this.animacja=true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAnimacja() {
        return animacja;
    }

    public void ruchWLewo(int a){
        this.x-=a;
        if(x>470) x=470;
        if(x<10) x=10;
        this.animacja=!animacja;
    }

    public void ruchWPrawo(int a){
        this.x+=a;
        if(x>470) x=470;
        if(x<10) x=10;
        this.animacja=!animacja;
    }
}
