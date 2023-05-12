import { dummyData } from "@/utils/data";
import Home from "../pages/index";
import "@testing-library/jest-dom";
import { act, fireEvent, getAllByTestId, render, screen, waitFor } from "@testing-library/react";
const api = require("../utils/api_client");


describe("HomePage", () => {

    it("Checks the Home page  rendered properly", () => {
        render(<Home tasks={dummyData} />);

        // To Checks if all components have been rendered.
        expect(screen.getByRole('heading')).toHaveTextContent('Tasks');
        expect(screen.getByTestId('btn-done-tasks')).toBeInTheDocument();
        expect(screen.getByTestId('btn-undone-tasks')).toBeInTheDocument();
        expect(screen.getByTestId("addTaskForm")).toBeInTheDocument();
        expect(screen.getByTestId('newTaskInput')).toBeInTheDocument();
        expect(screen.getByTestId("addTaskBtn")).toBeInTheDocument();
        expect(screen.getByTestId("tasksSection")).toBeInTheDocument();
    });

    it("Checks that the 'add task button' is disabled (because the input is empty by default).", () => {
        render(<Home tasks={dummyData} />);
        expect(screen.getByTestId("addTaskBtn")).toBeDisabled();
    });

    it("Checks we can add a task to the list properly.", async () => {
        render(<Home tasks={dummyData} />);
        expect(screen.getByTestId('newTaskInput')).toBeInTheDocument();

        const taskInput = screen.getByTestId('newTaskInput');
        const addTaskBtn = screen.getByTestId("addTaskBtn");

        //Change input and test if state of input form has been updated.
        const label: string = "The task test.";
        fireEvent.change(taskInput, { target: { value: label } });
        expect(taskInput).toHaveTextContent(label);


        api.addTaskApi = jest.fn().mockImplementationOnce(() =>
            ({ label, id: -1, status: false })
        );

        //Await the DOM to update
        await waitFor(() => {
            addTaskBtn.click();
        });

        //The input has been cleared.
        expect(taskInput).toHaveTextContent("");

        //Test if the new task has been inserted.
        const list = screen.getAllByRole("description");
        expect(list[0]).toHaveTextContent(label);
    });


    it("Checks that the selector buttons work.", async () => {
        render(<Home tasks={dummyData} />);
        const doneTasksBtn = screen.getByTestId("btn-done-tasks");
        const undoneTasksBtn = screen.getByTestId("btn-undone-tasks");

        //Check for done tasks
        //Await the DOM to update
        await waitFor(() => {
            doneTasksBtn.click();
        });

        //Check if all tasks are only done tasks
        var list = screen.getAllByRole("task-action-btn");
        list.forEach(el => {
            expect(el).toBeChecked();
        });


        //Check for undone tasks
        //Await the DOM to update
        await waitFor(() => {
            doneTasksBtn.click();
        });

        //Check if all tasks are only undone tasks
        list = screen.getAllByRole("task-action-btn");
        list.forEach(el => {
            expect(el).not.toBeChecked();
        });
    });

    it("Checks can update a task status.", async () => {
        render(<Home tasks={dummyData} />);
        const list = screen.getAllByRole("task-action-btn");
        const listLengthBefore = list.length;
        const firstTaskDescription = screen.getAllByRole("description")[0];

        api.updateStatusTask = jest.fn().mockImplementationOnce(() => 1);

        //Choose the first task to test
        await waitFor(() => {
            list[0].click();
        })

        expect(screen.getAllByRole("task-action-btn").length).toEqual(listLengthBefore - 1);
    });

});

