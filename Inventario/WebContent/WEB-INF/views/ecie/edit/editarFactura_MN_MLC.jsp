<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){

	var operacionFacturaStore = new Ext.data.SimpleStore({
            fields:['id', 'operacionFactura'],
            data:[
            	['VentaMayorista','Venta mayorista de vienes y servicios'],
            	['VentaInterna','Venta interna con operación de cobro y pago'],
            	['EntregaProductos','Entrega de productos en consignación o depósito'],
            	['VentaActF','Venta activos fijos tangibles'],
            	['Devolucion','Devolución']
            ]
		});
		
		var estadoValeStore = new Ext.data.SimpleStore({
            fields:['id', 'estadoVale'],
            data:[
            	['Revision','Revision'],
            	['Confeccion','Confeccion'],
            	['Cancelado','Cancelado']
            ]
		});
		var almacenOrigenStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarAlmacen.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'nombre', 'descripcion']
		});
		var responsAutorizaStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarResponsable.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id','descripcion','nombre','apellido1','apellido2']		
    	});
    	var recibidoStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarResponsable.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'nombre', 'descripcion','apellido1']
    	});
    	var empresaStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarEmpresa.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'nombre']
    	});
    	
		var generalPanel = new Ext.Panel({
			title:'Datos',
			frame: true,
			border: false,
			bodyStyle: 'padding:10px 10px 0',
			labelWidth:125,
			layout: 'form',
			items:[	
			
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'noControl',
		  			maxLength: 10,
					width: 150,
		  			value:'${command.noControl}',
		  			fieldLabel:'No. Control',
		  			autoComplete:'off'
				}),
				
	  			new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'recargoDescuentoMN',
					width: 150,
		  			value:'${command.recargoDescuentoMN}',
		  			fieldLabel:'Recargo CUP en %',
		  			autoComplete:'off'
				 }),
				 new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'recargoDescuentoMLC',
					width: 150,
		  			value:'${command.recargoDescuentoMLC}',
		  			fieldLabel:'Recargo CUC en %',
		  			autoComplete:'off'
				 }),				 
				new Ext.form.ComboBox({
					labelStyle: 'font-weight:bold',  
					allowBlank: false,
		  			blankText: 'Este campo es requerido',
					store: estadoValeStore,
					displayField:'estadoVale',
					width: 150,
					triggerAction: 'all',
					name: 'estadoVale',
					fieldLabel: 'Estado Vale',
					value: '${command.estadoVale}',
					mode: 'local',
					typeAhead: true,
				    triggerAction: 'all',
				    editable: false
				}),
				new Ext.form.ComboBox({
					labelStyle: 'font-weight:bold',  
					allowBlank: false,
		  			blankText: 'Este campo es requerido',
					store: operacionFacturaStore,					
					width: 150,
					displayField:'operacionFactura',
					name: 'operacionFactura',
					valueField: 'id',					
					hiddenName: 'operacionFactura',
					fieldLabel: 'Tipo de operación',
					value: '${command.operacionFactura}',
					mode: 'local',
					typeAhead: true,
				    triggerAction: 'all',
				    editable: false
				}),
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'noSolicitud',
		  			maxLength: 10,
					width: 150,
		  			value:'${command.noSolicitud}',
		  			fieldLabel:'No. de Solicitud',
		  			autoComplete:'off'
				})
				]
		 });
		
		 var resultTpl = new Ext.XTemplate(
	        '<tpl for="."><div class="search-item">',
               	'<tpl for=".">',
        		'{nombre}',
        		'</tpl>',
        		' ',
        		'{apellido1}<br/>',	            
	        '</div></tpl>'
	    );		
				
		var otrosDatosPanel = new Ext.Panel({
			title:'Otros Datos',
			frame: true,
			border: false,
			bodyStyle: 'padding:10px 10px 0',
			labelWidth:125,
			layout: 'form',
			items:[
			new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'noDoc',
		  			maxLength: 15,
					width: 150,
		  			value:'${command.noDoc}',
		  			fieldLabel:'No del contrato ',
		  			autoComplete:'off'
				}),
			new Ext.form.ComboBox({
					store: empresaStore,
			       	typeAhead: true,
			        mode: 'local',
			        forceSelection: true,
			        triggerAction: 'all',
			        selectOnFocus:true,
					allowBlank:false,
		  			blankText:'Este campo es requerido',
					labelStyle: 'font-weight:bold',  
					displayField:'nombre',
					width: 150,					
					pageSize: 200,
					valueField: 'id',
					hiddenName: 'empresa.id',
					fieldLabel: 'Comprador',
					value: '${command.empresa.nombre}',
					hiddenValue: '${command.empresa.id}'
	  			}),
			new Ext.form.ComboBox({
					labelStyle: 'font-weight:bold',  
					editable: false,
					store: almacenOrigenStore,
					allowBlank:false,
		  			blankText:'Este campo es requerido',
					displayField: 'nombre',
					typeAhead: false,
					width: 150,
					forceSelection: true,
					triggerAction: 'all',
					pageSize: 20,
					valueField: 'id',
					hiddenName: 'almacenOrigen.id',
					fieldLabel: 'Almacén Origen',
					value: '${command.almacenOrigen.nombre}',
					hiddenValue: '${command.almacenOrigen.id}'
	  			}),
				new Ext.form.ComboBox({
					store: recibidoStore,
					typeAhead: true,
			        mode: 'local',
			        forceSelection: true,
			        triggerAction: 'all',
			        selectOnFocus:true,
					allowBlank:false,
		  			blankText:'Este campo es requerido',
					labelStyle: 'font-weight:bold',  
					displayField:'nombre',
					width: 150,
					tpl: resultTpl,
					itemSelector: 'div.search-item',
					pageSize: 20,
					valueField: 'id',
					hiddenName: 'recibido.id',
					fieldLabel: 'Recibido',
					value: '${command.recibido.nombre}',
					hiddenValue: '${command.recibido.id}'
	  			}),
  				new Ext.form.DateField({
					editable: false,	
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'fchRecibido',
					width: 150,
					format: 'd-m-Y',
		  			value:'${command.fchRecibido}',
		  			fieldLabel:'Fecha de recepción',
		  			autoComplete:'off'
				}),
				new Ext.form.HtmlEditor({
				    width: 200,
    				height: 40,
				    fieldLabel: 'Comentario',
				    labelStyle: 'font-weight:bold',
					name:'comentario',
	  				value:'${command.comentario}',
	  				enableLists: false,
	  				enableSourceEdit: false,
	  				enableColors: false,
        			enableAlignments: false,
        			enableFont: false,
        			enableFontSize: false,
        			enableFormat: false,
        			enableLinks: false
  				})
			]
		 });
