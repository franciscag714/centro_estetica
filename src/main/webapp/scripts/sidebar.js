const sidebarButton = document.getElementById("show-sidebar-btn");
const sidebar = document.getElementById("sidebar");

sidebarButton.addEventListener("click", function () {
  sidebar.style.display = sidebar.style.display === "block" ? "none" : "block";
});
