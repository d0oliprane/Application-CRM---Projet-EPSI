document.addEventListener('DOMContentLoaded', function() {
    let mdp = document.getElementById('mdp');
    let confirmMdp = document.getElementById('ConfirmMdp');
    let mdpError = document.getElementById('mdpError');

    function validatePassword() {
        if (mdp.value !== confirmMdp.value) {
            mdpError.classList.remove('hidden');
            return false;
        } else {
            mdpError.classList.add('hidden');
            return true;
        }
    }

    document.querySelector('form').addEventListener('submit', function(event) {
        if (!validatePassword()) {
            event.preventDefault();
        }
    });
});