import { BASE_URL_API } from "@/config/config";
const api = require("../utils/api_client");

import { useState } from "react";

interface propsType {
    addTask: Function
}

export default function NewTaskForm({ addTask }: propsType) {
    const [value, setValue] = useState("");


    function handleInputChange(e: React.ChangeEvent<HTMLTextAreaElement>) {
        setValue(e.target.value);
    }

    function handleAddTask(e: React.FormEvent<HTMLInputElement>) {
        e.preventDefault();
        notify();
    }


    const notify = async () => {
        const content = await api.addTaskApi(BASE_URL_API + "/api/addTask", { label: value, status: false });
        addTask(content);
        setValue("");
    }


    return (
        <form data-testid="addTaskForm" className="flex">
            <textarea value={value} data-testid="newTaskInput" cols={10} rows={3} placeholder="New task" onChange={handleInputChange} className="w-[20rem] px-3 py-2 mr-2 bg-white border border-slate-300 rounded-md text-sm" />
            <input data-testid="addTaskBtn" onClick={handleAddTask} type="submit" disabled={!value} className={"w-[5rem] h-10  bg-[#5370f5] flex justify-center items-center text-white text-sm rounded-md cursor-pointer disabled:opacity-30 "} value="Add" />
        </form>
    );
}

function postData(arg0: string, arg1: { label: string; status: boolean; }) {
    throw new Error("Function not implemented.");
}
