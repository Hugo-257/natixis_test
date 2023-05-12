package com.natixis_test.backend.controller;

import com.natixis_test.backend.model.ErrorParser;
import com.natixis_test.backend.model.Task;
import com.natixis_test.backend.model.bodyRequest.UpdateTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    /**
     * Pour appel endpoint { GET , /getAllTasks.
     *
     * @return Toutes les tâches enregistrées avec status:<br>
     * -<b>200</b> : si la liste n'est pas vide.<br>
     * -<b>204</b> : si la liste est vide.<br>
     * -<b>500</b> : s'il y a une erreur.
     */
    @GetMapping("/getAllTasks")
    public ResponseEntity<?> getAllTasks() {
        try {
            List<Task> tasks = Task.getAllTasks();
            if (tasks.isEmpty()) {
                return new ResponseEntity<>(tasks, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorParser(ErrorParser.DEFAULT_ERROR_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Pour appel endpoint { GET , /getAllTasks}.
     *
     * @return Toutes les tâches à faire avec comme status code:<br>
     * -<b>200</b> : si la liste n'est pas vide.<br>
     * -<b>204</b> : si la liste est vide.<br>
     * -<b>500</b> : s'il y a une erreur.
     */
    @GetMapping("/getUndoneTasks")
    public ResponseEntity<?> getUndoneTasks() {
        try {
            List<Task> tasks = Task.getAllUndoneTasks();
            if (tasks.isEmpty()) {
                return new ResponseEntity<>(tasks, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Pour appel endpoint { GET , /getTaskById/{id}}.
     *
     * @param id L'identifiant de la tâche.
     * @return -La tâche correspondant à l'id trouvé avec status 200.<br>
     * -Une réponse avec status 404 si aucune tâche ne correspond à l'id.<br>
     * -Une réponse avec status 500 s'il y a une erreur.
     */
    @GetMapping("/getTaskById/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable int id) {
        try {
            Task t = Task.findTask(id);
            if (t != null)
                return new ResponseEntity<>(t, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Pour appel endpoint { GET , /getTaskById/{id}}.
     *
     * @param id   identifiant de la tâche.
     * @param body body du request.
     * @return -La tâche correspondant à l'id avec les nouvelles valeurs trouvé avec status 200.<br>
     * -Une réponse avec status 404 si aucune tâche ne correspond à l'id.<br>
     * -Une réponse avec status 500 s'il y a une erreur.
     */

    @PutMapping("/updateStatusTask/{id}")
    public ResponseEntity<?> updateStatusTask(@PathVariable int id, @RequestBody UpdateTask body) {
        try {
            Task t = Task.findTask(id);
            if (t != null) {
                t.setStatus(body.status());
                return new ResponseEntity<>(t, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Pour appel endpoint {POST , /addTask}.
     *
     * @return -La tâche ajoutée avec status code 201.<br>
     * -Une réponse avec status 500 s'il y a une erreur au cours de l'exécution.
     */
    @PostMapping("/addTask")
    public ResponseEntity<?> addTask(@RequestBody Task task) {
        try {
            task.setId(Task.ALL_TASKS.size());
            Task.ALL_TASKS.add(task);
            return new ResponseEntity<>(task, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorParser(ErrorParser.DEFAULT_ERROR_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
