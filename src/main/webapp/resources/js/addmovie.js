$(function() {
    $('#input-tags').selectize({
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
    $('#select-genre').selectize({
        maxItems: 15
    });
});

$(function() {
    $('#addmovieForm').on('submit', function(e) {
        var fyear = $('#year').val();
        var ftitle = $('#title').val();
        var fdescription = $('#description').val();
        var fgenre = $('#select-genre').val();
        var fdirector = $('#director').val();
        var factors = $('#input-tags').val();

        /*if(isNaN(fyear) || fyear < 1900 || fyear > (new Date()).getFullYear()+2 ){
            e.preventDefault();
            alert("Please enter a valid year");
        }*/
        if(ftitle == ""){
            e.preventDefault();
            alert("Please enter a film name");
        }
        else if(fdescription == null){
            e.preventDefault();
            alert("Please enter a film description");
        }
        else if(fgenre.length() == 0){
            e.preventDefault();
            alert("Please enter at least one genre");
        }
        else if(fdirector == ""){
            e.preventDefault();
            alert("Please enter a director name");
        }
        else if(factors == ""){
            e.preventDefault();
            alert("Please enter at least one actor");
        }
    });
});