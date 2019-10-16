<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
	
		var ds = new Ext.data.JsonStore({
            url: '../${modulo}/listarCentroCosto.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','nombre','descripcion','codigo']
		});
	
		
		var centroCostoGrid = new Ext.grid.GridPanel({
			id: 'centroCostoGrid',
			monitorResize:true,
			autoScroll:true,
			loadMask:true,
			stripeRows: true,
			sm: new Ext.grid.CheckboxSelectionModel({
				singleSelect:false
			}),
			title: 'Listado de los Centros de Costo',
			border: false,
			monitorResize:true,
        	store: ds,
		    viewConfig: {forceFit: true},
        	columns: [
				{header: "Nombre", width: 10, dataIndex: 'nombre', sortable: true},
				{header: "Código", width: 10, dataIndex: 'codigo', sortable: true},
				{header: "Descripción", width: 10, dataIndex: 'descripcion', sortable: true}
				
					
			],
		    
			tbar: new Ext.Toolbar({
				items: [{
					text: 'Adicionar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/add.png',
					tooltip:'Adicionar Centro de Costo',
					listeners:{
						click:function(){editarCentroCosto('../${modulo}/editarCentroCosto.htm');}
			  		}
				},
				
				{
					text: 'Modificar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/pencil.png',
					tooltip:'Modificar Centro de Costo',
					listeners:{
						click:function(){
							if(centroCostoGrid.selModel.hasSelection()){
								modificar(centroCostoGrid, '../${modulo}/editarCentroCosto.htm');
							}
							else{
            					Ext.MessageBox.show({  
									title: 'Centro de Costo',  
									msg: 'Debe seleccionar un Centro de Costo.',  
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
					tooltip:'Eliminar Centro de Costo',
					listeners:{
						click:function(){
							if(centroCostoGrid.selModel.hasSelection()){
								Ext.MessageBox.show({
				    		       title:'Confirmación',
						           msg: '¿Desea eliminar el Centro de Costo?',
						           buttons: Ext.MessageBox.YESNO,
						           fn: function (btn){
								       	 if (btn == 'yes'){
								       	 	Ext.Ajax.request({
												url: '../ecie/eliminarCentroCosto.htm',
												params: {
													id: centroCostoGrid.selModel.getSelected().id
												},
												success: function (response, options) {
													if(Ext.decode(response.responseText).success == true){
														centroCostoGrid.getStore().reload();
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
									title: 'Listado de los Centros de Costos',  
									msg: 'Debe seleccionar un Centro de Costo',  
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
					tooltip:'Imprimir Centros de Costos',
					listeners:{
						click:function(){
							descargar('../ecie/imprimirCentroCosto.htm', ds.baseParams);
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
	    
   	  
		
		
	function editarCentroCosto(url, id){
			cargarVentana(url, {id: id});
	    }
	   
	   centroCostoGrid.on('celldblclick', gridCellDblClick, this);
		
		function gridCellDblClick(target, rowIndex, columnIndex, e){
			detalles(centroCostoGrid, tp, '../${modulo}/detallesCentroCosto.htm');
		}
   		var tp = new Ext.TabPanel({
			id: 'centroCostoTabPanel',
			border: false,
	        monitorResize:true,
	        enableTabScroll:true,
	        defaults: {autoScroll:true},
	        items:[centroCostoGrid],
	        activeTab:0
		}); 
		
		
		var centroCostoPanel = new Ext.Panel({
		  border: false,
		  layout: 'fit',
		  title:'Nomenclador',
		  items:[tp]
		});
		
	   	return centroCostoPanel;
	}
}
</aek:jsmin>