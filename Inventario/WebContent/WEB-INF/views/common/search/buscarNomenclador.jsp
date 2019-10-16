<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin></aek:jsmin>
{
	success: true,
	view: function(){
		
		${stores}

		var datosForm = new Ext.form.FormPanel({
  			id:'datosForm',
  			layout:'form',
  			frame: true,
  			border:false,
  			monitorValid: true,
			bodyStyle:'padding:5px 5px 0',
			defaults: {width: 300},
			items:[${items}],
			buttons:[
				new Ext.Button({
					text:'Aceptar',
					formBind: true,
					listeners:{
						click:function(){
							
							var datosForm = Ext.getCmp('datosForm');
							var bf = datosForm.getForm();
							
							var nomencladorGrid = Ext.getCmp('nomencladorGrid');
							var ds = nomencladorGrid.getStore();
							var params = bf.getValues();
							var lastOptions = ds.baseParams;
							Ext.apply(lastOptions, params);
							ds.reload({params:lastOptions});
							win.close();
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
			title: 'Buscar ${nomenclador}',
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
