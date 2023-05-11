package com.natixis_test.backend.utils;

import com.natixis_test.backend.model.Task;

import java.util.ArrayList;
import java.util.List;

public class FactoryTasks{
    static String description="Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.";

    /**
     *Fonction qui génère un nombre de tâches de départ.
     * @param n Le nombre de tâches de départ.
     * @return Liste de tâches sans leur ids initialisés.
     */
    public  List<Task> getStarterTasks(int n){
        List<Task> list=new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            Task t= new Task(description,false );
            list.add(t);
        }
        return list;
    }
}