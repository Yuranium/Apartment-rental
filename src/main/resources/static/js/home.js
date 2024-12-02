document.addEventListener("DOMContentLoaded", function() {
    const inputField = document.getElementById("name");
    const suggestionsBox = document.getElementById("suggestions");

    function showSuggestions(suggestions) {
        suggestionsBox.innerHTML = "";
        const list = document.createElement("ul");

        suggestions.forEach(suggestion => {
            const listItem = document.createElement("li");
            listItem.textContent = suggestion;

            listItem.addEventListener("click", function() {
                inputField.value = suggestion;
                suggestionsBox.innerHTML = "";
            });

            list.appendChild(listItem);
        });

        suggestionsBox.appendChild(list);
    }

    inputField.addEventListener("input", function() {
        const query = inputField.value;
        if (query.length < 2) {
            suggestionsBox.innerHTML = "";
            return;
        }
        fetch(`http://localhost:8080/apartments/api/autocomplete?q=${encodeURIComponent(query)}`)
            .then(response => response.json())
            .then(data => {
                if (data.length > 0) {
                    showSuggestions(data);
                } else {
                    suggestionsBox.innerHTML = "<p>Нет предложений</p>";
                }
            })
            .catch(error => {
                console.error("Ошибка автодополнения:", error);
                suggestionsBox.innerHTML = "<p>Ошибка загрузки данных</p>";
            });
    });

    document.addEventListener("click", function(event) {
        if (event.target !== inputField) {
            suggestionsBox.innerHTML = "";
        }
    });
});