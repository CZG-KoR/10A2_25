package assets.actors;

import assets.GameAsset;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import main.Tiles;

public abstract class AbstractTower extends GameAsset {
    public static ArrayList<AbstractTower> allTowers = new ArrayList<>();
    protected Enemy enemy = Enemy.Enemy;
    // Standard, Fast, Tank, Weg
    protected int[][] u = { { 500, 3, 10, 2, 3 }, { 600, 3, 20, 4, 4 }, { 1000, 4, 40, 5, 7 } };
    protected int upgradeCost, upgradeCostFlowers, damage, fireRate, range, level, modef;
    protected static int i = 0;
    protected static int dist = 5;

    public AbstractTower(int x, int y, ImageIcon img, String name) {
        super(x, y, img, name);
        this.upgradeCost = 500;
        this.upgradeCostFlowers = 3;
        this.damage = 20;
        this.fireRate = 2;
        this.range = 3;
        this.level = 0;
        this.modef = 0;
        allTowers.add(this);
    }

    public AbstractTower(int upgradeCost, int upgradeCostFlowers, int damage, int fireRate, int range, int level, int modef,
            int x, int y, ImageIcon img, String name) {
        super(x, y, img, name);
        this.upgradeCost = upgradeCost;
        this.upgradeCostFlowers = upgradeCostFlowers;
        this.damage = damage;
        this.fireRate = fireRate;
        this.range = range;
        this.level = level;
        this.modef = modef;
        allTowers.add(this);
    }

    // prüfen ob gegner innerhalb der towerrange existieren
    public boolean Enemyinrange(AbstractTower tower) {
        boolean g = false;
        for (Enemy e : Enemy.Standard) {
            if (Math.sqrt((e.getX() - tower.getX()) * (e.getX() - tower.getX())
                    + (e.getY() - tower.getY()) * (e.getY() - tower.getY())) <= tower.range) {
                g = true;
            }
        }
        for (Enemy e : Enemy.Fast) {
            if (Math.sqrt((e.getX() - tower.getX()) * (e.getX() - tower.getX())
                    + (e.getY() - tower.getY()) * (e.getY() - tower.getY())) <= tower.range) {
                g = true;
            }
        }
        for (Enemy e : Enemy.Tank) {
            if (Math.sqrt((e.getX() - tower.getX()) * (e.getX() - tower.getX())
                    + (e.getY() - tower.getY()) * (e.getY() - tower.getY())) <= tower.range) {
                g = true;
            }
        }
        return g;
    }

    // prüfen ob sich ein bestimmtes tile innerhalb der towerrange befindet
    public boolean tileinrange(Tiles tile, AbstractTower tower) {
        boolean g = false;
        if (Math.sqrt((tile.getX() - tower.getX()) * (tile.getX() - tower.getX())
                + (tile.getY() - tower.getY()) * (tile.getY() - tower.getY())) <= tower.range) {
            g = true;
        }
        return g;
    }

    // upgraden der tower mit überschreiben der parameter
    public void upgrade(AbstractTower tower) {
        if (tower.level <= 2 && gamelogic.Shop.money >= tower.upgradeCost && gamelogic.Shop.flowers >= tower.upgradeCostFlowers ) {
            gamelogic.Shop.money -= tower.upgradeCost;
            gamelogic.Shop.flowers -= tower.upgradeCostFlowers;
            int x = tower.level;
            tower.upgradeCost = tower.u[x][0];
            tower.upgradeCostFlowers = tower.u[x][1];
            tower.damage = tower.u[x][2];
            tower.fireRate = tower.u[x][3];
            tower.range = tower.u[x][4];
            tower.level = tower.level + 1;
            tower.updateImage();
            main.Main.c.repaint();
        }
    }
    // anpassen der zugeordneten bilder je nach Towerlevel
    public void updateImage() {
        String path = "/Bilder/Koala" + getTowerType() + "LVL" + level + ".png";
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            this.setImg(new ImageIcon(imgURL));
        } else {
            System.err.println("Could not find image: " + path);
        }
    }

    public abstract int getTowerType();

    // einsetzen der Freezeeigenschaft gewährleisten
    public void upgradefreeze(AbstractTower tower) {
        if (gamelogic.Shop.mango >= 1) {
            boolean g = false;
            for (AbstractTower t : allTowers) {
                if (t.modef == 1 && Math.sqrt(Math.pow(t.getX() - tower.getX(), 2) + Math.pow(t.getY() - tower.getY(), 2)) >= dist) {
                    g = true;
                    break;
                }
            }
            if (g == false) {
                tower.modef = 1;
                gamelogic.Shop.mango -= 1;
            }
        }
    }



    // parameter für gerade der towerschüsse finden
    public double[] shootfunction(AbstractTower tower, Enemy en) {
        double[] f = { 0, 0, 0, 0, 0, 0 };
        int x1 = tower.getX();
        int y1 = tower.getY();
        int x2 = en.getX();
        int y2 = en.getY();
        
        main.Tiles nextTile = main.Main.getNextTile(en.getTile());
        double hitX = x2;
        double hitY = y2;
        
        if (nextTile != null) {
            double dx = x2 - x1;
            double dy = y2 - y1;
            
            double ux = nextTile.getX() - x2;
            double uy = nextTile.getY() - y2;
            double uLen = Math.sqrt(ux * ux + uy * uy);
            
            if (uLen > 0) {
                ux /= uLen;
                uy /= uLen;
                
                double dot = dx * ux + dy * uy;
                if (dot < 0) {
                    double distSq = dx * dx + dy * dy;
                    double s = -distSq / (2 * dot);
                    hitX = x2 + s * ux;
                    hitY = y2 + s * uy;
                }
            }
        }

        // division durch 0 verhindern
        if (x1 == (int)hitX) {
            f[0] = 0;
        } else {
            f[0] = (hitY - y1) / (hitX - x1);
        }
        f[1] = y1 - f[0] * x1;
        f[2] = x1;
        f[3] = y1;
        f[4] = hitX;
        f[5] = hitY;
        return f;
    }
    // grundlage für Schießen der einzellnen Tower
    public abstract void shoot(AbstractTower tower);

    public int getUpgradeCost() { 
        return upgradeCost; 
    }
    public int getUpgradeCostFlowers() { 
        return upgradeCostFlowers; 
    }
    public int getDamage() { 
        return damage; 
    }
    public int getFireRate() { 
        return fireRate; 
    }
    public int getRange() { 
        return range; 
    }
}

