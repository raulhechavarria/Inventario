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
			
				new Ext.form.TextField({
					editable: false,	
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido, no debe exceder de 200 caracteres',
		  			name:'nombre',
		  			maxLength: 200,
					width: 150,
					value:'${command.nombre}',
		  			fieldLabel:'Proveedor',
		  			autoComplete:'off'
				}),
			
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido, no debe exceder de 300 caracteres',
		  			name:'direccion',
		  			maxLength: 300,
					width: 150,
		  			value:'${command.direccion}',
		  			fieldLabel:'Dirección',
		  			autoComplete:'off'
				}),			
				
				new Ext.form.ComboBox({
					store: organismoStore,
					labelStyle: 'font-weight:bold',
					displayField: 'nombre',
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
				new Ext.form.TextField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'reup',
		  			maxLength: 25,
					width: 150,
		  			value:'${command.reup}',
		  			fieldLabel:'Código Reup',
		  			autoComplete:'off'
				}),
				new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'codigoNit',
					width: 150,
		  			value:'${command.codigoNit}',
		  			fieldLabel:'Código Nit',
		  			autoComplete:'off'
				}),
				new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'agenciaMn',
		  			maxLength: 9,
					width: 150,
		  			value:'${command.agenciaMn}',
		  			fieldLabel:'Agencia CUP',
		  			autoComplete:'off'
				 }),
				 new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'agenciaMlc',
		  			maxLength: 9,
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
			
			    new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'cuentaMlc',
		  			maxLength: 16,
					width: 150,
		  			value:'${command.cuentaMlc}',
		  			fieldLabel:'Cuenta CUC',
		  			autoComplete:'off'
				}),				
						
				new Ext.form.TextField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'tituloMlc',
		  			width: 150,
		  			value:'${command.tituloMlc}',
		  			fieldLabel:'Título CUC',
		  			autoComplete:'off'
				}),
				new Ext.form.TextField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'cuentaMn',
					width: 150,
					maxLength: 16,
		  			value:'${command.cuentaMn}',
		  			fieldLabel:'Cuenta CUP',
		  			autoComplete:'off'
				}),
				new Ext.form.TextField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'tituloMn',
					width: 150,
		  			value:'${command.tituloMn}',
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
		  			name:'correo',
					width: 150,
		  			value:'${command.correo}',
		  			fieldLabel:'Email',
		  			autoComplete:'off'
				 }),
				 new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
		  			name:'fax',
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
						            url : '../${modulo}/adicionarEmpresa.htm',
						            params: {
										id: '${command.id}'
									},
						            waitMsg : 'Salvando datos...',
						            failure: failure,
		            				success: function (form, action) {
										win.close();
										cargarTab(Ext.getCmp('empresaTabPanel'),
										{idempresa: action.result.id});
										empresaGrid = Ext.getCmp('empresaGrid');
										empresaGrid.getStore().reload();
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
			title: 'Editar Empresa',
		</c:if>
		<c:if test="${empty command.id}">					
			title: 'Adicionar Empresa',
		</c:if>
	        resizable: false,                
            closable: true,
            modal: true,                   
            constrain: true,
            layout: 'fit',
			height: 350,
            width:420,
            items:[datosForm]
		});
		            
		return win;			
	}
}
</aek:jsmin>