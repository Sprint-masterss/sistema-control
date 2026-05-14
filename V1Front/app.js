const viewLogin = document.getElementById('view-login');
const viewDashboard = document.getElementById('view-dashboard');
const btnLogin = document.getElementById('btn-login');
const btnLogout = document.getElementById('btn-logout');

// Lógica de acceso
btnLogin.addEventListener('click', () => {
    const email = document.getElementById('inp-email').value;
    const pass = document.getElementById('inp-pass').value;

    if (email && pass) {
        viewLogin.classList.add('hidden');
        viewDashboard.classList.remove('hidden');
    } else {
        alert("Por favor completa los campos");
    }
});

// Lógica de salida
btnLogout.addEventListener('click', (e) => {
    e.preventDefault();
    viewDashboard.classList.add('hidden');
    viewLogin.classList.remove('hidden');
});