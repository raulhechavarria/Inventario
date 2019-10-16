<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){

		Ext.apply(Ext.form.VTypes, {
	        password : function(val, field) {
		        if (field.initialPassField) {
		            var datosForm = Ext.getCmp(field.initialPassField);
		            return (val == datosForm.getValue());
		        }
		        return true;
		    },
	    	passwordText : 'Las contraseñas no coinciden'
		});

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
			labelWidth:125,
			defaults: {width: 250},
			items:[
				new Ext.form.TextField({
		  			allowBlank:false,
		  			invalidClass : '',
		  			blankText:'Este campo es requerido',
		  			labelStyle: 'font-weight:bold',
		  			name:'nombre',
		  			value:'${command.nombre}',
		  			fieldLabel:'Nombre completo',
		  			autoComplete:'off'
			    }),
				new Ext.form.TextField({
					allowBlank:false,
					invalidClass : '',
	  				blankText:'Este campo es requerido',
	  				labelStyle: 'font-weight:bold',
	  				name:'login',
					value:'${command.login}',
					fieldLabel:'Nombre del usuario',
					autoComplete:'off'
			    }),
				new Ext.form.TextField({
					allowBlank:false,
					invalidClass : '',
	  				blankText:'Este campo es requerido',
	  				labelStyle: 'font-weight:bold',
  					inputType:'password',
					id:'password',
					name:'password',
					//value:'*******',
					fieldLabel: 'Contraseña',
					autoComplete:'off'
				}),
				new Ext.form.TextField({
					allowBlank:false,
					invalidClass : '',
	  				blankText:'Este campo es requerido',
	  				labelStyle: 'font-weight:bold',
  					inputType:'password',
					name:'pass-cfrm',
					vtype: 'password',
					initialPassField: 'password',
					//value:'*******',
					fieldLabel:'Confirmar contraseña',
					autoComplete:'off'
				}),
				new Ext.ux.form.LovCombo({
					allowBlank: false,
					invalidClass : '',
	  				blankText: 'Este campo es requerido',
					id:'roles',
					hiddenName :'idRoles',
					fieldLabel:'Roles',
					hideOnSelect: true,
					triggerAction:'all',
					editable: false,
					listWidth: 250,
					value: '${rolesUsuario}',
					valueField: 'id',
					displayField: 'nombre',
					mode: 'local',
					store: store,
					beforeBlur: Ext.emptyFn
				}),
				new Ext.form.Checkbox({
					fieldLabel: 'Cuenta deshabilitada',
					name: 'deshabilitado',
				<c:if test="${not empty command.id}">					
					checked: ${command.deshabilitado}
				</c:if>
				<c:if test="${empty command.id}">
					checked: false
				</c:if>
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
						            url : '../common/adicionarUsuario.htm', 
						            params:{
						            	id: '${command.id}'
						            }, 
						            waitMsg : 'Salvando datos...',  
						            failure: failure,  
		            				success: function (form, request) {					                
										win.close();
										usuariosGrid = Ext.getCmp('usuariosGrid');
										usuariosGrid.getStore().reload();
		            				}
		            			})
	            			}
	            			else{
	            				showErrorMsg('Usuarios y Permisos','Introduzca bien los datos');
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
			title: 'Editar Usuario',
		</c:if>
		<c:if test="${empty command.id}">					
			title: 'Adicionar Usuario',
		</c:if>
            resizable: false,                
            closable: true,
            modal: true,
            plain: true,                    
            layout: 'fit',       
            constrain: true,
            height:250,
            width:440,
            items:[datosForm]
        });
		            
		return win;			
	}
}
</aek:jsmin>