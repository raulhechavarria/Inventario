<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){

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
    	var valeProductoStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarValeProducto.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'noControl']
    	});
    	
	var datosForm = new Ext.form.FormPanel({
  			id:'datosForm',
          	frame: true,
  			border:false,
  			monitorValid: true,
  			bodyStyle:'padding:5px 5px 0',
			items:[	
			
				new Ext.form.DateField({
					editable: false,	
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'fechaCambio',
					width: 150,
					format: 'd-m-Y',
		  			value:'${command.fechaCambio}',
		  			fieldLabel:'Fecha de Cambio',
		  			autoComplete:'off'
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
					store: valeProductoStore,
					displayField: 'vale',
					typeAhead: false,
					width: 150,
					forceSelection: true,
					triggerAction: 'all',
					pageSize: 20,
					valueField: 'id',
					hiddenName: 'valeProducto.id',
					fieldLabel: 'Vale',
					value: '${command.valeProducto.id}',
					hiddenValue: '${command.valeProducto.id}'
	  					  				
				}),
				 new Ext.form.NumberField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'cantEntradaVale',
					width: 150,
		  			value:'${command.cantEntradaVale}',
		  			fieldLabel:'Cant. Entrada Vale',
		  			autoComplete:'off'
				}),
				 new Ext.form.NumberField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'cantSalidaVale',
					width: 150,
		  			value:'${command.cantSalidaVale}',
		  			fieldLabel:'Cant. Salida Vale',
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
						            url : '../${modulo}/adicionarTarjetero.htm',
						            params: {
										id: '${command.id}'
									},
						            waitMsg : 'Salvando datos...',
						            failure: failure,
		            				success: function (form, action) {
										win.close();
										cargarTab(Ext.getCmp('tarjeteroTabPanel'),
										{idtarjetero: action.result.id});
										tarjeteroGrid = Ext.getCmp('tarjeteroGrid');
										tarjeteroGrid.getStore().reload();
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
			title: 'Editar Tarjetero',
		</c:if>
		<c:if test="${empty command.id}">					
			title: 'Adicionar Tarjetero',
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