import { BASE_URL_API } from "@/config/config";
import { useState } from "react";
const api = require("../utils/api_client");

export interface TaskType {
    id: number,
    label: string,
    status: boolean,
}

interface propsType {
    task: TaskType,
    updateTask: Function
}

export default function Task(props: propsType) {

    const [id, setId] = useState(props.task.id);
    const [label, setLabel] = useState(props.task.label);
    const [status, setStatus] = useState(props.task.status);

    const handleToogleStatus = async () => {
        const res = await api.updateStatusTask(BASE_URL_API + "/api/updateStatusTask/" + id, { status: !status });

        if (res) {
            setStatus(!status);
            props.updateTask({
                id,
                label,
                status: !status
            })
        }
    }

    return (
        <div className="flex flex-col justify-between  items-center mt-6 px-4 py-6 w-[22em]  bg-white rounded-md drop-shadow-md ">
            <p role={"description"} className="ml-2 text-base text-center font-light mb-6">{label}</p>
            <div className="flex items-center mr-4">
                <input role={"task-action-btn"} checked={status} id="green-checkbox" type="checkbox" onChange={handleToogleStatus} className="w-5 h-5" />
            </div>
        </div>
    );
}