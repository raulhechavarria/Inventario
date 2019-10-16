<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags' %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Control de Inventario</title>

<link rel="stylesheet" type="text/css" href="../css/header.css"></link>
<link rel="stylesheet" type="text/css" href="../js/ext-3.1.0/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="../css/Ext.ux.form.LovCombo.css" />
<link rel="stylesheet" type="text/css" href="../css/file-upload.css"/>

<link rel="shortcut icon" href="../img/common/favicon.png" type="image/x-icon" />

<script type="text/javascript" src="../js/ext-3.1.0/ext-base.js"></script>
<script type="text/javascript" src="../js/ext-3.1.0/ext-all.js"></script>

<script type="text/javascript" src="../js/SearchField.js"></script>
<script type="text/javascript" src="../js/ext-3.1.0/ext-lang-es.js"></script>
<script type="text/javascript" src="../js/LovCombo.js"></script>
<script type="text/javascript" src="../js/index.js"></script>
<script type="text/javascript" src="../js/FileUploadField.js"></script>
<script type="text/javascript" src="../js/Select.js"></script>
<script type="text/javascript" src="../js/TreeCombo.js"></script>
<script type="text/javascript" src="../js/DisplayField.js"></script>
<script type="text/javascript" src="../js/rsh.js"></script>

