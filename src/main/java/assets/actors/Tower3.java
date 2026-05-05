package assets.actors;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import main.Tiles;

public class Tower3 extends AbstractTower {
    public static ArrayList<Tower3> Towers3 = new ArrayList<>();
    protected static int[][] u = { { 500, 3, 10, 2, 3 }, { 600, 3, 20, 4, 4 }, { 1000, 4, 40, 5, 7 } };

    public Enemy getTargetEnemy() {
        ArrayList<Enemy> enemies = farestEnemys(this);
        if (!enemies.isEmpty()) {
            return enemies.get(0);
        }
        return null;
    }

    public Tower3(int x, int y, ImageIcon img, String name) {
        super(x, y, img, name);
        this.u = Tower3.u;
    }

    public Tower3(int upgradeCost, int upgradeCostFlowers, int damage, int fireRate, int range, int level, int modef,
            int x, int y, ImageIcon img, String name) {
        super(upgradeCost, upgradeCostFlowers, damage, fireRate, range, level, modef, x, y, img, name);
        this.u = Tower3.u;
    }

    // prüfen welcher enemy innerhalb der towerrange am weitesten fortgeschritten
    // ist
    public ArrayList<Enemy> farestEnemys(Tower3 tower) {
        Tiles a = null;
        ArrayList<Enemy> b = new ArrayList<>();
        // tiles vom größten zum kleinsten index durchgehen
        for (int j = 0; j < 87; j++) {
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
                                }
                            }
                            for (Enemy e : Enemy.Fast) {
                                if (e.getX() == xt && e.getY() == yt) {
                                    b.add(e);
                                }
                            }
                            for (Enemy e : Enemy.Tank) {
                                if (e.getX() == xt && e.getY() == yt) {
                                    b.add(e);
                                }
                            }
                            if (!b.isEmpty()) {
                                return b;
                            }
                        }
                    }
                }
            }
        }
        return b;
    }

    @Override
    // schießen der Tower
    public void shoot(AbstractTower tower) {
        if (Enemyinrange(tower)) {
            ArrayList<Enemy> b = farestEnemys((Tower3) tower);
            for (Enemy e : b) {
                // unterscheidung bezüglich der freeze-Eigenschaft
                if (tower.modef == 0) {
                    e.takeDamage(tower.damage);
                }
                if (tower.modef == 1 && e.getFreeze() == 0) {
                    e.takeDamageandFreeze(tower.damage / 5);
                }
                if (tower.modef == 1 && e.getFreeze() != 0) {
                    e.takeDamage(tower.damage / 2);
                }
            }
        }
    }

    @Override
    // Tower Typ zurückgeben
    public int getTowerType() {
        return 3;
    }

    // tower auf dem Spielfeld platzieren und initialisieren
    static public void place(int x, int y) {
        if (gamelogic.Shop.money >= u[0][0] && gamelogic.Shop.flowers >= u[0][1]) {
            gamelogic.Shop.money -= u[0][0];
            gamelogic.Shop.flowers -= u[0][1];
            Tower3 k = new Tower3(x, y, null, "3T" + i + "");
            k.updateImage();
            i = i + 1;
            Towers3.add(k);
            main.Main.c.repaint();
        }
    }

}
