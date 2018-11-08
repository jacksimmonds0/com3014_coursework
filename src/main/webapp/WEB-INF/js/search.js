search = function() {
    var searchInput = document.getElementById("search-box").value;

    alert(searchInput);

    // var xhr = new XMLHttpRequest();
    // xhr.open("GET", "http://localhost:8080/search/" + searchInput, true);
    // xhr.setRequestHeader("Content-type", "application/json");
    // xhr.send();

    window.location.replace("localhost:8080/search/" + searchInput);
}