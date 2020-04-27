package model;

/**
 * At first every god is divided by their type
 */
//interface Model.Deity e class delle singole divinità

public interface Deity{
    public enum  GodType{
        PLAYER, OPPONENT
    }

    public GodType type();
}
