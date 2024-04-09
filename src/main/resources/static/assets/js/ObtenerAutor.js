// Variables
var currentPage = 1;
var itemsPerPage = 5;
var totalItems = 100;

const btnAutor=document.getElementById("btnAddAutor");

btnAutor.addEventListener("click", function(){
    let totalPaginaciones=0;

async function obtenerListaDeObjetos(page) {
    return new Promise(resolve => {
        // Lógica para obtener la lista de objetos, que podría ser una solicitud HTTP o cualquier otra operación asíncrona
        // Supongamos que aquí llamamos a una función asíncrona o hacemos una solicitud HTTP
        // Ejemplo ficticio:
        setTimeout(() => {
            fetch(`/pruebas/autor?fila=` + page)
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

fetch(`/pruebas/totalPaginasAutores`)
.then(response => response.json())
.then(data => {
    totalPaginaciones=data;
    
})
.catch(error => console.error('Error:', error));




// Función para generar los ítems de la página actual
async function generateItems(page) {
    var listaDeObjetos2 = await obtenerListaDeObjetos(page);
    const listaAutores = document.getElementById('content');
    listaAutores.innerHTML = "";
    listaDeObjetos2
        .forEach(objeto => {
            var nuevoDiv=document.createElement("div");
            nuevoDiv.class="tab-item"
            nuevoDiv.id = "content-libro";
            nuevoDiv.innerHTML=`<img src="/imagenes/autor/${objeto.img}" alt="" style="width:120px;">
            <h4>${objeto.nombre}</h4>
        `;
            listaAutores.appendChild(nuevoDiv);
            nuevoDiv.addEventListener("click",function(){
                console.log(objeto.id);
                document.getElementById("autorId").value=objeto.id;
                document.getElementById("autorId").innerHTML=objeto.id;
                
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
async function render() {
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