function report(){	
	$.ajax({
		url : "/matricula/dataRptMatriculadosMaterias",
		method : 'GET',
		success : function(response){
			console.log(response);
			
			var toData = [];
			var toLabels = [];
			var toColors = [];
			
			$.each(response, function(i, item){
				console.log(item);
				toData.push(item.estudiantes);
				toLabels.push(item.nombre);						
				toColors.push(getRandomColor());
			});
									
			var barChartData = {
				labels: toLabels,
				datasets: [{
					label: 'Alumnos',
					backgroundColor: getRandomColor(),
					borderColor: getRandomColor(),
					borderWidth: 1,
					data: toData
				}]

			};

			window.onload = function() {
				var ctx = document.getElementById('chart-area-1').getContext('2d');
				window.myBar = new Chart(ctx, {
					type: 'bar',
					data: barChartData,
					options: {
						responsive: true,
						legend: {
							position: 'top',
						},
						title: {
							display: true,
							text: 'Número de matriculados por materia'
						}
					}
				});

			};
			
		},
		error : function(err){
			console.log(err);
		}		
	});	
}

function report3(){	
	var color = Chart.helpers.color;
	$.ajax({
		url : "/matricula/dataRptConteoMatriculadosMaterias",
		method : 'GET',
		success : function(response){
			
			var toData = [];
			var toLabels = [];
			var Users = [];
			
			//Agregar Datos a Tabla del reporte 
			$.each(response, function(i, item){
				document.getElementById("tablaValores").insertRow(-1).innerHTML = '<td><b>'+item.materia+'</b></td><td></td><td></td>';	
				$.each(item.usuarios,function(x,elemento){
					document.getElementById("tablaValores").insertRow(-1).innerHTML = '<td></td><td>'+elemento.usuario+'</td><td>'+elemento.cant+'</td>';
				});
			});
			
			//Saber que materias y que usuarios existen
			$.each(response, function(i, item){
				//Agregar las materias que existen
				toLabels.push(item.materia);	
				$.each(item.usuarios,function(x,elemento){
					//Agregar los usuarios que existan
					if(!Users.includes(elemento.usuario)){
						Users.push(elemento.usuario);
					}
				});
			});
			
			//Crear los Datasets
			$.each(Users, function(i,item){
				var valores = new Array(toLabels.lenght);
				valores.fill(0);
				$.each(response,function(x,elemento){
					$.each(elemento.usuarios,function(y,valor){
						if(valor.usuario == item){
							valores[toLabels.indexOf(elemento.materia)] = valor.cant;
						}
					});
				});
				var dat = {
					label: item,
					backgroundColor: color(getRandomColor()).alpha(0.5).rgbString(),			
					borderWidth: 1,
					data: valores,
				}
				toData.push(dat);
			});
									
			var barChartData = {
				labels: toLabels,
				datasets: toData
			};

			window.onload = function() {
				var ctx = document.getElementById('canvas').getContext('2d');
				window.myBar = new Chart(ctx, {
					type: 'bar',
					data: barChartData,
					options: {
						responsive: true,
						legend: {
							position: 'top',
						},
						title: {
							display: true,
							text: 'Matrículas por materia y por usuario'
						},
						scales: {
					        yAxes: [{
					            ticks:{
									beginAtZero: true
								}
					        }]
					    }
						
					}
				});
		
			};	
			
		},
		error : function(err){
			console.log(err);
		}		
	});	
}

function report2(){
	
	var color = Chart.helpers.color;
	var barChartData = {
		labels: ['Certificación I', 'Ingeniería Web', 'Métodos numéricos', 'Estructura de datos', 'Metodología'],
		datasets: [{
			label: 'diegoismael',
			backgroundColor: color(getRandomColor()).alpha(0.5).rgbString(),			
			borderWidth: 1,
			data: [
				10,
				2,
				5,
				9,
				8				
			]
		}, {
			label: 'dsanchez',
			backgroundColor: color(getRandomColor()).alpha(0.5).rgbString(),			
			borderWidth: 1,
			data: [
				16,
				12,
				4,
				0,
				9
			]
		}]

	};

	window.onload = function() {
		var ctx = document.getElementById('canvas').getContext('2d');
		window.myBar = new Chart(ctx, {
			type: 'bar',
			data: barChartData,
			options: {
				responsive: true,
				legend: {
					position: 'top',
				},
				title: {
					display: true,
					text: 'Matrículas por materia y por usuario'
				}
			}
		});

	};		
}

$(document).ready(function(){
	
	report3();		

	
});
