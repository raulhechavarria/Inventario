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
				 'tipo': 'Factura_MN_MLC'
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
		
		/*var facturaStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarFactura.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20, 'tipo': 'Factura_MN_MLC' },
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
    	  	fields:['id','fechaVale','fchRecibido','fchResponsAutoriza','fchSolicElab','noVale','noControl',
   	    	        'comentario','totalMN','totalMLC','recargoMN','recargoMLC','descuentoMN','descuentoMLC',
   	    			'serviciosMN','serviciosMLC','importeNeto','tipoVale','estadoVale','empresa','almacenDestino',
   	    			'responsAutoriza','solicElab','recibido','centroCosto']
		});*/
	
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
					value:'${command.recargoDescuentoMN}',
					fieldLabel:'Recargo CUP en %'
				}),
				new Ext.form.DisplayField({
					value:'${command.recargoDescuentoMLC}',
					fieldLabel:'Recargo CUC en %'
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
					value:'${command.importeMN}',
					fieldLabel:'Importe CUP'
				}),
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
					value:'${command.almacenOrigen.nombre}',
					fieldLabel:'Almac�n Destino'
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
		
		var detallesFacturaGrid = new Ext.grid.GridPanel({
			id: 'detallesFacturaGrid',
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
	       		{header: "C�digo", width: 80, dataIndex: 'producto', sortable: true},
				{header: "Cantidad Producto", width: 80, dataIndex: 'cantProdVale', sortable: true},
				{header: "Precio CUP", width: 80, dataIndex: 'precioMNVale', sortable: true},
				{header: "Importe CUP", width: 80, dataIndex: 'importeMNVale', sortable: true},
				{header: "Recargo CUP", width: 80, dataIndex: 'recargoDescuentoMN', sortable: true},
				{header: "<b><font color=black> Total CUP</font></b>", width: 80, dataIndex: 'totalMN', sortable: true},	
				{header: "Precio CUC", width: 80, dataIndex: 'precioMLCVale', sortable: true},
				{header: "Importe CUC", width: 80, dataIndex: 'importeMLCVale', sortable: true},
				{header: "Recargo CUC", width: 80, dataIndex: 'recargoDescuentoMLC', sortable: true},
				{header: "<b><font color=black> Total CUC</font></b>", width: 80, dataIndex: 'totalMLC', sortable: true},
				{header: "Existencia Origen", width: 80, dataIndex: 'existenciaOrigen', sortable: true}
			],
			tbar: new Ext.Toolbar({
				items: [
				{
					text: 'Adicionar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/add.png',
					listeners:{
						click:function(){
							editarSalidaValeProducto('../${modulo}/editarSalidaValeProducto.htm',{ 'idVale': '${command.id}'});
						}		
					}	  			
		  		},{
					text: 'Modificar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/pencil.png',
					tooltip:'Modificar Producto',
					listeners:{
						click:function(){
							if(detallesFacturaGrid.selModel.hasSelection()){
							
								modificarVale(detallesFacturaGrid, '../${modulo}/editarSalidaValeProducto.htm',{ 'idVale': '${command.id}'});
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
				},{
						text: 'Eliminar',
						cls:'x-btn-text-icon bmenu',
						icon:'../img/common/delete.png',
						tooltip:'Eliminar Vale Producto',
						listeners:{
						click:function(){
							if(detallesFacturaGrid.selModel.hasSelection()){
								Ext.MessageBox.show({
				    		       title:'Confirmaci�n',
						           msg: '�Desea eliminar el Vale Producto?',
						           buttons: Ext.MessageBox.YESNO,
						           fn: function (btn){
								       	 if (btn == 'yes'){
								       	 	Ext.Ajax.request({
												url: '../${modulo}/deleteSalidaValeProducto.htm',
												params: {
													id: detallesFacturaGrid.selModel.getSelected().id
												},
												success: function (response, options) {
													if(Ext.decode(response.responseText).success == true){
														detallesFacturaGrid.getStore().reload();
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
									title: 'Vale Producto',  
									msg: 'Debe seleccionar un Vale Producto a eliminar.',  
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
	    
		var detallesFactura= new Ext.Panel({
			id: 'detallesFactura',
			title: 'Detalles Factura',
			closable: true,
			layout: 'border',
			height: 600,
			border:false,
			monitorResize:true,
			autoScroll: true,
			items:[
				new Ext.form.FormPanel({
					id: 'detallesFacturaForm',
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
						tooltip:'Imprimir Detalles de la Factura',
							listeners:{
								click:function(){
									descargar('../ecie/impDetallesFactura.htm', { idVale: '${command.id}','tipo': 'Factura_MN_MLC'});
							}			  			
				  		}
					}
					
						
					  ]	
					})
				}),
				
				new Ext.TabPanel({
					id:'facturaTabPanel',
					region:'center',
					border: false,
					activeTab:0,
					items:[
						detallesFacturaGrid
					]
				})
				
			]
		});
		
	   detallesFacturaGrid.on('celldblclick', gridCellDblClick, this);
		
		function gridCellDblClick(target, rowIndex, columnIndex, e){
			cargarVentana('../${modulo}/editarSalidaValeProducto.htm', {id: detallesFacturaGrid.selModel.getSelected().id,'idVale': '${command.id}'});
		}
		
		
		function editarSalidaValeProducto(url, id){
			var idp = id.idVale;
			cargarVentana(url, {idVale: idp});
	    }
	    
	   function modificarVale(detallesFacturaGrid, url, id){
	   var idm = id.idVale;
			cargarVentana(url, {id: detallesFacturaGrid.selModel.getSelected().id,idVale:idm});
		}
	   
	   
		return detallesFactura;			
	}
}
</aek:jsmin>