document.getElementById('loginForm').addEventListener('submit', attemptLogin);

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

      // Esconder elementos de login
      document.getElementById('login').style.display = 'none';

      // Exibir elementos de upload de arquivo e área de pré-visualização
      document.getElementById('uploadBox').style.display = 'block';

      // Exibir elementos de upload de arquivo e área de pré-visualização
      document.getElementById('arquivo').style.display = 'block';
      document.getElementById('preview').style.display = 'block';
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

const input = document.querySelector('#arquivo');
input.addEventListener('change', async function() {
    const arquivo = this.files[0];
    const leitor = new FileReader();

    leitor.addEventListener('load', async function() {
        const formData = new FormData();
        formData.append('file', arquivo);

        try {
            const response = await fetch('/api/transcription/audio', {
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                const data = await response.json();
                console.log('Transcrição do áudio:', data.transcription);
            } else {
                console.error('Erro ao enviar arquivo de áudio:', response.status);
            }
        } catch (error) {
            console.error('Erro durante o upload de áudio:', error);
        }
    });

    leitor.readAsArrayBuffer(arquivo);
    preview.value = leitor.result;
});
