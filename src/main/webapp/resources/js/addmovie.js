$(function() {
    $('#select-genre').selectize({
        maxItems: 15
    });

    $('#select-actors').selectize({
        maxItems: 10,
        maxOptions: 5,
        delimiter: ',',
        persist: false,
        create: function(input) {
            return {
                value: input,
                text: input
            }
        }
    });

    $('#select-director').selectize({
        maxItems: 1,
        maxOptions: 5,
        delimiter: ',',
        persist: false,
        create: function(input) {
            return {
                value: input,
                text: input
            }
        }
    });
});

$(function() {
    $('#addmovieForm').on('submit', function(e) {
        var fyear = $('#year').val();
        var ftitle = $('#title').val();
        var fdescription = $('#description').val();
        var fgenre = $('#select-genre').val();
        var fdirector = $('#select-director').val();
        var factors = $('#select-actors').val();

        if(isNaN(fyear) || fyear < 1900 || fyear > (new Date()).getFullYear()+2 ){
                    e.preventDefault();
                    alert("Please enter a valid year");
                }
        else if(ftitle === ""){
            e.preventDefault();
            alert("Please enter a film name");
        }
        else if(fdescription === ""){
            e.preventDefault();
            alert("Please enter a film description");
        }
        else if(fdirector.length === 0){
            e.preventDefault();
            alert("Please enter a director name");
        }
        else if(fdirector.split(" ").length !== 2){
            e.preventDefault();
            alert("Please enter a first and last name for the director");
        }
        else if(factors.length === 0){
            e.preventDefault();
            alert("Please enter at least one actor");
        }
        else if(fgenre === "" || fgenre == null || fgenre.length() === 0){
            e.preventDefault();
            alert("Please enter at least one genre");
        }
        for (var i = 0; i < factors.length; i++) {
            if(factors[i].split(" ").length !== 2){
                e.preventDefault();
                alert("Please enter a first and last name for the actor " + actors_array[i]);
            }
        }
    });
});