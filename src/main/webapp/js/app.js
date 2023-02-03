var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'))
var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
  return new bootstrap.Popover(popoverTriggerEl)
})

$(document).ready(function() {
  $('#alumnosTabla').DataTable();
} );

$(document).ready(function() {
  $('#escuelasTabla').DataTable();
} );

$(document).ready(function() {
  $('#usuariosTabla').DataTable();
} );
