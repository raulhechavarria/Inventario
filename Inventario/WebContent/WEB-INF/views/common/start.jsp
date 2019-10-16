<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
	
		Ext.chart.Chart.CHART_URL = '../img/charts.swf';
		
		var annoStore = new Ext.data.SimpleStore({
            fields:['id', 'anno'],
            data:[
            	['2011','2011'],
            	['2012','2012'],
            	['2013','2013'],
            	['2014','2014'],
            	['2015','2015'],
            	['2016','2016'],
            	['2017','2017'],
            	['2018','2018'],
            	['2019','2019'],
            	['2020','2020'],
            	['2021','2021'],
            	['2022','2022'],
            	['2023','2023'],
            	['2024','2024'],
            	['2025','2025'],
            	['2026','2026']          	
            ]
		});
		
	    var store = new Ext.data.JsonStore({
	    	url: '../ecie/InventarioCompras.json',
	    	autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'mes',
  	    	fields:['mes', 'compras','ventas']
	    });
	
		var gp = new Ext.Panel({
	        iconCls:'chart',
	        title: 'Importes Anual',
	        frame:true,
	        layout:'table',
		    layoutConfig: {columns:1},
		    items: {
	            xtype: 'linechart',
	            store: store,
	            width:900,
	        	height:370,
	            url: '../img/charts.swf',
	            xField: 'mes',
	          //  yField: 'valor',
	            yAxis: new Ext.chart.NumericAxis({
	                displayName: 'compras',
	                labelRenderer : Ext.util.Format.numberRenderer('0,0')
	            }),
	            tipRenderer : function(chart, record,index, series){
	            if(series.yField == 'compras'){
	                return Ext.util.Format.number(record.data.compras, '0,0') + ' Entradas en ' + record.data.mes;
	            }else{
	                return Ext.util.Format.number(record.data.ventas, '0,0') + ' Salidas en ' + record.data.mes;
	        	}},
	        	  chartStyle: {
                padding: 10,
                animationEnabled: true,
                font: {
                    name: 'Tahoma',
                    color: 0x444444,
                    size: 11
                },
                dataTip: {
                    padding: 5,
                    border: {
                        color: 0x99bbe8,
                        size:1
                    },
                    background: {
                        color: 0xDAE2F6,
                        alpha: .9
                    },
                    font: {
                        name: 'Tahoma',
                        color: 0x15428B,
                        size: 10,
                        bold: true
                    }
                },
                xAxis: {
                    color: 0x69aBc8,
                    majorTicks: {color: 0x69aBc8, length: 4},
                    minorTicks: {color: 0x69aBc8, length: 2},
                    majorGridLines: {size: 1, color: 0xeeeeee}
                },
                yAxis: {
                    color: 0x69aBc8,
                    majorTicks: {color: 0x69aBc8, length: 4},
                    minorTicks: {color: 0x69aBc8, length: 2},
                    majorGridLines: {size: 1, color: 0xdfe8f6}
                }
            },
	        series: [{
                type: 'line',
                displayName: 'Compras del mes',
                yField: 'compras',
                style: {
                    color:0x322EF8
                }
            },{
                type:'line',
                displayName: 'Salidas del mes',
                yField: 'ventas',
                style: {
                	color: 0xAA002F
                }
            }]
            }
	    });

		var panelGrafic = new Ext.Panel({
			title: 'Entradas y salidas del Año',
			border: true,
			layout:'table',
			layoutConfig: {columns:2},
			tbar: new Ext.Toolbar({
				items: [
					'Año(si no seleciona Año se grafica el actual):',
				new Ext.form.ComboBox({
					id:'annocomb',
					labelStyle: 'font-weight:bold',  
					store: annoStore,
					displayField:'anno',
					valueField :'id',
					value: '${command.anno}',
					width: 70,
					triggerAction: 'all',
					name: 'anno',
					fieldLabel: 'Año',
					mode: 'local',
					typeAhead: true,
				    triggerAction: 'all',
				    editable: false,
						listeners:{
							 select: function(combo, record, index){
							 var usercombox = Ext.getCmp('Divisioncombox');									
						 		var params = { 
						 			anno: combo.getValue()
						 		};
								var lastOptions = store.baseParams;
								Ext.apply(lastOptions, params);
								store.reload({params:lastOptions});
							}
				        }
					})]
				}),
			items:[gp]
		});

		return panelGrafic;
	}
}
</aek:jsmin>