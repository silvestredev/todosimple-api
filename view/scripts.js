const url = "http://localhost:8000/task/1";

//chamando a API 
async function getAPI(url) {
    
    const res = await fetch(url, { method: "GET" });

    let data = await res.json();
    console.log(data);

    if(res) {
        hiddeLoader();
    }

    showTaskById(data);
}

getAPI(url);

//removendo o "loading" do html quando a API retornar a response
function hiddeLoader() {
    
    document.getElementById("loading").style.display = "none";
}

//inserindo o retorno da API na table
function showTaskById(task) {
    
    let table = 
        `<thead>
            <th scope="col">TaskId</th>
            <th scope="col">Title</th>
            <th scope="col">Description</th>
            <th scope="col">User</th>
            <th scope="col">UserId</th>
        </thead>`;

    table += `<tr>
                <td scope="row">${task.id}</td>
                <td scope="row">${task.title}</td>
                <td scope="row">${task.description}</td>
                <td scope="row">${task.user.name}</td>
                <td scope="row">${task.user.id}</td>
            </tr>`;

    document.getElementById("tasks").innerHTML = table;
}

