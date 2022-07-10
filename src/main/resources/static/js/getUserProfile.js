$(document).ready(function() {
    $.ajax({
        url: "/v1/me"
    }).then(function(data) {
	   $('#profilePicture').attr("src", data.images[0].url );
    });
});
