<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){

		var solicitudStore = new Ext.data.JsonStore({
            url: '../${modulo}/listaSoliEnt.json',
          //  autoLoad: true,
       	 //   remoteSort: true,
			baseParams: {limit:20},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','noVale','noControl','almacenOrigenId','almacenOrigen' ]
		});
		
		var estadoValeStore = new Ext.data.SimpleStore({
            fields:['id', 'estadoVale'],
            data:[
            	['Revision','Revision'],
            	['Confeccion','Confeccion'],
            	['Cancelado','Cancelado']
            ]
		});
	
		var almacenDestinoStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarAlmacen.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'nombre', 'descripcion']
		});
    	var recibidoStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarResponsable.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'nombre', 'apellido1', 'descripcion']
    	});
    	
    	 var resultTpl = new Ext.XTemplate(
	        '<tpl for="."><div class="search-item">',
               	'<tpl for=".">',
        		'{nombre}',
        		'</tpl>',
        		' ',
        		'{apellido1}<br/>',	            
	        '</div></tpl>'
	    );
	    
    	var solicitudTpl = new Ext.XTemplate(
	        '<tpl for="."><div class="search-item">',
               	'<tpl for=".">',
        		'{noControl}',
        		'</tpl>',
        		' ',
        		'{noVale}<br/>',	            
	        '</div></tpl>'
	    );
	    
		var datosForm = new Ext.form.FormPanel({
			id: 'datosForm',
			frame: true,
			monitorValid: true,
			border: false,
			bodyStyle: 'padding:10px 10px 0',
			items:[	
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'noControl',
					width: 150,
		  			value:'${command.noControl}',
		  			fieldLabel:'No. Control',
		  			autoComplete:'off'
				}),
				
		/*		new Ext.form.ComboBox({
			        typeAhead: true,
			        forceSelection: true,
			        triggerAction: 'all',
			        selectOnFocus:true,
					allowBlank:false,
				}),*/
				new Ext.form.ComboBox({
					store: solicitudStore,
					displayField: 'noControl',
					width: 150,
					blankText:'Este campo es requerido',
					minChars : 1,
					label: 'Solicitud:',
					labelStyle: 'font-weight:bold',
					listWidth: 320,
					typeAhead: false,
					id: 'vale',
					forceSelection: true,
					triggerAction: 'all',
					loadingText: 'Buscando solicitudes de referencia...',
					value: '${command.noDoc}',									
					valueField: 'noControl',
					itemSelector: 'div.search-item',
					tpl: solicitudTpl,
					pageSize:50,
					hiddenValue: '${command.noDoc}',
					hiddenName: 'noDoc',
					autocomplete: "on",
					fieldLabel: 'Vale Referencia'
				}),
				new Ext.form.ComboBox({
					labelStyle: 'font-weight:bold',  
					allowBlank: false,
					editable: false,
		  			blankText: 'Este campo es requerido',
					store: estadoValeStore,
					displayField:'estadoVale',
					value: '${command.estadoVale}',
					width: 150,
					triggerAction: 'all',
					name: 'estadoVale',
					fieldLabel: 'Estado vale',
					mode: 'local',
					typeAhead: true,
				    triggerAction: 'all',
				    editable: false
				}),
				new Ext.form.ComboBox({
					store: recibidoStore,
			        typeAhead: true,
			        mode: 'local',
			        forceSelection: true,
			        triggerAction: 'all',
			        selectOnFocus:true,
					allowBlank:false,
		  			blankText:'Este campo es requerido',
					labelStyle: 'font-weight:bold',  
					displayField:'nombre',
					width: 150,
					tpl: resultTpl,
					itemSelector: 'div.search-item',
					pageSize: 20,
					valueField: 'id',
					hiddenName: 'recibido.id',
					fieldLabel: 'Entregado por',
					value: '${command.recibido.nombre}',
					hiddenValue: '${command.recibido.id}'
	  			}),
	  			new Ext.form.DateField({
					editable: false,	
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'fchRecibido',
					width: 150,
					format: 'd-m-Y',
		  			value:'${command.fchRecibido}',
		  			fieldLabel:'Fecha Entrega',
		  			autoComplete:'off'
				}),
	  			
	  			new Ext.form.HtmlEditor({
				    width: 200,
    				height: 40,
				    fieldLabel: 'Comentario',
				    labelStyle: 'font-weight:bold',
					name:'comentario',
	  				value:'${command.comentario}',
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
						            url : '../${modulo}/addDevolucion.htm',
						            params: {
										id: '${command.id}',
										tipoVale: 'Devolucion'
									},
						            waitMsg : 'Salvando datos...',
						            failure: failure,
		            				success: function (form, action) {
										win.close();
										cargarTab(Ext.getCmp('devolucionTabPanel'),
										{iddevolucion: action.result.id});
										DevolucionGrid = Ext.getCmp('DevolucionGrid');
										DevolucionGrid.getStore().reload();
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
			title: 'Editar Devolución',
		</c:if>
		<c:if test="${empty command.id}">					
			title: 'Adicionar Devolución',
		</c:if>
	        resizable: false,                
            closable: true,
            modal: true,                   
            constrain: true,
            layout: 'fit',
			height: 300,
            width:420,
            items:[datosForm]
		});
		            
		return win;			
	}
}
</aek:jsmin>