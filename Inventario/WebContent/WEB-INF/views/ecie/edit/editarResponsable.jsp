<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    

<%@page import="org.springframework.web.util.HtmlUtils"%><aek:jsmin></aek:jsmin>
{
	success: true,
	view: function(){
		
		var datosForm = new Ext.form.FormPanel({
  			id:'datosForm',
          	frame: true,
  			border:false,
  			monitorValid: true,
  			bodyStyle:'padding:5px 5px 0',
			items:[
			
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
					name:'nombre',
					width: 150,
		  			value:'${command.nombre}',
		  			fieldLabel:'Nombre',
		  			autoComplete:'off'
				}),	
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
					name:'apellido1',
					width: 150,
		  			value:'${command.apellido1}',
		  			fieldLabel:'1er Apellido',
		  			autoComplete:'off'
				}),					
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
					name:'apellido2',
					width: 150,
		  			value:'${command.apellido2}',
		  			fieldLabel:'2do Apellido',
		  			autoComplete:'off'
				}),	
				new Ext.form.NumberField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
					name:'carnetIdentidad',
					width: 150,
					maxLength: 11,
					autoCreate: {tag: 'input', type: 'text', autocomplete: 'off', maxlength: '11'},
					minLength: 11,
					minLengthText: 'Debe completar los 11 caracteres',
		  			value:'${command.carnetIdentidad}',
		  			fieldLabel:'Carné de Identidad',
		  			autoComplete:'off'
				}),	
				 new Ext.form.TextArea({
					width: 260,
					height: 120,
					fieldLabel: 'Descripción',
					labelStyle: 'font-weight:bold',
					name:'descripcion',
					value: '${command.descripcion}'
  				})
				
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
						            url : '../${modulo}/adicionarResponsable.htm',
						             params: {
										id: '${command.id}'
									},
						            waitMsg : 'Salvando datos...',
						            failure: failure,
		            				success: function (form, action) {
										win.close();
										cargarTab(Ext.getCmp('clasificadorTabPanel'),
										{idresponsable: action.result.id});
										responsableGrid = Ext.getCmp('responsableGrid');
										responsableGrid.getStore().reload();
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
			title: 'Editar Responsable',
		</c:if>
		<c:if test="${empty command.id}">					
			title: 'Adicionar Responsable',
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
