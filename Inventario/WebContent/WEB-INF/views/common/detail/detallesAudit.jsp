<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
		
		function json2leaf(json) {
			var ret = [];
			if(typeof json === 'string' || typeof json === 'number' ){
				ret.push({text: 'id' + ' : "' + json + '"', leaf: true, icon: 'blue.gif'});
			} else {
				for (var i in json) {
					if (json.hasOwnProperty(i)) {
						if (json[i] === null) {
							ret.push({text: i + ' : null', leaf: true, icon: 'green.gif'});
						} else if (typeof json[i] === 'string') {
							ret.push({text: i + ' : "' + json[i] + '"', leaf: true, icon: 'blue.gif'});
						} else if (typeof json[i] === 'number') {
							ret.push({text: i + ' : ' + json[i], leaf: true, icon: 'green.gif'});
						} else if (typeof json[i] === 'boolean') {
							ret.push({text: i + ' : ' + (json[i] ? 'true' : 'false'), leaf: true, icon: 'yellow.gif'});
						} else if (typeof json[i] === 'object') {
							ret.push({text: i, children: json2leaf(json[i]), icon: Ext.isArray(json[i]) ? 'array.gif' : 'object.gif'});
						} else if (typeof json[i] === 'function') {
							ret.push({text: i + ' : function', leaf: true, icon: 'red.gif'});
						}
					}
				}
			}
			return ret;
		}
		
		var treeJSON = new Ext.tree.TreePanel({
			id: 'tree',
	    	loader: new Ext.tree.TreeLoader(),
			lines: true, 
	        root: new Ext.tree.TreeNode({text: 'Datos de la acción'}),
	        //autoScroll: true,
	        trackMouseOver: false
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
					value:'${command.principal}',
					fieldLabel:'Usuario'
				}),
				new Ext.form.DisplayField({
					value:'${command.actionPerformed}',
					fieldLabel:'Accion'
			    }),
				new Ext.form.DisplayField({
					value:'${command.clientIpAddress}',
					fieldLabel:'Ip Cliente'
				}), 
				new Ext.form.DisplayField({
					value:'${command.serverIpAddress}',
					fieldLabel:'Ip Servidor'
				}),
			    new Ext.form.DisplayField({
			    	value:'${command.whenActionWasPerformed}',
					fieldLabel:'Fecha'
				})
			]	
		});
		
		var rightPanel = new Ext.Panel({
			columnWidth: 0.5,
			border: false,
			bodyStyle: 'padding:10px 10px 0',
			defaults: {width: 350},
			labelWidth: 120,
			items:[
				treeJSON
			]
		});
		
		var detallesAudit = new Ext.Panel({
			id: 'detallesAudit',
			title: 'Detalles de la Accion',
			layout: 'column',
			closable: true,
			border:false,
			monitorResize:true,
			autoScroll: true,
			items:[
				new Ext.Panel({
					id: 'detallesAuditForm',
					layout:'column',
					collapseMode: 'mini',
					height: 400,
					border: false,
					autoScroll: true,
					items:[
						leftPanel, rightPanel
					],
					tbar: new Ext.Toolbar({
						items: [
						{
							text: 'Imprimir',
							cls:'x-btn-text-icon bmenu',
							icon:'../img/common/printer.png',
							tooltip:'Imprimir Acción de Auditoría',
							listeners:{
								click:function(){
									descargar('../common/imprimirDetallesAudit.htm');
								}			  			
					  		}
						}]
					})
				})
			]
		});
		
		var rootNode = treeJSON.getRootNode();
		var text = '${command.resourceOperatedUpon}';
		
		try{
			rootNode.appendChild( json2leaf( Ext.decode(text) ) );
		}catch(e){
			alert(e);
		}
		return detallesAudit;
	}
}
</aek:jsmin>