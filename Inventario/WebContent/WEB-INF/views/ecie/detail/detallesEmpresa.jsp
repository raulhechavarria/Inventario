<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
	
		var organismoStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarOrganismo.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
   	        root: 'data',
	   	    totalProperty: 'totalCount',
    	    id: 'id',
  	    	fields:['id', 'nombre', 'descripcion']
		});
		
		var leftPanel = new Ext.Panel({
			columnWidth: 0.3,
			border: false,
			bodyStyle: 'padding:10px 10px 0',
			defaults: {width: 250},
			labelWidth: 100,
			layout: 'form',
			items:[
				new Ext.form.DisplayField({
					value:'${command.nombre}',
					fieldLabel:'Empresa'
			    }),
				new Ext.form.DisplayField({
					value:'${command.direccion}',
					fieldLabel:'Dirección'
				}),
				new Ext.form.DisplayField({
					value:'${command.organismo.nombre}',
					fieldLabel:'Organismo'
				}),
				new Ext.form.DisplayField({
					value:'${command.reup}',
					fieldLabel:'Código Reup'
				}),
				new Ext.form.DisplayField({
					value:'${command.codigoNit}',
					fieldLabel:'Código Nit'
				}),
				new Ext.form.DisplayField({
					value:'${command.agenciaMn}',
					fieldLabel:'Agencia CUP'
				}),
				new Ext.form.DisplayField({
					value:'${command.agenciaMlc}',
					fieldLabel:'Agencia CUC'
				}),
				new Ext.form.DisplayField({
					value:'${command.resolDirectora}',
					fieldLabel:'resolDirectora'
				})
			]	
		});
		
		var rightPanel = new Ext.Panel({
			columnWidth: 0.4,
			border: false,
			bodyStyle: 'padding:10px 50px 0',
			defaults: {width: 250},
			labelWidth: 120,
			layout: 'form',
			items:[
				new Ext.form.DisplayField({
					value:'${command.cuentaMlc}',
					fieldLabel:'Cuenta CUC'
				}),
			    new Ext.form.DisplayField({
					value:'${command.tituloMlc}',
					fieldLabel:'Título CUC'
				}),
				new Ext.form.DisplayField({
					value:'${command.cuentaMn}',
					fieldLabel:'Cuenta CUP'
				}),
				new Ext.form.DisplayField({
					value:'${command.tituloMn}',
					fieldLabel:'Título CUP'
				}),
				new Ext.form.DisplayField({
					value:'${command.telefono}',
					fieldLabel:'Telefono'
				}),
				new Ext.form.DisplayField({
					value:'${command.correo}',
					fieldLabel:'Email'
				}),
				new Ext.form.DisplayField({
					value:'${command.fax}',
					fieldLabel:'Fax'
				})
				
			]
		});
		
		var detallesEmpresa = new Ext.Panel({
			id: 'detallesEmpresa',
			title: 'Detalles del Proveedor',
			closable: true,
			height: 400,
			border:false,
			monitorResize:true,
			autoScroll: true,
			items:[
				new Ext.form.FormPanel({
					id: 'detallesEmpresaForm',
					layout:'column',
					region:'north',
					height: 280,
					split: true,
					collapseMode: 'mini',
					maxSize: 185,
					minSize: 62,
					border: false,
					items:[
						leftPanel, rightPanel
					],
					tbar: new Ext.Toolbar({
						items: [
						
						{
							text: 'Imprimir',
							cls:'x-btn-text-icon bmenu',
							icon:'../img/common/printer.png',
							tooltip:'Imprimir Detalles del Proveedor',
							listeners:{
								click:function(){
									descargar('../ecie/impDetallesEmpresa.htm', {id: '${command.id}'});
								}			  			
					  		}
						}
					  ]	
					})
				})
			]
		});
		
		return detallesEmpresa;			
	}
}
</aek:jsmin>