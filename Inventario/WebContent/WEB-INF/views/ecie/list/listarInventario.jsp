<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
		Ext.chart.Chart.CHART_URL = '../img/charts.swf';
		
/*******************************************************************************************************/

		var fecha = new Ext.data.SimpleStore({
		 	fields:['id', 'fecha'],
			data:[
	          	  ['01','Enero'],   
	          	  ['02','Febrero'],
	          	  ['03','Marzo'],   
	          	  ['04','Abril'],
	          	  ['05','Mayo'],   
	          	  ['06','Junio'],
	          	  ['07','Julio'],   
	          	  ['08','Agosto'],
	          	  ['09','Septiembre'],   
	          	  ['10','Octubre'],
	          	  ['11','Noviembre'],   
	          	  ['12','Diciembre']
			 ]
		});

		var ds = new Ext.data.JsonStore({
 			url: '../${modulo}/listarInventario.json',
			baseParams: {'tipo': 'Inventario' },
			autoLoad: true,
			root: 'data',
    	    totalProperty: 'totalCount',
            id: 'id',
            fields: [
            	'id',
		      	{name:'invIniMN', type: 'string'},
		      	{name:'invFinalMN', type: 'string'}, 
		      	{name:'MovimientoMN', type: 'string'},     	
		      	{name:'invIniMLC', type: 'string'},
		      	{name:'invFinalMLC', type: 'string'},
		      	{name:'MovimientoMLC', type: 'string'},
		      	{name:'almacen', type: 'string'},
		      	{name:'almacenId', type: 'string'},
		      	{name:'fecha', type: 'string'}			      	
            ]
	    });
   		
   		function filterB(record, id, c){
	    	var idc = Ext.getCmp('almacenFilter');
	    	if(record.data.almacenId == idc.getValue()){
	    		return true;
	    	}
	    	return false;
	    }
	    
	    function applyGreenLink(val){
	        return '<font color="green">' + val + '</font>';
	    };
				
		var model = new Ext.grid.RowSelectionModel();
		
		var almacenStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarAlmacen.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:50},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','nombre','codigo']
		});
				
		var resultTpl = new Ext.XTemplate(
	        '<tpl for="."><div class="search-item">',
               	'<tpl for=".">',
        		'{codigo}',
        		'</tpl>',
        		' ',
        		'{nombre}<br/>',	            
	        '</div></tpl>'
	    );
	    
	    /*var productoTpl = new Ext.XTemplate(
	        '<tpl for="."><div class="search-item">',
               	'<tpl for=".">',
        		'{codigo}',
        		'</tpl>',
        		' ',
        		'{nombre}<br/>',	            
	        '</div></tpl>'
	    );*/
	    
	    var gp = new Ext.grid.GridPanel({
	    	loadMask:true,
			id: 'InventarioGrid',
			monitorResize:true,
			border:false,
			stripeRows:true,
			autoScroll:true,
			height: 220,
			title: 'Inventario',
		    selModel: model,
		    enableColumnMove: false,
			enableHdMenu: false,
	        frame: false,
	     	width:450,
	        height:180,
	        store: ds,
	        headerAsText: false,
	        viewConfig: {forceFit: true},	
	        columns: [
	        	{header: "<b><font color=blue size=2>Fecha</font></b>", width: 10, dataIndex: 'fecha', sortable: true},
	        	{header: "<b><font color=blue size=2>Inicial CUP</font></b>",width: 10, dataIndex: 'invIniMN', sortable: true},
	        	{header: "<b><font color=blue size=2>Final CUP</font></b>",width: 10, dataIndex: 'invFinalMN', sortable: true},
	        	{header: "<b><font color=blue size=2>Mov CUP</font></b>",width: 10, dataIndex: 'MovimientoMN', sortable: true},
	        	{header: "<b><font color=blue size=2>Inicial CUC</font></b>",width: 10, dataIndex: 'invIniMLC', sortable: true},
	        	{header: "<b><font color=blue size=2>Final CUC</font></b>",width: 10, dataIndex: 'invFinalMLC', sortable: true},
	        	{header: "<b><font color=blue size=2>Mov CUC</font></b>",width: 10, dataIndex: 'MovimientoMLC', sortable: true},
	        	{header: "<b><font color=blue size=2>Almacén</font></b>",width: 10, dataIndex: 'almacen', sortable: true}
		    ],
	        tbar: new Ext.Toolbar({
				items: [
				{
					text: 'Imprimir',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/printer.png',
					tooltip:'Imprimir Cuadre Diario',
					listeners:{
					
					click:function(record){
						if(gp.selModel.hasSelection()){
						var idInventario = gp.selModel.getSelected().id;
							descargar('../ecie/imprimirInventario.htm',{idInventario: idInventario});
				  		}	
				  		
							else{
	           					Ext.MessageBox.show({  
									title: 'Imprimir Inventario',  
									msg: 'Debe seleccionar un Inventario.',  
									buttons: Ext.MessageBox.OK,  
									icon: Ext.MessageBox.ERROR  
	               				});
	           				}
	           			}			
			  		}
				},
				'-','Meses:',
				new Ext.form.ComboBox({
					labelStyle: 'font-weight:bold',  
					allowBlank: false,
					blankText: 'Este campo es requerido',
					store: fecha,
					displayField:'fecha',
					width: 80,
					triggerAction: 'all',
					id: 'Mes',
					name: 'fecha',
					fieldLabel: 'Inventario',
					mode: 'local',
					typeAhead: true,
				    triggerAction: 'all',
				    editable: false,
				    listeners:{
					 	select: function(combo, record, index){ // override default onSelect to do redirect
					 		var params = {
					 			'mes': record.data.id
					 		};
					 		
					 		var lastOptions = ds.baseParams;
							Ext.apply(lastOptions, params);
						
							ds.reload({params:lastOptions});
							store.reload({params:lastOptions});
							
					 	},
			        }
				})
				,'->','Almacén:',
					new Ext.form.ComboBox({
						store: almacenStore,
						id:'almacenFilter',
						mode: 'local',
						displayField: 'codigo',
						width: 150,
						label: 'Almacén:',
						labelStyle: 'font-weight:bold',
						listWidth: 320,
						typeAhead: false,
						forceSelection: true,
						triggerAction: 'all',
						loadingText: 'Buscando Almacenes...',									
						pageSize: 20,
						valueField: 'id',
						itemSelector: 'div.search-item',
						tpl: resultTpl,
						autocomplete: "on",
						fieldLabel: 'Almacén',
						listeners:{
						select: function(record){
							ds.filterBy(filterB);
				        }
					}
					})
				]
			}),
		   	bbar: new Ext.PagingToolbar({
		        store: ds,       // grid and PagingToolbar using same store
		        displayInfo: true,
		        pageSize: 348
		    })
    	});
		
		
