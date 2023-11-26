package view;

import model.Direction;
import model.Ghost;

import javax.swing.*;
import java.awt.*;

public class GhostView implements Drawable {

    private Ghost ghostModel;
    private ImageIcon[] ghostImages;

    public GhostView(Ghost ghostModel) {
        this.ghostModel = ghostModel;
        this.ghostImages = new ImageIcon[Direction.values().length];
        loadImages();
    }

    @Override
    public void draw(Graphics g) {
        Point position = ghostModel.getPosition();
        ImageIcon currentImage = ghostImages[ghostModel.getDirection().ordinal()];
        g.drawImage(currentImage.getImage(), position.x * 50, position.y * 50, null);
    }

    public void loadImages() {
        ghostImages[Direction.UP.ordinal()] = new ImageIcon("resources/ghosts/red/up.png");
        ghostImages[Direction.DOWN.ordinal()] = new ImageIcon("resources/ghosts/red/down.png");
        ghostImages[Direction.LEFT.ordinal()] = new ImageIcon("resources/ghosts/red/left.png");
        ghostImages[Direction.RIGHT.ordinal()] = new ImageIcon("resources/ghosts/red/right.png");
    }

}
