<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
		
		var ds = new Ext.data.JsonStore({
	        url: '../common/listarAudit.json',
	        autoLoad: true,
	      	remoteSort: true,
			baseParams: {limit:20},
	  	    root: 'data',
	   	    totalProperty: 'totalCount',
	   	    id: 'id',
	 	    fields:['id','action', 'clientIp','serverIp','principal','date']
		});
	
		var gp = new Ext.grid.GridPanel({
			id: 'AuditGrid',
			title: 'Listado de Auditorías',
			monitorResize:true,
			loadMask:true,
			stripeRows: true,
			sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
		   	store: ds,
		    viewConfig: {forceFit: true},
	       	columns: [
				{header: "Acción", width: 120, dataIndex: 'action', sortable: true},
				{header: "Ip Cliente", width: 180, dataIndex: 'clientIp', sortable: true},
				{header: "Ip Server", width: 180, dataIndex: 'serverIp', sortable: true},
				{header: "Usuario", width: 180, dataIndex: 'principal', sortable: true},
				{header: "Fecha", width: 180, dataIndex: 'date', sortable: true}
			],
			tbar: new Ext.Toolbar({
				items: [
				{
					text: 'Detalles',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/lightning.png',
					tooltip:'Ver detalles de la Auditoría',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								cargarDetallesAudit(gp.selModel.getSelected().id);
							}
							else{
								showWarningMsg('Detalles del Auditoría','Debe seleccionar una acción de Auditoría');
	           				}			
					  	}
			  		}
				},
			/*	{xtype: 'tbspacer'},
				{xtype: 'tbseparator'},
				{xtype: 'tbspacer'},
				{
					text: 'Imprimir',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/printer.png',
					tooltip:'Imprimir Listado',
					listeners:{
						click:function(){
							descargar('../common/imprimirAudit.htm', ds.baseParams);
						}			  			
			  		}
				},*/
				{xtype: 'tbfill'},
				'Buscar: ',
				new Ext.app.SearchField({
	                store: ds,
	                width:320
	            })]
			}),
			bbar: new Ext.PagingToolbar({
		        store: ds,
		        displayInfo: true,
		        pageSize: 20
		    })
	    });
		
	    gp.on('celldblclick', gridCellDblClick, this);
		
		function gridCellDblClick(target, rowIndex, columnIndex, e){
			cargarDetallesAudit(gp.selModel.getSelected().id);
		}
	    
		var tp = new Ext.TabPanel({
			id: 'AuditTabPanel',
			resizeTabs:true,
			border: false,
	        minTabWidth: 115,
	        tabWidth:150,
			monitorResize:true,
	        enableTabScroll:true,
	        defaults: {autoScroll:true},
	        items:[gp],
	        activeTab:0
		}); 
				    
		function cargarDetallesAudit(id){					
			cargarTab(tp, 'detallesAuditPanel',
					  '../common/detallesAudit.htm', {id: id});
	    }
	
		var AuditPanel = new Ext.Panel({
			border: false,
			layout: 'fit',
			title:'Auditoría',
			items:[tp]
		});
	    
	    return AuditPanel;
	}
}
</aek:jsmin>