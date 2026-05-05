
package assets;

import javax.swing.ImageIcon;

public abstract class GameAsset {
    protected int x, y;
    protected ImageIcon img;
    protected String name;

    public GameAsset(int x, int y, ImageIcon img, String name) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public ImageIcon getImg() {
        return img;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
