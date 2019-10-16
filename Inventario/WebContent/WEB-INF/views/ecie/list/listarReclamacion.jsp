<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
	
		var ds = new Ext.data.JsonStore({
			url: '../${modulo}/listarReclamacion.json',
			baseParams: {'tipo': 'Reclamacion' },
 			autoLoad: true,
			root: 'data',
    	    totalProperty: 'totalCount',
		    id: 'id',
            fields: [
	           	'id',
	           	{name: 'noVale', type: 'string'},
	           	{name: 'fechaVale', type: 'string'},
	           	{name: 'empresa', type: 'string'},
	           	{name: 'noDoc', type: 'string'},
	           	{name: 'responsable', type: 'string'},
	         	{name: 'noDoc', type: 'string'},
	           	{name: 'estadoVale', type: 'string'}
		        
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
			id: 'reclamacionGrid',
			monitorResize:true,
			autoScroll:true,
			loadMask:true,
			stripeRows: true,
			sm: new Ext.grid.CheckboxSelectionModel({
				singleSelect:false
			}),
			title: 'Listado de Reclamaciones',
			border: false,
			monitorResize:true,
        	store: ds,
		    viewConfig: {forceFit: true},
        	columns: [
        	    {header: "No Vale", width: 10, dataIndex: 'noVale', sortable: true},
        	    {header: "Proveedor", width: 10, dataIndex: 'empresa', sortable: true},
        	    {header: "Fecha Vale", width: 10, dataIndex: 'fechaVale', sortable: true},
        	    {header: "Estado Vale", width: 10, dataIndex: 'estadoVale', sortable: true, renderer: estadoVale}
				
				],
			tbar: new Ext.Toolbar({
				items: [
				{
					text: 'Adicionar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/add.png',
					tooltip:'Adicionar Reclamacion',
					listeners:{
						click:function(){editarReclamacion('../${modulo}/editarReclamacion.htm');}				  			
			  		}
				}
				,
					{
					text: 'Detalles',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/lightning.png',
					tooltip:'Detalles Reclamación',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								detalles(gp, tp, '../${modulo}/detallesReclamacion.htm');
							}
							else{
	           					Ext.MessageBox.show({  
									title: 'Detalles de Reclamación',  
									msg: 'Debe seleccionar una Reclamación.',  
									buttons: Ext.MessageBox.OK,  
									icon: Ext.MessageBox.ERROR  
	               				});
	           				}			
					  	}
					}
				},
				{
					text: 'Modificar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/pencil.png',
					tooltip:'Modificar Reclamación',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								modificar(gp, '../${modulo}/editarReclamacion.htm');

								
							}
							else{
            					Ext.MessageBox.show({  
									title: 'Reclamación',  
									msg: 'Debe seleccionar una Reclamación',  
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
					tooltip:'Confirmar Reclamación',
					listeners:{
						click:function(){
						confirmar(gp,'../${modulo}/confirReclamacion.htm',this);
							}
		  			}
				},
				{
					text: 'Eliminar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/delete.png',
					tooltip:'Reclamación',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								Ext.MessageBox.show({
				    		       title:'Confirmación',
						           msg: '¿Desea eliminar la Reclamación?',
						           buttons: Ext.MessageBox.YESNO,
						           fn: function (btn){
								       	 if (btn == 'yes'){
								       	 	Ext.Ajax.request({
												url: '../${modulo}/eliminarReclamacion.htm',
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
									title: 'Listado de las Reclamaciones',  
									msg: 'Debe seleccionar una Reclamación a eliminar.',  
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
					tooltip:'Imprimir Reclamación',
					listeners:{
						click:function(){
							descargar('../ecie/imprimirReclamacion.htm', ds.baseParams);
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
	   	 
		
		function editarReclamacion(url, id){
			cargarVentana(url, {id: id, tipo: 'Reclamacion'});
	    }
   
	    gp.on('celldblclick', gridCellDblClick, this);
		
		function gridCellDblClick(target, rowIndex, columnIndex, e){
			detalles(gp, tp, '../${modulo}/detallesReclamacion.htm');
		}
   		var tp = new Ext.TabPanel({
			id: 'Reclamacion',
			border: false,
	        monitorResize:true,
	        enableTabScroll:true,
	        defaults: {autoScroll:true},
	        items:[gp],
	        activeTab:0
		}); 
   	
		var reclamacionPanel = new Ext.Panel({
		  border: false,
		  layout: 'fit',
		  title:'Operaciones',
		  items:[tp] 
		});
		
	    
	       
	    
	   	return reclamacionPanel;
	}
}
</aek:jsmin>