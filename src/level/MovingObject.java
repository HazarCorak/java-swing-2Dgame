package level;

public abstract class MovingObject extends LevelObject {
    protected abstract void update();

    public abstract void updatePos();

    protected abstract void portalInteraction();

    public abstract void setX(int x);

    public abstract void setY(int y);
}
