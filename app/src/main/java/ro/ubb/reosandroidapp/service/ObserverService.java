package ro.ubb.reosandroidapp.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CristianCosmin on 13.01.2018.
 */

public class ObserverService {

    private static ObserverService service = null;

    public static ObserverService getInstance(){
        if(service ==null){

        }
        return service;
    }

    private static List<Observer> observers = new ArrayList<>();
    public static void attach(Observer observer){
        observers.add(observer);
    }

    public static void notifyAllObservers(){
        for(Observer observer:observers){
            observer.update();
        }
    }
}
