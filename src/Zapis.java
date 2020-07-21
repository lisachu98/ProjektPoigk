import java.io.*;
import java.util.*;

//Klasa odpowiedzialna za zapis oraz odczyt stanu gry
public class Zapis{
    private boolean zapispom;
    private boolean odczytpom;

    public Zapis() {
        this.zapispom = true;
        this.odczytpom = true;
    }

    public boolean isZapispom() {
        return zapispom;
    }

    public boolean isOdczytpom() {
        return odczytpom;
    }

    //Metoda która scala poniższe metodu zapisu konkretnych klas
    public void zapis(Gra gra, Koszyk koszyk, Owoce owoce){
        zapisGra(gra);
        zapisKoszyk(koszyk);
        zapisOwoce(owoce);
        this.zapispom=false;
    }

    public void odczyt(){this.odczytpom=false;}

    public void resetZapis(){this.zapispom=true;}

    public void resetOdczyt(){this.odczytpom=true;}

    //Poniżej wszystkie metody odpowiadają za zapis oraz odczyt poszczegówlnych klas
    public void zapisGra(Gra gra){
        try {
            FileOutputStream fos = new FileOutputStream("gra.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gra);
            oos.close();
            fos.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public Gra odczytGra(){
        try {
            Gra giera = new Gra(1);
            FileInputStream fis = new FileInputStream("gra.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);
            giera = (Gra) ois.readObject();
            ois.close();
            return giera;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public void zapisKoszyk(Koszyk koszyk){
        try {
            FileOutputStream fos = new FileOutputStream("koszyk.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(koszyk);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public Koszyk odczytKoszyk(){
        try {
            Koszyk koszyczek = new Koszyk();
            FileInputStream fis = new FileInputStream("koszyk.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);
            koszyczek = (Koszyk) ois.readObject();
            ois.close();
            return koszyczek;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public void zapisOwoce(Owoce owoce){
        try {
            List<Owoc> pom = Collections.list(owoce.getOwoce().elements());
            FileOutputStream fos = new FileOutputStream("owoce.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(pom);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public Owoce odczytOwoce(){
        try {
            Owoce owocki = new Owoce();
            FileInputStream fis = new FileInputStream("owoce.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Vector<Owoc> owoce = new Vector<Owoc>((List)ois.readObject());
            owocki.setOwoce(owoce);
            ois.close();
            return owocki;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}