package com.natixis_test.backend.controller;

import com.natixis_test.backend.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mock;

    @Test
    void getAllTasks() throws Exception {
        //Réinitialiser le jeu de test.
        Task.reinitialize();
        Task.initializedStarter=true;

        RequestBuilder request = MockMvcRequestBuilders.get("/api/getAllTasks");
        MvcResult result = mock.perform(request).andReturn();

        //Vérifier le status code de retour
        assertEquals(200, result.getResponse().getStatus());

        //Extraction et formatage de la réponse
        String json = result.getResponse().getContentAsString();
        Task[] tasks;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            tasks = jsonb.fromJson(json, Task[].class);
        }
        List<Task> taskList = Stream.of(tasks).collect(Collectors.toList());

        //Comparer la taille des types retournés avec 4 (le nombre de tâches initialisées par défaut en mémoire).
        assertEquals(4,taskList.size());

    }

    @Test
    void getUndoneTasks() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/getUndoneTasks");
        MvcResult result = mock.perform(request).andReturn();

        //Vérifier le status code de retour
        assertEquals(200, result.getResponse().getStatus());

        //Extraction et formatage de la réponse
        String json = result.getResponse().getContentAsString();
        Jsonb jsonb = JsonbBuilder.create();
        Task[] tasks = jsonb.fromJson(json, Task[].class);
        List<Task> taskList = Stream.of(tasks).collect(Collectors.toList());

        //Verifier si toutes les tâches retournées n'ont pas encore été faites
        for (Task task : taskList) {
            assertEquals(task.getStatus(), false);
        }
    }


    // Edge cases à tester :
    // - Id correspond à une tâche donnée
    // - Id ne correspond à aucune tâche
    @Test
    void getTaskById() throws Exception {
        //Id correspond à une tâche
        RequestBuilder request = MockMvcRequestBuilders.get("/api/getTaskById/1");
        MvcResult result = mock.perform(request).andReturn();

        //Vérifier le status code de retour
        assertEquals(200, result.getResponse().getStatus());

        //Extraction et formatage de la réponse
        String json = result.getResponse().getContentAsString();
        Jsonb jsonb = JsonbBuilder.create();
        Task task = jsonb.fromJson(json, Task.class);

        assertEquals(task.getId(), 1);

        //Id correspond à aucune tâche
        request = MockMvcRequestBuilders.get("/api/getTaskById/-1");
        result = mock.perform(request).andReturn();

        assertEquals(404, result.getResponse().getStatus());
    }

    // Edge cases à tester :
    //-Mettre à jour une tâche qui existe
    //-Mettre à jour une tâche qui n'existe pas
    @Test
    void updateStatusTask() throws Exception {
        String body = "{\"status\":true}";
        RequestBuilder request = MockMvcRequestBuilders.put("/api/updateStatusTask/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body);
        MvcResult result = mock.perform(request).andReturn();

        //Vérifier le status code de retour
        assertEquals(200, result.getResponse().getStatus());

        //Extraction et formatage de la réponse
        String json = result.getResponse().getContentAsString();
        Jsonb jsonb = JsonbBuilder.create();
        Task task = jsonb.fromJson(json, Task.class);

        assertEquals(task.getId(), 1);
        assertEquals(task.getStatus(), true);

        //Id correspond à aucune tâche
        request = MockMvcRequestBuilders.put("/api/updateStatusTask/-1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body);
        result = mock.perform(request).andReturn();

        assertEquals(404, result.getResponse().getStatus());
    }

    //Edge cases à tester :
    // Créer une tâche et vérifier le status code de retour et vérifier le contenu du body.
    @Test
    void addTask() throws Exception {
        String body = "{\"label\":\"Just a test.\",\"status\":true}";
        RequestBuilder request = MockMvcRequestBuilders.post("/api/addTask")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body);
        MvcResult result = mock.perform(request).andReturn();

        //Vérifier le status code de retour
        assertEquals(201, result.getResponse().getStatus());

        //Extraction et formatage de la réponse
        String json = result.getResponse().getContentAsString();
        Jsonb jsonb = JsonbBuilder.create();
        Task task = jsonb.fromJson(json, Task.class);

        assertEquals("Just a test.", task.getLabel());
        assertEquals(true, task.getStatus());
    }
}