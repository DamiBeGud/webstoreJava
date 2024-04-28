export function toast(alertType, message) {
    let alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${alertType} alert-dismissible fade show m-3`;
    alertDiv.setAttribute('role', 'alert');
    alertDiv.style.cssText = ` position: sticky; bottom: 0px;left:100%; max-width:300px;height:60px; border-radius: 10px; z-index: 1000;`;

    
    alertDiv.innerHTML = `
    <div>
    <span style="font-size: 1.2em; overflow: hidden; text-overflow: ellipsis; position: absolute; left: 5px;">${message}</span>
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" style="background-color: transparent; border: none; position: absolute; right: 5px;">
        <span aria-hidden="true">&times;</span>
    </button>
    </div>
    `;

    let closeButton = alertDiv.querySelector('.btn-close');
    closeButton.addEventListener('click', () => closeToast(alertDiv));

    document.body.appendChild(alertDiv);
}

function closeToast(alertDiv) {
    alertDiv.classList.remove('show');
    setTimeout(() => {
        alertDiv.parentNode.removeChild(alertDiv);
    }, 500);
}
