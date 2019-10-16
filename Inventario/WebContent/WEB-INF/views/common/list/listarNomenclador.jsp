<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
		var ds = new Ext.data.JsonStore({
            url: '../${modulo}/listar${nomenclador}.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:[${fields}]
		});
		
		var gp = new Ext.grid.GridPanel({
			id: 'nomencladorGrid',
			border: false,
			title: 'Nomenclador de ${nombreNomenclador}',
			loadMask:true,
			autoScroll:true,
			stripeRows:true,
			monitorResize: true,
			autoScroll:true,
			autoExpandColumn:'nombre',
			sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
        	store: ds,
		    viewConfig: {forceFit: true},
        	columns: [${columns}],
			tbar: new Ext.Toolbar({
				items: [
				{
					text: 'Adicionar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/add.png',
					tooltip:'Adicionar ${nomenclador}',
					listeners:{
						click:function(){
							cargarVentana('../${modulo}/editar${nomenclador}.htm');
						}			  			
			  		}
				},
				{
					text: 'Modificar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/pencil.png',
					tooltip:'Modificar ${nomenclador}',
					listeners:{
						click:function(){
							modificar(gp, '../${modulo}/editar${nomenclador}.htm');
					  	}
			  		}
				},/*
				{
					text: 'Detalles',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/lightning.png',
					tooltip:'Detalles de ${nomenclador}',
					listeners:{
						click:function(){
							detalles(gp, tp, '../${modulo}/detalles${nomenclador}.htm');
					  	}
			  		}
				},*/
				{
					text: 'Eliminar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/delete.png',
					tooltip:'Eliminar ${nomenclador}',
					listeners:{
						click:function(){
							eliminar(gp, '../${modulo}/eliminar${nomenclador}.htm');
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
					tooltip:'Imprimir Listado',
					listeners:{
						click:function(){
							descargar('../${modulo}/imprimir${nomenclador}.htm', ds.baseParams);
						}			  			
			  		}
				},
				{xtype: 'tbfill'},
				{
					cls:'x-btn-text-icon bmenu',
					text: 'Buscar',
					icon:'../img/common/Buscar.PNG',				
					tooltip:'Buscar ${nomenclador}',
					listeners:{
						click:function(){
							cargarVentana('../${modulo}/buscar${nomenclador}.htm');
						}
					}
				},
				{
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/edit-clear16.png',	
					tooltip:'Limpiar Búsqueda',
					listeners:{
						click:function(){
							
							var lastOptions = ds.baseParams;
							var params = lastOptions;
							
							for(param in params){
								if(param != 'limit'){
									params[param] = '';
								}
							}
							Ext.apply(lastOptions, params);
							ds.reload({params:lastOptions});
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
		    
		gp.on('celldblclick', gridCellDblClick, this);
		
		function gridCellDblClick(target, rowIndex, columnIndex, e){
			cargarVentana('../${modulo}/editar${nomenclador}.htm',{id: gp.selModel.getSelected().id});
		}
		
		var tp = new Ext.TabPanel({
			id: 'nomencladorTabPanel',
			//resizeTabs:true, // turn on tab resizing
			border: false,
	        minTabWidth: 115,
	        tabWidth:250,
			monitorResize:true,
	        enableTabScroll:true,
	        defaults: {autoScroll:true},
	        items:[gp],
	        activeTab:0
		});
		
		var nomencladorPanel = new Ext.Panel({
			border: false,
			layout: 'fit',
			items:[tp]
		});
				
		return nomencladorPanel;
	}
}
</aek:jsmin>