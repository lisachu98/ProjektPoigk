import java.awt.*;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Sterowanie extends JFrame{
    //Inicjalizowanie zmiennych potrzebnych do realizacji gry
    private Image       tlo;
    private Image       koszyk1;
    private Image       koszyk2;
    private Image       serce;
    private boolean     klawisze[];
    private Timer       zegar;
    private Koszyk      koszyk;
    private Gra         gra;
    private Owoce       owoce;
    private Muzyka      muzyka;
    private TablicaWynikow tabela;
    private Zapis       save;

    class Zadanie extends TimerTask{
    //Funkcja opisująca co robi program co krok zegaru
        public void run()
        {
            //Strzałki to ruch lewo/prawo
            if(klawisze[0] && gra.isPauza()) koszyk.ruchWLewo(5);

            if(klawisze[1] && gra.isPauza()) koszyk.ruchWPrawo(5);

            //Spacja to pauza
            if(klawisze[2] && gra.isPauzapom()) gra.pauza();

            //S oraz D to odpowiednio zapis oraz odczyt
            if(klawisze[3] && save.isZapispom()) {
               save.zapis(gra, koszyk, owoce);
            }

            if(klawisze[4] && save.isOdczytpom()){
                gra = save.odczytGra();
                koszyk = save.odczytKoszyk();
                owoce = save.odczytOwoce();
                save.odczyt();
            }

            gra.dzialanieGry(owoce, koszyk, zegar, muzyka, tabela);

            repaint();
        }
    }

    Sterowanie(){
        super("GraPOiGK");
        setBounds(50,50,600,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        createBufferStrategy(2);

        //Przypisywanie wartości zmiennym
        klawisze        = new boolean[5];
        tlo             = new ImageIcon("obrazki/tlo.png").getImage();
        koszyk1         = new ImageIcon("obrazki/koszyk1.png").getImage();
        koszyk2         = new ImageIcon("obrazki/koszyk2.png").getImage();
        serce           = new ImageIcon("obrazki/serce.png").getImage();
        koszyk          = new Koszyk();
        gra             = new Gra();
        owoce           = new Owoce();
        muzyka          = new Muzyka();
        tabela          = new TablicaWynikow();
        save            = new Zapis();

        //Zegar odpowiadający za działanie gry
        zegar = new Timer();
        zegar.scheduleAtFixedRate(new Zadanie(),1000,20);

        this.addKeyListener(new KeyListener(){
            //Tutaj jest obsługa wciskanych klawiszy
            public void keyPressed(KeyEvent e){
                switch(e.getKeyCode()){
                    case KeyEvent.VK_LEFT:    klawisze[0] = true; break;
                    case KeyEvent.VK_RIGHT:   klawisze[1] = true; break;
                    case KeyEvent.VK_SPACE:   klawisze[2] = true; break;
                    case KeyEvent.VK_S:       klawisze[3] = true; break;
                    case KeyEvent.VK_D:       klawisze[4] = true; break;
                }
            }

            public void keyReleased(KeyEvent e){
                switch(e.getKeyCode()){
                    case KeyEvent.VK_LEFT:    klawisze[0] = false; break;
                    case KeyEvent.VK_RIGHT:   klawisze[1] = false; break;
                    case KeyEvent.VK_SPACE:   klawisze[2] = false; gra.resetPauza(); break;
                    case KeyEvent.VK_S:       klawisze[3] = false; save.resetZapis(); break;
                    case KeyEvent.VK_D:       klawisze[4] = false; save.resetOdczyt(); break;
                }
            }

            public void keyTyped(KeyEvent e){
            }
        }
        );
    }

    public static void main(String[] args){
        Sterowanie okno = new Sterowanie();
        okno.repaint();
    }

    //Rysowanie planszy oraz jej elementów
    public void paint(Graphics g)
    {
        BufferStrategy bstrategy = this.getBufferStrategy();
        Graphics2D g2d = (Graphics2D)bstrategy.getDrawGraphics();

        g2d.drawImage(tlo, 0,0,null);
        g2d.setColor(Color.red);
        for(int i=0;i<owoce.getOwoce().size();i++){
            if(owoce.getOwoce().elementAt(i).isDojrzale()) g2d.setColor(Color.red);
            else g2d.setColor(Color.green);
            g2d.fill(new Ellipse2D.Float(owoce.getOwoce().elementAt(i).getX(),owoce.getOwoce().elementAt(i).getY(),40,40));
        }
        for(int i=0;i<gra.getZycie();i++){
            g2d.drawImage(serce, i*35+10,40,null);
        }
        if(koszyk.isAnimacja()) g2d.drawImage(koszyk1,koszyk.getX(),koszyk.getY(),null);
        else g2d.drawImage(koszyk2,koszyk.getX(),koszyk.getY(),null);
        g2d.setFont(new Font("Arial",Font.BOLD,40));
        g2d.setColor(Color.black);
        if(gra.isKoniecgry()) {
            g2d.drawString("Koniec gry!", 160, 380);
            g2d.drawString("Tabela Wynikow:", 160, 420);
            int ii=1;
            for(String nick : tabela.getWyniki().keySet()){
                g2d.drawString(ii + ". " + nick + " " + tabela.getWyniki().get(nick), 160, 420 + ii*40);
                ii++;
            }
        }
        if(!gra.isPauza()) g2d.drawString("PAUZA", 230, 380);
        g2d.setFont(new Font("Arial",Font.BOLD,20));
        g2d.drawString("Punkty: " + gra.getPunkty(), 10, 95);
        g2d.dispose();

        bstrategy.show();
    }
}