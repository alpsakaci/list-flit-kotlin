function searchPlaylist() {
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("playlistSearchInput");
	filter = input.value.toUpperCase();
	table = document.getElementById("playlistsTable");
	tr = table.getElementsByTagName("tr");

	for (i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("td")[0];
		if (td) {
			txtValue = td.textContent || td.innerText;
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}

function searchTrack() {
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("trackSearchInput");
	filter = input.value.toUpperCase();
	table = document.getElementById("tracksTable");
	tr = table.getElementsByTagName("tr");

	for (i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("td")[0];
		td2 = tr[i].getElementsByTagName("td")[1];
		td3 = tr[i].getElementsByTagName("td")[2];
		if (td) {
			txtValue = td.textContent || td.innerText;
			txtValue2 = td2.textContent || td2.innerText;
			txtValue3 = td3.textContent || td3.innerText;
			if (txtValue.toUpperCase().indexOf(filter) > -1 || 
				txtValue2.toUpperCase().indexOf(filter) > -1 || 
				txtValue3.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}
