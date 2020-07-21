import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.util.Timer;

//Klasa posiadająca podstawowe wartości oraz metody potrzebne do rozgrywki
public class Gra implements Serializable{
    private int punkty;
    private int zycie;
    private int trudnosc;
    private boolean trudnoscpom;
    private boolean pauza;
    private boolean pauzapom;
    private boolean koniecgry;
    private String nick;

    public Gra() {
        this.punkty = 0;
        this.zycie = 3;
        this.trudnosc = 1;
        this.trudnoscpom = true;
        this.pauza = true;
        this.pauzapom = true;
        this.koniecgry = false;
        this.nick = JOptionPane.showInputDialog("Wpisz nick");
        if(this.nick == null || this.nick=="") this.nick="Anonim";
    }

    public Gra(int i){
        this.punkty = 0;
        this.zycie = 3;
        this.trudnosc = 1;
        this.trudnoscpom = true;
        this.pauza = true;
        this.pauzapom = true;
        this.koniecgry = false;
        this.nick="Anonim";
    }

    public boolean isPauza() {
        return pauza;
    }

    public boolean isPauzapom() {
        return pauzapom;
    }

    public int getZycie() {
        return zycie;
    }

    public boolean isKoniecgry() {
        return koniecgry;
    }

    public int getPunkty() {
        return punkty;
    }

    public void pauza(){
        this.pauza=!pauza;
        this.pauzapom=false;
    }

    public void zmianaTrudnosci(){
        if ((punkty + 1) % 10 == 0 && trudnoscpom) {
            trudnosc++;
            this.trudnoscpom = false;
        }
    }

    public void resetPauza(){
        this.pauzapom = true;
    }

    //Metoda która sprawdza czy zachodzi zderzenie pomiędzy koszykiem a jabłkami
    public void zderzenia(Owoce owoce, Koszyk koszyk, Timer zegar, Muzyka muzyka, TablicaWynikow tabela){
        owoce.ruchOwockow();
        for(int i=0;i<owoce.getOwoce().size();i++){
            Shape jablko = new Ellipse2D.Float(owoce.getOwoce().elementAt(i).getX(),owoce.getOwoce().elementAt(i).getY(),40,40);
            if(jablko.intersects(koszyk.getX(), koszyk.getY(), 120, 1)){
                if(owoce.getOwoce().elementAt(i).isDojrzale()) {
                    owoce.getOwoce().remove(i);
                    this.punkty++;
                    this.trudnoscpom = true;
                    muzyka.mPunkt(1);
                }
                else{
                    this.zycie--;
                    owoce.getOwoce().remove(i);
                    if(this.zycie==0){
                        tabela.dodanieWyniku(this.nick, this.punkty);
                        this.koniecgry=true;
                        muzyka.mPunkt(2);
                        zegar.cancel();
                    }
                    else muzyka.mPunkt(3);
                }
            }
            if(jablko.intersects(0, 800, 600, 1) && owoce.getOwoce().elementAt(i).isDojrzale()){
                if(owoce.getOwoce().elementAt(i).isDojrzale()) {
                    this.zycie--;
                    owoce.getOwoce().remove(i);
                    if(this.zycie==0){
                        tabela.dodanieWyniku(this.nick, this.punkty);
                        this.koniecgry=true;
                        muzyka.mPunkt(2);
                        zegar.cancel();
                    }
                    else muzyka.mPunkt(3);
                }
                else{
                    owoce.getOwoce().remove(i);
                }
            }
        }
    }

    //Metoda która scala wszystkie metody potrzebne do rozgrywki
    public void dzialanieGry(Owoce owoce, Koszyk koszyk, Timer zegar, Muzyka muzyka, TablicaWynikow tabela){
        if(this.pauza) {
            owoce.zrzutOwocka(this.trudnosc);
            zderzenia(owoce, koszyk, zegar, muzyka, tabela);
            zmianaTrudnosci();
        }
    }
}
