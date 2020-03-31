package Model;

/**
 * La suddivisione avviene prima in base al tipo di effetto e successivamente
 * ogni divinità implementerà il prorpio potere
 *
 * @author Francisco
 */
//interface Model.Deity e class delle singole divinità
interface Deity{
    public enum  type{
        Move, Build, Opponent;
    }
}

class Atlas implements Deity, BuildingRule{
    type TypeOfGod = type.Build;
    public void power() {//può costruire su qualsiasi livello

    }
}
class Demetra implements Deity, BuildingPhase{
    type TypeOfGod = type.Build;
    public void power() {

    }
}
class Artemis implements Deity, MovementPhase{
    type TypeOfGod = type.Move;
    public void power() {

    }
}
class Apollo implements Deity, MovementRule{
    type TypeOfGod = type.Move;
    public void power() {

    }
}
class Athena implements Deity, MovementRule{
    type TypeOfGod = type.Move;
    String playerOwner = //nickname.player;
//metodo observer e il notify
    public void power() {

    }
}

//interface delle regole

interface BuildingRule {
    public void power();
}
interface BuildingPhase {
    public void power();
}
interface MovementRule {
    public void power();
}
interface MovementPhase {
    public void power();
}



//classi che implementano le interface delle regole

class DefaultBuildingRule implements BuildingRule{
    public void power() {
    }
}

class DefaultBuildingPhase implements BuildingPhase{
    public void power() {
    }
}

class DefaultMovementRule implements MovementRule{
    public void power() {
    }
}

class DefaultMovementPhase {
    public void power() {
    }
}

class BuildingRuleChecker {
    char buildingRules[];
    //regole da soddisfare
    public void checkRules(){
        //controlla tutte le regole in array e controlla se sono state soddisfatte
        //boolean per la costruzione
    }
}

class MovementRulesChecker {

}


