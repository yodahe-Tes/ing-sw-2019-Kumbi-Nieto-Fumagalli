package Network;


import java.util.ArrayList;
import java.util.List;

public class Observable<O> {

    private final List<Observer<O>> obss = new ArrayList<>();

    public void addObs(Observer<O> observer){
        synchronized (obss){
           obss.add(observer);
        }
    }

    public void removeObs(Observer<O> observer){
        synchronized (obss){
            obss.remove(observer);
        }
    }

    public void notify(O message){
        synchronized (obss){
            for (Observer<O> observer : obss){
                observer.update(message);
            }
        }
    }

}
