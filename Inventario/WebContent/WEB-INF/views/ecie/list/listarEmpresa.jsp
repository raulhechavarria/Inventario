<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
	
		var ds = new Ext.data.JsonStore({
            url: '../${modulo}/listarEmpresa.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','nombre','direccion','organismo.nombre','reup','codigoNit','agenciaMn','agenciaMlc','resolDirectora','cuentaMlc','tituloMlc','cuentaMn','tituloMn','telefono','correo','fax']
		});
	
		
		var empresaGrid = new Ext.grid.GridPanel({
			id: 'empresaGrid',
			monitorResize:true,
			autoScroll:true,
			loadMask:true,
			stripeRows: true,
			sm: new Ext.grid.CheckboxSelectionModel({
				singleSelect:false
			}),
			title: 'Listado de los Proveedores',
			border: false,
			monitorResize:true,
        	store: ds,
		    viewConfig: {forceFit: true},
        	columns: [
				{header: "Proveedor", width: 10, dataIndex: 'nombre', sortable: true},
				{header: "Organismo", width: 10, dataIndex: 'organismo.nombre', sortable: true},
				{header: "Dirección", width: 10, dataIndex: 'direccion', sortable: true},
				{header: "Teléfono", width: 10, dataIndex: 'telefono', sortable: true}
					
			],
		    
			tbar: new Ext.Toolbar({
				items: [{
					text: 'Adicionar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/add.png',
					tooltip:'Adicionar Proveedor',
					listeners:{
						click:function(){editarEmpresa('../${modulo}/editarEmpresa.htm');}
			  		}
				},
				{
					text: 'Detalles',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/lightning.png',
					tooltip:'Detalles del Proveedor',
					listeners:{
						click:function(){
							if(empresaGrid.selModel.hasSelection()){
								detalles(empresaGrid, tp, '../${modulo}/detallesEmpresa.htm');
							}
							else{
	           					Ext.MessageBox.show({  
									title: 'Detalles del Proveedor',  
									msg: 'Debe seleccionar un Proveedor.',  
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
					tooltip:'Modificar Proveedor',
					listeners:{
						click:function(){
							if(empresaGrid.selModel.hasSelection()){
								modificar(empresaGrid, '../${modulo}/editarEmpresa.htm');
							}
							else{
            					Ext.MessageBox.show({  
									title: 'Proveedores',  
									msg: 'Debe seleccionar un Proveedor.',  
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
					tooltip:'Eliminar Proveedor',
					listeners:{
						click:function(){
							if(empresaGrid.selModel.hasSelection()){
								Ext.MessageBox.show({
				    		       title:'Confirmación',
						           msg: '¿Desea eliminar el Proveedor?',
						           buttons: Ext.MessageBox.YESNO,
						           fn: function (btn){
								       	 if (btn == 'yes'){
								       	 	Ext.Ajax.request({
												url: '../${modulo}/eliminarEmpresa.htm',
												params: {
													id: empresaGrid.selModel.getSelected().id
												},
												success: function (response, options) {
													if(Ext.decode(response.responseText).success == true){
														empresaGrid.getStore().reload();
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
									title: 'Listado de los Proveedores',  
									msg: 'Debe seleccionar un Proveedor',  
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
					tooltip:'Imprimir Proveedores',
					listeners:{
						click:function(){
							descargar('../ecie/imprimirEmpresa.htm', ds.baseParams);
				  		}	
			  		}
				}
				]
			}),
			bbar: new Ext.PagingToolbar({
		        store: ds,       // grid and PagingToolbar using same store
		        displayInfo: true,
		        pageSize: 20
		    })
	    });
	    
   	  
		
		
	function editarEmpresa(url, id){
			cargarVentana(url, {id: id});
	    }
	   
	   empresaGrid.on('celldblclick', gridCellDblClick, this);
		
		function gridCellDblClick(target, rowIndex, columnIndex, e){
			detalles(empresaGrid, tp, '../${modulo}/detallesEmpresa.htm');
		}
   		var tp = new Ext.TabPanel({
			id: 'empresaTabPanel',
			border: false,
	        monitorResize:true,
	        enableTabScroll:true,
	        defaults: {autoScroll:true},
	        items:[empresaGrid],
	        activeTab:0
		}); 
		
		
		var empresaPanel = new Ext.Panel({
		  border: false,
		  layout: 'fit',
		  title:'Nomenclador',
		  items:[tp]
		});
		
	   	return empresaPanel;
	}
}
</aek:jsmin>