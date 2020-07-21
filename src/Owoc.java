import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

//Klasa tworząca losowe jabłka w zależności od trudności
public class Owoc implements Serializable {
    private int x;
    private int y;
    private boolean dojrzale;
    private int predkosc;

    public Owoc(int trudnosc) {
        int rand = ThreadLocalRandom.current().nextInt(0,551);
        int rand2 = ThreadLocalRandom.current().nextInt(0,101);
        int rand3, pom=1;
        for(int i=0;i<trudnosc;i++) {
            rand3 = ThreadLocalRandom.current().nextInt(0, 101);
            if (rand3 < 8*trudnosc) pom++;
        }
        if(rand2<trudnosc*6) this.dojrzale=false;
        else this.dojrzale=true;
        this.predkosc=pom;
        this.x = rand;
        this.y = -20;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isDojrzale() {
        return dojrzale;
    }

    public void ruchOwocka(){
        this.y+=this.predkosc;
    }
}