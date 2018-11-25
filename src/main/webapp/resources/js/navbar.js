$(document).ready(function () {
    var searchBox = $("#search-box");

    // type ahead search functionality
    searchBox.on("input", function () {
        $.ajax({
            url:"searchbox",
            type:"GET",
            accept: "application/json",
            data: {
                "term": searchBox.val()
            },
            success: function(data) {
                var result, itemInner, item, title,
                    searchDropdown = $('#search-dropdown'),
                    results = data.response;

                searchDropdown.empty();
                if(results.length === 0) {
                    searchDropdown.css('display', 'none');
                    return;
                }

                searchDropdown.css('display', 'block');
                for(var i = 0; i < results.length; i++) {
                    result = results[i];

                    item = document.createElement("li");
                    itemInner = document.createElement("a");

                    itemInner.href = "/movie?id=" + result.id;
                    title = document.createTextNode(result.title);
                    itemInner.appendChild(title);

                    item.appendChild(itemInner);
                    searchDropdown.append(item);
                }

            }
        });
    });
});
