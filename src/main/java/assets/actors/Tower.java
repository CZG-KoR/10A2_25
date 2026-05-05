package assets.actors;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import main.Tiles;

public class Tower extends AbstractTower {

    public static ArrayList<Tower> Towers1 = new ArrayList<>();
    protected static int[][] u = { { 500, 3, 10, 2, 3 }, { 600, 3, 20, 4, 4 }, { 1000, 4, 40, 5, 7 } };

    public Tower(int x, int y, ImageIcon img, String name) {
        super(x, y, img, name);
        this.u = Tower.u;
    }

    public Tower(int upgradeCost, int upgradeCostFlowers, int damage, int fireRate, int range, int level, int modef,
            int x, int y, ImageIcon img, String name) {
        super(upgradeCost, upgradeCostFlowers, damage, fireRate, range, level, modef, x, y, img, name);
        this.u = Tower.u;
    }

    // den gegner innerhalb der towerrange finden der auf dem weg am weitesten
    // fortgeschritten ist
    public Enemy farestEnemy(Tower tower) {
        Enemy en = enemy;
        boolean g = false;
        Tiles a = null;
        int c = 0;
        ArrayList<Enemy> b = new ArrayList<>();
        // tiles vom größten zum kleinsten index durchgehen
        outer: for (int j = 0; j < 87; j++) {
            for (int k = 0; k < 35; k++) {
                for (int l = 0; l < 25; l++) {
                    if (main.Main.lilM[k][l].getID() == 89 - j) {
                        a = main.Main.lilM[k][l];
                        if (tileinrange(a, tower)) {
                            int xt = a.getX();
                            int yt = a.getY();
                            // für ermitteltes tile alle dort befindlichen gegner suchen
                            for (Enemy e : Enemy.Standard) {
                                if (e.getX() == xt && e.getY() == yt) {
                                    b.add(e);
                                    g = true;
                                }
                            }
                            for (Enemy e : Enemy.Fast) {
                                if (e.getX() == xt && e.getY() == yt) {
                                    b.add(e);
                                    g = true;
                                }
                            }
                            for (Enemy e : Enemy.Tank) {
                                if (e.getX() == xt && e.getY() == yt) {
                                    b.add(e);
                                    g = true;
                                }
                            }
                            // sobald gegner auf diesem tile gefunden wurden, suche abbrechen
                            if (g) {
                                break outer;
                            }
                        }
                    }
                }
            }
        }
        // gegner mit höchsten Lebenspunkten auswählen
        for (Enemy e : b) {
            if (e.getHealthpoints() > c) {
                c = e.getHealthpoints();
                en = e;
            }
        }
        return en;
    }

    // tower die gegner beschädigen lassen
    @Override
    public void shoot(AbstractTower tower) {
        if (Enemyinrange(tower)) {
            Enemy en = farestEnemy((Tower) tower);
            if (en == null) {
                return;
            }
            // unterscheidung bezüglich der freeze-Eigenschaft
            if (tower.modef == 0) {
                en.takeDamage((int) tower.damage);
            }
            if (tower.modef == 1 && en.getFreeze() == 0) {
                en.takeDamageandFreeze(tower.damage / 5);
            }
            if (tower.modef == 1 && en.getFreeze() != 0) {
                en.takeDamage(tower.damage / 5);
            }
        }
    }

    @Override
    public int getTowerType() {
        return 1;
    }

    // tower auf dem Spielfeld platzieren und initialisieren
    public static void place(int x, int y) {
        if (gamelogic.Shop.money >= u[0][0] && gamelogic.Shop.flowers >= u[0][1]) {
            gamelogic.Shop.money -= u[0][0];
            gamelogic.Shop.flowers -= u[0][1];
            Tower k = new Tower(x, y, null, "T" + i + "");
            k.updateImage();
            i = i + 1;
            Towers1.add(k);
            main.Main.c.repaint();
        }
    }

}

      
                
              
              
                     
            
                
            
            
        
    
