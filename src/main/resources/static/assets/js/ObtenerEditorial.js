// Variables
var currentPage = 1;
var itemsPerPage = 5;
var totalItems = 100;
var totalPaginaciones = 0;
const btnEditorial=document.getElementById("btnAddEditorial");

btnEditorial.addEventListener("click", function(){



async function obtenerListaDeObjetos(page) {
    return new Promise(resolve => {
        // Lógica para obtener la lista de objetos, que podría ser una solicitud HTTP o cualquier otra operación asíncrona
        // Supongamos que aquí llamamos a una función asíncrona o hacemos una solicitud HTTP
        // Ejemplo ficticio:
        setTimeout(() => {
            fetch(`/pruebas/editorial?fila=` + page)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Hubo un problema al llamar al endpoint.');
                    }
                    resolve(response.json()); // Resolvemos la promesa con la lista de objetos
                })
                .catch(error => {
                    console.error('Error al llamar al endpoint:', error);
                });

        }, 500); // Simulamos una operación asíncrona con un retardo de 1 segundo
    });
}


fetch(`/pruebas/totalPaginasEditoriales`)
    .then(response => response.json())
    .then(data => {
        totalPaginaciones = data;
    })
    .catch(error => console.error('Error:', error))



// Función para generar los ítems de la página actual
async function generateItems(page) {
    var listaDeObjetos = await obtenerListaDeObjetos(page);
    const listaEditoriales= document.getElementById('content');
    listaEditoriales.innerHTML = "";
    listaDeObjetos
        .forEach(objeto => {
            var nuevoDiv = document.createElement("div");
            nuevoDiv.class="tab-item"
            nuevoDiv.id = "content-libro";
            nuevoDiv.innerHTML =`<img src="/imagenes/editorial/${objeto.logo}" alt="" style="width:120px;">
            <h4>${objeto.nombre}</h4>
            `;
            listaEditoriales.appendChild(nuevoDiv);
            nuevoDiv.addEventListener("click",function(){
                console.log(objeto.id);
                document.getElementById("editorialId").value=objeto.id;
                document.getElementById("editorialId").innerHTML=objeto.id;
                
            });
        });
        
}

// Función para generar la paginación
function generatePagination(page) {
    var totalPages = totalPaginaciones;
    var pagination = [];
    pagination.push('<li class="page-item ' + (page === 1 ? 'disabled' : '') + '"><a class="page-link" href="#" data-page="' + (page - 1) + '">Anterior</a></li>');
    for (var i = 1; i <= totalPages; i++) {
        pagination.push('<li class="page-item ' + (i === page ? 'active' : '') + '"><a class="page-link" href="#" data-page="' + i + '">' + i + '</a></li>');
    }
    pagination.push('<li class="page-item ' + (page === totalPages ? 'disabled' : '') + '"><a class="page-link" href="#" data-page="' + (page + 1) + '">Siguiente</a></li>');
    return pagination.join('');
}

// Función para manejar el evento de clic en los botones de paginación
function handlePaginationClick(e) {
    e.preventDefault();
    var page = parseInt(e.target.getAttribute('data-page'));
    if (!isNaN(page)) {
        currentPage = page;
        render();
    }
}


// Función para renderizar la paginación y los ítems de la página actual
function render() {
    document.getElementById('content').innerHTML = generateItems(currentPage);
    document.getElementById('pagination').innerHTML = generatePagination(currentPage);
}

// Evento para manejar el clic en los botones de paginación
document.getElementById('pagination').addEventListener('click', handlePaginationClick);

// Renderiza la página inicial

render();


//generateItems(2);
//captura el evento clik del div hijo
});


