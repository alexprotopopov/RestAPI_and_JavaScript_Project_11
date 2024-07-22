$(async function () {
    await getUserA()
})

function getUserA() {
    let table = $('#CurrentUser tbody');
    table.empty();

    fetch("/api/user").then(res => res.json())
        .then(user => {
            const roles = user.roles.map(role => role.name).join(', ')
            $('#navbar-email').append(`<span>${user.username}</span>`)
            $('#navbar-roles').append(`<span>${user.roles.map(role => " " + role.name.substring(5))}</span>`)
            let tabContent = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.age}</td>
                            <td>${user.username}</td>
                           <td>${user.roles.map(role => " " + role.name.substring(5))}</td>
                        </tr>
                )`;
            table.append(tabContent);
        })

}