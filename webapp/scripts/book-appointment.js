document.getElementById("clear-filters-btn").addEventListener("click", () => {
  document.getElementById("date").value = "";
  document.getElementById("start-time").value = "";
  document.getElementById("end-time").value = "";
  document.getElementById("filter-form").submit();
});
