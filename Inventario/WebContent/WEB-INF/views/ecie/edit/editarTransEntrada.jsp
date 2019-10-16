<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){

	

		var estadoValeStore = new Ext.data.SimpleStore({
            fields:['id', 'estadoVale'],
            data:[
            	['Revision','Revision'],
            	['Confeccion','Confeccion'],
            	['Cancelado','Cancelado']
            ]
		});
	
		var almacenDestinoStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarAlmacen.json',
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
  	    	fields:['id', 'nombre', 'descripcion', 'apellido1']
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
				new Ext.form.ComboBox({
					labelStyle: 'font-weight:bold',  
					editable: false,
					allowBlank: false,
		  			blankText: 'Este campo es requerido',
					store: almacenDestinoStore,
					displayField: 'nombre',
					typeAhead: false,
					width: 150,
					forceSelection: true,
					triggerAction: 'all',
					pageSize: 20,
					valueField: 'id',
					hiddenName: 'almacenDestino.id',
					fieldLabel: 'Almacén Destino',
					value: '${command.almacenDestino.nombre}',
					hiddenValue: '${command.almacenDestino.id}'
	  			}),
				new Ext.form.ComboBox({
					labelStyle: 'font-weight:bold',  
					editable: false,
					store: empresaStore,
					displayField: 'nombre',
					typeAhead: false,
					width: 150,
					forceSelection: true,
					triggerAction: 'all',
					pageSize: 20,
					valueField: 'id',
					hiddenName: 'empresa.id',
					fieldLabel: 'Proveedor',
					value: '${command.empresa.nombre}',
					hiddenValue: '${command.empresa.id}'
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
					value: '${command.estadoVale}',
					fieldLabel: 'Estado Vale',
					mode: 'local',
					typeAhead: true,
				    triggerAction: 'all',
				    editable: false
				}),
				new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'totalMN',
					width: 150,
		  			value:'${command.totalMN}',
		  			fieldLabel:'Total CUP',
		  			autoComplete:'off'
				 }),
				 new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'totalMLC',
					width: 150,
		  			value:'${command.totalMLC}',
		  			fieldLabel:'Total CUC',
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
		 
	var datosPanel = new Ext.Panel({
			title:'Otros Datos',
			frame: true,
			border: false,
			bodyStyle: 'padding:10px 10px 0',
			labelWidth:125,
			layout: 'form',
			items:[	
			
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
	  			
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					name:'noDoc',
					width: 150,
					maxLength: 15,
		  			value:'${command.noDoc}',
		  			fieldLabel:'No. Documento',
		  			autoComplete:'off'
				}),
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					name:'nombreDoc',
					width: 150,
		  			value:'${command.nombreDoc}',
		  			fieldLabel:'Documento',
		  			autoComplete:'off'
				}),
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					name:'nombreTransportador',
					maxLength: 30,
					width: 150,
		  			value:'${command.nombreTransportador}',
		  			fieldLabel:'Transportador',
		  			autoComplete:'off'
				}),
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					name:'cITransportador',
					width: 150,
					maxLength: 11,
		  			autoCreate: {tag: 'input', type: 'text', autocomplete: 'off', maxlength: '11'},
					minLength: 11,
					minLengthText: 'Debe completar los 11 caracteres',
					value:'${command.cITransportador}',
		  			fieldLabel:'CI. Transportador',
		  			autoComplete:'off'
				}),
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					name:'noTransporte',
					maxLength: 25,
					width: 150,
		  			value:'${command.noTransporte}',
		  			fieldLabel:'No. Transporte',
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
				generalPanel, datosPanel
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
						            url : '../${modulo}/addTransEntrada.htm',
						            params: {
										id: '${command.id}',
										tipoVale: 'TransferenciaEntrada',
										recargoMLC: '0.00',recargoMN: '0.00',descuentoMLC: '0.00',
										descuentoMN: '0.00',serviciosMN: '0.00',serviciosMLC: '0.00'
									},
						            waitMsg : 'Salvando datos...',
						            failure: failure,
		            				success: function (form, action) {
										win.close();
										cargarTab(Ext.getCmp('transEntradaPanel'),
										{idtransEntrada: action.result.id});
										entradaGrid = Ext.getCmp('entradaGrid');
										entradaGrid.getStore().reload();
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
			title: 'Editar Transferencia de Entrada',
		</c:if>
		<c:if test="${empty command.id}">					
			title: 'Adicionar Transferencia de Entrada',
		</c:if>
	        resizable: false,                
            closable: true,
            modal: true,                   
            constrain: true,
            layout: 'fit',
			height: 360,
            width:400,
            items:[datosForm]
		});
		            
		return win;			
	}
}
</aek:jsmin>