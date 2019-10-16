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
				 'tipo': 'RecepcionACiegas'
			},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','producto','cantProdVale','precioMNVale','importeMNVale','precioMLCVale','importeMLCVale','recargoDescuentoMN','recargoDescuentoMLC',
   	    	'totalMN','totalMLC','existenciaOrigen','existenciaDestino','codigo','unidadMedida']
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
					value:'${command.estadoVale}',
					fieldLabel:'Estado Vale'
				}),
				new Ext.form.DisplayField({
					value:'${command.empresa.nombre}',
					fieldLabel:'Proveedor'
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
					value:'${command.almacenDestino.nombre}',
					fieldLabel:'Almacén Destino'
				}),

				new Ext.form.DisplayField({
					value:'${command.recibido.nombre}',
					fieldLabel:'Recibido'
				}),
				new Ext.form.DisplayField({
					value:'${command.fchRecibido}',
					fieldLabel:'Fecha Recibido'
				}),
				new Ext.form.DisplayField({
					value:'${command.comentario}',
					fieldLabel:'Comentario'
				})
			]	
		});
		
		
		var detallesRecepcionACiegasGrid = new Ext.grid.GridPanel({
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
	       		{header: "Producto", width: 80, dataIndex: 'producto', sortable: true},
				{header: "Código", width: 80, dataIndex: 'codigo', sortable: true},
				{header: "Cantidad Producto", width: 80, dataIndex: 'cantProdVale', sortable: true},
				{header: "Unidad de Medida", width: 80, dataIndex: 'unidadMedida', sortable: true}
			],
			tbar: new Ext.Toolbar({
				items: [
				{
					text: 'Adicionar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/add.png',
					listeners:{
						click:function(){
							editarEntradaValeProducto('../${modulo}/editarSalidaValeProducto.htm',{ 'idVale': '${command.id}'});
						}		
					}	  			
		  		},{
					text: 'Modificar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/pencil.png',
					tooltip:'Modificar Producto',
					listeners:{
						click:function(){
							if(detallesRecepcionACiegasGrid.selModel.hasSelection()){
							
								modificarVale(detallesRecepcionACiegasGrid, '../${modulo}/editarSalidaValeProducto.htm',{ 'idVale': '${command.id}'});
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
							if(detallesRecepcionACiegasGrid.selModel.hasSelection()){
								Ext.MessageBox.show({
				    		       title:'Confirmación',
						           msg: '¿Desea eliminar el Vale Producto?',
						           buttons: Ext.MessageBox.YESNO,
						           fn: function (btn){
								       	 if (btn == 'yes'){
								       	 	Ext.Ajax.request({
												url: '../${modulo}/deleteEntradaValeProducto.htm',
												params: {
													id: detallesRecepcionACiegasGrid.selModel.getSelected().id
												},
												success: function (response, options) {
													if(Ext.decode(response.responseText).success == true){
														detallesRecepcionACiegasGrid.getStore().reload();
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
	    
		var detallesRecepcionACiegas= new Ext.Panel({
			id: 'detallesRecepcionACiegas',
			title: 'Detalles Recepción a Ciegas',
			closable: true,
			layout: 'border',
			height: 600,
			border:false,
			monitorResize:true,
			autoScroll: true,
			items:[
				new Ext.form.FormPanel({
					id: 'detallesRecepcionACiegasForm',
					layout:'column',
					region:'north',
					split: true,
					collapseMode: 'mini',
					height: 240,
					maxSize: 240,
					minSize: 62,
					border: false,
					items:[
						leftPanel, centerPanel
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
						tooltip:'Imprimir Detalles de la Recepcion a Ciegas',
							listeners:{
								click:function(){
									descargar('../ecie/impDetallesRecepcionACiegas.htm', { idVale: '${command.id}','tipo': 'RecepcionACiegas'});
							}			  			
				  		}
					}
					
						
					  ]	
					})
				}),
				
				new Ext.TabPanel({
					id:'recepcionACiegasTabPanel',
					region:'center',
					border: false,
					activeTab:0,
					items:[
						detallesRecepcionACiegasGrid
					]
				})
				
			]
		});
		
	   detallesRecepcionACiegasGrid.on('celldblclick', gridCellDblClick, this);
		
		function gridCellDblClick(target, rowIndex, columnIndex, e){
			cargarVentana('../${modulo}/editarSalidaValeProducto.htm', {id: detallesRecepcionACiegasGrid.selModel.getSelected().id,'idVale': '${command.id}'});
		}
		
		
		function editarEntradaValeProducto(url, id){
			var idp = id.idVale;
			cargarVentana(url, {idVale: idp});
	    }
	    
	   function modificarVale(detallesRecepcionACiegasGrid, url, id){
	   var idm = id.idVale;
			cargarVentana(url, {id: detallesRecepcionACiegasGrid.selModel.getSelected().id,idVale:idm});
		}
	   
	   
		return detallesRecepcionACiegas;			
	}
}
</aek:jsmin>