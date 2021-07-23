$('#modalEdit').on('show.bs.modal', function (event) {
    let user = $(event.relatedTarget);
    $("#modalEdit #editId").val(user.data('id'));
    $("#modalEdit #editFirstName").val(user.data('name'));
    $("#modalEdit #editLastName").val(user.data('lname'));
    $("#modalEdit #editAge").val(user.data('age'));
    $("#modalEdit #editEmail").val(user.data('email'));
    $("#modalEdit #editPassword").val(user.data('password'));
    $("#modalEdit #editRolesSelector").val(user.data('roles'));
});

$('#modalDelete').on('show.bs.modal', function (event) {
    let user = $(event.relatedTarget);
    $("#modalDelete #deleteId").val(user.data('id'));
    $("#modalDelete #deleteFirstName").val(user.data('name'));
    $("#modalDelete #deleteLastName").val(user.data('lname'));
    $("#modalDelete #deleteAge").val(user.data('age'));
    $("#modalDelete #deleteEmail").val(user.data('email'));
    $("#modalDelete #deletePassword").val(user.data('password'));
    $("#modalDelete #deleteRolesSelector").val(user.data('roles'));
});
