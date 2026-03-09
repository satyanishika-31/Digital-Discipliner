const months = ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "July", "Aug", "Sep", "Oct", "Nav", "Dec"];

// 1. SESSION DATA: Get specifically logged-in user email
const userEmail = localStorage.getItem('userEmail');

// Redirect if session is missing
if (!userEmail) {
    window.location.href = 'login.html';
}

// Load tasks and filter for the current user
let allTasks = JSON.parse(localStorage.getItem('habitTasks')) || [];
let tasks = allTasks.filter(t => t.userEmail === userEmail || !t.userEmail);
let myChart;

function renderProfile() {
    const listContainer = document.getElementById('habitsList');
    let totalActiveDays = 0;
    let totalPossibleDays = tasks.length * 365;

    listContainer.innerHTML = '';

    tasks.forEach(task => {
        let taskHtml = `
            <div class="habit-row border-b border-zinc-900 pb-8 mt-4">
                <h4 class="text-lg font-black text-white uppercase italic mb-6">${task.name}</h4>
                <div class="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-6 gap-4">
        `;

        months.forEach(month => {
            const days = task.progress[month] || [];
            totalActiveDays += days.length;

            taskHtml += `
                <div class="bg-zinc-900/30 p-3 rounded border border-zinc-800/50">
                    <p class="text-[9px] font-bold text-zinc-600 mb-2 uppercase">${month}</p>
                    <div class="flex flex-wrap gap-1">`;
            for (let i = 1; i <= 31; i++) {
                const active = days.includes(i);
                taskHtml += `<div class="grid-cube ${active ? 'cube-active' : 'cube-empty'}"></div>`;
            }
            taskHtml += `</div></div>`;
        });

        taskHtml += `</div></div>`;
        listContainer.innerHTML += taskHtml;
    });

    // Consistency Calculation
    let percentage = totalPossibleDays > 0 ? Math.round((totalActiveDays / totalPossibleDays) * 100) : 0;

    document.getElementById('count-habits').innerText = tasks.length;
    document.getElementById('count-total').innerText = totalActiveDays;
    document.getElementById('percentText').innerText = percentage + "%";
    document.getElementById('stat-consistency').innerText = percentage + "%";

    updateChart(percentage);
    calculateLevel(totalActiveDays);
    calculateStreakEngine(tasks); 
}

// TASK 2: STREAK ENGINE (Rule-Based Logic)
function calculateStreakEngine(userTasks) {
    let currentStreak = 0;
    const today = new Date();
    const currentMonthName = months[today.getMonth()];
    const currentDay = today.getDate();

    userTasks.forEach(task => {
        let tempStreak = 0;
        const progress = task.progress[currentMonthName] || [];
        
        // Logic: Count backwards from today
        for (let i = currentDay; i > 0; i--) {
            if (progress.includes(i)) {
                tempStreak++;
            } else if (i === currentDay) {
                continue; 
            } else {
                break; // Gap detected: Streak Reset Rule
            }
        }
        if (tempStreak > currentStreak) currentStreak = tempStreak;
    });

    updateMotivationUI(currentStreak);
    updateStreakBadge(currentStreak); // Added this call to trigger the badge update
}

// TASK 3: DISPLAY MOTIVATION
function updateMotivationUI(streak) {
    const summaryText = document.getElementById('summaryText');

    if (streak >= 10) {
        summaryText.innerHTML = `<span class="text-green-500 font-bold">HABIT MASTER:</span> ${streak} day streak! You've reached peak consistency.`;
    } else if (streak >= 4) {
        summaryText.innerHTML = `<span class="text-orange-500 font-bold">ON FIRE:</span> ${streak} days running. Keep the momentum!`;
    } else if (streak > 0) {
        summaryText.innerHTML = `<span class="text-blue-400 font-bold">SPARK:</span> ${streak} day streak. A great start!`;
    } else {
        summaryText.innerHTML = `<span class="text-zinc-500 italic">No active streak. Start a habit today to begin your journey.</span>`;
    }
}

// STREAK BADGE VISUALS
function updateStreakBadge(streak) {
    const container = document.getElementById('streak-badge-container');
    const icon = document.getElementById('badge-icon');
    const rank = document.getElementById('badge-rank');
    const status = document.getElementById('badge-status');

    if (!container) return; // Guard clause

    // Clear previous color classes
    rank.classList.remove('text-blue-400', 'text-orange-500', 'text-green-500');

    if (streak === 0) {
        container.className = "mt-6 p-4 rounded-2xl border border-zinc-800 bg-zinc-900/20";
        icon.innerText = "🌑";
        rank.innerText = "Neutral";
        status.innerText = "START A HABIT TODAY";
    } 
    else if (streak < 4) {
        container.className = "mt-6 p-4 rounded-2xl border border-blue-900/50 bg-blue-900/10 shadow-[0_0_15px_rgba(59,130,246,0.1)]";
        icon.innerText = "🧊";
        rank.innerText = "Spark";
        rank.classList.add('text-blue-400');
        status.innerText = "GATHERING COLD ENERGY";
    }
    else if (streak < 10) {
        container.className = "mt-6 p-4 rounded-2xl border border-orange-900/50 bg-orange-900/10 shadow-[0_0_15px_rgba(255,140,0,0.2)]";
        icon.innerText = "🔥";
        rank.innerText = "Momentum";
        rank.classList.add('text-orange-500');
        status.innerText = "YOU'RE ON FIRE";
    }
    else {
        container.className = "mt-6 p-4 rounded-2xl border border-green-900/50 bg-green-900/10 shadow-[0_0_20px_rgba(34,197,94,0.3)]";
        icon.innerText = "👑";
        rank.innerText = "Habit Master";
        rank.classList.add('text-green-500');
        status.innerText = "UNSTOPPABLE DISCIPLINE";
    }
}

function updateChart(percent) {
    const ctx = document.getElementById('completionChart').getContext('2d');
    if (myChart) myChart.destroy();
    myChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            datasets: [{
                data: [percent, 100 - percent],
                backgroundColor: ['#ff8c00', '#1a1a1a'],
                borderWidth: 0,
                borderRadius: 10,
            }]
        },
        options: { cutout: '85%', responsive: true, plugins: { tooltip: { enabled: false } } }
    });
}

function calculateLevel(points) {
    const levelElem = document.getElementById('userLevel');
    let level = Math.floor(points / 20) + 1;
    levelElem.innerText = level;
}

function initUserMeta() {
    const nameInput = document.getElementById('userNameInput');
    const bioInput = document.getElementById('userBio');

    nameInput.value = localStorage.getItem(`pName_${userEmail}`) || localStorage.getItem('userName') || "OPERATOR";
    nameInput.oninput = (e) => localStorage.setItem(`pName_${userEmail}`, e.target.value);

    bioInput.value = localStorage.getItem(`pBio_${userEmail}`) || "Daily goals...";
    bioInput.oninput = (e) => localStorage.setItem(`pBio_${userEmail}`, e.target.value);
}

window.addEventListener('DOMContentLoaded', () => {
    initUserMeta();
    renderProfile();
});