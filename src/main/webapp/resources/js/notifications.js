document.addEventListener('DOMContentLoaded', function () {
  const messages = document.querySelector('.popup-notifications')
  if (messages && messages.innerHTML.trim() !== '') {
    messages.style.display = 'block';
    setTimeout(function () {
      messages.style.display = 'none'
    }, 5000)
  }
})
