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
				 'tipo': 'CargaInicial'
			},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','producto','cantProdVale','precioMNVale','importeMNVale','precioMLCVale','importeMLCVale','recargoDescuentoMN',
   	    	'totalMN','existenciaOrigen','existenciaDestino']
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
		
		var almacenStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarAlmacen.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'nombre', 'descripcion']
		});
		var productoStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarProducto.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'nombre', 'descripcion']
    	});
    	
		
		var leftPanel = new Ext.Panel({
			columnWidth: 0.3,
			border: false,
			bodyStyle: 'padding:10px 10px 0',
			defaults: {width: 250},
			labelWidth: 100,
			layout: 'form',
			items:[
				new Ext.form.DisplayField({
					value:'${command.fechaCambio}',
					fieldLabel:'Fecha de Cambio'
			    }),
				new Ext.form.DisplayField({
					value:'${command.producto.nombre}',
					fieldLabel:'Producto'
				}),
				new Ext.form.DisplayField({
					value:'${command.almacen.nombre}',
					fieldLabel:'Almacén'
				}),
				new Ext.form.DisplayField({
					value:'${command.valeProducto.vale.noVale}',
					fieldLabel:'Vale'
				}),
				new Ext.form.DisplayField({
					value:'${command.cantEntradaVale}',
					fieldLabel:'Cant. Entrada Vale'
				}),
				new Ext.form.DisplayField({
					value:'${command.cantSalidaVale}',
					fieldLabel:'Cant. Salida Vale'
				})
			]	
		});
		
		var rightPanel = new Ext.Panel({
			columnWidth: 0.4,
			border: false,
			bodyStyle: 'padding:10px 50px 0',
			defaults: {width: 250},
			labelWidth: 120,
			layout: 'form',
			items:[
				new Ext.form.DisplayField({
					value:'${command.cantExist}',
					fieldLabel:'Cantidad Existente'
				}),
			    new Ext.form.DisplayField({
					value:'${command.precioMNExist}',
					fieldLabel:'Precio CUP Existente'
				}),
				new Ext.form.DisplayField({
					value:'${command.precioMLCExist}',
					fieldLabel:'Precio CUC Existente'
				}),
				new Ext.form.DisplayField({
					value:'${command.impMNExist}',
					fieldLabel:'Imp. en CUP Existente'
				}),
				new Ext.form.DisplayField({
					value:'${command.impMLCExist}',
					fieldLabel:'Imp. en CUC Existente'
				})
				
			]
		});
		
		var detallesExistencia = new Ext.Panel({
			id: 'detallesExistencia',
			title: 'Existencia',
			closable: true,
			height: 400,
			border:false,
			monitorResize:true,
			autoScroll: true,
			items:[
				new Ext.form.FormPanel({
					id: 'detallesExistenciaForm',
					layout:'column',
					region:'north',
					height: 280,
					split: true,
					collapseMode: 'mini',
					maxSize: 185,
					minSize: 62,
					border: false,
					items:[
						leftPanel, rightPanel
					],
					tbar: new Ext.Toolbar({
						items: [
						
						{
							text: 'Imprimir',
							cls:'x-btn-text-icon bmenu',
							icon:'../img/common/printer.png',
							tooltip:'Imprimir Detalles Existencia',
							listeners:{
								click:function(){
									descargar('../ecie/imprimirDetallesExistencia.htm', {id: '${command.id}'});
								}			  			
					  		}
						}
					  ]	
					})
				})
			]
		});
		
		return detallesExistencia;			
	}
}
</aek:jsmin>