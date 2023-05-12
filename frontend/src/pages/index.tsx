import { useEffect, useState } from "react";
import Task, { TaskType } from "@/components/Task";
import { BASE_URL_API } from "@/config/config";
import NewTaskForm from "@/components/NewTaskForm";
import SelectorTasks from "@/components/SelectorTasks";
import { AnimatePresence, motion } from "framer-motion"
const api = require("../utils/api_client");


interface propsType {
  tasks: TaskType[],
}

export default function Home(props: propsType) {

  var [tasks, setTasks] = useState<TaskType[]>(props.tasks);
  var [statusTasks, setStatusTasks] = useState(false);

  function addTask(newTask: TaskType) {
    setTasks([
      newTask,
      ...tasks
    ])
  }

  function updateTask(task: TaskType) {
    setTasks(tasks.map(el => {
      if (el.id == task.id) return task;
      return el;
    }))
  }

  function toogleChoice() {
    setStatusTasks(!statusTasks);

  }

  return (
    <div className="w-full h-full   px-32 pt-8">
      <h1 className="text-xl font-semibold mb-4">Tasks</h1>
      <NewTaskForm addTask={addTask} />

      <SelectorTasks toogleChoice={toogleChoice} />

      <div data-testid="tasksSection" className="grid grid-cols-3 gap-1">
        {
          tasks.map((el) =>
            (el.status == statusTasks) ?
            <AnimatePresence key={el.id}>
                <motion.div key="modal"
                  initial={{ opacity: 0 }}
                  animate={{ opacity: 1 }}
                  exit={{opacity: 0 }}
                 >
                  <Task task={el} updateTask={updateTask} />
                </motion.div>
            </AnimatePresence> :null
          )
        }
      </div>
    </div>
  );
}


// This gets called on every request
export async function getServerSideProps() {
  // Fetch data from external API
  const res = await api.getAllTasks();
  const tasks = await res.json();

  // Pass data to the page via props
  return { props: { tasks } };
}