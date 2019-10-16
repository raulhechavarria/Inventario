<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
	
		var ds = new Ext.data.JsonStore({
            url: '../${modulo}/listarEcie.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','nombre','direccion','organismo.nombre',
   	    	'reup','nit','agenciaMN','agenciaMlc','resolDirectora','cuentaCuc',
   	    	'tituloCtaCuc','cuentaCup','tituloCtaCup','telefono','email','fax','fechaOperacion','numeroValeOperacional']
		});
	
		
		var ecieGrid = new Ext.grid.GridPanel({
			id: 'ecieGrid',
			monitorResize:true,
			autoScroll:true,
			loadMask:true,
			stripeRows: true,
			sm: new Ext.grid.CheckboxSelectionModel({
				singleSelect:false
			}),
			title: 'Ecie',
			border: false,
			monitorResize:true,
        	store: ds,
		    viewConfig: {forceFit: true},
        	columns: [
        	
        	    {header: "Fecha de Operación", width: 10, dataIndex: 'fechaOperacion', sortable: true},
				{header: "Ecie", width: 10, dataIndex: 'nombre', sortable: true},
				{header: "Organismo", width: 10, dataIndex: 'organismo.nombre', sortable: true},
				{header: "Dirección", width: 10, dataIndex: 'direccion', sortable: true},
				{header: "Teléfono", width: 10, dataIndex: 'telefono', sortable: true}
					
			],
		    
			tbar: new Ext.Toolbar({
				items: [
				{
					text: 'Detalles',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/lightning.png',
					tooltip:'Detalles de la Ecie',
					listeners:{
						click:function(){
							if(ecieGrid.selModel.hasSelection()){
								detalles(ecieGrid, tp, '../${modulo}/detallesEcie.htm');
							}
							else{
	           					Ext.MessageBox.show({  
									title: 'Detalles de la Ecie',  
									msg: 'Debe seleccionar una Ecie.',  
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
					tooltip:'Modificar Ecie',
					listeners:{
						click:function(){
							if(ecieGrid.selModel.hasSelection()){
								modificar(ecieGrid, '../${modulo}/editarEcie.htm');
							}
							else{
            					Ext.MessageBox.show({  
									title: 'Ecies',  
									msg: 'Debe seleccionar una Ecie.',  
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
					tooltip:'Imprimir Detalles Ecie',
					listeners:{
						click:function(){
						/*        descargar('../ecie/imprimirEcie.htm',);*/
							descargar('../ecie/impDetallesEcie.htm', ds.baseParams);
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
	    
   	  
		
		
	function editarEcie(url, id){
			cargarVentana(url, {id: id});
	    }
	   
	   ecieGrid.on('celldblclick', gridCellDblClick, this);
		
		function gridCellDblClick(target, rowIndex, columnIndex, e){
			detalles(ecieGrid, tp, '../${modulo}/detallesEcie.htm');
		}
   		var tp = new Ext.TabPanel({
			id: 'ecieTabPanel',
			border: false,
	        monitorResize:true,
	        enableTabScroll:true,
	        defaults: {autoScroll:true},
	        items:[ecieGrid],
	        activeTab:0
		}); 
		
		
		var eciePanel = new Ext.Panel({
		  border: false,
		  layout: 'fit',
		  title:'Nomenclador',
		  items:[tp]
		});
		
	   	return eciePanel;
	}
}
</aek:jsmin>