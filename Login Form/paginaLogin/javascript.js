document.getElementById('submit-btn').addEventListener('click', async function (e) {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('http://localhost:8080/authlogin/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        const text = await response.text(); // porque o controller retorna STRING

        if (!response.ok) {
            window.location.href = "error.html"
        } else {
            window.location.href = "sucessful.html"
        }

    } catch (err) {
        window.location.href = "error.html"
    }
});