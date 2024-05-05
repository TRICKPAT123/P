document.getElementById("nameForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent the form from submitting

    var userName = document.getElementById("nameInput").value;

    // Check if the name is correct
    if (userName === "TRICKPAT") {
        window.location.href = "index.html"; // Redirect to index2.html if the name is correct
    } else {
        var message = document.getElementById("message");
        message.innerText = "hahaha, " + userName + ", madi."; // Display error message
        message.style.color = "white"; // Change text color to white
    }
});
