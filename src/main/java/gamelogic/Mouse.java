
package gamelogic;

import java.awt.event.*;
import assets.actors.Tower;
import assets.actors.Tower2;
import assets.actors.Tower3;

public class Mouse implements MouseListener {
    boolean placingopen1 = false;
    boolean placingopen2 = false;
    boolean placingopen3 = false;
    boolean upgradeopen = false;
    boolean upgradefreeze = false;
    int tol = 50;

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        // Shop / Upgrade Menü Klicks
        if (x >= 1390 && x <= 1665 && y >= 870 && y <= 1030) {
            upgradeopen = true;
            upgradefreeze = false;
            placingopen1 = false;
            placingopen2 = false;
            placingopen3 = false;
        }
        if (x >= 1645 && x <= 1920 && y >= 870 && y <= 1030) {
            upgradefreeze = true;
            upgradeopen = false;
            placingopen1 = false;
            placingopen2 = false;
            placingopen3 = false;
        }

        if (x >= 1340 && x <= 1640 && y >= 350 && y <= 645) {
            placingopen1 = true;
            upgradeopen = false;
            upgradefreeze = false;
            placingopen2 = false;
            placingopen3 = false;
            return;
        }
        if (x >= 1510 && x <= 1810 && y >= 350 && y <= 645) {
            placingopen2 = true;
            upgradeopen = false;
            upgradefreeze = false;
            placingopen1 = false;
            placingopen3 = false;
            return;
        }
        if (x >= 1680 && x <= 1980 && y >= 350 && y <= 645) {
            placingopen3 = true;
            upgradeopen = false;
            upgradefreeze = false;
            placingopen1 = false;
            placingopen2 = false;
            return;
        }

        // Upgrade-Logik (Polymorph über alle Tower)
        if (upgradeopen && x >= 0 && x <= 1400 && y >= 0 && y <= 1080) {
            for (assets.actors.AbstractTower t : assets.actors.AbstractTower.allTowers) {
                if (Math.sqrt((x - t.getX()) * (x - t.getX()) + (y - t.getY()) * (y - t.getY())) <= tol) {
                    t.upgrade(t);
                    upgradeopen = false;
                    break;
                }
            }
        }

        if (upgradefreeze && x >= 0 && x <= 1400 && y >= 0 && y <= 1080) {
            for (assets.actors.AbstractTower t : assets.actors.AbstractTower.allTowers) {
                if (Math.sqrt((x - t.getX()) * (x - t.getX()) + (y - t.getY()) * (y - t.getY())) <= tol) {
                    t.upgradefreeze(t);
                    upgradefreeze = false;
                    break;
                }
            }
        }

        // Placing-Logik (Zusammengefasst via DRY)
        if (placingopen1 && x >= 0 && x <= 1400 && y >= 0 && y <= 1080)
            placing(x, y, 1);
        if (placingopen2 && x >= 0 && x <= 1400 && y >= 0 && y <= 1080)
            placing(x, y, 2);
        if (placingopen3 && x >= 0 && x <= 1400 && y >= 0 && y <= 1080)
            placing(x, y, 3);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void placing(int x, int y, int t) {
        if (x >= 0 && x <= 1400 && y >= 0 && y <= 1080) {
            int tX = x / 40;
            int tY = y / 40;

            if (tX >= 0 && tX < 35 && tY >= 0 && tY < 25) {
                main.Tiles tile = main.Main.lilM[tX][tY];
                if (tile.isIsPlaceble()) {
                    if (t == 1) {
                        Tower.place(tX * 40, tY * 40);
                        placingopen1 = false;
                    }
                    if (t == 2) {
                        Tower2.place(tX * 40, tY * 40);
                        placingopen2 = false;
                    }
                    if (t == 3) {
                        Tower3.place(tX * 40, tY * 40);
                        placingopen3 = false;
                    }
                }
            }
        }
    }
}
