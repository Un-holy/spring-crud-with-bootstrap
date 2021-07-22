// $('document').ready(function() {
//
//     $('.table .btn').on('click', function(even) {
//
//         event.preventDefault()
//
//         // var href = $(this).attr('href');
//         // $.get(href, function (user, status) {
//         //     $('#modalForm #editId').val(user.id);
//         //     $('#modalForm #editFirstName').val(user.firstName);
//         //
//         // });
//
//
//
//         // $('#modalEdit').modal();
//
//     });
//
// });


    $('#modalEdit').on('show.bs.modal', function (event) {
        let user = $(event.relatedTarget)
        $("#modalEdit #editId").val(user.data('id'))
        $("#modalEdit #editFirstName").val(user.data('name'))
        $("#modalEdit #editLastName").val(user.data('lname'))
        $("#modalEdit #editAge").val(user.data('age'))
        $("#modalEdit #editEmail").val(user.data('email'))
        $("#modalEdit #editPassword").val(user.data('password'))
        $("#modalEdit #editRolesSelector").val(user.data('roles'))
        
})
