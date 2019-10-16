<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
{
success: true,
view: function(){
 	
			var store = new Ext.data.JsonStore({
				data:${roles},
		        idProperty: 'id',
		        fields: [
		            'nombre',
		            {name: 'id', type: 'int'}
		        ]
		    });
		    
			var datosForm = new Ext.form.FormPanel({
	  			id:'datosForm',
	  			layout:'form',
	  			frame: true,
	  			border:false,
	  			monitorValid: true,
				bodyStyle:'padding:5px 5px 0',
				defaults: {width: 300},
				items:[
					new Ext.form.TextField({
						labelStyle: 'font-weight:bold',
			  			allowBlank:false,
			  			invalidClass : '',
			  			blankText:'Este campo es requerido',
			  			maxLength: 50,
			  			name:'nombre',
			  			value:'${command.nombre}',
			  			fieldLabel:'Nombre',
			  			autoComplete:'off'
					}),
					new Ext.form.TextField({
			  			maxLength: 100,
		  				name:'descripcion',
						value:'${command.descripcion}',
						fieldLabel:'Descripción',
						autoComplete:'off'
					}),
					new Ext.ux.form.LovCombo({
						allowBlank:false,
						invalidClass : '',
		  				blankText:'Este campo es requerido',
						id:'roles',
						hiddenName :'idRoles',
						fieldLabel:'Roles',
						hideOnSelect:true,
						triggerAction:'all',
						editable:false,
						listWidth:250,
						value: '${rolesData}',
						valueField:'id',
						displayField:'nombre',
						mode: 'local',
						store:store,
						beforeBlur: Ext.emptyFn
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
							            url: '../common/adicionar${nomenclador}.htm',
							            params:{
							            	id:'${command.id}'
							            },
							            waitMsg : 'Salvando datos...',  
							            failure: function (form, action) {  
						 					Ext.MessageBox.show({  
												title: 'Error',  
												msg: 'Error al salvar los datos.',  
												buttons: Ext.MessageBox.OK,  
												icon: Ext.MessageBox.ERROR  
			                				});  
			            				},  
			            				success: function (form, request) {					                
											win.close();
											nomencladorGrid = Ext.getCmp('nomencladorGrid');
											nomencladorGrid.getStore().reload();
			            				}
			            			})
		            			} else{
		            				showErrorMsg('Error','Introduzca bien los datos');
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
				title: 'Editar ${nomenclador}',
			</c:if>
			<c:if test="${empty command.id}">					
				title: 'Adicionar ${nomenclador}',
			</c:if>
                resizable: false,                
                closable: true,
                modal: true,                   
                constrain: true,
                width:450,
                items:[datosForm]
            });
		            
	return win;			
	}
}