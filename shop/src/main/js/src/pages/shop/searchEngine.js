export function searchEngine(){

    document.addEventListener('DOMContentLoaded', function() {
        let searchInput = document.getElementById('searchInput');
        let searchSuggestions = document.getElementById('searchSuggestions');
        if(searchInput != null){


        searchInput.addEventListener('input', function() {
            let query = searchInput.value.toLowerCase();
            if (query.trim() === '') {
                searchSuggestions.style.display = 'none';
                return;
            }
            
            fetchSuggestions(query);
        });
        searchSuggestions.addEventListener('click', function(event) {
            if (event.target.tagName === 'DIV') {
                searchInput.value = event.target.innerText;
                searchSuggestions.style.display = 'none';
            }
        });
    
        document.addEventListener('click', function(event) {
            if (!searchSuggestions.contains(event.target) && event.target !== searchInput) {
                searchSuggestions.style.display = 'none';
            }
        });
    }
});


}
function fetchSuggestions(query) {
    fetch(`/api/v1/product/search?search=${encodeURIComponent(query)}`)
        .then(response => response.json())
        .then(data => {
            displaySuggestions(data);
        })
        .catch(error => {
            console.error('Error fetching suggestions:', error);
        });
}

function displaySuggestions(suggestions) {
    let searchSuggestions = document.getElementById('searchSuggestions');
    let suggestionHTML = '';
    suggestions.forEach(item => {
        suggestionHTML += `
            <div style="
                display: flex;
                align-items: center;
                padding: 10px;
                background-color: #f9f9f9;
                border-bottom: 1px solid #ccc;
                cursor: pointer;
            ">
            <img src="${item.image}" alt="${item.name}" style="width: 50px; height: 50px; margin-right: 10px;">
            <div>
                <a href="http://localhost:8080/product/${item.id}" style="color:black">
                    <div>${item.name}</div>
                    <div>$${item.price}</div>
                </a>
                </div>
            </div>
        `;
    });
    searchSuggestions.innerHTML = suggestionHTML;
    searchSuggestions.style.display = 'block';
}