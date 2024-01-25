function copyToClipboard(elementId) {
  const textElement = document.querySelector("[id$='" + elementId + "']")
  if (textElement) {
    const text = textElement.innerText
    navigator.clipboard.writeText(text).then(function() {
      console.log('Text successfully copied to clipboard')
    }).catch(function(err) {
      console.error('Error in copying text: ', err)
    })
  } else {
    console.error('Element with ID suffix ' + elementId + ' not found')
  }
}
