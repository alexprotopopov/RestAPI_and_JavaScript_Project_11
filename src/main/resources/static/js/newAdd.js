async function newUser() {
    const formAddNewUser = document.forms['formNew']
    formAddNewUser.addEventListener('userAddBtn', function (event) {
        event.preventDefault()
        let rolesNewUser = []
        for (let i = 0; i < formAddNewUser.rolesNewUser.options.length; i++) {
            if (formAddNewUser.rolesNewUser.options[i].selected) {
                rolesNewUser.push({
                    id: formAddNewUser.rolesNewUser.options[i].value,
                    name: "ROLE_" + formAddNewUser.rolesNewUser.options[i].text
                })
                break
            }}
        const newUser = {
            firstname: formAddNewUser.querySelector('#NewUserName').value,
            lastname: formAddNewUser.querySelector('#NewLastName').value,
            age: parseInt(formAddNewUser.querySelector('#NewUserAge').value, 10),
            email: formAddNewUser.querySelector('#NewUserEmail').value,
            password: formAddNewUser.querySelector('#NewUserPass').value,
            role: rolesNewUser        }
        fetch('/api/admin/save_person', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(newUser)
        }).then(() => {
            formAddNewUser.reset()
            $('#nav-users_table-tab').click();
            allUsers()
            console.log(newUser)
        })
    })
}
