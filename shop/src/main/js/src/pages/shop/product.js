export function product(){
    document.addEventListener('DOMContentLoaded', function () {
        const form = document.querySelector('.reviewForm');
        if(form != null){
        form.addEventListener('submit', function (event) {
            event.preventDefault();
  
            const productId =parseInt(form.getAttribute('id'));
            const title = document.getElementById('reviewTitle').value;
            const review = document.getElementById('reviewContent').value;
            const rating = parseInt(document.getElementById('reviewRating').value);
            const name = document.getElementById('name').value
  
            const apiUrl = 'http://localhost:8080/api/v1/review/create';
  
            // Make the API call
            fetch(apiUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    productId: productId,
                    title: title,
                    review: review,
                    rating: rating,
                    name:name,
                }),
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                // Handle success response from the API
                console.log('Review submitted successfully:', data);
                // You can add further actions here if needed, like showing a success message to the user
            })
            .catch(error => {
                // Handle errors
                console.error('There was a problem with the review submission:', error);
                // You can add further actions here if needed, like showing an error message to the user
            });
        });
    }    
});


document.addEventListener("DOMContentLoaded", function() {
    
    const buttonsaddProductToCart = document.querySelectorAll(".addProductToCart");
    
    if(buttonsaddProductToCart != null){

    buttonsaddProductToCart.forEach(button => {

        button.addEventListener("click", function() {
            const url = button.id;
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then(response => {
                console.log("Response:", response);
                const productsInCart = document.getElementById('cartItemCount');
                let productsInCartNumber = parseInt(productsInCart.innerHTML);
                productsInCartNumber += 1;
                productsInCart.innerHTML = productsInCartNumber;
            })
            .catch(error => {
                console.error("Error:", error);
            });
        });
    });

}
});



document.addEventListener("DOMContentLoaded", function() {
    
    const removeProductFromCart = document.querySelectorAll(".removeProductFromCart");
    
    if(removeProductFromCart != null){

        removeProductFromCart.forEach(button => {

        button.addEventListener("click", function() {
            const url = button.id;
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then(response => response.json())
            .then(data => {
                console.log("Data:", data);
                const productsInCart = document.getElementById('cartItemCount');
                let productsInCartNumber = parseInt(productsInCart.innerHTML);
                productsInCartNumber -= 1;
                productsInCart.innerHTML = productsInCartNumber;
                document.getElementById('productCard-' + data).remove();
                document.getElementById('cartSummery-' + data).remove();
            })
            .catch(error => {
                console.error("Error:", error); 
            });
        });
    });

}
});

document.addEventListener('DOMContentLoaded', function() {
    const selectElementsQty = document.querySelectorAll('.qty-select');
    if(selectElementsQty != null){
    selectElementsQty.forEach(selectElement => {
        selectElement.addEventListener('change', function() {
            const selectedValue = this.value;

            const url = this.id;
            fetch(url + '/value/' + selectedValue, {
                method: 'POST', // or 'PUT'
                headers: {
                    'Content-Type': 'application/json',
                },

            })
            .then(response => response.json())
            .then(data=>{
                console.log('qty data' + data.id + data.qty + data.price)
                const cardPrice = document.getElementById('productCardPrice-' + data.id);
                const summeryPrice = document.getElementById('summaryPrice-' + data.id);
                const totalSummery = document.getElementById('totalSummary');
                let summeryPriceNum = parseFloat(summeryPrice.innerHTML);
                let totlaSummeryNum = parseFloat(totalSummery.innerHTML);
                cardPrice.innerHTML= "";
                cardPrice.innerHTML= data.price;
                summeryPrice.innerHTML= "";
                summeryPrice.innerHTML= data.price;
                totlaSummeryNum = totlaSummeryNum + data.price - summeryPriceNum;
                totalSummery.innerHTML = "";
                totalSummery.innerHTML = totlaSummeryNum
            })
            .catch(error => {
                console.error('Request failed', error);
            });
        });
    });
    }
});







}
