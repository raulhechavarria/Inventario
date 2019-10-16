<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){

		var productoStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarProducto.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:3000},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'nombre', 'descripcion']
    	});
    	
		var almacenStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarAlmacen.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'nombre', 'descripcion']
		});
	
		var clasificadorProdStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarClasificadorProducto.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'nombre', 'descripcion','codigo']
		});
		
		var datosForm = new Ext.form.FormPanel({
  			id:'datosForm',
          	frame: true,
  			border:false,
  			monitorValid: true,
  			bodyStyle:'padding:5px 5px 0',
			items:[
				new Ext.form.ComboBox({
					labelStyle: 'font-weight:bold',  
					editable: false,
					store: almacenStore,
					displayField: 'nombre',
					typeAhead: false,
					width: 150,
					forceSelection: true,
					triggerAction: 'all',
					pageSize: 20,
					valueField: 'id',
					hiddenName: 'almacen.id',
					fieldLabel: 'Almacen',
					value: '${command.almacen.nombre}',
					hiddenValue: '${command.almacen.id}'
	  			}),
	  			new Ext.form.ComboBox({
					labelStyle: 'font-weight:bold',  
					editable: false,
					store: productoStore,
					displayField: 'nombre',
					typeAhead: false,
					width: 150,
					forceSelection: true,
					triggerAction: 'all',
					pageSize: 20,
					valueField: 'id',
					hiddenName: 'producto.id',
					fieldLabel: 'Producto',
					value: '${command.producto.nombre}',
					hiddenValue: '${command.producto.id}'
	  			}),	
				new Ext.form.ComboBox({
					store: clasificadorProdStore,
					displayField: 'nombre',
					width: 150,
					listWidth: 320,
					typeAhead: false,
					forceSelection: true,
					triggerAction: 'all',
					loadingText: 'Buscando Clasificador...',
					pageSize: 20,
					valueField: 'id',
					hiddenName: 'clasificadorProducto.id',
					value: '${command.clasificadorProducto.nombre}',
					hiddenValue: '${command.clasificadorProducto.id}',
					fieldLabel: 'Familia de Producto'
				}),			
				new Ext.form.DateField({
					editable: false,	
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'fechaCambio',
					width: 150,
					format: 'd-m-Y',
		  			value:'${command.fechaCambio}',
		  			fieldLabel:'Fecha',
		  			autoComplete:'off'
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
						            url : '../${modulo}/adicionarInventarioProducto.htm',
						             params: {
										id: '${command.id}'
									},
						            waitMsg : 'Salvando datos...',
						            failure: failure,
		            				success: function (form, action) {
										win.close();
										cargarTab(Ext.getCmp('productoTabPanel'),
										{idproducto: action.result.id});
										productoGrid = Ext.getCmp('productoGrid');
										productoGrid.getStore().reload();
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
			title: 'Editar Inventario Periodico',
		</c:if>
		<c:if test="${empty command.id}">					
			title: 'Adicionar Inventario Periodico',
		</c:if>
	        resizable: false,                
            closable: true,
            modal: true,                   
            constrain: true,
            layout: 'fit',
			height: 320,
            width:420,
            items:[datosForm]
		});
		            
		return win;			
	}
}
</aek:jsmin>