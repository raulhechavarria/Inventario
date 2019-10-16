<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){

		var organismoStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarOrganismo.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'nombre', 'descripcion']
		});
		
		
		var generalPanel = new Ext.Panel({
			title:'Datos',
			height: 220,
			frame: true,
			border: false,
			bodyStyle: 'padding:10px 10px 0',
			layout: 'form',
			items:[
			    new Ext.form.DateField({
					editable: false,	
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'fechaOperacion',
					width: 150,
					format: 'Y-m-d',
		  			value:'${command.fechaOperacion}',
		  			fieldLabel:'Fecha de Operaciones',
		  			autoComplete:'off'
				}),	
				new Ext.form.TextField({
					editable: false,	
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'nombre',
		  			maxLength: 70,
					width: 150,
					value:'${command.nombre}',
		  			fieldLabel:'Ecie',
		  			autoComplete:'off'
				}),
			
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'direccion',
					width: 150,
		  			value:'${command.direccion}',
		  			fieldLabel:'Dirección',
		  			autoComplete:'off'
				}),	
			
				new Ext.form.ComboBox({
					store: organismoStore,
					displayField: 'nombre',
					labelStyle: 'font-weight:bold',
					width: 150,
					listWidth: 320,
					typeAhead: false,
					forceSelection: true,
					triggerAction: 'all',
					loadingText: 'Buscando Organismos...',
					pageSize: 20,
					valueField: 'id',
					hiddenName: 'organismo.id',
					value: '${command.organismo.nombre}',
					hiddenValue: '${command.organismo.id}',
					fieldLabel: 'Organismo'
				}),	
				new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'reup',
					width: 150,
		  			value:'${command.reup}',
		  			fieldLabel:'Código Reup',
		  			autoComplete:'off'
				}),
				new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'nit',
					width: 150,
		  			value:'${command.nit}',
		  			fieldLabel:'Código Nit',
		  			autoComplete:'off'
				}),
				new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'agenciaMN',
					width: 150,
		  			value:'${command.agenciaMN}',
		  			fieldLabel:'Agencia CUP',
		  			autoComplete:'off'
				 }),
				 new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'agenciaMlc',
					width: 150,
		  			value:'${command.agenciaMlc}',
		  			fieldLabel:'Agencia CUC',
		  			autoComplete:'off'
				 }),
				  new Ext.form.TextField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'resolDirectora',
					width: 150,
		  			value:'${command.resolDirectora}',
		  			fieldLabel:'Resolución',
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
			
				new Ext.form.NumberField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'noValeOperacional',
					width: 150,
		  			value:'${command.noValeOperacional}',
		  			fieldLabel:'Consecutivo Operacional(No Vale)',
		  			autoComplete:'off'
				}),
				new Ext.form.NumberField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'noValeRecepcionACiega',
					width: 150,
		  			value:'${command.noValeRecepcionACiega}',
		  			fieldLabel:'Consecutivo Recepcion a ciega(No Vale)',
		  			autoComplete:'off'
				}),
				new Ext.form.NumberField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'noValeReclamacion',
					width: 150,
		  			value:'${command.noValeReclamacion}',
		  			fieldLabel:'Consecutivo de Reclamaciones(No Vale)',
		  			autoComplete:'off'
				}),	
				new Ext.form.NumberField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'cuentaCuc',
					width: 150,
		  			value:'${command.cuentaCuc}',
		  			fieldLabel:'Cuenta CUC',
		  			autoComplete:'off'
				}),				
						
				new Ext.form.TextField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'tituloCtaCuc',
					width: 150,
		  			value:'${command.tituloCtaCuc}',
		  			fieldLabel:'Título CUC',
		  			autoComplete:'off'
				}),
				new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'cuentaCup',
					width: 150,
		  			value:'${command.cuentaCup}',
		  			fieldLabel:'Cuenta CUP',
		  			autoComplete:'off'
				}),
				new Ext.form.TextField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'tituloCtaCup',
					width: 150,
		  			value:'${command.tituloCtaCup}',
		  			fieldLabel:'Título CUP',
		  			autoComplete:'off'
				 }),
				 new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'telefono',
					width: 150,
		  			value:'${command.telefono}',
		  			fieldLabel:'Teléfono',
		  			autoComplete:'off'
				 }),
				 new Ext.form.TextField({
					labelStyle: 'font-weight:bold',
					name:'email',
					width: 150,
		  			value:'${command.email}',
		  			fieldLabel:'Email',
		  			autoComplete:'off'
				 }),
				 new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
					name:'fax',
					maxLength: 25,
					width: 150,
		  			value:'${command.fax}',
		  			fieldLabel:'Fax',
		  			autoComplete:'off'
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
						            url : '../${modulo}/adicionarEcie.htm',
						            params: {
										id: '1'
									},
						            waitMsg : 'Salvando datos...',
						            failure: failure,
		            				success: function (form, action) {										
										cargarTab(Ext.getCmp('ecieTabPanel'),
										{idecie: action.result.id});
										ecieGrid = Ext.getCmp('ecieGrid');
										ecieGrid.getStore().reload();
										var fechaCombo = Ext.getCmp('fechaCombo');
										fechaCombo.setRawValue(datosForm.getForm().findField('fechaOperacion').getRawValue());
										win.close();
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
			title: 'Editar Ecie',
		</c:if>
		<c:if test="${empty command.id}">					
			title: 'Adicionar Ecie',
		</c:if>
	        resizable: false,                
            closable: true,
            modal: true,                   
            constrain: true,
            layout: 'fit',
			height: 420,
            width:420,
            items:[datosForm]
		});
		            
		return win;			
	}
}
</aek:jsmin>