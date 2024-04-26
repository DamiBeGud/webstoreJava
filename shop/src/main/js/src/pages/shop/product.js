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











}
