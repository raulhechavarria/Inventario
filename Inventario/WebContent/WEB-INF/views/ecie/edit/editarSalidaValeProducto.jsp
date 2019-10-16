<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
	
	

		var productoStore = new Ext.data.JsonStore({
			//proxy: new Ext.data.ScriptTagProxy({
	            url: '../${modulo}/listProducto.json',
	        //}),
			baseParams: {limit:20},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','nombre','codigo']
		});
	 
	var resultTpl = new Ext.XTemplate(
	        '<tpl for="."><div class="search-item">',
               	'<tpl for=".">',
        		'{codigo}',
        		'</tpl>',
        		' ',
        		'{nombre}<br/>',	            
	        '</div></tpl>'
	    );
		var generalPanel = new Ext.Panel({
			
			frame: true,
			border: false,
			bodyStyle: 'padding:10px 10px 0',
			labelWidth:125,
			layout: 'form',
			items:[
				
				new Ext.form.ComboBox({
					store: productoStore,
					displayField: 'codigo',
					width: 150,
					minChars : 1,
					labelStyle: 'font-weight:bold',
					listWidth: 320,
					typeAhead: false,
					forceSelection: true,
					triggerAction: 'all',
					loadingText: 'Buscando Productos...',
					pageSize: 40,
					valueField: 'id',
					itemSelector: 'div.search-item',
					tpl: resultTpl,
					hiddenName: 'producto.id',
					value: '${command.producto.codigo}',
					hiddenValue: '${command.producto.id}',
					autocomplete: "on",
					fieldLabel: 'Código'
				}),	
				
				new Ext.form.NumberField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			decimalPrecision : 3,
		  			name:'cantProdVale',
		  	        width: 150,
		  			fieldLabel:'Cant. Producto Vale',
		  			value:'${command.cantProdVale}',
		  			autoComplete:'off'
				})
				 
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
				generalPanel
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
						            url : '../${modulo}/adicionEntradaValeProducto.htm',
						            params: {
										id: '${command.id}',
										idVale: '${command2}'
										
									},
						            waitMsg : 'Salvando datos...',
						            failure: failure,
		            				success: function (form, action) {
										win.close();
										cargarTab(Ext.getCmp('recepcionTabPanel'),
										{idrecepcion: action.result.id});
										detallesRecepcionGrid = Ext.getCmp('detallesRecepcionGrid');
										detallesRecepcionGrid.getStore().reload();
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
			title: 'Editar Vale Producto',
		</c:if>
		<c:if test="${empty command.id}">					
			title: 'Adicionar Vale Producto',
		</c:if>
	        resizable: false,                
            closable: true,
            modal: true,                   
            constrain: true,
            layout: 'fit',
			height: 180,
            width:420,
            items:[datosForm]
		});
		            
		return win;			
	}
}
</aek:jsmin>