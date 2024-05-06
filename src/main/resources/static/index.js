document.getElementById('loginForm').addEventListener('submit', attemptLogin);
document.getElementById('audioFileInput').addEventListener('change', uploadAudio);

async function attemptLogin(event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

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
            localStorage.setItem('token', token);

            document.getElementById('login').style.display = 'none';
            document.getElementById('uploadBox').style.display = 'block';
            document.getElementById('audioBox').style.display = 'block';
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

async function uploadAudio(event) {
    const fileInput = event.target;
    const file = fileInput.files[0];

    try {
        const formData = new FormData();
        formData.append('file', file);

        const token = localStorage.getItem('token');

        const response = await fetch('/api/transcription/audio', {
            method: 'POST',
            body: formData,
            headers: {
                Authorization: `Bearer ${token}`
            }
        });

        if (!response.ok) {
            throw new Error('Erro no envio do arquivo de áudio.');
        }

        const data = await response.json();
        console.log('Transcrição do áudio:', data);

    } catch (error) {
        console.error('Erro durante o upload de áudio:', error);
    }
}