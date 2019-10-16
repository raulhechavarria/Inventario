<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
	
		var ds = new Ext.data.JsonStore({
            url: '../${modulo}/listarTipoVale.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','nombre','descripcion','codigo','clasificacionTipoVale','precioCosto','margen']
		});
	
	   	var gp = new Ext.grid.GridPanel({
			id: 'tipoValeGrid',
			title: 'Tipo de Vale',
			border: false,
			height: 500,
        	store: ds,
		    viewConfig: {forceFit: true},
        	columns: [
				{header: "Nombre", width: 10, dataIndex: 'nombre', sortable: true},
				{header: "Código", width: 10, dataIndex: 'codigo', sortable: true},
				{header: "Clasificación del TipoVale", width: 10, dataIndex: 'clasificacionProducto', sortable: true},
				{header: "Precio", width: 10, dataIndex: 'precioCosto', sortable: true},
				{header: "Margen", width: 10, dataIndex: 'margen', sortable: true},
				{header: "Descripción", width: 10, dataIndex: 'descripcion', sortable: true}
				
			],
			tbar: new Ext.Toolbar({
				items: [{
					text: 'Adicionar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/add.png',
					tooltip:'Adicionar Tipo de Vale',
					listeners:{
						click:function(){
							cargarVentana('../ecie/edit/editarTipoVale.htm');
						}			  			
			  		}
				},
				{
					text: 'Modificar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/pencil.png',
					tooltip:'Modificar Tipo de Vale',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								modificar(gp, '../${modulo}/editarTipoVale.htm');

								
							}
							else{
            					Ext.MessageBox.show({  
									title: 'Tipo de Vale',  
									msg: 'Debe seleccionar un Tipo de Vale.',  
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
					tooltip:'Tipo de Vale',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								Ext.MessageBox.show({
				    		       title:'Confirmación',
						           msg: '¿Desea eliminar el Tipo de Vale?',
						           buttons: Ext.MessageBox.YESNO,
						           fn: function (btn){
								       	 if (btn == 'yes'){
								       	 	Ext.Ajax.request({
												url: '../${modulo}/eliminarTipoVale.htm',
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
									title: 'Listado de los Tipos de Vales',  
									msg: 'Debe seleccionar un Tipo de Vale a eliminar.',  
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
					tooltip:'Imprimir Tipo de Vale',
					listeners:{
						click:function(){
							descargar('../ecie/imprimirTipoVale.htm', ds.baseParams);
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
	   	 
		
		function editarTipoVale(id){
			cargarVentana('../ecie/edit/editarTipoVale.htm', {id: id});
	   }
   
   	
		var tipoValePanel = new Ext.Panel({
		  border: false,
		  layout: 'fit',
		  title:'Nomencladores',
		  items:[gp] 
		});
		
	    
	       
	    
	   	return tipoValePanel;
	}
}
</aek:jsmin>