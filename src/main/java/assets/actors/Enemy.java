package assets.actors;

import assets.GameAsset;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import main.Main;
import java.util.Random;

public class Enemy extends GameAsset {

    static Enemy Enemy = new Enemy(0, 0, null, "Dummy");
    public static ArrayList<Enemy> Standard = new ArrayList<>();
    public static ArrayList<Enemy> Tank = new ArrayList<>();
    public static ArrayList<Enemy> Fast = new ArrayList<>();
    double damage;
    int freeze, healthpoints, bounty;
    private int tile;

    public Enemy(int x, int y, ImageIcon img, String name) {
        super(x, y, img, name);
        this.damage = 10;
        this.freeze = 0;
        this.healthpoints = 100;
        this.bounty = 5;
        this.tile = 3;

    }

    public Enemy(double damage, int freeze, int healthpoints, int bounty, int x, int y, ImageIcon img, String name) {
        super(x, y, img, name);
        this.damage = damage;
        this.freeze = freeze;
        this.healthpoints = healthpoints;
        this.bounty = bounty;
        this.tile = 3;
    }

    public void update() {
        if (this.freeze >= 1) {
            freeze--;
        } else {
            // wenn die Funktion null ist, dann sind Gegner am Ende-> Schaden machen
            if (Main.getNextTile(tile) == null) {
                doDamage(damage);
            }
            // x und y des nächsten Tiles
            else {
                int deltaX = Main.getNextTile(tile).getX();
                int deltaY = Main.getNextTile(tile).getY();
                // Gegner auf x und y bewegen, bis Differenz zwischen Gegner und dem nächsten
                // Tile auf x und y 0 ist
                if (deltaX != getX()) {
                    if (deltaX > getX()) {
                        this.setX(getX() + 1);
                    } else {
                        this.setX(getX() - 1);
                    }
                }

                if (deltaY != getY()) {
                    if (deltaY > getY()) {
                        this.setY(getY() + 1);
                    } else {
                        this.setY(getY() - 1);
                    }
                }
                // Wenn Gegner auf dem nächsten Tile ist, Id des Feldes, auf dem Gegner ist,
                // aktualisieren
                if (deltaX == getX() && deltaY == getY()) {
                    this.tile = Main.getNextTile(tile).getID();
                }
            }
        }
    }

    public double getDamage() {
        return damage;
    }

    public int getFreeze() {
        return freeze;
    }

    public int getHealthpoints() {
        return healthpoints;
    }

    public void setHealthpoints(int healthpoints) {
        this.healthpoints = healthpoints;
    }

    public void takeDamage(int damage) {
        // Schaden nehmen, wenn weniger als 0, sterben
        this.healthpoints -= damage;
        if (healthpoints <= 0) {
            this.die();
        }
    }

    public void takeDamageandFreeze(int damage) {
        // Schaden nehmen, wenn weniger als 0, sterben
        this.healthpoints -= damage;
        if (healthpoints <= 0) {
            this.die();
            return;
        }
        // Wert zwischen 25 und 50 generieren-> Ticks, langsamere Gegner sind länger
        // freezed
        Random r = new Random();
        double help = r.nextGaussian(50, 25);
        this.freeze = (int) Math.round(help);
    }

    public int die() {
        // Jeden Gegner in der jeweiligen Liste suchen und löschen, der keine Leben mehr
        // hat
        for (int i = Standard.size() - 1; i >= 0; i--) {
            if (Standard.get(i).healthpoints <= 0) {
                gamelogic.Shop.money += Standard.get(i).bounty;
                Standard.remove(i);
            }
        }
        for (int i = Tank.size() - 1; i >= 0; i--) {
            if (Tank.get(i).healthpoints <= 0) {
                gamelogic.Shop.money += Tank.get(i).bounty;
                Tank.remove(i);
            }
        }
        for (int i = Fast.size() - 1; i >= 0; i--) {
            if (Fast.get(i).healthpoints <= 0) {
                gamelogic.Shop.money += Fast.get(i).bounty;
                Fast.remove(i);
            }
        }
        // Wenn keine Gegner mehr auf dem Feld sind, nächste Wave starten
        if (Standard.isEmpty() && Fast.isEmpty() && Tank.isEmpty()) {
            Main.wave++;
            Main.waves(Main.wave);
        }
        return bounty;
    }

    public int getBounty() {
        return bounty;
    }

    public int getTile() {
        return tile;
    }

    // Gegner machen Schaden am Ziel
    public void doDamage(double damage) {
        Main.Healthbase -= damage;
        if (Main.Healthbase <= 0) {
            System.out.println("You Lose!");
            Main.wave = 0;
            for (int i = 0; i < Standard.size(); i++) {
                // Standard.remove(i);
                Standard.clear();
            }
            for (int i = 0; i < Fast.size(); i++) {
                // Fast.remove(i);
                Fast.clear();
            }
            for (int i = 0; i < Tank.size(); i++) {
                // Tank.remove(i);
                Tank.clear();
            }
        } else {
            System.out.println("health: " + Main.Healthbase);
        }
        if (Main.Healthbase <= 0) {
            System.out.println("You Lose!");
            System.exit(0);
        }
    }
}
