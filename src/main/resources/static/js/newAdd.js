document.addEventListener('DOMContentLoaded', function () {
    newUserFunction();
});

async function newUserFunction() {
    const formAddNewUser = document.forms['formNew'];
    formAddNewUser.addEventListener('submit', function (event) {
        event.preventDefault();
        let rolesNewUser = [];
        for (let i = 0; i < formAddNewUser.rolesNewUser.options.length; i++) {
            if (formAddNewUser.rolesNewUser.options[i].selected) {
                rolesNewUser.push({
                    id: parseInt(formAddNewUser.rolesNewUser.options[i].value),
                    name: "ROLE_" + formAddNewUser.rolesNewUser.options[i].text,
                    authority: "ROLE_" + formAddNewUser.rolesNewUser.options[i].text
                });
                break;
            }
        }
        const newUser = {
            firstName: formAddNewUser.querySelector('#NewUserName').value,
            lastName: formAddNewUser.querySelector('#NewLastName').value,
            age: parseInt(formAddNewUser.querySelector('#NewUserAge').value, 10),
            username: formAddNewUser.querySelector('#NewUserEmail').value,
            password: formAddNewUser.querySelector('#NewUserPass').value,
            roles: rolesNewUser
        };
        console.log('Sending user data:', JSON.stringify(newUser));
        fetch('/api/admin/save_person', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(newUser)
        }).then(response => {
            if (!response.ok) {
            throw new Error('Ошибка ввода данных');
        }
            formAddNewUser.reset();
            $('#nav-users_table-tab').click();
            allUsers();
            console.log(newUser);
        })
    });
}
