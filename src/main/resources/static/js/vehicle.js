$(document).ready(function() {

    var count_x = employeeCount.map(x => x[0])
    var count_y = employeeCount.map(x => x[1])

    var transaction_y = transactions.map(x => x["amount"])
    var transaction_x = transactions.map(x => x["purpose"])



    var pieData = [
        {
            value: 30,
            color:"#F38630"
        },
        {
            value : 50,
            color : "#E0E4CC"
        },
        {
            value : 100,
            color : "#69D2E7"
        }

    ];

    var barChartData = {
        labels: count_x,
        datasets: [
            {
                fillColor: "rgba(220,220,220,0.5)",
                strokeColor: "rgba(220,220,220,1)",
                data: count_y
            },
            {
                fillColor: "rgba(151,187,205,0.5)",
                strokeColor: "rgba(151,187,205,1)",
                data: [1, 2, 4, 3, 1, 5, 2]
            }
        ]

    };


    new Chart(document.getElementById("bar").getContext("2d")).Bar(barChartData);
    new Chart(document.getElementById("pie").getContext("2d")).Pie(pieData);


});
