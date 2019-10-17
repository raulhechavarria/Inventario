<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
		
		var acerca = new Ext.Panel({
		title:'Acerca de',
		frame: true,
		border: false,
		bodyStyle: 'padding:5px 5px 0',
		layout: 'fit',       
		html: '<div id="acercaD" style="padding:10px"><img src="../img/common/este.png"><br><hr><P>Este software fue creado para gestionar el control de la existencia de los productos en los almacenes de la Empresa ECIE</P><br></div>'
		});
		
		var creditos = new Ext.Panel({
		title:'Créditos',
		frame: true,
		border: false,
		autoScroll: true,
		bodyStyle: 'padding:10px 10px 0',
		layout: 'fit',       
		html: '<div id="acercaD" style="padding:10px"><P> Equipo de Trabajo<br><hr>Raul Hechavarria..........................Programador<br></hr></P></div>'
		});
		
		var licencia = new Ext.Panel({
		title:'Licencia',
		frame: true,
		autoScroll: true,
		border: false,
		bodyStyle: 'padding:10px 10px 0',
		layout: 'fit',       
		html: '<div id="acercaD" style="padding:10px"><P>Datos de Licencia</P><hr><P>Copyright © 2011. desoft.sa. Reservados todos los derechos.<br>Este producto está protegido por las leyes de derechos de autor.<br>La reproducción o distribución ilícita de este software,<br>o de cualquier parte del mismo, está penado por la ley con<br>severas sanciones  civiles y penales.</P></div>'
		});
		
		var tabs = new Ext.TabPanel({
			activeTab:0,
			deferredRender: false,
			renderTo: Ext.getBody(),
			border: false,
			items:[
				acerca, creditos, licencia
			]
		});
				
		
		
		var acercaDeWin = new Ext.Window({
			title: 'Inventario ECIE',
			resizable: false,
			closable: true,
		    modal: true,                
		    layout: 'fit',       
		    constrain: true,
		    width:450,
		    height: 255,
		    items:[tabs],
		    buttons: [{
		        text: 'Cerrar',
		        	handler: function(){
		            	acercaDeWin.close();
		            }
		    }]
		});

		return acercaDeWin;
	}
}
</aek:jsmin>