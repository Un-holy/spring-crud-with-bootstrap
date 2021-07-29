// $('#modalEdit').on('show.bs.modal', function (event) {
//     let user = $(event.relatedTarget);
//     $("#modalEdit #editId").val(user.data('id'));
//     $("#modalEdit #editFirstName").val(user.data('name'));
//     $("#modalEdit #editLastName").val(user.data('lname'));
//     $("#modalEdit #editAge").val(user.data('age'));
//     $("#modalEdit #editEmail").val(user.data('email'));
//     $("#modalEdit #editPassword").val(user.data('password'));
//     $("#modalEdit #editRolesSelector").val(user.data('roles'));
// });
//
// $('#modalDelete').on('show.bs.modal', function (event) {
//     let user = $(event.relatedTarget);
//     $("#modalDelete #deleteId").val(user.data('id'));
//     $("#modalDelete #deleteFirstName").val(user.data('name'));
//     $("#modalDelete #deleteLastName").val(user.data('lname'));
//     $("#modalDelete #deleteAge").val(user.data('age'));
//     $("#modalDelete #deleteEmail").val(user.data('email'));
//     $("#modalDelete #deletePassword").val(user.data('password'));
//     $("#modalDelete #deleteRolesSelector").val(user.data('roles'));
// });


async function getUsersTable() {
    let table = $('#usersTable tbody')
    table.empty()

    await fetch('/admin/users')
        .then(response => response.json())
        .then(users => {
            users.forEach(user => {
                table.append(`
                    <tr id="tr-${user.id}">
                        <td>
                            ${user.id}
                        </td>
                        <td name="editFirstName">
                            ${user.firstName}
                        </td>
                        <td name="editLastName">
                            ${user.lastName}
                        </td>
                        <td name="editAge">
                            ${user.age}
                        </td>
                        <td name="editEmail">
                            ${user.email}
                        </td>
                        <td name="editRoles">
                        </td>
                    </tr>
                `);
                user.roles.forEach(role => {
                    $('#usersTableContent td:last').append(`
                        <span>${role.roleName.substring(5)}</span>
                    `)
                });
                $('#usersTableContent #tr-' + user.id).append(`
                    <td>
                        <button id="btn-edit-${user.id}" name="editBtn" type="button" class="btn btn-info" data-toggle="modal" data-target="#modalEdit">Edit</button>
                    </td>
                    <td>
                        <button id="btn-delete-${user.id}" name="deleteBtn" type="button" class="btn btn-danger" data-toggle="modal" data-target="#modalDelete">Delete</button>
                    </td>
                `);
            })
        });

    $("button[name = 'editBtn']").on("click", async button => {
        let btn = button.target;
        let id = btn.getAttribute("id").substring(9)
        fetch('/admin/users/' + id)
            .then(response => response.json())
            .then(user => {
                $('#modalEdit #editId').val(user.id);
                $('#modalEdit #editFirstName').val(user.firstName);
                $('#modalEdit #editLastName').val(user.lastName);
                $('#modalEdit #editAge').val(user.age);
                $('#modalEdit #editEmail').val(user.email);
                $('#modalEdit #editPassword').val(user.password);
                $('#modalEdit option').prop('selected', false)
                $('#modalEdit #editRolesSelector').empty();
                $('#modalEdit #editRolesSelector').append(`<option id="roleA" value="ROLE_ADMIN" requared>ADMIN</option>`)
                $('#modalEdit #editRolesSelector').append(`<option id="roleU" value="ROLE_USER" requared>USER</option>`)

                roleA = $('#roleA').val();
                roleU = $('#roleU').val();

                for (const role of user.roles) {
                    if (role.roleName === roleA) {
                        $('#modalEdit #roleA').prop('selected', true);
                    }
                    if (role.roleName === roleU) {
                        $('#modalEdit #roleU').prop('selected', true);
                    }
                }
            });
    });

    $("button[id = 'btnEditConfirm']").on("click", async button => {
        button.preventDefault();

        let user = {
            id: $('#modalEdit #editId').val(),
            firstName: $('#modalEdit #editFirstName').val(),
            lastName: $('#modalEdit #editLastName').val(),
            age: $('#modalEdit #editAge').val(),
            email: $('#modalEdit #editEmail').val(),
            password: $('#modalEdit #editPassword').val(),
            roles: $('#modalEdit select').val()
        };

        let response = await fetch('/admin/users/' + user.id, {
            method: 'PATCH',
            headers: {
                'content-type': 'application/json'
            },
            body: JSON.stringify(user),
        });

        $('#usersTableContent #tr-' + user.id + " td[name = 'editFirstName']").html(user.firstName);
        $('#usersTableContent #tr-' + user.id + " td[name = 'editLastName']").html(user.lastName);
        $('#usersTableContent #tr-' + user.id + " td[name = 'editAge']").html(user.age);
        $('#usersTableContent #tr-' + user.id + " td[name = 'editEmail']").html(user.email);
        $('#usersTableContent #tr-' + user.id + " td[name = 'editRoles']").empty();

        user.roles.forEach(role => {
            $('#usersTableContent #tr-' + user.id + " td[name = 'editRoles']").append(`
                <span>${role.substring(5)}</span>
            `)
        });

        $("div#modalEdit").modal('hide');
    });
}


getUsersTable();