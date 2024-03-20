let graphDataP = window.graphDataPreparation;
let graphDataA = window.graphDataAcheminement;
let graphDataT = window.graphDataTerminees;


let selectedMonth = "tous";

$(document).ready(function() {
    google.charts.load('current', {
        packages : [ 'corechart', 'bar' ]
    });
    google.charts.setOnLoadCallback(function() {
        drawColumnChart("tous");
    });

    $("#selectMonth").change(function() {
        selectedMonth = $(this).val();
        drawColumnChart(selectedMonth);
    });

});

let months = ["janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"];
function drawColumnChart(selectedMonth) {
    let data = new google.visualization.DataTable();
    data.addColumn('string', 'Mois');
    data.addColumn('number', 'En préparation');
    data.addColumn('number', 'En cours d\'acheminement');
    data.addColumn('number', 'Livrée');

    let graphData;

        graphData = {
            preparation: graphDataP,
            acheminement: graphDataA,
            terminees: graphDataT
        };

    let sortedMonths;
    if (selectedMonth === 'tous') {
        sortedMonths = months;
    } else {
        sortedMonths = [selectedMonth];
    }

    sortedMonths.forEach(function(month) {
        let row = [month, graphData.preparation[month] || 0, graphData.acheminement[month] || 0, graphData.terminees[month] || 0];
        data.addRow(row);
    });

    let options = {
        title : 'Statistique des ventes',
        hAxis : {
            title : 'Mois',
        },
        vAxis : {
            title : 'Ventes'
        },
        colors: ['#00a2ff', '#000000', '#ff9a00'],
        backgroundColor: '#ECF0F1',
        bar: { groupWidth: '60%' }
    };

    let chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
    chart.draw(data, options);
}

////////////////////////////////////////////////////////////////////////////////////////////////

let graphDataPEmp = window.graphDataPreparationE;
let graphDataAEmp = window.graphDataAcheminementE;
let graphDataTEmp = window.graphDataTermineesE;

let selectedMonthE = "tous";

$(document).ready(function() {
    google.charts.load('current', {
        packages : [ 'corechart', 'bar' ]
    });
    google.charts.setOnLoadCallback(function() {
        drawColumnChartE("tous", "");
    });

    $("#selectMonthE").change(function() {
        let selectedMonthE = $(this).val();
        let selectedEmploye = $("#selectEmploye").val();
        drawColumnChartE(selectedMonthE, selectedEmploye);
    });

    $("#selectEmploye").change(function() {
        let selectedMonthE = $("#selectMonthE").val();
        let selectedEmploye = $(this).val();
        drawColumnChartE(selectedMonthE, selectedEmploye);
    });

});

let monthsE = ["janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"];
function drawColumnChartE(selectedMonthE, selectedEmploye) {
    let data = new google.visualization.DataTable();
    data.addColumn('string', 'Mois');
    data.addColumn('number', 'En préparation');
    data.addColumn('number', 'En cours d\'acheminement');
    data.addColumn('number', 'Livrée');

    let graphData = {
        preparationE: graphDataPEmp,
        acheminementE: graphDataAEmp,
        termineesE: graphDataTEmp
    };

    let sortedMonths;
    if (selectedMonthE === 'tous') {
        sortedMonths = monthsE;
    } else {
        sortedMonths = [selectedMonthE];
    }

    sortedMonths.forEach(function(selectedMonthE) {
        let prepValue = (graphData.preparationE[selectedEmploye + '_' + selectedMonthE] !== undefined) ? graphData.preparationE[selectedEmploye + '_' + selectedMonthE] : null;
        let achemValue = (graphData.acheminementE[selectedEmploye + '_' + selectedMonthE] !== undefined) ? graphData.acheminementE[selectedEmploye + '_' + selectedMonthE] : null;
        let termValue = (graphData.termineesE[selectedEmploye + '_' + selectedMonthE] !== undefined) ? graphData.termineesE[selectedEmploye + '_' + selectedMonthE] : null;
        let row = [selectedMonthE, prepValue, achemValue, termValue];

        data.addRow(row);
    });

    let options = {
        title : 'Statistique des ventes par employé',
        hAxis : {
            title : 'Mois',
        },
        vAxis : {
            title : 'Ventes'
        },
        colors: ['#00a2ff', '#000000', '#ff9a00'],
        backgroundColor: '#ECF0F1',
        bar: { groupWidth: '60%' }
    };

    let chart = new google.visualization.ColumnChart(document.getElementById('chart_emp'));
    chart.draw(data, options);
}


////////////////////////////////////////////////////////////////////////////////////////////

let chiffresAffaires = window.chiffresAffairesParClient;

$(document).ready(function() {
    google.charts.load('current', {
        packages : [ 'corechart', 'bar' ]
    });
    google.charts.setOnLoadCallback(drawColumnChartClient);

});

