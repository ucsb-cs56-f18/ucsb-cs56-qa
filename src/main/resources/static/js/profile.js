google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

// Draw the chart and set the chart values
function drawChart() {
  var data = google.visualization.arrayToDataTable([
  ['Category', 'Times answerd'],
  ['School', 10],
  ['Math', 3],
  ['Life', 5],
  ['Social', 4],
  ['Computer Science', 12],
  ['Others', 8]
]);

  // Optional; add a title and set the width and height of the chart
  var options = {'title':'Types of Questions Answered', 'width':450, 'height':388};

  // Display the chart inside the <div> element with id="piechart"
  var chart = new google.visualization.PieChart(document.getElementById('piechart'));
  chart.draw(data, options);
}

$(document).ready(function(){
    $("#q1").mousemove(function() {
        $("#q1").addClass("active");
        $("#q2").removeClass("active");
        $("#q3").removeClass("active");
    });
    $("#q2").mousemove(function() {
        $("#q1").removeClass("active");
        $("#q2").addClass("active");
        $("#q3").removeClass("active");
    });
    $("#q3").mousemove(function() {
        $("#q1").removeClass("active");
        $("#q2").removeClass("active");
        $("#q3").addClass("active");
    });
});