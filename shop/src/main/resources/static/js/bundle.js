(()=>{"use strict";!function(){let e,t=document.getElementById("updateProduct");function n(t){e=t.target.id,console.log("id:",e)}function o(t){e=t.target.id,console.log("id:",e),fetch("http://localhost:8080/api/v1/product/"+e).then((e=>{if(!e.ok)throw new Error("Network response was not ok");return e.json()})).then((e=>{console.log("Data:",e),document.getElementById("productName").value=e.name,document.getElementById("productPrice").value=e.price,document.getElementById("productDescription").value=e.description})).catch((e=>{console.error("Error:",e)}))}function d(){const e=document.querySelectorAll(".addStock");console.log(e),null!=e&&e.forEach((function(e){e.addEventListener("click",n)}));const t=document.querySelectorAll(".editProduct");console.log(t),null!=t&&t.forEach((function(e){e.addEventListener("click",o)}))}function r(){let e=document.querySelector("tbody"),t=document.getElementById("notFound");e&&e.remove(),t&&t.remove()}null!=t&&t.addEventListener("click",(function(){let t=document.getElementById("productName").value,n=parseFloat(document.getElementById("productPrice").value),o=document.getElementById("productDescription").value;const d={id:parseInt(e),name:t,price:n,description:o},r={method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify(d)};fetch("http://localhost:8080/api/v1/product/update",r).then((e=>{if(!e.ok)throw new Error("Network response was not ok");return e.json()})).then((e=>{console.log("Response:",e),document.getElementById("closeEditSidebarButton").click(),document.getElementById("name-"+e.id).innerHTML="",document.getElementById("name-"+e.id).innerHTML=e.name,document.getElementById("price-"+e.id).innerHTML="",e.discount?document.getElementById("price-"+e.id).innerHTML=e.discountPrice:document.getElementById("price-"+e.id).innerHTML=e.price,document.getElementById("description"+e.id).innerHTML="",document.getElementById("description"+e.id).innerHTML=e.description})).catch((e=>{console.error("Error:",e)}))})),d(),document.addEventListener("DOMContentLoaded",(function(){let t=document.getElementById("addStockButton");null!=t&&t.addEventListener("click",(function(){console.log("id"+e);let t=document.getElementById("editAmount").value;const n=JSON.stringify({id:parseInt(e),stock:parseInt(t)});fetch("http://localhost:8080/api/v1/product/update/stock",{method:"POST",headers:{"Content-Type":"application/json"},body:n}).then((e=>{if(!e.ok)throw new Error("Network response was not ok");return e.json()})).then((e=>{document.getElementById("closeAddStockSidebar").click(),console.log("Success:",e);let t=document.getElementById("stock-"+e.id);t.innerHTML="",t.innerHTML=e.stock})).catch((e=>{console.error("Error:",e)}))}))})),document.addEventListener("DOMContentLoaded",(function(){let e=document.getElementById("searchButton");null!=e&&e.addEventListener("click",(function(){let e=document.querySelector(".inputid").getAttribute("id"),t=document.getElementById("searchInput").value,n={userId:parseInt(e),searchString:t};fetch("http://localhost:8080/api/v1/product/company/search",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify(n)}).then((e=>{if(!e.ok)throw r(),document.querySelector("table").insertAdjacentHTML("afterend",'<p class="alert alert-danger" id="notFound">No products with that name were found.</p>'),new Error("Network response was not ok");return e.json()})).then((e=>{console.log("Response:",e),function(e){const{products:t,categories:n,subCategories:o}=e;r();let d=document.querySelector("table");if(console.log("pr"+t),t.length>0){let e=document.createElement("tbody");t.forEach((t=>{let d;d=t.discount?t.discountPrice:t.price;let r=document.createElement("tr");r.innerHTML=`\n                    <td>${t.id}</td>\n                    <td id="name-${t.id}">${t.name}</td>\n                    <td>$<span id="price-${t.id}">${d}</span></td>\n                    <td id="stock-${t.id}">${t.stock}</td>\n                    <td>${n[t.category-1].name}</td>\n                    <td>${o[t.subCategory-1].name}</td>\n                    <td><button class="btn btn-primary editProduct" data-toggle="collapse" data-target="#editProductCollapse" id="${t.id}">Edit</button></td>\n                    <td><button class="btn btn-secondary addStock" data-toggle="collapse" data-target="#editAmountCollapse" id="${t.id}">Add</button></td>\n                    <td><button class="btn btn-danger">Delete</button></td>`,e.appendChild(r)})),d.appendChild(e),console.log("rebuilt with new data")}}(e),d()})).catch((e=>{console.error("Error:",e)}))}))}))}(),document.addEventListener("DOMContentLoaded",(function(){const e=document.querySelector(".reviewForm");null!=e&&e.addEventListener("submit",(function(t){t.preventDefault();const n=parseInt(e.getAttribute("id")),o=document.getElementById("reviewTitle").value,d=document.getElementById("reviewContent").value,r=parseInt(document.getElementById("reviewRating").value),c=document.getElementById("name").value;fetch("http://localhost:8080/api/v1/review/create",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({productId:n,title:o,review:d,rating:r,name:c})}).then((e=>{if(!e.ok)throw new Error("Network response was not ok");return e.json()})).then((e=>{console.log("Review submitted successfully:",e)})).catch((e=>{console.error("There was a problem with the review submission:",e)}))}))})),document.addEventListener("DOMContentLoaded",(function(){const e=document.querySelectorAll(".addProductToCart");null!=e&&e.forEach((e=>{e.addEventListener("click",(function(){const t=e.id;fetch(t,{method:"POST",headers:{"Content-Type":"application/json"}}).then((e=>{console.log("Response:",e)})).catch((e=>{console.error("Error:",e)}))}))}))})),document.addEventListener("DOMContentLoaded",(()=>{document.querySelectorAll(".rating-class").forEach((e=>{const t=parseInt(e.getAttribute("id"));if(0!=t)for(let n=0;n<5;n++){const o=document.createElement("span");o.classList="rating",o.innerHTML=n<=t?"&#9733;":"&#9734;",e.appendChild(o)}else{const t=document.createElement("span");t.innerHTML="No reviews",e.appendChild(t)}}))}))})();