<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
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
  	    	fields:['id', 'nombre', 'apellido1', 'descripcion']
    	});
    	var solicElabStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarResponsable.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'nombre', 'apellido1', 'descripcion']
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
    	var resultTpl = new Ext.XTemplate(
	        '<tpl for="."><div class="search-item">',
               	'<tpl for=".">',
        		'{nombre}',
        		'</tpl>',
        		' ',
        		'{apellido1}<br/>',	            
	        '</div></tpl>'
	    );	
    	
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
	  			new Ext.form.ComboBox({
					labelStyle: 'font-weight:bold',  
					allowBlank: false,
		  			blankText: 'Este campo es requerido',
					store: conceptoAjusteStore,					
					width: 150,
					displayField:'conceptoAjuste',
					name: 'conceptoAjuste',
					valueField: 'id',					
					hiddenName: 'conceptoAjuste',
					fieldLabel: 'Concepto de Ajuste',
					value: '${command.conceptoAjuste}',
					mode: 'local',
					typeAhead: true,
				    triggerAction: 'all',
				    editable: false
				}),				
	  			new Ext.form.ComboBox({
					store: solicElabStore,
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
					hiddenName: 'solicElab.id',
					fieldLabel: 'Solicita Elaboración',
					value: '${command.solicElab.nombre}',
					hiddenValue: '${command.solicElab.id}'
	  			}),
	  			
	  			new Ext.form.DateField({
					editable: false,	
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'fchSolicElab',
					width: 150,
					format: 'd-m-Y',
		  			value:'${command.fchSolicElab}',
		  			fieldLabel:'Fecha de Sol. de Elaboración',
		  			autoComplete:'off'
				})
				
	  			
				]
		 });
				
			
		var otrosDatosPanel = new Ext.Panel({
			title:'Otros Datos',
			frame: true,
			border: false,
			bodyStyle: 'padding:10px 10px 0',
			labelWidth:125,
			layout: 'form',
			items:[
				new Ext.form.ComboBox({
					store: responsAutorizaStore,
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
					hiddenName: 'responsAutoriza.id',
					fieldLabel: 'Responsable que Autoriza',
					value: '${command.responsAutoriza.nombre}',
					hiddenValue: '${command.responsAutoriza.id}'
	  			}),
	  			new Ext.form.DateField({
					editable: false,	
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'fchResponsAutoriza',
					width: 150,
					format: 'd-m-Y',
		  			value:'${command.fchResponsAutoriza}',
		  			fieldLabel:'Fecha de Autorización',
		  			autoComplete:'off'
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
		 
	 
		 
		var tabs = new Ext.TabPanel({
			activeTab:0,
			deferredRender: false,
			renderTo: Ext.getBody(),
			items:[
				generalPanel, otrosDatosPanel
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
						            url : '../${modulo}/addSalidaPorAjuste.htm',
						            params: {
										id: '${command.id}',
										tipoVale: 'SalidaPorAjuste'
									},
						            waitMsg : 'Salvando datos...',
						            failure: failure,
		            				success: function (form, action) {
										win.close();
										cargarTab(Ext.getCmp('salidaPorAjusteTabPanel'),
										{idsalidaPorAjuste: action.result.id});
										salidaPorAjusteGrid = Ext.getCmp('salidaPorAjusteGrid');
										salidaPorAjusteGrid.getStore().reload();
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
			title: 'Editar Salida Por Ajuste',
		</c:if>
		<c:if test="${empty command.id}">					
			title: 'Adicionar Salida Por Ajuste',
		</c:if>
	        resizable: false,                
            closable: true,
            modal: true,                   
            constrain: true,
            layout: 'fit',
			height: 310,
            width:420,
            items:[datosForm]
		});
		            
		return win;			
	}
}
</aek:jsmin>