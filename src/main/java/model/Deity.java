package model;

/**
 * La suddivisione avviene prima in base al tipo di effetto e successivamente
 * ogni divinità implementerà il prorpio potere
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
