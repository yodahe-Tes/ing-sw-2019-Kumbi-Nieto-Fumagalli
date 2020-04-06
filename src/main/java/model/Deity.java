package model;

/**
 * At first every god is divided by their type
 */
//interface Model.Deity e class delle singole divinità
public interface Deity{
    public enum  GodType{
        BuildingRule, MovementRule, BuildingPhase, MovementPhase, opponent
    }

    public String type(){
        return GodType;

    }
}