function drawColumnChartClient() {
    let data = new google.visualization.DataTable();
    data.addColumn('string', 'Client');
    data.addColumn('number', 'Chiffre d\'affaires');

    Object.entries(chiffresAffaires).forEach(([client, chiffreAffaire]) => {
        data.addRow([client, chiffreAffaire]);
    });

    let options = {
        title : 'Chiffre d\'affaires par client',
        hAxis : {
            title : 'Client',
        },
        vAxis : {
            title : 'Chiffre d\'affaires'
        },
        colors: ['#ff9a00'],
        backgroundColor: '#ECF0F1',
        bar: { groupWidth: '60%' }
    };

    let chart = new google.visualization.ColumnChart(document.getElementById('chart_client'));
    chart.draw(data, options);
}


let top5ProduitsPlusVendus = window.top5ProduitsPlusVendus;

$(document).ready(function() {
    google.charts.load('current', {
        packages : [ 'corechart', 'bar' ]
    });
    google.charts.setOnLoadCallback(drawColumnChartProduitPlus);
});

function drawColumnChartProduitPlus() {
    let data = new google.visualization.DataTable();
    data.addColumn('string', 'Produit');
    data.addColumn('number', 'Quantité Vendue');

    top5ProduitsPlusVendus.forEach(function(produit) {
        data.addRow([produit[0], produit[1]]);
    });

    let options = {
        title: 'Top 5 des produits les plus vendus',
        hAxis : {
            title : 'Produit',
        },
        vAxis : {
            title : 'Quantité'
        },
        colors: ['#00a2ff'],
        backgroundColor: '#ECF0F1',
        bar: { groupWidth: '60%' }
    };

    let chart = new google.visualization.ColumnChart(document.getElementById('chart_produitPlus'));
    chart.draw(data, options);
}

/////////////////////////////////////////////////////////////////////////////////////////////

let top5ProduitsMoinsVendus = window.top5ProduitsMoinsVendus;

$(document).ready(function() {
    google.charts.load('current', {
        packages : [ 'corechart', 'bar' ]
    });
    google.charts.setOnLoadCallback(drawColumnChartProduitMoins);
});

function drawColumnChartProduitMoins() {
    let data = new google.visualization.DataTable();
    data.addColumn('string', 'Produit');
    data.addColumn('number', 'Quantité Vendue');

    top5ProduitsMoinsVendus.forEach(function(produit) {
        data.addRow([produit[0], produit[1]]);
    });

    let options = {
        title: 'Top 5 des produits les moins vendus',
        hAxis : {
            title : 'Produit',
        },
        vAxis : {
            title : 'Quantité'
        },
        colors: ['#ff9a00'],
        backgroundColor: '#ECF0F1',
        bar: { groupWidth: '60%' }
    };

    let chart = new google.visualization.ColumnChart(document.getElementById('chart_produitMoins'));
    chart.draw(data, options);
}

/////////////////////////////////////////////////////////////////////////////////////////

let chiffreAffairesParMoisEtAnnee = window.chiffreAffairesParMoisEtAnnee;

$(document).ready(function() {
    google.charts.load('current', {
        packages : [ 'corechart', 'bar' ]
    });
    google.charts.setOnLoadCallback(drawColumnChartChiffreAffaires);
});

function drawColumnChartChiffreAffaires() {
    let data = new google.visualization.DataTable();
    data.addColumn('string', 'Mois et Année');
    data.addColumn('number', 'Chiffre d\'affaires');

    chiffreAffairesParMoisEtAnnee.forEach(function(row) {
        let mois = row[0];
        let annee = row[1];
        let chiffreAffaires = row[2];
        let label = mois + '/' + annee;
        data.addRow([label, chiffreAffaires]);
    });

    let options = {
        title: 'Chiffre d\'affaires par mois et par année',
        hAxis : {
            title : 'Mois et Année',
        },
        vAxis : {
            title : 'Chiffre d\'affaires'
        },
        colors: ['#00a2ff'],
        backgroundColor: '#ECF0F1',
        bar: { groupWidth: '60%' }
    };

    let chart = new google.visualization.LineChart(document.getElementById('chart_chiffreMoisAnnee'));
    chart.draw(data, options);
}

////////////////////////////////////////////////////////////////////////////////

let chiffreAffairesTotal = window.chiffreAffairesTotal;
console.log(chiffreAffairesTotal);
$(document).ready(function() {
    google.charts.load('current', {
        packages: ['corechart']
    });
    google.charts.setOnLoadCallback(drawPieChartChiffreTotal);
});

function drawPieChartChiffreTotal() {
    let data = new google.visualization.DataTable();
    data.addColumn('string', 'Type');
    data.addColumn('number', 'Montant');

    data.addRow(['Chiffre d\'affaires total', chiffreAffairesTotal]);

    let options = {
        title: 'Chiffre d\'affaires total',
        pieHole: 0.5,
        colors: ['#ff9a00'],
        backgroundColor: '#ECF0F1',
        pieSliceTextStyle: {
            color: 'black'
        }
    };

    let chart = new google.visualization.PieChart(document.getElementById('chart_chiffreTotal'));
    chart.draw(data, options);
}