<script type="text/javascript" src="../js/treegrid/TreeGridSorter.js"></script>
<script type="text/javascript" src="../js/treegrid/TreeGridColumnResizer.js"></script>
<script type="text/javascript" src="../js/treegrid/TreeGridNodeUI.js"></script>
<script type="text/javascript" src="../js/treegrid/TreeGridLoader.js"></script>
<script type="text/javascript" src="../js/treegrid/TreeGridColumns.js"></script>
<script type="text/javascript" src="../js/treegrid/TreeGrid.js"></script>
</head>
<body>
	<div id="ventana" style="display: none;"></div>
	<div class="x-hide-display" id="Headerx">
		<div id="header"> 
			<img style="float:left" src="../img/common/banner.png" width="100%" height="90">
		</div>
	</div>
	
	<iframe id="iframe" name="iframe" class="x-hidden"></iframe>
	
	<form id="dform" method="post" target="iframe" action="">
		<input type="text" name="query" class="x-hidden">
	</form>
	
	<script type="text/javascript">
	<aek:jsmin>
		var fechaServidor =  '<%=new java.util.Date()%>';//'<%=new java.text.SimpleDateFormat("MM-dd-yyyy").format(new java.util.Date())%>';
		
		var vistaActive = '../common/start.htm';
		window.dhtmlHistory.create({
	        toJSON: function(o) {
	                return Ext.encode(o);
	        }
	        , fromJSON: function(o) {
	                return Ext.decode(o,true);
	        }
		});

		Ext.Ajax.request({
			url: '../ecie/listarEcie.json',
			success: function (response, options) {
				var resp = Ext.decode(response.responseText);
				Ext.getCmp("fechaCombo").setValue(resp.data[0].fechaOperacion);
			},
			failure: failure
		});
		
		var yourListener = function(newLocation, historyData) {
			if(historyData){
				vistaActive = historyData.newLocation;
			}
		};
		
		function targetBlank (url) {
		  //blankWin = window.open(url,'externa','menubar=yes,toolbar=yes,location=yes,directories=yes,fullscreen=yes,titlebar=yes,hotkeys=yes,status=yes,scrollbars=yes,resizable=yes');
		   blankWin = window.open(url,'externa');
		}
		// 'externa' es como poner '_blank' pero cada vez que doy al botón me manda abrir en la misma ventana.
		Ext.onReady(function(){
			Ext.BLANK_IMAGE_URL = "../js/ext-3.1.0/resources/images/default/s.gif"
			Ext.QuickTips.init();
			
			dhtmlHistory.initialize();
			dhtmlHistory.addListener(yourListener);

			window.onload = function(){
				Ext.Ajax.request({
					url: '../common/checkOpenSecurity.htm'
				});
			};
			
			window.onunload = function(){
				Ext.Ajax.request({
					url: '../common/checkCloseSecurity.htm'
				});
			};
		
			var viewPort = new Ext.Viewport({
				layout: 'border',
				items:[
					new Ext.Panel({
						border: false,
						header: false,								
						region: 'north',						
						title : 'Sistema Gestor de Distribución de Toneles',
						height: '82',									
						monitorResize: true,
						contentEl: 'Headerx',
						bbar  : new Ext.Toolbar({
							autoWidth: false,
							cls: 'top-toolbar',
							items:[
								new Ext.Toolbar.Button({
						  			text: 'Principal',
						  			cls: 'x-btn-text-icon bmenu',
						  			icon: '../img/common/house.png',
						  			tooltip: 'Ir a la página de inicio',
						  			listeners:{
									  	click: function(){
									  		cargarVista('../common/start.htm');
									  //recargar o refrescar el treePanel 
									  	}
							  		}
								}),
						
								new Ext.Toolbar.Fill(),
								{xtype: 'tbspacer'},
								{xtype: 'tbseparator'},
								{xtype: 'tbspacer'},
								new Ext.Toolbar.Button({
									labelStyle: 'font-weight:bold',
						  			text: 'Fecha Operacional :',
						  			disabled: true
								}),	
								
								new Ext.form.TextField({
									id: 'fechaCombo',
									width: 65,
									disabled: true
								}),	
								
							{xtype: 'tbspacer'},
							{xtype: 'tbseparator'},
							{xtype: 'tbspacer'},
							
								new Ext.Toolbar.Button({
						  			text: 'Acerca de ...',
						  			cls : 'x-btn-text bmenu',
						  			tooltip: 'Acerca de',
						  			listeners:{
									  	click: function(){
									  		cargarVentana('../common/about.htm');
									  	}
							  		}
								}),
						
								new Ext.Toolbar.Spacer(),
								new Ext.Toolbar.Separator(),
								new Ext.Toolbar.Spacer(),
								new Ext.Toolbar.Button({
						  			icon: '../img/common/disconnect.png',
						  			text: 'Cerrar sesión',
						  			cls: 'x-btn-text-icon bmenu',
						  			tooltip: 'Cerrar la sección actual',
							  		listeners:{
							  			click: function(){
							  				confirmLogout();
							  			}			  			
							  		}
							    })
							]
						})						
					}),
					new Ext.Panel({
						title: 'Menú',
						region: 'west',
				        layout: 'border',
				        border: true,
				        width: 215,
				        collapsible: true,
						collapseMode: 'default',					        
					    margins: '2 2 2 2',
					    items:[
					    	new Ext.tree.TreePanel({
					    		loader: new Ext.tree.TreeLoader({
					    			url:'../common/menu.htm'
					    		}),
					    		rootVisible: false,
					  			id: 'treeMenu',
					  			region: 'center',
					  			animate: true,
					  			autoScroll: true,
					  			layout: 'fit',
					  			animCollapse: true,
					  			lines: false,
					  			useArrows: true,
					  			border: false,
					  			margins: '0 2 2 2',
								root: new Ext.tree.AsyncTreeNode({
							        text: 'Top Site',
							        draggable: false,
							        id: 'source'
								})
					    	})
					    ],
					    tools:[
					    {
				  			handler: function(event, toolEl, panel){Ext.getCmp('treeMenu').collapseAll()},
			  				id: 'minus'
					    },
					    {
				  			handler: function(event, toolEl, panel){Ext.getCmp('treeMenu').expandAll()},
			  				id: 'plus'
					    }
						]
					}),
					new Ext.Panel({
			  			id: 'workView',
			  			region: 'center',
						monitorResize: true,
			  			layout: 'fit',
			  			border: true,
			  			margins: '2 2 2 2'
			  		})			  							
				]
			})
		    
			Ext.getCmp('treeMenu').on('click', function(node){
				if (node.attributes.url){
					cargarVista(node.attributes.url);					
					window.dhtmlHistory.add(node.id,{path:node.getPath(),newLocation:node.attributes.url});
				}
			});
			
			Ext.Ajax.on('requestcomplete', function( oConn, oResp, oOpts ) {
				
				var oData;
				try{
					oData = Ext.decode(oResp.responseText);
					
					if ( Ext.type(oData) == 'object' && ! oData.success && Ext.type(oData.exceptionType) != false) {
						if ( oData.exceptionType == 'expired' ) {
							document.location.href='../common/login.htm';
						}else {
							if(oData.exceptionType == 'conexiondb'){
								Ext.MessageBox.show({
				                    title: 'Error',
				                    msg: 'Se ha perdido la conexión',
				                    buttons: Ext.MessageBox.OK,
				                    icon: Ext.MessageBox.ERROR
				                });
							}
							else{
								oResp.responseText = '{success: false}';
								if( oData.exceptionType == 'accessdenied' ){
						        	Ext.MessageBox.show({
					                    title: 'Error',
					                    msg: 'Acceso Denegado',
					                    buttons: Ext.MessageBox.OK,
					                    icon: Ext.MessageBox.ERROR
					                });
						        }
			        		}
						}
					}
				}catch(e){
					//document.location.href='../common/index.htm';
				}
			});
			
			var wv = Ext.getCmp("workView");
			
			cargarVista(vistaActive);
		});
	</aek:jsmin>
	</script>
</body>
</html>