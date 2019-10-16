<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
	
		var ds = new Ext.data.JsonStore({
            url: '../common/listarSecurity.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'idsession',
   	    	fields:['nombre', 'lastRequest']
		});
	
		var gp = new Ext.grid.GridPanel({
			id: 'usuariosConectadosGrid',
			title: 'Desconectar usuarios',
			border: false,
			height: 500,
        	store: ds,
		    viewConfig: {forceFit: true},
        	columns: [
				{header: "Nombre", width: 120, dataIndex: 'nombre', sortable: true},
				{header: "Ultima Peticion", width: 180, dataIndex: 'lastRequest', sortable: true}
			],
			tbar: new Ext.Toolbar({
				items: [{
					text: 'Desconectar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/disconnect.png',
					tooltip:'Desconectar un usuario',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								Ext.MessageBox.show({
				    		       title:'Confirmación',
						           msg: '¿Desea desconectar al usuario?',
						           buttons: Ext.MessageBox.YESNO,
						           fn: function (btn){
								       	 if (btn == 'yes'){
								       	 	Ext.Ajax.request({
												url: '../common/desconectarSecurity.htm',
												params: {
													id: gp.selModel.getSelected().id
												},
												success: function (response, options) {
													if(Ext.decode(response.responseText).success == true){
														gp.getStore().reload();
													}
													else{
														failure(response, options);
													}
												},
												failure: failure,
												waitMsg : 'Desconectando al usuario...'
											});
										}	
								       },
						           icon: Ext.MessageBox.QUESTION
						       });
							}
							else{
								showWarningMsg('Desconectar usuarios', 'Debe seleccionar un usuario a desconectar');
            				}
            			}
					}
				},
				{xtype: 'tbfill'},
				'Buscar: ',
				new Ext.app.SearchField({
	                store: ds,
	                width:320
	            })]
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