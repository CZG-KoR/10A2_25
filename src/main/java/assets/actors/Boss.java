
package assets.actors;

import javax.swing.ImageIcon;

public class Boss extends Enemy {

    int bountyFlower;

    public Boss(int x, int y, ImageIcon img, String name) {
        super(x, y, img, name);
        this.bountyFlower = 1;
    }

    public Boss(int damage, int velocity, int healthpoints, int bounty, int x, int y, ImageIcon img, String name) {
        super(x, y, img, name);
        this.damage = damage;
        this.healthpoints = healthpoints;
        this.bounty = bounty;
        this.bountyFlower = 1;
    }

    public int getBountyFlower() {
        return bountyFlower;
    }

}
