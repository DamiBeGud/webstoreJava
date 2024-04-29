export function ordersDashboard(){
    document.addEventListener("DOMContentLoaded", function() {
        let getSingleOrderDashBoard = document.querySelectorAll('.getSingleOrderDashBoard');
        if(getSingleOrderDashBoard != null){
        getSingleOrderDashBoard.forEach(function(button) {
            button.addEventListener('click', function() {
                let orderId = button.getAttribute('id');
                

                    fetch('http://localhost:8080/api/v1/orders/' + orderId)
                    .then(response => response.json())
                    .then((data)=>{
                        updateOrderModal(data);
                    })
                    .catch(error => {
                        console.log('Request failed', error);
                    });
 
            });
        });
    }
    });

    function updateOrderModal(responseData) {
        const modalTitle = document.getElementById('orderModalLabel');
        const modalBody = document.querySelector('#orderModal .modal-body table tbody');
    
        modalTitle.innerHTML = 'Order #' + responseData.id;
    
        modalBody.innerHTML = '';
    
        responseData.orderedProducts
        .forEach(product => {
            const row = document.createElement('tr');
    
            row.innerHTML = `
                <td>#${product.id}</td>
                <td>${product.name}</td>
                <td>${product.qty}</td>
                <td>$${product.price.toFixed(2)}</td>
            `;
    
            modalBody.appendChild(row);
        });
        console.log(responseData)
    }
    
    
    
    
}