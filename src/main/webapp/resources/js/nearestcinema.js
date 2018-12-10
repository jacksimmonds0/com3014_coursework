
var map;
var infoWindow;
$(document).ready (function () {
    $.get("https://api.ipify.org?format=json",
        function (data) {
            $.ajax({
                url:"GeoIPTest",
                type:"POST",
                data: $.param({ipAddress: data.ip}),
                success: function(data){
                    $("#city").append("<h1>Cinemas around "+(data.city!="null"?data.city:"you")+"</h1>");
                    initMap(data);
                }
            });
        });
});


function initMap(location) {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {
            lat: Number(location.latitude),
            lng: Number(location.longitude)},
        zoom: 15
    });
    var marker = new google.maps.Marker({
        position: {
            lat: Number(location.latitude),
            lng: Number(location.longitude)},
        map: map,
        title:
            "Public IP:"+location.ipAddress
            +" @ "+location.city
    });
    infowindow = new google.maps.InfoWindow();
    var service = new google.maps.places.PlacesService(map);
    var request = {
        location: {
            lat: Number(location.latitude),
            lng: Number(location.longitude)},
        radius: 500,
        keyword: 'cinema',
        type: 'cinema'
    };
    service = new google.maps.places.PlacesService(map);
    service.nearbySearch(request, callback);
}

function callback(results, status) {
    if (status === google.maps.places.PlacesServiceStatus.OK) {
        $('.list-group').empty();
        for (var i = 0; i < results.length; i++) {

            var request = {
                placeId: results[i].place_id
            };
            var service = new google.maps.places.PlacesService(map);
            service.getDetails(request, function(place, status1) {
                if (status1 === google.maps.places.PlacesServiceStatus.OK) {
                    var k = (place.website)?place.website:"#";
                    $('.list-group').append("<a href=\""+k+"\" class=\"list-group-item list-group-item-action\">"+place.name+"</a>\n");

                }
            });

            createMarker(results[i]);
        }


    }
}

function createMarker(place) {
    var placeLoc = place.geometry.location;
    var marker = new google.maps.Marker({
        map: map,
        position: place.geometry.location
    });

    google.maps.event.addListener(marker, 'click', function() {
        infowindow.setContent(place.name);
        infowindow.open(map, this);
    });

}