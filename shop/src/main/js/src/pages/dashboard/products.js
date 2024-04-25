export function products () {
  let productId

  function logButtonId(event) {
    productId = event.target.id;
    console.log('id:', productId);
  }

  function addEventListeners(){
    
      const buttons = document.querySelectorAll('.addStock');
      console.log(buttons)
      buttons.forEach(function(button) {
        button.addEventListener('click', logButtonId);
      });
   
  }
  addEventListeners()

  document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('addStockButton').addEventListener('click', function() {
        console.log("id" + productId);

        // Get the amount from the input field
        let stock = document.getElementById('editAmount').value;


        // Construct the request body
        const requestBody = JSON.stringify({
            id: parseInt(productId),
            stock: parseInt(stock)
        });

        // Define the fetch options
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: requestBody
        };
        // Make the fetch request
        fetch('http://localhost:8080/api/v1/product/update/stock', options)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                // Handle success response
                document.getElementById("closeAddStockSidebar").click();
                console.log('Success:', data);
                let stockElement = document.getElementById("stock-" + data.id);
                stockElement.innerHTML = "";
                stockElement.innerHTML = data.stock;
            })
            .catch(error => {
                // Handle error
                console.error('Error:', error);
            });
    });
});





  // Search
  document.addEventListener('DOMContentLoaded', function () {
    // Add click event listener to searchButton
    document
      .getElementById('searchButton')
      .addEventListener('click', function () {
        // Get id value from element with class inputid
        let id = document.querySelector('.inputid').getAttribute('id')

        // Get input value from element with id searchInput
        let searchInputValue = document.getElementById('searchInput').value

        // Create JSON object with id and searchInputValue
        let data = {
          userId: parseInt(id),
          searchString: searchInputValue
        }

        // Make HTTP request
        fetch('http://localhost:8080/api/v1/product/company/search', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(data)
        })
          .then(response => {
            if (!response.ok) {
              removeTable()
              document
                .querySelector('table')
                .insertAdjacentHTML(
                  'afterend',
                  '<p class="alert alert-danger" id="notFound">No products with that name were found.</p>'
                )
              throw new Error('Network response was not ok')
            }
            return response.json()
          })
          .then(data => {
            // Handle successful response
            console.log('Response:', data)
            updateTable(data)
            addEventListeners()
          })
          .catch(error => {
            // Handle error
            console.error('Error:', error)
          })
      })
  })

  function updateTable (products) {
    removeTable()
    let table = document.querySelector('table')
    if (products.length > 0) {
      // Rebuild table with new data
      let tbody = document.createElement('tbody')
      products.forEach(product => {
        let tr = document.createElement('tr')
        tr.innerHTML = `
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>$<span>${product.price}</span></td>
                    <td id="stock-${product.id}">${product.stock}</td>
                    <td>Category</td>
                    <td>SubCategory</td>
                    <td><button class="btn btn-primary" data-toggle="collapse" data-target="#editProductCollapse">Edit</button></td>
                    <td><button class="btn btn-secondary addStock" data-toggle="collapse" data-target="#editAmountCollapse" id="${product.id}">Add</button></td>
                    <td><button class="btn btn-danger">Delete</button></td>`
        tbody.appendChild(tr)
      })
      table.appendChild(tbody)
      console.log('rebuilt with new data')
    }
  }

  function removeTable () {
    let tbody = document.querySelector('tbody')
    let notFoundMessage = document.getElementById('notFound')
    if (tbody) {
      tbody.remove()
    }
    if (notFoundMessage) {
      notFoundMessage.remove()
    }
  }
}
