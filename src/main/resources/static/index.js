document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('loginForm');
    form.addEventListener('submit', attemptLogin);
});

async function attemptLogin(event) {
    event.preventDefault();

    const formData = new FormData(event.target);
    const username = formData.get('username');
    const password = formData.get('password');

    try {
        console.log("Tentativa de login com usuário:", username);
        console.log("Senha:", password);

        const response = await fetch("/user/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        if (response.ok) {
            const data = await response.json();
            console.log("Resposta do servidor:", data);
            const token = data.token;
            storeUsername(username);
            storeToken(token);
            // Redirecionar ou realizar outras operações aqui após o login bem-sucedido
        } else {
            const error = await response.text();
            console.error("Login failed:", error);
            alert("Login failed. Please check your credentials.");
        }
    } catch (error) {
        console.error("An error occurred during login:", error);
        alert("An error occurred during login. Please try again later.");
    }
}

function storeToken(token) {
    localStorage.setItem('Token', token);
}

function storeUsername(username) {
    localStorage.setItem('username', username);
}