var datoStransPanel = new Ext.Panel({
			title:'Datos del transportador',
			frame: true,
			border: false,
			bodyStyle: 'padding:10px 10px 0',
			labelWidth:125,
			layout: 'form',
			items:[	
			
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'nombreTransportador',
					width: 150,
		  			value:'${command.nombreTransportador}',
		  			fieldLabel:'Transportador',
		  			autoComplete:'off'
				}),
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'cITransportador',
		  			maxLength: 11,
		  			autoCreate: {tag: 'input', type: 'text', autocomplete: 'off', maxlength: '11'},
					minLength: 11,
					minLengthText: 'Debe completar los 11 caracteres',
					width: 150,
		  			value:'${command.cITransportador}',
		  			fieldLabel:'CI. Transportador',
		  			autoComplete:'off'
				}),
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'noTransporte',
		  			maxLength: 25,
					width: 150,
		  			value:'${command.noTransporte}',
		  			fieldLabel:'No. Chapa, Carta o Casilla del ferrocarril',
		  			autoComplete:'off'
				}),
				 new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'receptor',
		  			maxLength: 25,
					width: 150,
		  			value:'${command.receptor}',
		  			fieldLabel:'Receptor',
		  			autoComplete:'off'
				}),
				 new Ext.form.HtmlEditor({
				    width: 200,
    				height: 40,
				    fieldLabel: 'Comentario',
				    labelStyle: 'font-weight:bold',
					name:'comentario',
	  				value:'${command.comentario}',
	  				enableLists: false,
	  				enableSourceEdit: false,
	  				enableColors: false,
        			enableAlignments: false,
        			enableFont: false,
        			enableFontSize: false,
        			enableFormat: false,
        			enableLinks: false
  				})
  				
				]
		 });
		var tabs = new Ext.TabPanel({
			activeTab:0,
			deferredRender: false,
			renderTo: Ext.getBody(),
			items:[
				generalPanel, otrosDatosPanel,datoStransPanel
			]
		});
				
			
		var datosForm = new Ext.form.FormPanel({
  			id:'datosForm',
            layout: 'fit',
            frame: true,
  			border:false,
  			monitorValid: true,
  			bodyStyle:'padding:5px 5px 0',
			items:[
				tabs
			],	
						
			buttons:[
				new Ext.Button({
					text:'Aceptar',
					type:'submit',
					formBind: true,
					listeners:{
						click:function(){
							if(datosForm.getForm().isValid()){
					 			datosForm = Ext.getCmp('datosForm');
					 			datosForm.form.submit({
						            url : '../${modulo}/addFactura.htm',
						            params: {
										id: '${command.id}',
										tipoVale: 'Factura_MN_MLC'
									},
						            waitMsg : 'Salvando datos...',
						            failure: failure,
		            				success: function (form, action) {
										win.close();
										cargarTab(Ext.getCmp('facturaTabPanel'),
										{idsolicitudEntrega: action.result.id});
										facturaGrid = Ext.getCmp('facturaGrid');
										facturaGrid.getStore().reload();
			            			}
	            				});
	            			}
	            			else{
	            				Ext.MessageBox.show({  
									title: 'Error',  
									msg: 'Introduzca bien los datos.',  
									buttons: Ext.MessageBox.OK,  
									icon: Ext.MessageBox.ERROR  
	            				});
	            			}
        	        	}
       	        	}
				}),
				new Ext.Button({
					text:'Cancelar',
					type:'button',
					listeners:{
						click:function(){
					 		win.close();
	 					}
					}
  				})
       		]
		});
		var win = new Ext.Window({
		<c:if test="${not empty command.id}">					
			title: 'Editar Factura de Ambas Monedas',
		</c:if>
		<c:if test="${empty command.id}">					
			title: 'Adicionar Factura de Ambas Monedas',
		</c:if>
	        resizable: false,                
            closable: true,
            modal: true,                   
            constrain: true,
            layout: 'fit',
			height: 380,
            width:420,
            items:[datosForm]
		});
		            
		return win;			
	}
}
</aek:jsmin>