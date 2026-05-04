package assets.actors;
import assets.GameAsset;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Tower2 extends AbstractTower {
    public static ArrayList<Tower2> Towers2 = new ArrayList<>();
    protected static int[][] u = { { 500, 3, 10, 2, 3 }, { 600, 3, 20, 4, 4 }, { 1000, 4, 40, 5, 7 } };

    public Tower2(int x, int y, ImageIcon img, String name) {
        super(x, y, img, name);
        this.u = Tower2.u;
    }

    public Tower2(int upgradeCost, int upgradeCostFlowers, int damage, int fireRate, int range, int level, int modef, int x, int y, ImageIcon img, String name) {
        super(upgradeCost, upgradeCostFlowers, damage, fireRate, range, level, modef, x, y, img, name);
        this.u = Tower2.u;
    }

    // alle gegner innerhalb der towerrange finden// alle Gegner in Towerreichweite auswählen
    public ArrayList<Enemy> chosenEnemys(Tower2 tower) {
        ArrayList<Enemy> b = new ArrayList<>();
        for (Enemy e : Enemy.Standard) {
            if (Math.sqrt(
                    (e.getX() - tower.getX()) * (e.getX() - tower.getX()) + (e.getY() - tower.getY()) * (e.getY() - tower.getY())) <= tower.range) {
                b.add(e);
            }
        }
        for (Enemy e : Enemy.Fast) {
            if (Math.sqrt(
                    (e.getX() - tower.getX()) * (e.getX() - tower.getX()) + (e.getY() - tower.getY()) * (e.getY() - tower.getY())) <= tower.range) {
                b.add(e);
            }
        }
        for (Enemy e : Enemy.Tank) {
            if (Math.sqrt(
                    (e.getX() - tower.getX()) * (e.getX() - tower.getX()) + (e.getY() - tower.getY()) * (e.getY() - tower.getY())) <= tower.range) {
                b.add(e);
            }
        }
        return b;
    }

    @Override
    // schießen der Tower
    public void shoot(AbstractTower tower) {
        if(Enemyinrange(tower)){
            ArrayList<Enemy> b = chosenEnemys((Tower2) tower);
            for (Enemy e : b) {
                // unterscheidung bezüglich der freeze-Eigenschaft
                if(tower.modef == 0){
                    e.takeDamage(tower.damage);
                }
                if(tower.modef == 1 && e.getFreeze() == 0){
                    e.takeDamageandFreeze(tower.damage/5);
                }
                if(tower.modef == 1 && e.getFreeze() != 0){
                    e.takeDamage(tower.damage/2);
                }
            }
        }
    }

    @Override
    // Tower Typ zurückgeben
    public int getTowerType() {
        return 2;
    }

    // tower auf dem Spielfeld platzieren und initialisieren
    static public void place(int x, int y) {
        if (gamelogic.Shop.money>=u[0][0] && gamelogic.Shop.flowers>=u[0][1]) {
            gamelogic.Shop.money-=u[0][0];
            gamelogic.Shop.flowers-=u[0][1];
            Tower2 k = new Tower2(x, y, null, "2T" + i + "");
            k.updateImage();
            i = i + 1;
            Towers2.add(k);
            main.Main.c.repaint();
        }
    }

}