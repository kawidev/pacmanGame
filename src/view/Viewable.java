package view;

import model.GameEntity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Viewable extends JComponent {

    protected static List<Viewable> allDrawables = new ArrayList<>();
    protected GameEntity entityModel;
    protected int x,y;

    public Viewable(GameEntity entityModel) {
        this.entityModel = entityModel;
        this.x = entityModel.getPosition().x;
        this.y = entityModel.getPosition().y;
        allDrawables.add(this);
    }

    public static List<Viewable> getAllDrawables() {
        return allDrawables;
    }
}
