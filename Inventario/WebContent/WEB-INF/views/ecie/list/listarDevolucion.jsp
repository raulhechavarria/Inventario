<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
	
		var ds = new Ext.data.JsonStore({
			url: '../${modulo}/listarDevolucion.json',
			baseParams: {'tipo': 'Devolucion' },
 			autoLoad: true,
			root: 'data',
    	    totalProperty: 'totalCount',
		    id: 'id',
            fields: [
	           	'id',
	           	{name: 'noVale', type: 'string'},
	           	{name: 'noControl', type: 'string'},
	           	{name: 'fechaVale', type: 'string'},
	           	{name: 'almacenDestino', type: 'string'},
	           	{name: 'estadoVale', type: 'string'},
	           	{name: 'ValeReferencia', type: 'string'},
	          //{name: 'centroCosto', type: 'string'},
	            {name: 'entregadoPor', type: 'string'},
	           	{name: 'fchRecibido', type: 'string'},
	           	{name: 'comentario', type: 'string'},
	           	{name: 'noDoc', type: 'string'}
		            ]
    	});	
   
	
		
	
		 // función para renderear la columna "estadoVale"
	    function estadoVale(estadoVale){
	        if(estadoVale == 'Confirmado'){
	            return '<span style="color: green; font-weight:bold" >' + estadoVale + '</span>';
	        }
	        if(estadoVale == 'Cancelado'){
	            return '<span style="color: red; font-weight: bold">'
	+ estadoVale + '</span>';
	        }
	        if(estadoVale == 'Confeccion'){
	            return '<span style="color: blue; font-weight: bold">'
	+ estadoVale + '</span>';
	        }
	        if(estadoVale == 'Revision'){
	        
	           return '<span style="color: brown; font-weight: bold">' + estadoVale +
	'</span>';
	        }
	    }
	   	var gp = new Ext.grid.GridPanel({
			id: 'DevolucionGrid',
			monitorResize:true,
			autoScroll:true,
			loadMask:true,
			stripeRows: true,
			sm: new Ext.grid.CheckboxSelectionModel({
				singleSelect:false
			}),
			title: 'Listado de Devoluciones',
			border: false,
			monitorResize:true,
        	store: ds,
		    viewConfig: {forceFit: true},
        	columns: [
        	    {header: "No. Control", width: 10, dataIndex: 'noControl', sortable: true},
        	    {header: "Vale Referencia", width: 10, dataIndex: 'ValeReferencia', sortable: true},
        	    {header: "Fecha Vale", width: 10, dataIndex: 'fechaVale', sortable: true},
        	    {header: "Fecha de Entrada", width: 10, dataIndex: 'fchRecibido', sortable: true},
				{header: "Estado Vale", width: 10, dataIndex: 'estadoVale', sortable: true, renderer: estadoVale}
				
				],
			tbar: new Ext.Toolbar({
				items: [{
					text: 'Adicionar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/add.png',
					tooltip:'Adicionar Devolución',
					listeners:{
						click:function(){editarDevolucion('../${modulo}/editarDevolucion.htm');}				  			
			  		}
				},{
					text: 'Detalles',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/lightning.png',
					tooltip:'Detalles Devolución',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								detalles(gp, tp, '../${modulo}/detallesDevolucion.htm');
							}
							else{
	           					Ext.MessageBox.show({  
									title: 'Detalles de Devolución',  
									msg: 'Debe seleccionar una Devolución.',  
									buttons: Ext.MessageBox.OK,  
									icon: Ext.MessageBox.ERROR  
	               				});
	           				}			
					  	}
					}
				},{
					text: 'Confirmar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/accept.png',
					tooltip:'Confirmar Devolución',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								Ext.MessageBox.show({
				    		       title:'Confirmación',
						           msg: 'Compruebe que la fecha de operacion este actualizada antes de confirmar ¿Desea Confirmar de todos modos?',
						           buttons: Ext.MessageBox.YESNO,
							           fn: function (btn){
								       	 if (btn == 'yes'){
								       	 	Ext.Ajax.request({
												url: '../${modulo}/confirmarDevolucion.htm',
												params: {
													id: gp.selModel.getSelected().id
												},
												success: function (response, options) {
													if(Ext.decode(response.responseText).success == true){
														gp.getStore().reload();
													}
													else{
														failure(response, options);
													}
												},
												failure: failure,
												waitMsg : 'Eliminando datos...'
											});
													  	}	
									       			   },
								           icon: Ext.MessageBox.QUESTION
								       });
									}
									else{
            							Ext.MessageBox.show({  
											title: 'Listado de las Devoluciones',  
											msg: 'Debe seleccionar una Devolución a confirmar.',  
											buttons: Ext.MessageBox.OK,  
											icon: Ext.MessageBox.ERROR  
                						});
									
								       }
					  			}
		  			}
				},
				
				
				{
					text: 'Eliminar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/delete.png',
					tooltip:'Devolución',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								Ext.MessageBox.show({
				    		       title:'Confirmación',
						           msg: '¿Desea eliminar la Devolución?',
						           buttons: Ext.MessageBox.YESNO,
						           fn: function (btn){
								       	 if (btn == 'yes'){
								       	 	Ext.Ajax.request({
												url: '../${modulo}/eliminarDevolucion.htm',
												params: {
													id: gp.selModel.getSelected().id
												},
												success: function (response, options) {
													if(Ext.decode(response.responseText).success == true){
														gp.getStore().reload();
													}
													else{
														failure(response, options);
													}
												},
												failure: failure,
												waitMsg : 'Eliminando datos...'
											});
										}	
								       },
						           icon: Ext.MessageBox.QUESTION
						       });
							}
							else{
            					Ext.MessageBox.show({  
									title: 'Listado de las Devoluciones',  
									msg: 'Debe seleccionar una Devolución a eliminar.',  
									buttons: Ext.MessageBox.OK,  
									icon: Ext.MessageBox.ERROR  
                				});
            				}
            			}
					}
				},
				{xtype: 'tbspacer'},
				{xtype: 'tbseparator'},
				{xtype: 'tbspacer'},
				{
					text: 'Imprimir',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/printer.png',
					tooltip:'Imprimir Devolución',
					listeners:{
						click:function(){
							descargar('../ecie/imprimirDevolucion.htm', ds.baseParams);
				  		}	
			  		}
				},
				'-','Desde:',
				new Ext.form.DateField({
					width: 80,
					label: 'Fecha:',
					labelStyle: 'font-weight:bold',
					listWidth: 320,
					typeAhead: false,
					id: 'fechaIni',
					triggerAction: 'all',
					autocomplete: "on",
					format:'Y-m-d',
					fieldLabel: 'Fecha',
					listeners:{
					 	select: function(field, fecha){ 
					 		var ff = Ext.getCmp('fechaFin');
					 		ff.reset();
					 		var params = {
					 			'fechaF':null,
					 			'fechaI': fecha.format('Y-m-d')
					 		};					 		
							var lastOptions = ds.baseParams;
							Ext.apply(lastOptions, params);						
							ds.reload({params:lastOptions});
					 	},
			        }
				}),
				'---------------','Hasta:',
				new Ext.form.DateField({
					width: 80,
					label: 'Fecha:',
					labelStyle: 'font-weight:bold',
					listWidth: 320,
					typeAhead: false,
					format:'Y-m-d',
					id: 'fechaFin',
					triggerAction: 'all',
					autocomplete: "on",
					fieldLabel: 'Fecha',
					listeners:{
					 	select: function(field, fecha){ // override default onSelect to do redirect
					 		var params = {
					 					 // Ext.getCmp('fecha1DateField').getRawValue(),
					 			'fechaF': fecha.format('Y-m-d'),
					 			fechaI: Ext.getCmp('fechaIni').getRawValue()
					 		};
							var lastOptions = ds.baseParams;
							Ext.apply(lastOptions, params);
						
							ds.reload({params:lastOptions});
					 	},
			        }
				})]
			}),
			bbar: new Ext.PagingToolbar({
		        store: ds,       // grid and PagingToolbar using same store
		        displayInfo: true,
		        pageSize: 20
		    })
	    });
	   	 
		
		function editarDevolucion(url, id){
			cargarVentana(url, {id: id,tipo: 'Devolucion'});
	    }
   
	    gp.on('celldblclick', gridCellDblClick, this);
		
		function gridCellDblClick(target, rowIndex, columnIndex, e){
			detalles(gp, tp, '../${modulo}/detallesDevolucion.htm');
		}
   		var tp = new Ext.TabPanel({
			id: 'Devolucion',
			border: false,
	        monitorResize:true,
	        enableTabScroll:true,
	        defaults: {autoScroll:true},
	        items:[gp],
	        activeTab:0
		}); 
   	
		var devolucionPanel = new Ext.Panel({
		  border: false,
		  layout: 'fit',
		  title:'Operaciones',
		  items:[tp] 
		});
		
	    
	       
	    
	   	return devolucionPanel;
	}
}
</aek:jsmin>