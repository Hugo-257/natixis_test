import next from "next/types";
import { useState } from "react";

const classNames = {
    notCurrent: "inline-block p-4 rounded-t-lg hover:text-gray-600 hover:bg-gray-50 dark:hover:bg-gray-800 dark:hover:text-gray-300",
    current: "inline-block p-4 text-blue-600 bg-gray-100 rounded-t-lg active dark:bg-gray-800 dark:text-blue-500"
  }

interface propsType{
    toogleChoice:Function
}

export default function SelectorTasks(props:propsType) {
    var [statusTasks, setStatusTasks] = useState(false);

    function handleToogleChoice() {
        props.toogleChoice(!statusTasks)
        setStatusTasks(!statusTasks);
      }

    return (
            <ul data-testid="checkif" className="flex flex-wrap text-sm  mt-4 font-medium text-center text-gray-500 border-b border-gray-200 dark:border-gray-700 dark:text-gray-400">
                <li className="mr-2">
                    <button data-testid={"btn-undone-tasks"} onClick={handleToogleChoice} className={statusTasks ? classNames.notCurrent : classNames.current}>Undone</button>
                </li>

                <li className="mr-2">
                    <button data-testid={"btn-done-tasks"} onClick={handleToogleChoice} className={statusTasks ? classNames.current : classNames.notCurrent}>Done</button>
                </li>
            </ul>
    )
}   