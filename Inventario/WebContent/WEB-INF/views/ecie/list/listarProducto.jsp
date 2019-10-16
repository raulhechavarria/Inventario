<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
	
		var ds = new Ext.data.JsonStore({
            url: '../${modulo}/listarProducto.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','nombre','descripcion','codigo','unidad.nombre','clasificadorProducto.nombre']
		});
	
	   	var gp = new Ext.grid.GridPanel({
			id: 'productoGrid',
			title: 'Listado de los Productos',
			border: false,
			height: 500,
        	store: ds,
		    viewConfig: {forceFit: true},
        	columns: [
				{header: "Producto", width: 10, dataIndex: 'nombre', sortable: true},
				{header: "Código", width: 10, dataIndex: 'codigo', sortable: true},
				{header: "Familia Producto", width: 10, dataIndex: 'clasificadorProducto.nombre', sortable: true},
				{header: "Unidad de Medida", width: 10, dataIndex: 'unidad.nombre', sortable: true},
				{header: "Descripción", width: 10, dataIndex: 'descripcion', sortable: true}
				
			],
			tbar: new Ext.Toolbar({
				items: [{
					text: 'Adicionar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/add.png',
					tooltip:'Adicionar Producto',
					listeners:{
						click:function(){editarProducto('../${modulo}/editarProducto.htm');}		
						}			  			
			  		
				},
				{
					text: 'Modificar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/pencil.png',
					tooltip:'Modificar Producto',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								modificar(gp, '../${modulo}/editarProducto.htm');

								
							}
							else{
            					Ext.MessageBox.show({  
									title: 'Productos',  
									msg: 'Debe seleccionar un Producto.',  
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
					tooltip:'Eliminar Producto',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								Ext.MessageBox.show({
				    		       title:'Confirmación',
						           msg: '¿Desea eliminar el Producto?',
						           buttons: Ext.MessageBox.YESNO,
						           fn: function (btn){
								       	 if (btn == 'yes'){
								       	 	Ext.Ajax.request({
												url: '../${modulo}/eliminarProducto.htm',
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
									title: 'Listado de los Productos',  
									msg: 'Debe seleccionar un Producto ',  
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
					tooltip:'Imprimir Productos',
					listeners:{
						click:function(){
							descargar('../ecie/imprimirProducto.htm', ds.baseParams);
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
	   	 
		
		function editarProducto(url, id){
			cargarVentana(url, {id: id});
	    }
   
   	var tp = new Ext.TabPanel({
			id: 'productoTabPanel',
			border: false,
	        monitorResize:true,
	        enableTabScroll:true,
	        defaults: {autoScroll:true},
	        items:[gp],
	        activeTab:0
		}); 
		var productoPanel = new Ext.Panel({
		  border: false,
		  layout: 'fit',
		  title:'Nomenclador',
		  items:[tp] 
		});
		
	    
	       
	    
	   	return productoPanel;
	}
}
</aek:jsmin>