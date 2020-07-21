import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

//Klasa przetrzymująca wyniki graczy
public class TablicaWynikow {
    private HashMap<String, Integer> wyniki;

    //W tym konstruktorze odczytujemy wyniki które zostały wcześniej zapisane w pliku
    public TablicaWynikow(){
        HashMap<String, Integer> wyniki = new HashMap<>();
        String linia;
        try {
            BufferedReader odczyt = new BufferedReader(new FileReader(("wyniki.txt")));
            while ((linia = odczyt.readLine()) != null) {
                String[] parts = linia.split(":", 2);
                if(parts.length==2) {
                    String nick = parts[0];
                    Integer wynik = Integer.parseInt(parts[1]);
                    wyniki.put(nick, wynik);
                }
            }
            this.wyniki = wyniki;
            odczyt.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Metoda dodająca nowy wynik po skończeniu gry
    public void dodanieWyniku(String imie, Integer punky){
        this.wyniki.put(imie, punky);
        boolean order = false;
        List<Map.Entry<String, Integer>> sortowanie = new LinkedList<>(this.wyniki.entrySet());
        sortowanie.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        this.wyniki = sortowanie.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

        try {
            BufferedWriter zapis = new BufferedWriter(new FileWriter("wyniki.txt"));
            for (Map.Entry<String, Integer> entry : this.wyniki.entrySet()) {
                zapis.write(entry.getKey() + ":" + entry.getValue());
                zapis.newLine();
            }
            zapis.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public HashMap<String, Integer> getWyniki() {
        return wyniki;
    }
}