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
   	    	fields:['id','descripcion','nombre','apellido1','apellido2','carnetIdentidad']
		});
			var establecimientoStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarEstablecimiento.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','nombre','descripcion','codigo','direccion']
		});
		var leftPanel = new Ext.Panel({
			columnWidth: 0.5,
			border: false,
			bodyStyle: 'padding:10px 10px 0',
			defaults: {width: 250},
			labelWidth: 100,
			layout: 'form',
			items:[
				new Ext.form.DisplayField({
					value:'${command.establecimientoEcie.nombre}',
					fieldLabel:'Establecimiento'
			    }),
				new Ext.form.DisplayField({
					value:'${command.responsable.nombre}',
					fieldLabel:'Responsable'
				}),
								
				new Ext.form.DisplayField({
					value:'${command.codigo}',
					fieldLabel:'Código'
				})
			]	
		});
		
		var rightPanel = new Ext.Panel({
			columnWidth: 0.5,
			border: false,
			bodyStyle: 'padding:10px 10px 0',
			defaults: {width: 250},
			labelWidth: 120,
			layout: 'form',
			items:[				
			    new Ext.form.DisplayField({
					value:'${command.nombre}',
					fieldLabel:'Nombre'
				})
				/*new Ext.form.DisplayField({
				
				<c:if test = "${not empty command.cargaInicial}">					
					value:'Si',
				</c:if>
				<c:if test = "${empty command.cargaInicial}">						
					value:'No',
				</c:if>
					fieldLabel:'Carga inicial'
				})*/
				
			]
		});
	
		var detallesAlmacen = new Ext.Panel({
			id: 'detallesAlmacen',
			title: 'Detalles del Almacen',
			layout: 'border',
			closable: true,
			height: 450,
			border:false,
			monitorResize:true,
			autoScroll: true,
			items:[
				new Ext.form.FormPanel({
					id: 'detallesAlmacenForm',
					layout:'column',
					region:'north',
					height: 185,
					split: true,
					collapseMode: 'mini',
					maxSize: 185,
					minSize: 62,
					border: false,
					items:[
						leftPanel, rightPanel
					]
					/*,
					tbar: new Ext.Toolbar({
						items: 
						[
						<c:if test = "${not empty command.cargaInicial}">
						{
						text: 'Carga Inicial',
						cls:'x-btn-text-icon bmenu',
						icon:'../img/common/add.png',
						listeners:
						{
						click:function(){editarCargaInicial('../${modulo}/editarCargaInicial.htm',{ 'idAlmacen': '${command.id}'});}	
						}
						}			  		
				  		</c:if>
						]
						
						
					})*/
				}),
				
				new Ext.Panel({
					title:'Descripcion',
					region:'center',
					border: false,
					layout: 'fit',
					bodyStyle:'padding:5px 5px',
					html: '${command.descripcion}'
				})
			]
		});
		
	 	function editarCargaInicial(url, id){
			var idp = id.idAlmacen;
			cargarVentana(url, {idAlmacen: idp});
	    }
	    
		
		return detallesAlmacen;			
	}
}
</aek:jsmin>