export function products () {
  let productId


  function updateProduct(){
   let name =  document.getElementById('productName').value
   let price = parseFloat(document.getElementById('productPrice').value) 
   let description = document.getElementById('productDescription').value 


    const postData= {
      id:parseInt(productId),
      name:name,
      price:price,
      description:description
    }
    const options = {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      },
      body: JSON.stringify(postData)
   };
    fetch('http://localhost:8080/api/v1/product/update', options)
      .then(response => {
          if (!response.ok) {
              throw new Error('Network response was not ok');
          }
          return response.json();
      })
      .then(data => {
          console.log('Response:', data);
          document.getElementById('closeEditSidebarButton').click();
          document.getElementById('name-' + data.id).innerHTML="";
          document.getElementById('name-' + data.id).innerHTML = data.name;
          document.getElementById('price-' + data.id).innerHTML = "";
          if(data.discount){
            document.getElementById('price-' + data.id).innerHTML = data.discountPrice;
          }else{
            document.getElementById('price-' + data.id).innerHTML = data.price;
          }
          document.getElementById('description' + data.id).innerHTML = "";
          document.getElementById('description' + data.id).innerHTML = data.description;
      })
      .catch(error => {
          console.error('Error:', error);
      });
  }



  document.getElementById('updateProduct').addEventListener('click',updateProduct)


  function addStockButton(event) {
    productId = event.target.id;
    console.log('id:', productId);
  }
  function editProductButton(event) {
    productId = event.target.id;
    console.log('id:', productId);

    fetch('http://localhost:8080/api/v1/product/' + productId)
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        console.log('Data:', data);
        document.getElementById('productName').value = data.name;
        document.getElementById('productPrice').value = data.price;
        document.getElementById('productDescription').value = data.description;
    })
    .catch(error => {
        console.error('Error:', error);
    });
  }

  function addEventListeners(){
      const buttonsAddStock = document.querySelectorAll('.addStock');
      console.log(buttonsAddStock)
      buttonsAddStock.forEach(function(button) {
        button.addEventListener('click', addStockButton);
      });

      const buttonsEditProduct = document.querySelectorAll('.editProduct');
      console.log(buttonsEditProduct);
      buttonsEditProduct.forEach(function(button) {
        button.addEventListener('click', editProductButton)
      })
   
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

  function updateTable (data) {
    const {products, categories, subCategories} = data;
    removeTable()
    let table = document.querySelector('table')
    console.log("pr" + products)
    if (products.length > 0) {
      // Rebuild table with new data
      let tbody = document.createElement('tbody')
      products.forEach(product => {
        let price;
        if(product.discount){
          price= product.discountPrice;
        }else{
          price = product.price;
        }
        let tr = document.createElement('tr')
        tr.innerHTML = `
                    <td>${product.id}</td>
                    <td id="name-${product.id}">${product.name}</td>
                    <td>$<span id="price-${product.id}">${price}</span></td>
                    <td id="stock-${product.id}">${product.stock}</td>
                    <td>${categories[product.category - 1].name}</td>
                    <td>${subCategories[product.subCategory - 1].name}</td>
                    <td><button class="btn btn-primary editProduct" data-toggle="collapse" data-target="#editProductCollapse" id="${product.id}">Edit</button></td>
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
