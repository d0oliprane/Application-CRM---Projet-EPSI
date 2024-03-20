function liveSearch() {

    let produits = document.querySelectorAll('.produits')
    let search_query = document.getElementById("searchbox").value;

    for (let i = 0; i < produits.length; i++) {
        if(produits[i].innerText.toLowerCase()
            .includes(search_query.toLowerCase())) {
            produits[i].classList.remove("is-hidden");
        } else {
            produits[i].classList.add("is-hidden");
        }
    }
    console.log("Fonction liveSearch() appelée");
}


