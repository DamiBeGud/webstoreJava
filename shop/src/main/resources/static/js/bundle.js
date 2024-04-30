(()=>{"use strict";function e(e,t){let n=0,o=document.createElement("div"),d=document.querySelectorAll(".alert");d.length>0&&(n=70*d.length),o.className=`alert alert-${e} alert-dismissible fade show m-3`,o.setAttribute("role","alert"),o.style.cssText=`position: sticky; bottom: ${n}px; left: 100%; max-width: 300px; height: 60px; border-radius: 10px; z-index: 1000;`,o.innerHTML=`\n    <div>\n    <span style="font-size: 1.2em; overflow: hidden; text-overflow: ellipsis; position: absolute; left: 5px;">${t}</span>\n    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" style="background-color: transparent; border: none; position: absolute; right: 5px;">\n        <span aria-hidden="true">&times;</span>\n    </button>\n    </div>\n    `,o.querySelector(".btn-close").addEventListener("click",(()=>function(e){e.classList.remove("show"),setTimeout((()=>{e.parentNode.removeChild(e)}),500)}(o))),document.body.appendChild(o)}!function(){let e,t=document.getElementById("updateProduct");function n(t){e=t.target.id,console.log("id:",e)}function o(t){e=t.target.id,console.log("id:",e),fetch("http://localhost:8080/api/v1/product/"+e).then((e=>{if(!e.ok)throw new Error("Network response was not ok");return e.json()})).then((e=>{console.log("Data:",e),document.getElementById("productName").value=e.name,document.getElementById("productPrice").value=e.price,document.getElementById("productDescription").value=e.description})).catch((e=>{console.error("Error:",e)}))}function d(){const e=document.querySelectorAll(".addStock");console.log(e),null!=e&&e.forEach((function(e){e.addEventListener("click",n)}));const t=document.querySelectorAll(".editProduct");console.log(t),null!=t&&t.forEach((function(e){e.addEventListener("click",o)}))}function r(){let e=document.querySelector("tbody"),t=document.getElementById("notFound");e&&e.remove(),t&&t.remove()}null!=t&&t.addEventListener("click",(function(){let t=document.getElementById("productName").value,n=parseFloat(document.getElementById("productPrice").value),o=document.getElementById("productDescription").value;const d={id:parseInt(e),name:t,price:n,description:o},r={method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify(d)};fetch("http://localhost:8080/api/v1/product/update",r).then((e=>{if(!e.ok)throw new Error("Network response was not ok");return e.json()})).then((e=>{console.log("Response:",e),document.getElementById("closeEditSidebarButton").click(),document.getElementById("name-"+e.id).innerHTML="",document.getElementById("name-"+e.id).innerHTML=e.name,document.getElementById("price-"+e.id).innerHTML="",e.discount?document.getElementById("price-"+e.id).innerHTML=e.discountPrice:document.getElementById("price-"+e.id).innerHTML=e.price,document.getElementById("description"+e.id).innerHTML="",document.getElementById("description"+e.id).innerHTML=e.description})).catch((e=>{console.error("Error:",e)}))})),d(),document.addEventListener("DOMContentLoaded",(function(){let t=document.getElementById("addStockButton");null!=t&&t.addEventListener("click",(function(){console.log("id"+e);let t=document.getElementById("editAmount").value;const n=JSON.stringify({id:parseInt(e),stock:parseInt(t)});fetch("http://localhost:8080/api/v1/product/update/stock",{method:"POST",headers:{"Content-Type":"application/json"},body:n}).then((e=>{if(!e.ok)throw new Error("Network response was not ok");return e.json()})).then((e=>{document.getElementById("closeAddStockSidebar").click(),console.log("Success:",e);let t=document.getElementById("stock-"+e.id);t.innerHTML="",t.innerHTML=e.stock})).catch((e=>{console.error("Error:",e)}))}))})),document.addEventListener("DOMContentLoaded",(function(){let e=document.getElementById("searchButton");null!=e&&e.addEventListener("click",(function(){let e=document.querySelector(".inputid").getAttribute("id"),t=document.getElementById("searchInput").value,n={userId:parseInt(e),searchString:t};fetch("http://localhost:8080/api/v1/product/company/search",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify(n)}).then((e=>{if(!e.ok)throw r(),document.querySelector("table").insertAdjacentHTML("afterend",'<p class="alert alert-danger" id="notFound">No products with that name were found.</p>'),new Error("Network response was not ok");return e.json()})).then((e=>{console.log("Response:",e),function(e){const{products:t,categories:n,subCategories:o}=e;r();let d=document.querySelector("table");if(console.log("pr"+t),t.length>0){let e=document.createElement("tbody");t.forEach((t=>{let d;d=t.discount?t.discountPrice:t.price;let r=document.createElement("tr");r.innerHTML=`\n                    <td>${t.id}</td>\n                    <td id="name-${t.id}">${t.name}</td>\n                    <td>$<span id="price-${t.id}">${d}</span></td>\n                    <td id="stock-${t.id}">${t.stock}</td>\n                    <td>${n[t.category-1].name}</td>\n                    <td>${o[t.subCategory-1].name}</td>\n                    <td><button class="btn btn-primary editProduct" data-toggle="collapse" data-target="#editProductCollapse" id="${t.id}">Edit</button></td>\n                    <td><button class="btn btn-secondary addStock" data-toggle="collapse" data-target="#editAmountCollapse" id="${t.id}">Add</button></td>\n                    <td><button class="btn btn-danger">Delete</button></td>`,e.appendChild(r)})),d.appendChild(e),console.log("rebuilt with new data")}}(e),d()})).catch((e=>{console.error("Error:",e)}))}))}))}(),document.addEventListener("DOMContentLoaded",(function(){const t=document.querySelector(".reviewForm");null!=t&&t.addEventListener("submit",(function(n){n.preventDefault();const o=parseInt(t.getAttribute("id")),d=document.getElementById("reviewTitle").value,r=document.getElementById("reviewContent").value,c=parseInt(document.getElementById("reviewRating").value),i=document.getElementById("name").value;fetch("http://localhost:8080/api/v1/review/create",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({productId:o,title:d,review:r,rating:c,name:i})}).then((e=>{if(!e.ok)throw new Error("Network response was not ok");return e.json()})).then((t=>{console.log("Review submitted successfully:",t),e("success","Review Submited")})).catch((t=>{console.error("There was a problem with the review submission:",t),e("alert","Error")}))}))})),document.addEventListener("DOMContentLoaded",(function(){const t=document.querySelectorAll(".addProductToCart");null!=t&&t.forEach((t=>{t.addEventListener("click",(function(){const n=t.id;fetch(n,{method:"POST",headers:{"Content-Type":"application/json"}}).then((t=>{console.log("Response:",t);const n=document.getElementById("cartItemCount");let o=parseInt(n.innerHTML);o+=1,n.innerHTML=o,e("success","Product added to Cart")})).catch((t=>{console.error("Error:",t),e("alert","Error")}))}))}))})),document.addEventListener("DOMContentLoaded",(function(){const t=document.querySelectorAll(".removeProductFromCart");null!=t&&t.forEach((t=>{t.addEventListener("click",(function(){const n=t.id;fetch(n,{method:"POST",headers:{"Content-Type":"application/json"}}).then((e=>e.json())).then((t=>{console.log("Data:",t);const n=document.getElementById("cartItemCount");let o=parseInt(n.innerHTML);o-=1,n.innerHTML=o,document.getElementById("productCard-"+t).remove(),document.getElementById("cartSummery-"+t).remove(),e("success","Product removed from cart")})).catch((t=>{console.error("Error:",t),e("alert","Error")}))}))}))})),document.addEventListener("DOMContentLoaded",(function(){const e=document.querySelectorAll(".qty-select");null!=e&&e.forEach((e=>{e.addEventListener("change",(function(){const e=this.value,t=this.id;fetch(t+"/value/"+e,{method:"POST",headers:{"Content-Type":"application/json"}}).then((e=>e.json())).then((e=>{console.log("qty data"+e.id+e.qty+e.price);const t=document.getElementById("productCardPrice-"+e.id),n=document.getElementById("summaryPrice-"+e.id),o=document.getElementById("totalSummary");let d=parseFloat(n.innerHTML),r=parseFloat(o.innerHTML);t.innerHTML="",t.innerHTML=e.price,n.innerHTML="",n.innerHTML=e.price,r=r+e.price-d,o.innerHTML="",o.innerHTML=r})).catch((e=>{console.error("Request failed",e)}))}))}))})),document.addEventListener("DOMContentLoaded",(()=>{let e=document.getElementById("paymentMethod");null!=e&&e.addEventListener("change",(()=>{let t=e.value,n=document.getElementById("creditCardForm"),o=document.getElementById("paypalForm");"creditCard"===t?(n.style.display="block",o.style.display="none"):"paypal"===t?(n.style.display="none",o.style.display="block"):(n.style.display="none",o.style.display="none")}))})),document.addEventListener("DOMContentLoaded",(()=>{document.querySelectorAll(".rating-class").forEach((e=>{const t=parseInt(e.getAttribute("id"));if(0!=t)for(let n=0;n<5;n++){const o=document.createElement("span");o.classList="rating",o.innerHTML=n<=t?"&#9733;":"&#9734;",e.appendChild(o)}else{const t=document.createElement("span");t.innerHTML="No reviews",e.appendChild(t)}}))})),document.addEventListener("DOMContentLoaded",(function(){let e=document.querySelectorAll(".getSingleOrderDashBoard");null!=e&&e.forEach((function(e){e.addEventListener("click",(function(){let t=e.getAttribute("id");fetch("http://localhost:8080/api/v1/orders/"+t).then((e=>e.json())).then((e=>{!function(e){const t=document.getElementById("orderModalLabel"),n=document.querySelector("#orderModal .modal-body table tbody");t.innerHTML="Order #"+e.id,n.innerHTML="",e.orderedProducts.forEach((e=>{const t=document.createElement("tr");t.innerHTML=`\n                <td>#${e.id}</td>\n                <td>${e.name}</td>\n                <td>${e.qty}</td>\n                <td>$${e.price.toFixed(2)}</td>\n            `,n.appendChild(t)})),console.log(e)}(e)})).catch((e=>{console.log("Request failed",e)}))}))}))})),document.addEventListener("DOMContentLoaded",(function(){let e=document.getElementById("searchInput"),t=document.getElementById("searchSuggestions");null!=e&&(e.addEventListener("input",(function(){let n=e.value.toLowerCase();""!==n.trim()?function(e){fetch(`/api/v1/product/search?search=${encodeURIComponent(e)}`).then((e=>e.json())).then((e=>{!function(e){let t=document.getElementById("searchSuggestions"),n="";e.forEach((e=>{n+=`\n            <div style="\n                display: flex;\n                align-items: center;\n                padding: 10px;\n                background-color: #f9f9f9;\n                border-bottom: 1px solid #ccc;\n                cursor: pointer;\n            ">\n            <img src="${e.image}" alt="${e.name}" style="width: 50px; height: 50px; margin-right: 10px;">\n            <div>\n                <a href="http://localhost:8080/product/${e.id}" style="color:black">\n                    <div>${e.name}</div>\n                    <div>$${e.price}</div>\n                </a>\n                </div>\n            </div>\n        `})),t.innerHTML=n,t.style.display="block"}(e)})).catch((e=>{console.error("Error fetching suggestions:",e)}))}(n):t.style.display="none"})),t.addEventListener("click",(function(n){"DIV"===n.target.tagName&&(e.value=n.target.innerText,t.style.display="none")})),document.addEventListener("click",(function(n){t.contains(n.target)||n.target===e||(t.style.display="none")})))}))})();