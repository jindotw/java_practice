package idv.jindotw.util;

public abstract class Exercise {

    protected int id;

    public Exercise(int id) {
        this.id = id;
    }

    public abstract String getTitle();

    public abstract String getDesc();

    public abstract void doExercise();

    public final int getId() {
        return id;
    }
}
