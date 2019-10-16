<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){

		var clasificacionStore = new Ext.data.SimpleStore({
			fields:['id', 'clasificacionProducto'],
			data:[
            	  ['Claro','Claro'],   
            	  ['Oscuro','Oscuro']
				 ]
			});
		
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
					name:'codigo',
					width: 150,
		  			value:'${command.codigo}',
		  			fieldLabel:'Codigo',
		  			autoComplete:'off'
				}),
				new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
					name:'precioCosto',
					width: 150,
		  			value:'${command.precioCosto}',
		  			fieldLabel:'Precio',
		  			autoComplete:'off'
				}),
				new Ext.form.TextField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
					name:'margen',
					width: 150,
		  			value:'${command.margen}',
		  			fieldLabel:'Margen',
		  			autoComplete:'off'
				}),
				
				new Ext.form.ComboBox({
					labelStyle: 'font-weight:bold',  
					allowBlank: false,
		  			blankText: 'Este campo es requerido',
					store: clasificacionStore,
					displayField:'clasificacionProducto',
					width: 150,
					triggerAction: 'all',
					name: 'clasificacionProducto',
					fieldLabel: 'Clasificacion del Producto',
					mode: 'local',
					typeAhead: true,
				    triggerAction: 'all',
				    editable: false
				}),
			
				 new Ext.form.HtmlEditor({
				    width: 260,
    				height: 120,
				    fieldLabel: 'Descripción',
				    labelStyle: 'font-weight:bold',
					name:'descripcion',
	  				value:'${command.descripcion}',
	  				enableLists: false,
	  				enableSourceEdit: false,
	  				enableColors: false,
        			enableAlignments: false,
        			enableFont: false,
        			enableFontSize: false,
        			enableFormat: false,
        			enableLinks: false
	  				
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
						            url : '../${modulo}/adicionarProducto.htm',
						             params: {
										id: '${command.id}'
									},
						            waitMsg : 'Salvando datos...',
						            failure: failure,
		            				success: function (form, action) {
										win.close();
										cargarTab(Ext.getCmp('tipoValeTabPanel'),
										{idtipoVale: action.result.id});
										tipoValeGrid = Ext.getCmp('tipoValeGrid');
										tipoValeGrid.getStore().reload();
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
			title: 'Editar Tipo de Vale',
		</c:if>
		<c:if test="${empty command.id}">					
			title: 'Adicionar Tipo de Vale',
		</c:if>
	        resizable: false,                
            closable: true,
            modal: true,                   
            constrain: true,
            layout: 'fit',
			height: 280,
            width:420,
            items:[datosForm]
		});
		            
		return win;			
	}
}
</aek:jsmin>