 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){

			
		var valeProductoStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarValeProducto.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {
				limit:20,
				 'idVale': '${command.id}',
				 'tipo': 'Recepcion'
			},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','producto','cantProdVale','precioMNVale','importeMNVale','precioMLCVale','importeMLCVale','recargoDescuentoMN','recargoDescuentoMLC',
   	    	'totalMN','totalMLC','existenciaOrigen','existenciaDestino']
		});
		var estadoValeStore = new Ext.data.SimpleStore({
            fields:['id', 'estadoVale'],
            data:[
            	['Confirmado','Confirmado'],
            	['Revision','Revision'],
            	['Confeccion','Confeccion'],
            	['Cancelado','Cancelado']
            ]
		});
		
		var recepcionStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarRecepcion.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20, 'tipo': 'Recepcion' },
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
    	  	fields:['id','fechaVale','fchRecibido','fchResponsAutoriza','fchSolicElab','noVale','noControl',
   	    	        'comentario','totalMN','totalMLC','recargoMN','recargoMLC','descuentoMN','descuentoMLC',
   	    			'serviciosMN','serviciosMLC','importeNeto','tipoVale','estadoVale','empresa','almacenDestino',
   	    			'responsAutoriza','solicElab','recibido','centroCosto']
		});
	
		var empresaStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarEmpresa.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','nombre','direccion','organismo.nombre','reup','codigoNit','agenciaMn','agenciaMlc','resolDirectora','cuentaMlc','tituloMlc','cuentaMn','tituloMn','telefono','correo','fax']
		});
		
		var almacenStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarAlmacen.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','nombre','descripcion','codigo','responsable.nombre','establecimientoEcie.codigo']
		});
		
		
    	var recibidoStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarResponsable.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'nombre', 'descripcion']
    	});
    	var centroCostoStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarCentroCosto.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'nombre']
    	});
		var leftPanel = new Ext.Panel({
			columnWidth: 0.33,
			border: false,
			bodyStyle: 'padding:10px 10px 0',
			defaults: {width: 250},
			labelWidth: 100,
			layout: 'form',
			items:[
				new Ext.form.DisplayField({
					value:'${command.fechaVale}',
					fieldLabel:'Fecha Vale'
				}),
				new Ext.form.DisplayField({
					value:'${command.noVale}',
					fieldLabel:'No. Vale'
				}),
				new Ext.form.DisplayField({
					value:'${command.noControl}',
					fieldLabel:'No. Control'
				}),
				new Ext.form.DisplayField({
					value:'${command.totalMN}',
					fieldLabel:'Total CUP'
				}),
				new Ext.form.DisplayField({
					value:'${command.totalMLC}',
					fieldLabel:'Total CUC'
				}),
				new Ext.form.DisplayField({
					value:'${command.importeMN}',
					fieldLabel:'Importe CUP'
				})
				
			]	
		});
		
		var centerPanel = new Ext.Panel({
			columnWidth: 0.33,
			border: false,
			bodyStyle: 'padding:6px 50px 0',
			defaults: {width: 250},
			labelWidth: 120,
			layout: 'form',
			items:[
				new Ext.form.DisplayField({
					value:'${command.importeMLC}',
					fieldLabel:'Importe CUC'
				}),
				new Ext.form.DisplayField({
					value:'${command.importeNeto}',
					fieldLabel:'Importe Neto'
				}),
				new Ext.form.DisplayField({
					value:'${command.estadoVale}',
					fieldLabel:'Estado Vale'
				}),
				new Ext.form.DisplayField({
					value:'${command.empresa.nombre}',
					fieldLabel:'Proveedor'
				}),
				new Ext.form.DisplayField({
					value:'${command.almacenDestino.nombre}',
					fieldLabel:'Almacén Destino'
				}),

				new Ext.form.DisplayField({
					value:'${command.recibido.nombre}',
					fieldLabel:'Recibido'
				})
			]	
		});
		
		var rightPanel = new Ext.Panel({
			columnWidth: 0.33,
			border: false,
			bodyStyle: 'padding:10px 10px 0',
			defaults: {width: 250},
			labelWidth: 120,
			layout: 'form',
			items:[
				
				new Ext.form.DisplayField({
					value:'${command.noDoc}',
					fieldLabel:'No. Documento'
				}),
				new Ext.form.DisplayField({
					value:'${command.nombreDoc}',
					fieldLabel:'Documento'
				}),
				new Ext.form.DisplayField({
					value:'${command.nombreTransportador}',
					fieldLabel:'Transportador'
				}),
				new Ext.form.DisplayField({
					value:'${command.cITransportador}',
					fieldLabel:'CI. Transportador'
				}),
				new Ext.form.DisplayField({
					value:'${command.noTransporte}',
					fieldLabel:'No. Transporte'
				}),
				new Ext.form.DisplayField({
					value:'${command.comentario}',
					fieldLabel:'Comentario'
				})
			]
		});
		
		var detallesRecepcionGrid = new Ext.grid.GridPanel({
			id: 'detallesRecepcionGrid',
			loadMask:true,
			title:'Productos',
			monitorResize:true,
			border: false,
			stripeRows:true,
			autoScroll:true,
			height: 220,
			store: valeProductoStore,
		    viewConfig: {forceFit: true},
			sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
	       	columns:  [
	       		{header: "Código", width: 80, dataIndex: 'producto', sortable: true},
				{header: "Cant. Prod", width: 80, dataIndex: 'cantProdVale', sortable: true},
				{header: "Precio CUP", width: 80, dataIndex: 'precioMNVale', sortable: true},
				{header: "Importe CUP", width: 80, dataIndex: 'importeMNVale', sortable: true},
				{header: "R.-D. CUP", width: 80, dataIndex: 'recargoDescuentoMN', sortable: true},
				{header: "<b><font color=black> Total CUP</font></b>", width: 80, dataIndex: 'totalMN', sortable: true},	
				{header: "Precio CUC", width: 80, dataIndex: 'precioMLCVale', sortable: true},
				{header: "Importe CUC", width: 80, dataIndex: 'importeMLCVale', sortable: true},
				{header: "R.-D. CUC", width: 80, dataIndex: 'recargoDescuentoMLC', sortable: true},
				{header: "<b><font color=black> Total CUC</font></b>", width: 80, dataIndex: 'totalMLC', sortable: true},
				{header: "Existencia Destino", width: 80, dataIndex: 'existenciaDestino', sortable: true}
			],
			tbar: new Ext.Toolbar({
				items: [{
					text: 'Modificar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/pencil.png',
					tooltip:'Modificar Producto',
					listeners:{
						click:function(){
							if(detallesRecepcionGrid.selModel.hasSelection()){
							
								modificarVale(detallesRecepcionGrid, '../${modulo}/editarEntradaValeProducto.htm',{ 'idVale': '${command.id}'});
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
				}]
			}),
			bbar: new Ext.PagingToolbar({
		        store: valeProductoStore,       // grid and PagingToolbar using same store
		        displayInfo: true,
		        pageSize: 20
		    })
	    });
	    
		var detallesRecepcion= new Ext.Panel({
			id: 'detallesRecepcion',
			title: 'Detalles Recepción',
			closable: true,
			layout: 'border',
			height: 600,
			border:false,
			monitorResize:true,
			autoScroll: true,
			items:[
				new Ext.form.FormPanel({
					id: 'detallesRecepcionForm',
					layout:'column',
					region:'north',
					split: true,
					collapseMode: 'mini',
					height: 240,
					maxSize: 240,
					minSize: 62,
					border: false,
					items:[
						leftPanel, centerPanel, rightPanel
					],
					tbar: new Ext.Toolbar({
						items: [
						
					{xtype: 'tbspacer'},
					{xtype: 'tbseparator'},
					{xtype: 'tbspacer'},
					{
						text: 'Imprimir',
						cls:'x-btn-text-icon bmenu',
						icon:'../img/common/printer.png',
						tooltip:'Imprimir Detalles del Recepcion',
							listeners:{
								click:function(){
									descargar('../ecie/impDetallesRecepcion.htm', { idVale: '${command.id}','tipo': 'Recepcion'});
							}			  			
				  		}
					}
					
						
					  ]	
					})
				}),
				
				new Ext.TabPanel({
					id:'recepcionTabPanel',
					region:'center',
					border: false,
					activeTab:0,
					items:[
						detallesRecepcionGrid
					]
				})
				
			]
		});
		
	   detallesRecepcionGrid.on('celldblclick', gridCellDblClick, this);
	   
		function gridCellDblClick(target, rowIndex, columnIndex, e){
			cargarVentana('../${modulo}/editarEntradaValeProducto.htm', {id: detallesRecepcionGrid.selModel.getSelected().id,'idVale': '${command.id}'});
		}
		
		function editarEntradaValeProducto(url, id){
			var idp = id.idVale;
			cargarVentana(url, {idVale: idp});
	    }
	    
	   function modificarVale(detallesRecepcionGrid, url, id){
	   var idm = id.idVale;
			cargarVentana(url, {id: detallesRecepcionGrid.selModel.getSelected().id,idVale:idm});
		}
	   
	   
		return detallesRecepcion;			
	}
}
</aek:jsmin>