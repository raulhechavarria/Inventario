<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){				
				
		var ds = new Ext.data.JsonStore({
            url: '../${modulo}/listarCargaInicial.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20, 'tipo': 'CargaInicial' },
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
    	  	fields:['id','fechaVale','noVale','noControl','importeNeto','tipoVale','estadoVale','almacenDestino',
   	    			'fchResponsAutoriza','responsAutoriza','fchSolicElab','solicElab']
		});
	
	   	var gp = new Ext.grid.GridPanel({
			id: 'cargaInicialGrid',
			monitorResize:true,
			autoScroll:true,
			loadMask:true,
			stripeRows: true,
			sm: new Ext.grid.CheckboxSelectionModel({
				singleSelect:false
			}),
			title: 'Listado de las Cargas Iniciales',
			border: false,
			monitorResize:true,
        	store: ds,
		    viewConfig: {forceFit: true},
        	columns: [
        		{header: "No. Vale", width: 10, dataIndex: 'noVale', sortable: true},
				{header: "Fecha de Vale", width: 10, dataIndex: 'fechaVale', sortable: true},
				{header: "No. Control", width: 10, dataIndex: 'noControl', sortable: true},
				{header: "Estado Vale", width: 10, dataIndex: 'estadoVale', sortable: true}
				
			],
			tbar: new Ext.Toolbar({
				items: [{
					text: 'Detalles',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/lightning.png',
					tooltip:'Detalles Carga Inicial',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								detalles(gp, tp, '../${modulo}/detallesCargaInicial.htm');
							}
							else{
	           					Ext.MessageBox.show({  
									title: 'Detalles Carga Inicial',  
									msg: 'Debe seleccionar una Carga Inicial.',  
									buttons: Ext.MessageBox.OK,  
									icon: Ext.MessageBox.ERROR  
	               				});
	           				}			
					  	}
					}
				},{
					text: 'Modificar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/pencil.png',
					tooltip:'Modificar Carga Inicial',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								modificar(gp, '../${modulo}/editarCargaInicial.htm');

								
							}
							else{
            					Ext.MessageBox.show({  
									title: 'Carga Inicial',  
									msg: 'Debe seleccionar una Carga Inicial.',  
									buttons: Ext.MessageBox.OK,  
									icon: Ext.MessageBox.ERROR  
                				});
            				}
					  	}
			  		}
				},
				{
							text: 'Confirmar',
							cls:'x-btn-text-icon bmenu',
							icon:'../img/common/accept.png',
							tooltip:'Confirmar Carga Inicial',
							listeners:{
								click:function(){
									Ext.MessageBox.show({
						    		       title:'Confirmación',
								           msg: 'Compruebe que la fecha de operacion este actualizada antes de confirmar ¿Desea Confirmar de todos modos?',
								           buttons: Ext.MessageBox.YESNO,
								           fn: function (btn){
										       	 if (btn == 'yes'){
										       	 	Ext.Ajax.request({
												url: '../${modulo}/confirmarCargaInicial.htm',
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
					  		}
						}
					
						,
				
				{
					text: 'Eliminar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/delete.png',
					tooltip:'Eliminar Carga Inicial',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								Ext.MessageBox.show({
				    		       title:'Confirmación',
						           msg: '¿Desea eliminar la Carga Inicial?',
						           buttons: Ext.MessageBox.YESNO,
						           fn: function (btn){
								       	 if (btn == 'yes'){
								       	 	Ext.Ajax.request({
												url: '../${modulo}/eliminarCargaInicial.htm',
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
									title: 'Listado de las Carga Iniciales',  
									msg: 'Debe seleccionar una Carga Inicial.',  
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
					tooltip:'Imprimir Carga Inicial',
					listeners:{
						click:function(){
							descargar('../ecie/imprimirCargaInicial.htm', ds.baseParams);
				  		}	
			  		}
				}]
			}),
			bbar: new Ext.PagingToolbar({
		        store: ds,       // grid and PagingToolbar using same store
		        displayInfo: true,
		        pageSize: 20
		    })
	    });
	   	 
		
		function editarCargaInicial(url, id){
			cargarVentana(url, {id: id});
	    }
   
	    gp.on('celldblclick', gridCellDblClick, this);
		
		function gridCellDblClick(target, rowIndex, columnIndex, e){
			detalles(gp, tp, '../${modulo}/detallesCargaInicial.htm');
		}
   		var tp = new Ext.TabPanel({
			id: 'cargaInicial',
			border: false,
	        monitorResize:true,
	        enableTabScroll:true,
	        defaults: {autoScroll:true},
	        items:[gp],
	        activeTab:0
		}); 
   	
		var cargaInicialPanel = new Ext.Panel({
		  border: false,
		  layout: 'fit',
		  title:'Movimientos',
		  items:[tp] 
		});
		
	    
	       
	    
	   	return cargaInicialPanel;
	}
}
</aek:jsmin>