<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){

		${stores}
		
		var nomencladorPanel = new Ext.Panel({
			frame: true,
			border: false,
			layout: 'form',
			autoHeight: true,
			labelWidth:100,
			bodyStyle: 'padding:5px 10px 5px 10px',
			defaults: {width: 300},
			items:[${items}]
		});	
		
		var datosForm = new Ext.form.FormPanel({
  			id:'datosForm',
  			layout:'fit',
  			frame: true,
  			border:false,
  			monitorValid: true,
			//bodyStyle:'padding:5px 5px 0',
			items:[nomencladorPanel],
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
						            url: '../${modulo}/adicionar${nomenclador}.htm',  
						            waitMsg : 'Salvando datos...',  
						            failure: failure,
						            success: function (form, action) {
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
			title: 'Editar ${nombreNomenclador}',
		</c:if>
		<c:if test="${empty command.id}">					
			title: 'Adicionar ${nombreNomenclador}',
		</c:if>
            resizable: false,                
            closable: true,
            modal: true,                   
            constrain: true,
            width:463,
            items:[datosForm]
        });
		            
		return win;			
	}
}
</aek:jsmin>