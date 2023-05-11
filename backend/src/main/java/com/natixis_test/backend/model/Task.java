package com.natixis_test.backend.model;

import com.natixis_test.backend.utils.FactoryTasks;
import lombok.*;


import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class Task {
    public static List<Task> allTasks = new ArrayList<>();
    public static boolean initializedStarter = false;
    private int id;
    private String label;
    private Boolean status;



    public Task(String label, Boolean status) {
        this.label = label;
        this.status = status;
    }


    static void checkIfInitialized() {
        if (!Task.initializedStarter) {
            FactoryTasks f = new FactoryTasks();
            List<Task> templates = f.getStarterTasks(4);
            for (Task task : templates) {
                task.setId(Task.allTasks.size());
                Task.allTasks.add(task);
                Task.initializedStarter = true;
            }
        }
    }

    public static void reinitialize(){
        FactoryTasks f = new FactoryTasks();
        List<Task> templates = f.getStarterTasks(4);
        Task.allTasks=new ArrayList<>();
        for (Task task : templates) {
            task.setId(Task.allTasks.size());
            Task.allTasks.add(task);
        }
    }

    public static List<Task> getAllTasks() {
        checkIfInitialized();
        return allTasks;
    }

    public static List<Task> getAllUndoneTasks() {
        checkIfInitialized();
        List<Task> res = new ArrayList<>();
        for (Task t : allTasks) {
            if (!t.status)
                res.add(t);
        }
        return res;
    }

    public static Task findTask(int id) {
        for (Task task : Task.getAllTasks()) {
            if (task.getId() == id)
                return task;
        }
        return null;
    }


    @Override
    public String toString() {
        return "id :" + this.id + " description :" + this.label.substring(0, 20) + "... status :" + this.status;
    }
}