<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin></aek:jsmin>
{
	success: true,
	view: function(){
	
		var leftPanel = new Ext.Panel({
			columnWidth: 0.5,
			border: false,
			bodyStyle: 'padding:10px 10px 0',
			defaults: {width: 250},
			labelWidth: 100,
			layout: 'form',
			items:[${itemsDer}]	
		});
		
		var rightPanel = new Ext.Panel({
			columnWidth: 0.5,
			border: false,
			bodyStyle: 'padding:10px 10px 0',
			defaults: {width: 250},
			labelWidth: 120,
			layout: 'form',
			items:[${itemsIzq}]
		});
		
		var detallesNomenclador = new Ext.Panel({
			id: 'detallesNomenclador',
			title: 'Detalles ${nomenclador}',
			layout: 'border',
			closable: true,
			height: 450,
			border:false,
			monitorResize: false,
			autoScroll: true,
			items:[
				new Ext.form.FormPanel({
					id: 'detallesForm',
					layout:'column',
					region:'center',
					height: 185,
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
							text: 'Adicionar',
							cls:'x-btn-text-icon bmenu',
							icon:'../img/common/add.png',
							tooltip:'Adicionar ${nomenclador}',
							listeners:{
								click:function(){
									cargarVentana('../${modulo}/editar${nomenclador}.htm');
								}			  			
			  				}
						},
						{
							text: 'Modificar',
							cls:'x-btn-text-icon bmenu',
							icon:'../img/common/pencil.png',
							tooltip:'Modificar ${nomenclador}',
							listeners:{
								click:function(){
									cargarVentana('../${modulo}/editar${nomenclador}.htm',{id:'${command.id}'});		
					  			}
			  				}
						},
						{
							text: 'Eliminar',
							cls:'x-btn-text-icon bmenu',
							icon:'../img/common/delete.png',
							tooltip:'Eliminar ${nomenclador}',
							listeners:{
								click:function(){
									var gp = Ext.getCmp('nomencladorGrid');
									eliminar(gp, '../${modulo}/eliminar${nomenclador}.htm');
							  	}
					  		}
						},
						{
							text: 'Imprimir',
							cls:'x-btn-text-icon bmenu',
							icon:'../img/common/printer.png',
							tooltip:'Imprimir Detalles',
							listeners:{
								click:function(){
									descargar('../${modulo}/imprimirDetalles${nomenclador}.htm', ds.baseParams);
								}			  			
					  		}
						}]
					})
				})
			]
		});
		
		return detallesNomenclador;			
	}
}
