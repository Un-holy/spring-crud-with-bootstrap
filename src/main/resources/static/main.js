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
    let table = $('#usersTable tbody');
    table.empty();

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
        }).then(function() {
            $("button[name = 'editBtn']").on("click", async f => {
                let btn = f.target;
                let id = btn.getAttribute("id").substring(9);
                fetch('/admin/users/' + id)
                    .then(response => response.json())
                    .then(user => {
                        $('#modalEdit #editId').val(user.id);
                        $('#modalEdit #editFirstName').val(user.firstName);
                        $('#modalEdit #editLastName').val(user.lastName);
                        $('#modalEdit #editAge').val(user.age);
                        $('#modalEdit #editEmail').val(user.email);
                        $('#modalEdit #editPassword').val(user.password);
                        $('#modalEdit option').prop('selected', false);
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

            $("button[name = 'deleteBtn']").on('click', async f => {
                let btn = f.target;
                let id = btn.getAttribute('id').substring(11);
                console.log(id);
                fetch('/admin/users/' + id)
                    .then(response => response.json())
                    .then(user => {
                        $('#modalDelete #deleteId').empty().val(user.id);
                        $('#modalDelete #deleteFirstName').empty().val(user.firstName);
                        $('#modalDelete #deleteLastName').empty().val(user.lastName);
                        $('#modalDelete #deleteAge').empty().val(user.age);
                        $('#modalDelete #deleteEmail').empty().val(user.email);
                        $('#modalDelete #deletePassword').empty().val(user.password);
                        $('#modalDelete option').prop('selected', false);
                        $('#modalDelete #deleteRolesSelector').empty();
                        $('#modalDelete #deleteRolesSelector').empty().append(`<option id="roleA" value="ROLE_ADMIN" requared>ADMIN</option>`)
                        $('#modalDelete #deleteRolesSelector').empty().append(`<option id="roleU" value="ROLE_USER" requared>USER</option>`)

                        roleA = $('#roleA').val();
                        roleU = $('#roleU').val();

                        for (const role of user.roles) {
                            if (role.roleName === roleA) {
                                $('#modalDelete #roleA').prop('selected', true);
                            }
                            if (role.roleName === roleU) {
                                $('#modalDelete #roleU').prop('selected', true);
                            }
                        }
                    });
              });
    });

    $("#btnEditConfirm").on('click', async f => {
        f.preventDefault();

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

        $('table #tr-' + user.id + " td[name = 'editFirstName']").html(user.firstName);
        $('table #tr-' + user.id + " td[name = 'editLastName']").html(user.lastName);
        $('table #tr-' + user.id + " td[name = 'editAge']").html(user.age);
        $('table #tr-' + user.id + " td[name = 'editEmail']").html(user.email);
        $('table #tr-' + user.id + " td[name = 'editRoles']").empty();

        user.roles.forEach(role => {
            $('#usersTableContent #tr-' + user.id + " td[name = 'editRoles']").append(`
                <span>${role.substring(5)}</span>
            `)
        });

        $('#modalEdit').modal('hide');
    });

    $('#btnDeleteConfirm').on('click', async f => {
        f.preventDefault();
        let id = $('#modalDelete #deleteId').val();
        console.log(id);
        await fetch('/admin/users/' + id, {
            method: 'DELETE'
        });

        $('table #tr-' + id).remove();

        $('#modalDelete').modal('hide');

    });

    $('#newUser #btnNewUserConfirm').on('click', async f => {
        f.preventDefault();
        console.log('inner click button');
        let user = {
            firstName: $('#newUser #newFirstName').val(),
            lastName: $('#newUser #newLastName').val(),
            age: $('#newUser #newAge').val(),
            email: $('#newUser #newEmail').val(),
            password: $('#newUser #newPassword').val(),
            roles: $('#newUser #newRolesSelector').val()
        };

        console.log(JSON.stringify(user));

        let response = await fetch('/admin/users',{
            method: 'POST',
            headers: {
                'content-type': 'application/json'
            },
            body: JSON.stringify(user)
        });

        let savedUser = await response.json();

        console.log(savedUser);
        console.log(typeof savedUser);

        $('#usersTableContent tbody').append(`
                    <tr id="tr-${savedUser.id}">
                        <td>
                            ${savedUser.id}
                        </td>
                        <td name="editFirstName">
                            ${savedUser.firstName}
                        </td>
                        <td name="editLastName">
                            ${savedUser.lastName}
                        </td>
                        <td name="editAge">
                            ${savedUser.age}
                        </td>
                        <td name="editEmail">
                            ${savedUser.email}
                        </td>
                        <td name="editRoles">
                        </td>
                    </tr>
                `);

        savedUser.roles.forEach(r => {
            $('#usersTableContent #tr-' + savedUser.id + ' td:last').append(`
                <span>${r.roleName.substring(5)}</span>
            `);
        });

        $('#usersTableContent #tr-' + savedUser.id).append(`
                    <td>
                        <button id="btn-edit-${savedUser.id}" name="editBtn" type="button" class="btn btn-info" data-toggle="modal" data-target="#modalEdit">Edit</button>
                    </td>
                    <td>
                        <button id="btn-delete-${savedUser.id}" name="deleteBtn" type="button" class="btn btn-danger" data-toggle="modal" data-target="#modalDelete">Delete</button>
                    </td>
        `);

        $('#newFirstName').val('');
        $('#newLastName').val('');
        $('#newAge').val('');
        $('#newEmail').val('');
        $('#newPassword').val('');
        $('#newRolesSelector').prop('selected', false);

        $("button[name = 'editBtn']").on("click", async f => {
            let btn = f.target;
            let id = btn.getAttribute("id").substring(9);
            fetch('/admin/users/' + id)
                .then(response => response.json())
                .then(user => {
                    $('#modalEdit #editId').val(user.id);
                    $('#modalEdit #editFirstName').val(user.firstName);
                    $('#modalEdit #editLastName').val(user.lastName);
                    $('#modalEdit #editAge').val(user.age);
                    $('#modalEdit #editEmail').val(user.email);
                    $('#modalEdit #editPassword').val(user.password);
                    $('#modalEdit option').prop('selected', false);
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

        $("button[name = 'deleteBtn']").on('click', async f => {
            let btn = f.target;
            let id = btn.getAttribute('id').substring(11);
            console.log(id);
            fetch('/admin/users/' + id)
                .then(response => response.json())
                .then(user => {
                    $('#modalDelete #deleteId').empty().val(user.id);
                    $('#modalDelete #deleteFirstName').empty().val(user.firstName);
                    $('#modalDelete #deleteLastName').empty().val(user.lastName);
                    $('#modalDelete #deleteAge').empty().val(user.age);
                    $('#modalDelete #deleteEmail').empty().val(user.email);
                    $('#modalDelete #deletePassword').empty().val(user.password);
                    $('#modalDelete option').prop('selected', false);
                    $('#modalDelete #deleteRolesSelector').empty();
                    $('#modalDelete #deleteRolesSelector').empty().append(`<option id="roleA" value="ROLE_ADMIN" requared>ADMIN</option>`)
                    $('#modalDelete #deleteRolesSelector').empty().append(`<option id="roleU" value="ROLE_USER" requared>USER</option>`)

                    roleA = $('#roleA').val();
                    roleU = $('#roleU').val();

                    for (const role of user.roles) {
                        if (role.roleName === roleA) {
                            $('#modalDelete #roleA').prop('selected', true);
                        }
                        if (role.roleName === roleU) {
                            $('#modalDelete #roleU').prop('selected', true);
                        }
                    }
                });
        });
    });
}

getUsersTable();
