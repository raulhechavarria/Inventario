<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag"%>
<aek:jsmin></aek:jsmin>
{
	success: true,
	view: function(){
	
	var ds = new Ext.data.JsonStore({
			url: '../${modulo}/listarExistencia.json',
	        sortInfo:{field: 'fechaCambio', direction: "ASC"},
	       	baseParams: {limit:40},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
            fields: [
            	'id',
		      	{name:'producto', type: 'string'},
		      	{name:'productoId', type: 'string'},
		      	{name:'almacen', type: 'string'},
		      	{name:'almacenId', type: 'string'},			      	
		      	{name:'vale', type: 'string'}, 
  	    		{name: 'cantEntradaVale', type: 'string'},
		        {name: 'cantSalidaVale', type: 'string'},
		        {name: 'cantExist', type: 'string'},
		        {name: 'precioMNExist', type: 'string'},
		        {name: 'precioMLCExist', type: 'string'},
		        {name: 'impMNExist', type: 'string'},
		        {name: 'impMLCExist', type: 'string'},  
		        {name: 'fechaCambio', type: 'date', dateFormat: 'Y-m-d'}	
            ]
	    });
	var reporteMenu = new Ext.menu.Menu({
			id: 'reporteMenu',       
			items: [
				{
				 	text: 'Existencia',
				 	icon:'../img/common/printer.png',
				    handler: function(record){
							var idClasificadorProducto = Ext.getCmp('clasificador').getValue();
							descargar('../ecie/impTarjeteroFamilia.htm',{idClasificadorProducto: idClasificadorProducto});
				  		}
				},
				{
				 	text: 'Ociosos',
				 	icon:'../img/common/printer.png',
				    handler: function(record){
							var idClasificadorProducto = Ext.getCmp('clasificador').getValue();
							descargar('../ecie/impTarjeteroOciosos.htm',{idClasificadorProducto: idClasificadorProducto});
				  		}
				}
			]
		}); 
			    
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
		
		var clasificadorStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarClasificador.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:50},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','nombre','descripcion']
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
	    
	    function filterB(record, id, c){
	    	var idc = Ext.getCmp('almacenFilter');
	    	if(record.data.almacenId == idc.getValue()){
	    		return true;
	    	}
	    	return false;
	    }
	   
	    var gp = new Ext.grid.GridPanel({
			id: 'exitenciaGrid',
			border:false,
			title: 'Existencia',
		    selModel: model,
		    enableColumnMove: false,
			enableHdMenu: false,
	        frame: false,
	        height:420,
	        store: ds,
	        headerAsText: false,
	        viewConfig: {forceFit: true},	
	        columns: [
	        	{header: "<b><font color=blue size=2>Fecha</font></b>", dataIndex: 'fechaCambio', width: 80,renderer: Ext.util.Format.dateRenderer('Y/m/d')},
	        	{header: "<b><font color=blue size=2>Producto</font></b>",dataIndex: 'producto',width: 110/*,hidden: true*/},
	        	{header: "<b><font color=blue size=2>Almacén</font></b>",dataIndex: 'almacen',width: 80/*,hidden: true*/},
	        	{header: "<b><font color=blue size=2>Entrada</font></b>",dataIndex: 'cantEntradaVale',width: 80},
	        	{header: "<b><font color=blue size=2>Salida</font></b>",dataIndex: 'cantSalidaVale',width: 80},
	        	{header: "<b><font color=blue size=2>Existencia	Almacén</font></b>",dataIndex: 'cantExist',width: 80}
		    ],
	        tbar: new Ext.Toolbar({
				items: [{
					text: 'Detalles',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/lightning.png',
					tooltip:'Detalles Exitencia',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								detalles(gp, tp, '../${modulo}/detallesExistencia.htm');
							}
							else{
	           					Ext.MessageBox.show({  
									title: 'Detalles Exitencia',  
									msg: 'Debe seleccionar una Exitencia.',  
									buttons: Ext.MessageBox.OK,  
									icon: Ext.MessageBox.ERROR  
	               				});
	           				}			
					  	}
					}
				},'-',
				{
					text: 'Imprimir',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/printer.png',
					tooltip:'Reportes de Existencias y Ociosos',
					menu: reporteMenu
				},
				'-','Familia de Producto:',
				new Ext.form.ComboBox({
					store: clasificadorStore,
					mode: 'local',
					displayField: 'nombre',
					width: 150,
					label: 'Familia de Producto:',
					labelStyle: 'font-weight:bold',
					listWidth: 150,
					typeAhead: false,
					id: 'clasificador',
					forceSelection: true,
					triggerAction: 'all',
					loadingText: 'Buscando Familia de Producto...',									
					valueField: 'id',
					hiddenName: 'clasificador.id',
					value: '${command.clasificador.nombre}',
					hiddenValue: '${command.clasificador.nombre}',
					autocomplete: "on",
					fieldLabel: 'Familia de Producto',
					listeners:{
					 	select: function(combo, record, index){ // override default onSelect to do redirect
					 		var params = {
								idClasificador: record.id
							};
							var lastOptions = ds.baseParams;
							Ext.apply(lastOptions, params);
							ds.reload({params:lastOptions});
						}
			        }
				})
				 ,'->','Almacén:',
					new Ext.form.ComboBox({
					id: 'almacenFilter',
					store: almacenStore,
					mode: 'local',
					displayField: 'nombre',
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
					hiddenName: 'almacen.id',
					value: '${command.almacen.codigo}',
					hiddenValue: '${command.almacen.id}',
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
		        pageSize: 40
		    })    
    	});
		
/*******************************************************************************************************/		

	   	 
		
		function editarExitencia(url, id){
			cargarVentana(url, {id: id});
	    }
   
    	gp.on('celldblclick', gridCellDblClick, this);
		
		function gridCellDblClick(target, rowIndex, columnIndex, e){
			detalles(gp, tp, '../${modulo}/detallesExistencia.htm');
		}
		function prueba (url, id){
		var idp = 
			cargarVentana(url, {id: id});
			
	    }
   
  		 var tp = new Ext.TabPanel({
			id: 'exitenciaTabPanel',
			border: false,
	        monitorResize:true,
	        enableTabScroll:true,
	        defaults: {autoScroll:true},
	        items:[gp],
	        activeTab:0
		}); 
			
		var exitenciaPanel = new Ext.Panel({
		  border: false,
		  layout: 'fit',
		  title:'Inventario Físico',
		  items:[tp] 
		});
		
	    return exitenciaPanel;
	}
}
