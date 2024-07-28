$(async function () {
    await allUsers();
    deleteUser();
    editCurrentUser();
});

///Заполнение таблицы на главной странице////
async function allUsers() {
    const table = $('#bodyAllUserTable');
    table.empty()
    fetch("/api/admin")
        .then(r => r.json())
        .then(data => {
            if (Array.isArray(data)) {
                data.forEach(user => {
                    let users = `$(
                        <tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td> ${user.lastName}</td>
                        <td> ${user.age}</td>
                        <td>${user.username}</td>
                        <td>${user.roles.map(role => " " + role.name.substring(5))}</td>
                            <td>
                                <button type="button" class="btn btn-sm btn-info" data-bs-toggle="modal" 
                                id="buttonEdit" data-action="edit" data-id="${user.id}" 
                               data-bs-target="#edit">Edit</button>
                            </td>
                           
                            <td>
                                <button type="button" class="btn btn-sm btn-danger" data-bs-toggle="modal" 
                                id="buttonDelete" data-action="delete" data-id="${user.id}" 
                                data-bs-target="#delete">Delete</button>
                            </td>
                        </tr>)`;
                    table.append(users);
                })
            } else {
                console.error('Data is not an array.');
            }
        })
        .catch((error) => {
            alert(error);
        })
}

async function getUser(id) {
    let url = `/api/admin/user/${id}`;
    let response = await fetch(url);
    return await response.json();
}

///DELETE///

function deleteUser() {
    const deleteForm = document.forms["formDeleteUser"];
    //formAddNewUser.addEventListener('submit', function(event)
    deleteForm.addEventListener('submit', function (event) {
        event.preventDefault();
        let url = "/api/admin/delete/" + deleteForm.idDel.value;
        fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(() => {
                $('#deleteFormCloseButton').click();
                return allUsers();
            })
            .catch((error) => {
                alert(error);
            });
    })
}

$(document).ready(function () {
    $('#delete').on("show.bs.modal", function (event) {
        const button = $(event.relatedTarget);
        const id = button.data("id");
        return viewDeleteModal(id);
    })
})

async function viewDeleteModal(id) {
    let userDelete = await getUser(id);
    let formDelete = document.forms["formDeleteUser"];
    formDelete.idDel.value = userDelete.id;
    formDelete.firstNameDel.value = userDelete.firstName;
    formDelete.lastNameDel.value = userDelete.lastName;
    formDelete.ageDel.value = userDelete.age;
    formDelete.emailDel.value = userDelete.username;
    $('#deleteRolesUser').empty();
    await fetch("/api/admin/roles")
        .then(r => r.json())
        .then(roles => {
            roles.forEach(role => {
                let selectedRole = false;
                for (let i = 0; i < userDelete.roles.length; i++) {
                    if (userDelete.roles[i].name === role.name) {
                        selectedRole = true;
                        break;
                    }
                }
                let element = document.createElement("option");
                element.text = role.name.substring(5);
                element.value = role.id;
                if (selectedRole) element.selected = true;
                $('#deleteRolesUser')[0].appendChild(element);
            })
        })
        .catch((error) => {
            alert(error);
        })
}

///EDIT///

function editCurrentUser() {
    const editForm = document.forms["formEditUser"];
    editForm.addEventListener("submit", function (event) {
        event.preventDefault();
        let editUserRoles = [];
        for (let i = 0; i < editForm.editRolesUser.options.length; i++) {
            if (editForm.editRolesUser.options[i].selected)
                editUserRoles.push({
                    id: parseInt(editForm.editRolesUser.options[i].value),
                    name: "ROLE_" + editForm.editRolesUser.options[i].text,
                    authority: "ROLE_" + editForm.editRolesUser.options[i].text
                });
        }

        const userEdit = {
            id: parseInt(editForm.querySelector('#idEdit').value),
            firstName: editForm.querySelector('#firstNameEdit').value,
            lastName: editForm.querySelector('#lastNameEdit').value,
            age: parseInt(editForm.querySelector('#ageEdit').value, 10),
            username: editForm.querySelector('#emailEdit').value,
            password: editForm.querySelector('#passwordEdit').value,
            roles: editUserRoles
        };
        console.log('Sending user data:', JSON.stringify(userEdit));
        const url = "/api/admin/update/" + editForm.querySelector('#idEdit').value;

        fetch(url, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(userEdit)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Ошибка ввода данных');
                }
                $('#editFormCloseButton').click();
                return allUsers();
            })
            .catch((error) => {
                alert('Error: ' + error.message);
            });
    });
}

$(document).ready(function () {
    $('#edit').on("show.bs.modal", function (event) {
        const button = $(event.relatedTarget);
        const id = button.data("id");
        return viewEditModal(id);
    });
});

async function viewEditModal(id) {
    let userEdit = await getUser(id);
    let form = document.forms["formEditUser"];
    form.idEdit.value = userEdit.id;
    form.firstNameEdit.value = userEdit.firstName;
    form.lastNameEdit.value = userEdit.lastName;
    form.ageEdit.value = userEdit.age;
    form.emailEdit.value = userEdit.username;
    form.passwordEdit.value = userEdit.password;

    $('#editRolesUser').empty();

    await fetch("/api/admin/roles")
        .then(r => r.json())
        .then(roles => {
            roles.forEach(role => {
                let selectedRole = false;
                for (let i = 0; i < userEdit.roles.length; i++) {
                    if (userEdit.roles[i].name === role.name) {
                        selectedRole = true;
                        break;
                    }
                }
                let element = document.createElement("option");
                element.text = role.name.substring(5);
                element.value = role.id;
                if (selectedRole) element.selected = true;
                $('#editRolesUser')[0].appendChild(element);
            });
        })
        .catch((error) => {
            alert(error);
        });
}



