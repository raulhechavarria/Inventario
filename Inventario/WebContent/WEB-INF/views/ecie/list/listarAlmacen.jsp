<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
	
		var ds = new Ext.data.JsonStore({
            url: '../${modulo}/listarAlmacen.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','nombre','descripcion','codigo','responsable.nombre','establecimientoEcie.codigo','cargaInicial']
		});
		
	    function cargaInicial(val){
	        if(val == "" || val == "false"){
	            return 'No';
	        }else {
	            return 'Si';
	        }
	    }
	
	   	var gp = new Ext.grid.GridPanel({
			id: 'almacenGrid',
			monitorResize:true,
			autoScroll:true,
			loadMask:true,
			stripeRows: true,
			sm: new Ext.grid.CheckboxSelectionModel({
				singleSelect:false
			}),
			title: 'Listado de los Almacenes',
			border: false,
			height: 500,
        	store: ds,
		    viewConfig: {forceFit: true},
        	columns: [
				{header: "Nombre", width: 10, dataIndex: 'nombre', sortable: true},
				{header: "Código", width: 10, dataIndex: 'codigo', sortable: true},
				{header: "Responsable", width: 10, dataIndex: 'responsable.nombre', sortable: true},
				{header: "Establecimiento ECIE", width: 10, dataIndex: 'establecimientoEcie.codigo', sortable: true},
				{header: "Descripción", width: 10, dataIndex: 'descripcion', sortable: true}
				
			],
			tbar: new Ext.Toolbar({
				items: [{
					text: 'Adicionar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/add.png',
					tooltip:'Adicionar Almacén',
					listeners:{
						click:function(){
						editarAlmacen('../${modulo}/editarAlmacen.htm');}		
						}			  			
			  		
				},
				{
					text: 'Detalles',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/lightning.png',
					tooltip:'Detalles del Almacén',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
							
								detalles(gp, tp, '../${modulo}/detallesAlmacen.htm');
							
							}
							else{
	           					Ext.MessageBox.show({  
									title: 'Detalles del Almacén',  
									msg: 'Debe seleccionar un Almacén.',  
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
					tooltip:'Modificar Almacén',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								modificar(gp, '../${modulo}/editarAlmacen.htm');

								
							}
							else{
            					Ext.MessageBox.show({  
									title: 'Almacenes',  
									msg: 'Debe seleccionar un Almacén.',  
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
					tooltip:'Eliminar Almacén',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								Ext.MessageBox.show({
				    		       title:'Confirmación',
						           msg: '¿Desea eliminar el Almacén?',
						           buttons: Ext.MessageBox.YESNO,
						           fn: function (btn){
								       	 if (btn == 'yes'){
								       	 	Ext.Ajax.request({
												url: '../${modulo}/eliminarAlmacen.htm',
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
									title: 'Listado de los Almacenes',  
									msg: 'Debe seleccionar un Almacén ',  
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
					tooltip:'Imprimir Almacenes',
					listeners:{
						click:function(){
							descargar('../ecie/imprimirAlmacen.htm', ds.baseParams);
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
	   	 
		
		function editarAlmacen(url, id){
			cargarVentana(url, {id: id});
	    }
   
   	function gridCellDblClick(target, rowIndex, columnIndex, e){
			detalles(gp, tp, '../${modulo}/detallesAlmacen.htm');
		}	 
		gp.on('celldblclick', gridCellDblClick, this);
		 
		function editarAlmacen(url, id){
			cargarVentana(url, {id: id});
	    }
   
		
   		var tp = new Ext.TabPanel({
			id: 'almacenTabPanel',
			border: false,
	        monitorResize:true,
	        enableTabScroll:true,
	        defaults: {autoScroll:true},
	        items:[gp],
	        activeTab:0
		});
		
		var almacenPanel = new Ext.Panel({
		  border: false,
		  layout: 'fit',
		  title:'Nomenclador',
		  items:[tp]
		});
		
	    
	       
	    
	   	return almacenPanel;
	}
}
</aek:jsmin>