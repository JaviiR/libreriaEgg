function buscarNombresAutor() {
    const input = document.getElementById("nombreAutorInput").value;
    const listaNombres = document.getElementById("listaNombresAutor");
    const idAutor=document.getElementById("autorId");
    listaNombres.innerHTML = ''; // Limpiar la lista en cada nueva búsqueda
    
    if (input.length > 0) {
        
        listaNombres.hidden=false;
        fetch(`/buscar/autor?nombre=${input}`)
            .then(response => response.json())
            .then(data => {
                data.forEach(autor => {
                    const resultado = document.createElement("div");
                    resultado.textContent = autor.nombre;
                    resultado.classList.add("resultado");
                    resultado.onclick = function() {
                        document.getElementById("nombreAutorInput").value = autor.nombre;
                        idAutor.value=autor.id;
                        listaNombres.innerHTML = '';
                        document.getElementById("listaNombresAutor").hidden=true;
                    };
                    listaNombres.appendChild(resultado);
                    
                });
                if(data==0){
                    document.getElementById("listaNombresAutor").hidden=true;
                }
            })
            .catch(error => console.error('Error:', error))
    }else{
        document.getElementById("listaNombresAutor").hidden=true;
    }
};


function buscarNombresEditorial() {
    const input = document.getElementById("nombreEditorialInput").value;
    const listaNombres = document.getElementById("listaNombresEditorial");
    const editorialId=document.getElementById("editorialId")
    listaNombres.innerHTML = ''; // Limpiar la lista en cada nueva búsqueda
    
    if (input.length > 0) {
        
        listaNombres.hidden=false;
        fetch(`/buscar/editorial?nombre=${input}`)
            .then(response => response.json())
            .then(data => {
                data.forEach(editorial => {
                    const resultado = document.createElement("div");
                    resultado.textContent = editorial.nombre;
                    resultado.classList.add("resultado");
                    resultado.onclick = function() {
                        document.getElementById("nombreEditorialInput").value = editorial.nombre;
                        editorialId.value=editorial.id;
                        listaNombres.innerHTML = '';
                        document.getElementById("listaNombresEditorial").hidden=true;
                    };
                    listaNombres.appendChild(resultado);
                    
                });
                if(data==0){
                    document.getElementById("listaNombresEditorial").hidden=true;
                }
            })
            .catch(error => console.error('Error:', error))
    }else{
        document.getElementById("listaNombresEditorial").hidden=true;
    }
}

document.addEventListener('click',function(event) {
    var isClickInsideDiv=document.getElementById("nombreAutorInput").contains(event.target);
    if(!isClickInsideDiv){
        document.getElementById("listaNombresAutor").hidden=true;
    }
});

document.addEventListener('click',function(event) {
    var isClickInsideDiv=document.getElementById("nombreEditorialInput").contains(event.target);
    if(!isClickInsideDiv){
        document.getElementById("listaNombresEditorial").hidden=true;
    }
});