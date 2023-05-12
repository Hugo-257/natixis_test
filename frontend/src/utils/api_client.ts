import { BASE_URL_API } from "@/config/config";

const getAllTasks = async () => {
	const res = await fetch(BASE_URL_API + "/api/getAllTasks");
	return res;
};

const addTaskApi = async (url: string, data = {}) => {
	const response = await fetch(url, {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(data),
	});
	return await response.json();
};

const updateStatusTask = async (url = "", data = {}) => {
	const response = await fetch(url, {
		method: "PUT",
		headers: {
			"Content-Type": "application/json",
			Accept: "*/*",
		},
		body: JSON.stringify(data),
	});
	return response ? 1 : 0;
};


module.exports={
    updateStatusTask,
    getAllTasks,
    addTaskApi,
}