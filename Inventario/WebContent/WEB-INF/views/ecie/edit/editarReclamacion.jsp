<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){

		var recepcionStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarRecepcion.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20, 'tipo':'SolicitudEntrega'},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','noVale','noDoc','almacenOrigenId','almacenOrigen' ]
		});
		
		var almacenOrigenStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarAlmacen.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'nombre', 'descripcion']
		});
		
		var estadoValeStore = new Ext.data.SimpleStore({
            fields:['id', 'estadoVale'],
            data:[
            	['Revision','Revision'],
            	['Confeccion','Confeccion'],
            	['Cancelado','Cancelado']
            ]
		});
    	
		var empresaStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarEmpresa.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'nombre', 'descripcion']
		});
    	var compradorStore = new Ext.data.JsonStore({
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
				/*new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'importeNeto',
					width: 150,
		  			value:'${command.importeNeto}',
		  			fieldLabel:'Importe Neto',
		  			autoComplete:'off'
				 }),*/
				 new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'noDoc',
					width: 150,
		  			value: '${command.noDoc}',
		  			fieldLabel: 'No. de Documento ',
		  			autoComplete:'off'
				}),
				new Ext.form.ComboBox({
					labelStyle: 'font-weight:bold',  
					editable: false,
					allowBlank:false,
		  			blankText:'Este campo es requerido',
					store: empresaStore,
					displayField: 'nombre',
					typeAhead: false,
					width: 150,
					forceSelection: true,
					triggerAction: 'all',
					pageSize: 20,
					valueField: 'id',
					hiddenName: 'empresa.id',
					fieldLabel: 'Proveedor',
					value: '${command.empresa.nombre}',
					hiddenValue: '${command.empresa.id}'
	  			}),
	  		new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'totalMN',
					width: 150,
		  			value:'${command.totalMN}',
		  			fieldLabel:'Total CUP',
		  			autoComplete:'off'
				 }),
				 new Ext.form.NumberField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'totalMLC',
					width: 150,
		  			value:'${command.totalMLC}',
		  			fieldLabel:'Total CUC',
		  			autoComplete:'off'
				 }),
				new Ext.form.ComboBox({
					labelStyle: 'font-weight:bold',  
					allowBlank: false,
					editable: false,
		  			blankText: 'Este campo es requerido',
					store: estadoValeStore,
					displayField:'estadoVale',
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
					store: compradorStore,
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
					fieldLabel: 'Comprador',
					value: '${command.recibido.nombre}',
					hiddenValue: '${command.recibido.id}'
	  			}),
	  		
	  			new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
		  			name:'receptor',
		  			maxLength: 25,
					width: 150,
		  			value:'${command.receptor}',
		  			fieldLabel:'Receptor',
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
						            url : '../${modulo}/addReclamacion.htm',
						            params: {
										id: '${command.id}',
										tipoVale: 'Reclamacion'
									},
						            waitMsg : 'Salvando datos...',
						            failure: failure,
		            				success: function (form, action) {
										win.close();
										cargarTab(Ext.getCmp('reclamacionTabPanel'),
										{idReclamacion: action.result.id});
										reclamacionGrid = Ext.getCmp('reclamacionGrid');
										reclamacionGrid.getStore().reload();
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
			title: 'Editar Reclamación',
		</c:if>
		<c:if test="${empty command.id}">					
			title: 'Adicionar Reclamación',
		</c:if>
	        resizable: false,                
            closable: true,
            modal: true,                   
            constrain: true,
            layout: 'fit',
			height: 420,
            width:420,
            items:[datosForm]
		});
		            
		return win;			
	}
}
</aek:jsmin>