async function handleLogin(event) {
    event.preventDefault();

    const email = event.target.email.value;
    const password = event.target.password.value;
    const statusLabel = document.getElementById('status-label');

    try {
        const response = await fetch('http://localhost:8081/api/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password }),
        });

        const data = await response.json();

        // --- PUT THE BLOCK HERE ---
        // Inside your handleLogin function

        if (response.ok) {
            // 1. Wipe any old "User A" data from the browser
            localStorage.clear();

            // 2. Save "User B" data from the Java response
            localStorage.setItem('userEmail', data.email);
            localStorage.setItem('userName', data.fullName); 

            statusLabel.innerText = "Login successful! Redirecting...";
            statusLabel.style.color = "#22c55e"; 
            
            // 3. Move to the habit page
            setTimeout(() => {
                window.location.href = 'habit.html';
            }, 1000);
        } else {
            // Handle wrong password/email
            statusLabel.innerText = data.error || "Invalid credentials.";
            statusLabel.style.color = "#ef4444";
        }
        // --------------------------

    } catch (error) {
        console.error("Error:", error);
        statusLabel.innerText = "Server error. Is your Java app running?";
    }
}