/*******************************************************************************************************/		
		
		function editarInventario(url, id){
			cargarVentana(url, {id: id});
	    }
   
    	gp.on('celldblclick', gridCellDblClick, this);
		
		function gridCellDblClick(target, rowIndex, columnIndex, e){
			detalles(gp, tp, '../${modulo}/detallesInventario.htm');
		}
		function prueba (url, id){
		var idp = 
			cargarVentana(url, {id: id});
	    }
   
  		 var tp = new Ext.TabPanel({
			id: 'inventarioTabPanel',
			border: false,
	        monitorResize:true,
	        enableTabScroll:true,
	        defaults: {autoScroll:true},
	        items:[gp],
	        activeTab:0
		}); 
		
		var store = new Ext.data.JsonStore({
			url: '../${modulo}/InventarioESMes.json',
		  	autoLoad: true,
		  	baseParams: {mes :Ext.getCmp('Mes').getValue() },
	       	fields: ['season', 'total'],
	     	root: 'data',
	    });
	    
		var storediario = new Ext.data.JsonStore({
		   	url: '../${modulo}/InventarioESDiario.json',
		  	autoLoad: true,
	       	fields: ['season', 'total'],
	     	root: 'data',
	    });
		
		var panelGrafic1 = new Ext.Panel({
			border: true,
			layout:'table',
			layoutConfig: {columns:1},
			items:[
				new Ext.Panel({
			        width: 400,
			        height: 200,
			        title: 'Total Salidas y Entradas del Mes',
		         	items: {
			            store: store,
			            xtype: 'piechart',
			            dataField: 'total',
			            categoryField: 'season',
			            //extra styles get applied to the chart defaults
			            extraStyle:
			            {
			                legend:
			                {
			                    display: 'bottom',
			                    padding: 5,
			                    font:
			                    {
			                        family: 'Tahoma',
			                        size: 15
			                    }
			                }
			            }
			        }
			    })
			]
		});
		
		
		var panelGrafic2 = new Ext.Panel({
			border: true,
			layout:'table',
			layoutConfig: {columns:1},
			items:[
				new Ext.Panel({
			        width: 400,
			        height: 200,
			        title: 'Total Salidas y Entradas del Dia',
			        items: {
			            store: storediario,
			            xtype: 'piechart',
			            dataField: 'total',
			            categoryField: 'season',
			            //extra styles get applied to the chart defaults
			            extraStyle:
			            {
			                legend:
			                {
			                    display: 'bottom',
			                    padding: 5,
			                    font:
			                    {
			                        family: 'Tahoma',
			                        size: 13
			                    }
			                }
			            }
			        }
			    })
			]
		});
	
		var panelGrafic = new Ext.Panel({
			title: 'Comportamiento del Inventario',
			border: true,
			layout:'table',
			layoutConfig: {columns:2},
			items:[panelGrafic1,panelGrafic2]
		});
			
		var inventarioPanel = new Ext.Panel({
			border: false,
			layout: 'form',
			title:'Inventario Físico',
			items:[tp,panelGrafic] 
		});
		 
	   	return inventarioPanel;
	}
}
</aek:jsmin>