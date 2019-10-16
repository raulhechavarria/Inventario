<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
	
		var ds = new Ext.data.JsonStore({
            url: '../common/listarUsuario.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['login', 'nombre', 'deshabilatado']
		});
	
	    // función para renderear la columna "Deshabilatado"
	    function deshabilatado(val){
	        if(val){
	            return 'Sí';
	        }else {
	            return 'No';
	        }
	    }
	
		var gp = new Ext.grid.GridPanel({
			id: 'usuariosGrid',
			title: 'Usuarios y Permisos',
			border: false,
			height: 500,
        	store: ds,
		    viewConfig: {forceFit: true},
        	columns: [
				{header: "Usuario", width: 120, dataIndex: 'login', sortable: true},
				{header: "Nombre completo", width: 180, dataIndex: 'nombre', sortable: true}
			],
			tbar: new Ext.Toolbar({
				items: [{
					text: 'Adicionar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/add.png',
					tooltip:'Adicionar Usuario',
					listeners:{
						click:function(){
							cargarVentana('../common/editarUsuario.htm');
						}			  			
			  		}
				},
				{
					text: 'Editar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/pencil.png',
					tooltip:'Editar Usuario',
					listeners:{
						click:function(){
							modificar(gp, '../common/editarUsuario.htm');
					  	}
			  		}
				},
				{
					text: 'Eliminar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/delete.png',
					tooltip:'Eliminar Usuario',
					listeners:{
						click:function(){
							eliminar(gp, '../common/eliminarUsuario.htm');
            			}
					}
				},
				{xtype: 'tbspacer'},
				{xtype: 'tbseparator'},
				{xtype: 'tbspacer'},
				{
					text: 'Imprimir',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/printer.png',
					tooltip:'Imprimir Listado',
					listeners:{
						click:function(){
							descargar('../common/imprimirUsuario.htm', ds.baseParams);
						}			  			
			  		}
				}]
			}),
			bbar: new Ext.PagingToolbar({
		        store: ds,       // grid and PagingToolbar using same store
		        displayInfo: true,
		        pageSize: 20
		    })
	    });
	    
	   	return gp;
	}
}
</aek:jsmin>