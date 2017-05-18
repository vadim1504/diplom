package model;

import java.awt.image.BufferedImage;

public class Slide {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    private int id;
    private BufferedImage bufferedImage;

    public Slide(int id, BufferedImage bufferedImage) {
        this.id = id;
        this.bufferedImage = bufferedImage;
    }
}
