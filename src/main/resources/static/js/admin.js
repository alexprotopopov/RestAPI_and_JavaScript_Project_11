$(async function () {
    await allUsers();
    // deleteUser();
    // await newUser();
   // fillModalDelete();
    // editCurrentUser();
});

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
                                <button type="button" class="btn btn-sm btn-info" data-toggle="modal" 
                                id="buttonEdit" data-action="edit" data-id="${user.id}" 
                                data-target="#edit">Edit</button>
                            </td>
                            <td>
                                <button type="button" class="btn btn-sm btn-danger" data-toggle="modal" 
                                id="buttonDelete" data-action="delete" data-id="${user.id}" 
                                data-target="#delete">Delete</button>
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

// async function getUser(id) {
//     let response = await fetch("/api/admin/${id}");
//     return await response.json();
// }

// function deleteUser() {
//     const deleteForm = document.forms["formDeleteUser"];
//     deleteForm.addEventListener("submit", function (event) {
//         event.preventDefault();
//         fetch("/api/admin/" + deleteForm.id.value, {
//             method: 'DELETE',
//             headers: {
//                 'Content-Type': 'application/json'
//             }
//         })
//             .then(() => {
//                 $('#deleteFormCloseButton').click();
//                 return allUsers();
//             })
//             .catch((error) => {
//                 alert(error);
//             });
//     })
// }
//
// $(document).ready(function () {
//     $('#delete').on("show.bs.modal", function (event) {
//         const button = $(event.relatedTarget);
//         const id = button.data("id");
//         return viewDeleteModal(id);
//     })
// })
//
// async function viewDeleteModal(id) {
//     let userDelete = await getUser(id);
//     let formDelete = document.forms["formDeleteUser"];
//     formDelete.id.value = userDelete.id;
//     formDelete.firstName.value = userDelete.firstName;
//     formDelete.lastName.value = userDelete.lastName;
//     formDelete.age.value = userDelete.age;
//     formDelete.email.value = userDelete.email;
//     formDelete.password.value = userDelete.password;
//
//
//     $('#deleteRolesUser').empty();
//     await fetch("/api/admin/roles")
//         .then(r => r.json())
//         .then(roles => {
//             roles.forEach(role => {
//                 let selectedRole = false;
//                 for (let i = 0; i < userDelete.roles.length; i++) {
//                     if (userDelete.roles[i].name === role.name) {
//                         selectedRole = true;
//                         break;
//                     }
//                 }
//                 let element = document.createElement("option");
//                 element.text = role.name.substring(5);
//                 element.value = role.id;
//                 if (selectedRole) element.selected = true;
//                 $('#deleteRolesUser')[0].appendChild(element);
//             })
//         })
//         .catch((error) => {
//             alert(error);
//         })
// }


/* ---------- Delete user ---------- */
// async function fillModalDelete(form, modal, id) {
//     let user = await getUser(id);
//     const roles = user.roles.map(role => role.name).join(', ')
//     //let roles = user.roles.map(role => role.name.substring(5)).join(" ");
//     document.getElementById('idDel').setAttribute('value', user.id);
//     document.getElementById('firstnameDel').setAttribute('value', user.firstName);
//     document.getElementById('lastnameDel').setAttribute('value', user.lastName);
//     document.getElementById('ageDel').setAttribute('value', user.age);
//     document.getElementById('emailDel').setAttribute('value', user.username);
//    // document.getElementById('passwordDel').setAttribute('value', user.password);
//    document.getElementById('roleDel').setAttribute('value', roles)
// }
//
// let deleteForm = document.forms["formDeleteUser"]
// async function deleteModal(id) {
//     // const modal = new bootstrap.Modal(document.querySelector('#modalDelete'));
//     const modal = new bootstrap.Modal(document.querySelector('#delete'));
//     await fillModalDelete(deleteForm, modal, id);
//     deleteForm.addEventListener("submit", ev => {
//         fetch("/api/admin/${id}", {
//             method: 'DELETE',
//             headers: {
//                 'Content-Type': 'application/json'
//             }
//         }).then(() => {
//             allUsers();
//         });
//     });
// }
//
//
//
// async function getUser(id) {
//     let url = `/api/admin/user/${id}`;
//     let response = await fetch(url);
//     if (!response.ok) {
//         throw new Error('Network response was not ok ' + response.statusText);
//     }
//     return await response.json();
// }

/*****************************/

// /////ADD User /////
// let addForm = document.getElementById('formNew')
// addForm.addEventListener('submit', addUser)
//
// function addUser() {
//     // let roles = [];
//     // const roleAdd = addForm.querySelector('#NewUserRoles').value
//     // for (let i = 0; i < roleAdd.options.length; i++) {
//     //     if (roleAdd.options[i].selected) roles.push({
//     //         id: addForm.roles.options[i].value,
//     //         role: "ROLE_" + addForm.roles.options[i].text
//     //     });
//     // }
//     const formAddNewUser = document.forms['formNew']
//     formAddNewUser.addEventListener('submit', function (event) {
//         event.preventDefault()
//         let rolesNewUser = []
//         for (let i = 0; i < formAddNewUser.roles.options.length; i++) {
//             if (formAddNewUser.roles.options[i].selected) {
//                 rolesNewUser.push({
//                     id: formAddNewUser.roles.options[i].value,
//                     name: "ROLE_" + formAddNewUser.roles.options[i].text
//                 })
//                 break
//             }
//         }
//         const newUser = {
//             firstname: addForm.querySelector('#NewUserName').value,
//             lastname: addForm.querySelector('#NewLastName').value,
//             age: parseInt(addForm.querySelector('#NewUserAge').value, 10),
//             email: addForm.querySelector('#NewUserEmail').value,
//             password: addForm.querySelector('#NewUserPass').value,
//             role: rolesNewUser
//         }
//         fetch("/api/admin/save_person", {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             const newUser = {  firstname: addForm.querySelector('#NewUserName').value,
//                 lastname: addForm.querySelector('#NewLastName').value,
//                 age: parseInt(addForm.querySelector('#NewUserAge').value, 10),
//                 email: addForm.querySelector('#NewUserEmail').value,
//                 password: addForm.querySelector('#NewUserPass').value,
//                 role: rolesNewUser
//
//             },
//             body: JSON.stringify({
//                 firstname: addForm.querySelector('#NewUserName').value,
//                 lastname: addForm.querySelector('#NewLastName').value,
//                 age: parseInt(addForm.querySelector('#NewUserAge').value, 10),
//                 email: addForm.querySelector('#NewUserEmail').value,
//                 password: addForm.querySelector('#NewUserPass').value,
//                 role: rolesNewUser
//             })
//         }).then(() => {
//             console.log ('obj', rolesNewUser);
//
//             allUsers()
//     });
//     })
// }
