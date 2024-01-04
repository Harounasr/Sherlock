document.addEventListener('DOMContentLoaded', function () {
    var messages = document.querySelector('.popup-notifications');
    if (messages && messages.innerHTML.trim() !== '') {
        messages.style.display = 'block';
        setTimeout(function () {
            messages.style.display = 'none';
        }, 5000);
    }
});
