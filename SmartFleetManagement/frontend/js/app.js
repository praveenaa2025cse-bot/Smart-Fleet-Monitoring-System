/* ===================================================================
   Smart Fleet Management System - Core Frontend Logic (app.js)
   Shared across every page: API helper, auth guard, sidebar, formatters.
   =================================================================== */

// Change this if your Spring Boot backend runs on a different host/port.
const API_BASE = "http://localhost:8080/api";

/* ---------------- Auth Guard ---------------- */

function getCurrentUser() {
  const raw = sessionStorage.getItem("fleetUser");
  return raw ? JSON.parse(raw) : null;
}

function requireLogin() {
  const user = getCurrentUser();
  if (!user) {
    window.location.href = "login.html";
    return null;
  }
  return user;
}

function logout() {
  sessionStorage.removeItem("fleetUser");
  window.location.href = "login.html";
}

/* ---------------- Generic Fetch Helper ---------------- */

async function apiRequest(path, options = {}) {
  const response = await fetch(`${API_BASE}${path}`, {
    headers: { "Content-Type": "application/json" },
    ...options,
  });

  let body = null;
  const text = await response.text();
  if (text) {
    try {
      body = JSON.parse(text);
    } catch (e) {
      body = text;
    }
  }

  if (!response.ok) {
    const message = (body && body.message) ? body.message : `Request failed (${response.status})`;
    throw new Error(message);
  }
  return body;
}

const api = {
  get: (path) => apiRequest(path, { method: "GET" }),
  post: (path, data) => apiRequest(path, { method: "POST", body: JSON.stringify(data) }),
  put: (path, data) => apiRequest(path, { method: "PUT", body: JSON.stringify(data) }),
  del: (path) => apiRequest(path, { method: "DELETE" }),
};

/* ---------------- Sidebar / Topbar Rendering ---------------- */

const NAV_ITEMS = [
  { href: "dashboard.html", label: "Dashboard", icon: "📊" },
  { href: "vehicles.html", label: "Vehicles", icon: "🚗" },
  { href: "drivers.html", label: "Drivers", icon: "🧑‍✈️" },
  { href: "trips.html", label: "Trips", icon: "🗺️" },
  { href: "fuel.html", label: "Fuel", icon: "⛽" },
  { href: "maintenance.html", label: "Maintenance", icon: "🔧" },
  { href: "reports.html", label: "Reports", icon: "📈" },
];

function renderShell(activePage, pageTitle) {
  const user = requireLogin();
  if (!user) return;

  const currentPath = window.location.pathname.split("/").pop();

  const navHtml = NAV_ITEMS.map((item) => {
    const isActive = item.href === activePage || item.href === currentPath;
    return `<a href="${item.href}" class="${isActive ? "active" : ""}">
              <span>${item.icon}</span><span class="text">${item.label}</span>
            </a>`;
  }).join("");

  const initials = (user.fullName || user.username || "?").trim().charAt(0).toUpperCase();

  document.getElementById("app-shell").innerHTML = `
    <aside class="sidebar">
      <div class="brand"><span class="logo-dot"></span><span class="text">Smart Fleet</span></div>
      <nav>${navHtml}</nav>
      <div class="sidebar-footer">
        <button class="btn btn-secondary btn-block" onclick="logout()">Logout</button>
      </div>
    </aside>
    <div class="main">
      <div class="topbar">
        <h2>${pageTitle}</h2>
        <div class="user-chip">
          <div class="avatar">${initials}</div>
          <span>${user.fullName || user.username}</span>
        </div>
      </div>
      <div class="content" id="page-content"></div>
    </div>
  `;
}

/* ---------------- Formatters ---------------- */

function formatCurrency(value) {
  const num = Number(value || 0);
  return "₹" + num.toLocaleString("en-IN", { maximumFractionDigits: 2 });
}

function formatStatusBadge(status) {
  if (!status) return "";
  const key = String(status).toLowerCase();
  const label = String(status).replace(/_/g, " ");
  return `<span class="badge badge-${key}">${label}</span>`;
}

function escapeHtml(str) {
  if (str === null || str === undefined) return "";
  return String(str)
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/"/g, "&quot;");
}

function showToastError(message) {
  alert(message);
}
