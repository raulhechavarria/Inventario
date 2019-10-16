<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){

		var unidadMedidaStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarUnidadMedida.json',
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
			
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido, no debe exceder de 200 caracteres',
					name:'nombre',
					maxLength: 100,
					width: 150,
		  			value:'${command.nombre}',
		  			fieldLabel:'Nombre',
		  			autoComplete:'off'
				}),	
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
					minLength: 14,
					minLengthText: 'Debe completar los 14 caracteres',
					maxLength: 14,
					autoCreate: {tag: 'input', type: 'text', autocomplete: 'off', maxlength: '14'},
		  			blankText:'Este campo es requerido',
					name:'codigo',
					width: 150,
		  			value:'${command.codigo}',
		  			fieldLabel:'Código',
		  			autoComplete:'off'
				}),	
				
				new Ext.form.ComboBox({
					store: clasificadorProdStore,
					labelStyle: 'font-weight:bold',
					allowBlank:false,
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
					fieldLabel: 'Familia Producto'
				}),			
						
				new Ext.form.ComboBox({
					store: unidadMedidaStore,
					labelStyle: 'font-weight:bold',
					allowBlank:false,
					displayField: 'nombre',
					width: 150,
					listWidth: 320,
					typeAhead: false,
					forceSelection: true,
					triggerAction: 'all',
					loadingText: 'Buscando Unidades de Medidas...',
					pageSize: 20,
					valueField: 'id',
					hiddenName: 'unidad.id',
					value: '${command.unidad.nombre}',
					hiddenValue: '${command.unidad.id}',
					fieldLabel: 'Unidad de Medida'
				}),
				 new Ext.form.HtmlEditor({
				    width: 260,
    				height: 90,
    				maxLength: 100,
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
			title: 'Editar Producto',
		</c:if>
		<c:if test="${empty command.id}">					
			title: 'Adicionar Producto',
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