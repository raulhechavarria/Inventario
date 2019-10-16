<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag"%>
<aek:jsmin>
{
	success: true,
	view: function(){
		 var ds = new Ext.data.JsonStore({
 			url: '../${modulo}/listarRecepcion.json',
			baseParams: {'tipo': 'Recepcion' },
			autoLoad: true,
			root: 'data',
    	    totalProperty: 'totalCount',
		    id: 'id',
            fields: [
            	'id',
            	{name: 'noVale', type: 'string'},
   				{name: 'fechaVale', type: 'string'},
                {name: 'fchRecibido', type: 'string'},
                {name: 'fchResponsAutoriza', type: 'string'},
                {name: 'fchSolicElab', type: 'string'},
				{name: 'noControl', type: 'string'},
		      	{name: 'comentario', type: 'string'},
		      	{name: 'totalMN', type: 'double'},			      	
		      	{name: 'totalMLC', type: 'double'}, 
  	    		{name: 'recargoMN', type: 'double'},
		        {name: 'recargoMLC', type: 'double'},
		        {name: 'descuentoMN', type: 'double'},
		        {name: 'descuentoMLC', type: 'double'},
		        {name: 'serviciosMN', type: 'double'},
		        {name: 'serviciosMLC', type: 'double'},
		        {name: 'importeNeto', type: 'double'},  
	        	{name: 'tipoVale', type: 'string'},
	            {name: 'estadoVale', type: 'string'},
	            {name: 'empresa', type: 'string'},
	            {name: 'almacenDestino', type: 'string'},
	            {name: 'responsAutoriza', type: 'string'},
	            {name: 'solicElab', type: 'string'},
                {name: 'recibido', type: 'string'},
                {name: 'centroCosto', type: 'string'},
                {name: 'noDoc', type: 'string'},
                {name: 'nombreDoc', type: 'string'},
	      	    {name: 'nombreTransportador', type: 'string'},
	      	    {name: 'cITransportador', type: 'string'},
	      	    {name: 'noTransporte', type: 'string'},
	      	    {name: 'importeMN', type: 'string'},
	      	    {name: 'importeMLC', type: 'string'}
            ]
	    });
		
 // funci�n para renderear la columna "estadoVale"
	    function estadoVale(estadoVale){
	       if(estadoVale == 'Confirmado'){
	            return '<span style="color: green; font-weight:bold" >' + estadoVale + '</span>';
	        }
	        if(estadoVale == 'Cancelado'){
	            return '<span style="color: red; font-weight: bold">'
	+ estadoVale + '</span>';
	        }
	        if(estadoVale == 'Confeccion'){
	            return '<span style="color: blue; font-weight: bold">'
	+ estadoVale + '</span>';
	        }
	        if(estadoVale == 'Revision'){
	        
	           return '<span style="color: brown; font-weight: bold">' + estadoVale +
	'</span>';
	        }
	    }
		var model = new Ext.grid.RowSelectionModel();
	
	   var gp = new Ext.grid.GridPanel({
			id: 'recepcionGrid',
			monitorResize:true,
			autoScroll:true,
			loadMask:true,
			stripeRows: true,
			sm: new Ext.grid.CheckboxSelectionModel({
				singleSelect:false
			}),
			title: 'Recepci�n',
			border: false,
			monitorResize:true,
        	store: ds,
		    viewConfig: {forceFit: true},
        	columns: [
        	  {header: "No. Vale", width: 10, dataIndex: 'noVale', sortable: true},
        	  {header: "Fecha de Vale", width: 10, dataIndex: 'fechaVale', sortable: true},
			  {header: "No. Control", width: 10, dataIndex: 'noControl', sortable: true},
			  {header: "Estado Vale", width: 10, dataIndex: 'estadoVale', sortable: true, renderer: estadoVale}
			],
			tbar: new Ext.Toolbar({
				items: [/*{
					text: 'Adicionar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/add.png',
					tooltip:'Adicionar Recepci�n',
					listeners:{
						click:function(){editarRecepcion('../${modulo}/editarRecepcion.htm');}				  			
			  		}
				},*/{
					text: 'Detalles',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/lightning.png',
					tooltip:'Detalles Recepci�n',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								detalles(gp, tp, '../${modulo}/detallesRecepcion.htm');
							}
							else{
	           					Ext.MessageBox.show({  
									title: 'Detalles de Recepci�n',  
									msg: 'Debe seleccionar una Recepci�n.',  
									buttons: Ext.MessageBox.OK,  
									icon: Ext.MessageBox.ERROR  
	               				});
	           				}			
					  	}
					}
				},{
					text: 'Modificar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/pencil.png',
					tooltip:'Modificar Recepci�n',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								modificar(gp, '../${modulo}/editarRecepcion.htm');
							}
							else{
            					Ext.MessageBox.show({  
									title: 'Recepciones',  
									msg: 'Debe seleccionar una Recepci�n.',  
									buttons: Ext.MessageBox.OK,  
									icon: Ext.MessageBox.ERROR  
                				});
            				}
					  	}
			  		}
				},
				{
					text: 'Confirmar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/accept.png',
					tooltip:'Confirmar la Recepci�n',
					listeners:{					
						click:function(){
						confirmar(gp,'../${modulo}/confirmarRecepcion.htm',this);
						}
			  		}
				}/*,
				{
					text: 'Eliminar',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/delete.png',
					tooltip:'Eliminar Recepci�n',
					listeners:{
						click:function(){
							if(gp.selModel.hasSelection()){
								Ext.MessageBox.show({
				    		       title:'Confirmaci�n',
						           msg: '�Desea eliminar la Recepci�n?',
						           buttons: Ext.MessageBox.YESNO,
						           fn: function (btn){
								       	 if (btn == 'yes'){
								       	 	Ext.Ajax.request({
												url: '../${modulo}/eliminarRecepcion.htm',
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
												waitMsg : 'Eliminando datos...'
											});
										}	
								       },
						           icon: Ext.MessageBox.QUESTION
						       });
							}
							else{
            					Ext.MessageBox.show({  
									title: 'Listado de las Recepciones',  
									msg: 'Debe seleccionar una Recepci�n.',  
									buttons: Ext.MessageBox.OK,  
									icon: Ext.MessageBox.ERROR  
                				});
            				}
            			}
					}
				},{
					text: 'Reclamaci�n',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/reclamacion.png',
					tooltip:'Reclamaci�n',
					listeners:{
						click:function(){
						 if(gp.selModel.hasSelection()){
							Ext.MessageBox.show({
			    		       title:'Reclamaci�n',
					           msg: 'Usted va hacer una Reclamaci�n del Vale selecionado',
					           buttons: Ext.MessageBox.YESNO,
					           fn: function (btn){
							       	 if (btn == 'yes'){
							       	 	Ext.Ajax.request({
									url: '../${modulo}/Reclamacion.htm',
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
									waitMsg : 'Eliminando datos...'
								});
									}	
							       },
					           icon: Ext.MessageBox.QUESTION
					       });
					       }
							else{
            					Ext.MessageBox.show({  
									title: 'Listado de las Recepciones',  
									msg: 'Debe seleccionar una Recepci�n.',  
									buttons: Ext.MessageBox.OK,  
									icon: Ext.MessageBox.ERROR  
                				});
            				}
						}
			  		}
				}*/,
				{xtype: 'tbspacer'},
				{xtype: 'tbseparator'},
				{xtype: 'tbspacer'},
				{
					text: 'Imprimir',
					cls:'x-btn-text-icon bmenu',
					icon:'../img/common/printer.png',
					tooltip:'Imprimir Recepciones',
					listeners:{
						click:function(){
							descargar('../ecie/imprimirRecepcion.htm', ds.baseParams);
				  		}	
			  		}
				},
				'-','Desde:',
				new Ext.form.DateField({
					width: 80,
					label: 'Fecha:',
					labelStyle: 'font-weight:bold',
					listWidth: 320,
					typeAhead: false,
					id: 'fechaIni',
					triggerAction: 'all',
					autocomplete: "on",
					format:'Y-m-d',
					fieldLabel: 'Fecha',
					listeners:{
					 	select: function(field, fecha){ 
					 		var ff = Ext.getCmp('fechaFin');
					 		ff.reset();
					 		var params = {
					 			'fechaF':null,
					 			'fechaI': fecha.format('Y-m-d')
					 		};					 		
							var lastOptions = ds.baseParams;
							Ext.apply(lastOptions, params);						
							ds.reload({params:lastOptions});
					 	},
			        }
				}),
				'---------------','Hasta:',
				new Ext.form.DateField({
					width: 80,
					label: 'Fecha:',
					labelStyle: 'font-weight:bold',
					listWidth: 320,
					typeAhead: false,
					format:'Y-m-d',
					id: 'fechaFin',
					triggerAction: 'all',
					autocomplete: "on",
					fieldLabel: 'Fecha',
					listeners:{
					 	select: function(field, fecha){ // override default onSelect to do redirect
					 		var params = {
					 					 // Ext.getCmp('fecha1DateField').getRawValue(),
					 			'fechaF': fecha.format('Y-m-d'),
					 			fechaI: Ext.getCmp('fechaIni').getRawValue()
					 		};
							var lastOptions = ds.baseParams;
							Ext.apply(lastOptions, params);
						
							ds.reload({params:lastOptions});
					 	},
			        }
				})
				]
			}),
			bbar: new Ext.PagingToolbar({
		        store: ds,       // grid and PagingToolbar using same store
		        displayInfo: true,
		        pageSize: 20
		    })
	    });
	   	 
		
		function editarRecepcion(url, id){
			cargarVentana(url, {id: id});
	    }
   
	    gp.on('celldblclick', gridCellDblClick, this);
		
		function gridCellDblClick(target, rowIndex, columnIndex, e){
			detalles(gp, tp, '../${modulo}/detallesRecepcion.htm');
		}
   		var tp = new Ext.TabPanel({
			id: 'recepcion',
			border: false,
	        monitorResize:true,
	        enableTabScroll:true,
	        defaults: {autoScroll:true},
	        items:[gp],
	        activeTab:0
		}); 
   	
		var recepcionPanel = new Ext.Panel({
		  border: false,
		  layout: 'fit',
		  title:'Movimientos',
		  items:[tp] 
		});
		
	   	return recepcionPanel;
	}
}
</aek:jsmin>