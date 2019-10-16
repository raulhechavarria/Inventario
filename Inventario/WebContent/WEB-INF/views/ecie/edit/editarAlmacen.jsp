<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){





		var responsableStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarResponsable.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','descripcion','nombre','apellido1','apellido2','']
		});
		
		var establecimientoStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarEstablecimientoEcie.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','nombre','descripcion','codigo']
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
  			id:'datosForm',
          	frame: true,
          	border:false,
  			monitorValid: true,
  			bodyStyle:'padding:5px 5px 0',
			items:[
			
				new Ext.form.TextField({
				    labelStyle: 'font-weight:bold',
					allowBlank:false,
					blankText:'Este campo es requerido',
					name:'nombre',
					width: 150,
					value:'${command.nombre}',
		  			fieldLabel:'Nombre',
		  			autoComplete:'off'
				}),				
						
				new Ext.form.TextField({
					labelStyle: 'font-weight:bold',
					allowBlank:false,
		  			blankText:'Este campo es requerido',
					name:'codigo',
					width: 150,
					maxLength: 5,
		  			value:'${command.codigo}',
		  			fieldLabel:'Código',
		  			autoComplete:'off'
				}),
				
				new Ext.form.ComboBox({
					store: responsableStore,
					mode: 'local',
					displayField: 'nombre',
					width: 150,
					labelStyle: 'font-weight:bold',
					listWidth: 320,
					typeAhead: false,
					forceSelection: true,
					triggerAction: 'all',
					loadingText: 'Buscando responsables...',
					pageSize: 20,
					valueField: 'id',
					itemSelector: 'div.search-item',
					tpl: resultTpl,
					hiddenName: 'responsable.id',
					value: '${command.responsable.nombre}',
					hiddenValue: '${command.responsable.id}',
					autocomplete: "on",
					fieldLabel: 'Responsable'
				}),	
				new Ext.form.ComboBox({
					store: establecimientoStore,
					displayField: 'nombre',
					width: 150,
					labelStyle: 'font-weight:bold',
					listWidth: 320,
					typeAhead: false,
					forceSelection: true,
					triggerAction: 'all',
					loadingText: 'Buscando Establecimiento Ecie...',
					pageSize: 20,
					valueField: 'id',
					hiddenName: 'establecimientoEcie.id',
					value: '${command.establecimientoEcie.nombre}',
					hiddenValue: '${command.establecimientoEcie.id}',
					fieldLabel: 'Establecimiento Ecie'
				}),	
			
				 new Ext.form.HtmlEditor({
				    width: 260,
    				height: 120,
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
						            url : '../${modulo}/adicionarAlmacen.htm',
						             params: {
										id: '${command.id}'
									},
						            waitMsg : 'Salvando datos...',
						            failure: failure,
		            				success: function (form, action) {
										win.close();
										cargarTab(Ext.getCmp('almacenTabPanel'),
										{idalmacen: action.result.id});
										almacenGrid = Ext.getCmp('almacenGrid');
										almacenGrid.getStore().reload();
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
			title: 'Editar Almacén',
		</c:if>
		<c:if test="${empty command.id}">					
			title: 'Adicionar Almacén',
		</c:if>
	        resizable: false,                
            closable: true,
            modal: true,                   
            constrain: true,
            layout: 'fit',
			height: 360,
            width:420,
            items:[datosForm]
		});
		            
		return win;			
	}
}
</aek:jsmin>