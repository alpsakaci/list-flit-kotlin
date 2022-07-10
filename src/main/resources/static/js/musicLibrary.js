function getCheckedTracks() {
	var items = [];
	var trackCheckboxes = document.querySelectorAll('.track-check');
	for (var i = 0; i < trackCheckboxes.length; i++) {
		if (trackCheckboxes[i].checked == true) {
			items.push(trackCheckboxes[i].id);
		}
	}

	return items;
}

function getSinglesList() {
	var singleTracks = getCheckedTracks();
	
	if (singleTracks.length == 0) {
		return null;
	}

	return {
		"id": -1,
		"name": "Single",
		"playlistItems": singleTracks
	};
}

function getCheckedPlaylistIds() {
	var items = [];
	var playlistCheckboxes = document.querySelectorAll('.playlist-check');
	for (var i = 0; i < playlistCheckboxes.length; i++) {
		if (playlistCheckboxes[i].checked == true) {
			items.push(playlistCheckboxes[i].id);
		}
	}

	return items;
}
