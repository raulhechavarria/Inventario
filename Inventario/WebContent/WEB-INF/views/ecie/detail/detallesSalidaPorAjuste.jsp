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
				 'tipo': 'SalidaPorAjuste'
			},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','producto','cantProdVale','precioMNVale','importeMNVale','precioMLCVale','importeMLCVale',
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
		var conceptoAjusteStore = new Ext.data.SimpleStore({
            fields:['id', 'conceptoAjuste'],
            data:[
			['Mermas', 'Mermas'],
			['RoturasAlmacen', 'Roturas en almacén'],
			['MermasRoturasTransport', 'Mermas y roturas transportación'],
			['MermasRoturasDistrib', 'Mermas y roturas Distribución'],
			['MermasAveriasMuelle', 'Mermas y averías Muelle'],
			['MalEstado', 'Mal estado '],
			['Vencimiento', 'Vencimiento'],
			['FaltantesReenvase', 'Faltantes en reenvase'],
			['SobrantesReenvase', 'Sobrantes en Reenvase'],
			['FaltanteConteoFisico', 'Faltante en Conteo Físico'],
			['SobranteConteoFisico', 'Sobrante en Conteo Físico'],
			['BajaUtensiliosHerramientas', 'Baja Utensilios y Herramientas'],
			['Otro', 'Otro']
           ]
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
		
		var responsAutorizaStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarResponsable.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'nombre', 'descripcion']
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

		var salidaPorAjuste= new Ext.data.JsonStore({
            url: '../${modulo}/listarSalidaPorAjuste.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20, 'tipo': 'SalidaPorAjuste' },
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','fechaVale','fchRecibido','fchResponsAutoriza','fchSolicElab','noVale','noControl',
   	    	        'comentario','totalMN','totalMLC','descuentoMN','descuentoMLC',
   	    			'serviciosMN','serviciosMLC','importeNeto','tipoVale','estadoVale','empresa','almacenOrigen',
   	    			'responsAutoriza','solicElab','recibido','centroCosto','conceptoAjuste']
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
					value:'${command.fchRecibido}',
					fieldLabel:'Fecha Recibido' 
				}),
				new Ext.form.DisplayField({
					value:'${command.fchResponsAutoriza}',
					fieldLabel:'Fecha Respons. Autoriza'
				}),
				new Ext.form.DisplayField({
					value:'${command.fchSolicElab}',
					fieldLabel:'Fecha Solicitud Elab.'
				}),
				new Ext.form.DisplayField({
					value:'${command.noVale}',
					fieldLabel:'No. Vale'
				}),
				new Ext.form.DisplayField({
					value:'${command.conceptoAjuste}',
					fieldLabel:'Concepto de Ajuste'
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
					value:'${command.importeNeto}',
					fieldLabel:'Importe Neto'
				}),
				new Ext.form.DisplayField({
					value:'${command.estadoVale}',
					fieldLabel:'Estado Vale'
				}),
				new Ext.form.DisplayField({
					value:'${command.empresa.nombre}',
					fieldLabel:'Empresa'
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
					value:'${command.almacenOrigen.nombre}',
					fieldLabel:'Almacén Origen'
				}),
				new Ext.form.DisplayField({
					value:'${command.responsAutoriza.nombre}',
					fieldLabel:'Responsable Autoriza'
				}),
				new Ext.form.DisplayField({
					value:'${command.solicElab.nombre}',
					fieldLabel:'Solic. Elaboración'
				}),
				new Ext.form.DisplayField({
					value:'${command.recibido.nombre}',
					fieldLabel:'Recibido'
				}),
				new Ext.form.DisplayField({
					value:'${command.centroCosto.nombre}',
					fieldLabel:'Centro Costo'
				}),
				new Ext.form.DisplayField({
					value:'${command.comentario}',
					fieldLabel:'Comentario'
				})
			]	
		});
		var detallesSalidaPorAjusteGrid = new Ext.grid.GridPanel({
			id: 'detallesRecepcionGrid',
			loadMask:true,
			monitorResize:true,
			title:'Productos',
			border: false,
			stripeRows:true,
			autoScroll:true,
			height: 220,
			store: valeProductoStore,
		    viewConfig: {forceFit: true},
			sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
	       	columns:  [
	       		{header: "Producto", width: 80, dataIndex: 'producto', sortable: true},
				{header: "Cantidad Producto", width: 80, dataIndex: 'cantProdVale', sortable: true},
				{header: "<b><font color=black> Importe CUP</font></b>", width: 80, dataIndex: 'importeMNVale', sortable: true},
				{header: "<b><font color=black> Importe CUC</font></b>", width: 80, dataIndex: 'importeMLCVale', sortable: true},
				{header: "Existencia Origen", width: 80, dataIndex: 'existenciaOrigen', sortable: true},
			],
			tbar: new Ext.Toolbar({
				items: [
				{
					text: 'Adicionar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/add.png',
					listeners:{
						click:function(){
							editarSalidaValeProducto('../${modulo}/editarEntradaValeProducto.htm',{ 'idVale': '${command.id}'});
						}		
					}		  			
		  		},{
						text: 'Modificar',
						cls:'x-btn-text-icon bmenu',
						icon:'../img/common/pencil.png',
						tooltip:'Vale Producto ',
						listeners:{
						click:function(){
							if(detallesSalidaPorAjusteGrid.selModel.hasSelection()){
								modificarVale(detallesSalidaPorAjusteGrid, '../${modulo}/editarEntradaValeProducto.htm',{ 'idVale': '${command.id}'});

								
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
							if(detallesSalidaPorAjusteGrid.selModel.hasSelection()){
								Ext.MessageBox.show({
				    		       title:'Confirmación',
						           msg: '¿Desea eliminar el Vale Producto?',
						           buttons: Ext.MessageBox.YESNO,
						           fn: function (btn){
								       	 if (btn == 'yes'){
								       	 	Ext.Ajax.request({
												url: '../${modulo}/deleteValeProducto.htm',
												params: {
													id: detallesSalidaPorAjusteGrid.selModel.getSelected().id
												},
												success: function (response, options) {
													if(Ext.decode(response.responseText).success == true){
														detallesSalidaPorAjusteGrid.getStore().reload();
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
		
		var detallesSalidaPorAjuste= new Ext.Panel({
			id: 'detallesSalidaPorAjuste',
			title: 'Detalles de la Salida Por Ajuste',
			closable: true,
			height: 600,
			layout: 'border',
			border:false,
			monitorResize:true,
			autoScroll: true,
			items:[
				new Ext.form.FormPanel({
					id: 'detallesSalidaPorAjusteForm',
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
						tooltip:'Imprimir Detalles del SalidaPorAjuste',
							listeners:{
								click:function(){
									descargar('../ecie/impDetallesSalidaPorAjuste.htm', { idVale: '${command.id}','tipo': 'SalidaPorAjuste'});
							}			  			
				  		}
					}
					
						
					  ]	
					})
				}),
				
				new Ext.TabPanel({
					id:'salidaPorAjusteTabPanel',
					region:'center',
					border: false,
					activeTab:0,
					items:[
						detallesSalidaPorAjusteGrid
					]
				})
				
			]
		});
		detallesSalidaPorAjusteGrid.on('celldblclick', gridCellDblClick, this);
		
		function gridCellDblClick(target, rowIndex, columnIndex, e){
			cargarVentana('../${modulo}/editarSalidaValeProducto.htm', {id: detallesSalidaPorAjusteGrid.selModel.getSelected().id,'idVale': '${command.id}'});
		}		

		function editarSalidaValeProducto(url, id){
		var idp = id.idVale;
			cargarVentana(url, {idVale: idp});
	    }
	       function modificarVale(detallesSalidaPorAjusteGrid, url, id){
	   var idm = id.idVale;
			cargarVentana(url, {id: detallesSalidaPorAjusteGrid.selModel.getSelected().id,idVale:idm});
		}
		
		return detallesSalidaPorAjuste;			
	}
}
</aek:jsmin>