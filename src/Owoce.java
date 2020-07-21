import java.io.Serializable;
import java.util.Vector;

//Klasa przetrzymująca wszystkie utworzone owoce
public class Owoce implements Serializable{
    private Vector<Owoc> owoce;
    private long czas;

    public Owoce() {
        this.owoce = new Vector<>();
        this.czas = System.nanoTime()/1000000;
    }

    public Vector<Owoc> getOwoce() {
        return owoce;
    }

    public void setOwoce(Vector<Owoc> owoce) {
        this.owoce = owoce;
    }

    //W tej metodzie są tworzone nowe jabłka z częstotliwością zależną od trudności
    public void zrzutOwocka(int trudnosc){
        long now = System.nanoTime()/1000000;
        if(now-this.czas>3000-trudnosc*150){
            owoce.add(new Owoc(trudnosc));
            this.czas=now;
        }
    }

    //Ta metoda odpowiada za ruch jabłek o odpowiednią odległość co krok zegara
    public void ruchOwockow(){
        for(int i=0;i<owoce.size();i++) owoce.elementAt(i).ruchOwocka();
    }
}