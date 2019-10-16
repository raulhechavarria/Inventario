
/**********************	 scripts de index.jsp	**********************/
	
	Ext.grid.CheckColumn = function(config){
		Ext.apply(this, config);
		if(!this.id){
			this.id = Ext.id();
		}
		this.renderer = this.renderer.createDelegate(this);
	};
		
	Ext.grid.CheckColumn.prototype = {
		init : function(grid){
			this.grid = grid;
			this.grid.on('render', function(){
				var view = this.grid.getView();
				view.mainBody.on('mousedown', this.onMouseDown, this);
			}, this);
		},
		onMouseDown : function(e, t){
			if(t.className && t.className.indexOf('x-grid3-cc-'+this.id) != -1){
				e.stopEvent();
				var index = this.grid.getView().findRowIndex(t);
				var record = this.grid.store.getAt(index);
				record.set(this.dataIndex, !record.data[this.dataIndex]);
			}
		},
		renderer : function(v, p, record){
			p.css += ' x-grid3-check-col-td'; 
			return '<div class="x-grid3-check-col'+(v?'-on':'')+' x-grid3-cc-'+this.id+'">&#160;</div>';
		}
	};
	
	Ext.form.VTypes = function(){
	    var nombre = /^[a-zA-Z0-9_. ]+$/;
	     return {
	        'nombre' : function(v){
	            return nombre.test(v);
	        },
	        'nombreText' : 'Este campo solo puede contener letras, n&uacute;meros, espacios, . y _',
	        'nombreMask' : /[a-z0-9_. ]/i
	    };
	}();
	
	function filtrar(store, params){
		var base = {'limit': 20, 'start': 0};
		for(p in params){
			base[p] = params[p];
		}
		store.reload({
			params: base
		});
	};
	
	function confirmLogout(){
		
	    Ext.MessageBox.show({
	       	title:'Confirmaci&oacute;n',
           	msg: '&iquest;Desea cerrar la sesi&oacute;n<security:authentication property="principal.username"/>&#63;',
           	buttons: Ext.MessageBox.YESNO,
           	fn: function (btn){
           		if (btn == 'yes'){
	       	 		document.location.href='../common/logout.htm';
	       	 	}
		   	},
           	icon: Ext.MessageBox.QUESTION
       	});
	}
	
	function cargarVista(url, params, refresh){
		if(url){
			var wv = Ext.getCmp("workView");
			if (wv.getComponent(vistaActive)){
				if (vistaActive.id == url && refresh == false){					
					wv.getComponent(url).show();
					return;
				} else {	
					wv.remove(vistaActive, true);
					vistaActive = null;
				}
			}
			
			Ext.Ajax.request({
				url: url,
				params: params,
				success: function(response, options) {
					var obj = Ext.decode(response.responseText);
					if(obj.success == true){
						var vistaNueva = obj.view();
						vistaNueva.id = url;
						wv.add(vistaNueva);
						wv.syncSize();
						vistaActive = vistaNueva;	
					}else{
						failure(response, options);
					}
				},
			    failure: function() {
			        Ext.Msg.alert("ArcheType", "Ha ocurrido un error al cargar la vista");
			    }
			});
		}
	}

	function cargarTab(contenedor, nombreTab, url, params){
		if(url){
			if (contenedor.getComponent(nombreTab)){
				contenedor.remove(nombreTab, true);
			}
			
			Ext.Ajax.request({
				url: url,
				params: params,
				success: function(response, options) {
					var obj = Ext.decode(response.responseText);
					if(obj.success == true){
						var nuevoTab = obj.view();
						nuevoTab.id = nombreTab;
						contenedor.add(nuevoTab);
						contenedor.syncSize();
						nuevoTab.show();
					}else{
						failure(response, options);
					}
				},
			    failure: function() {
			        Ext.Msg.alert("ArcheType", "Ha ocurrido un error al cargar la vista");
			    }
			});
		}
	}
	
	function cargarVentana(url, params){
		if(url){
			Ext.Ajax.request({
				url: url,
				params: params,
				success: function(response, options) {
					var obj = Ext.decode(response.responseText);
					if(obj.success == true){
						var ventanaNueva = obj.view();
						ventanaNueva.show();
					}else{
						failure(response, options);
					}
				},
			    failure: function() {
			        Ext.Msg.alert("ArcheType", "Ha ocurrido un error al cargar la vista");
			    }
			});
		}
	}
	
	function failure(response, action) {
		var errors = null;
		if(Ext.isEmpty(response.responseText) == false){
			errors = Ext.decode(response.responseText).errors;
		} else {
			errors = Ext.decode(action.response.responseText).errors;
		}
		var msg = '';
		
		Ext.each(errors,function(error){
			msg+= error.msg+'<br>';
		})
		if(msg == ''){
			msg = 'Error al procesar los datos.';
		}
		
		showErrorMsg('Error', msg);
    }
	
	function modificar(gp, url){
		if(gp.selModel.hasSelection()){
			cargarVentana(url, {id: gp.selModel.getSelected().id});
		} else{
			showWarningMsg('ArcheType', 'Debe seleccionar un elemento a modificar');
		}
	}
	
	function detalles(gp, tp, url){
		if(gp.selModel.hasSelection()){
			cargarTab(tp, 'detalles',url, {id: gp.selModel.getSelected().id});
		}
		else{
			showWarningMsg('ArcheType', 'Debe seleccionar un elemento para ver sus Detalles');
		}
	}
	
	function eliminar(gp, url){
		if(gp.selModel.hasSelection()){
			Ext.MessageBox.show({
		       title:'Confirmaci&oacute;n',
	           msg: '&iquest;Desea eliminar el registro seleccionado?',
	           buttons: Ext.MessageBox.YESNO,
	           fn: function (btn){
			       	 if (btn == 'yes'){
			       	 	Ext.Ajax.request({
							url: url,
							params: {
								id: gp.selModel.getSelected().id
							},
							success: function (response, options) {
								if(Ext.decode(response.responseText).success == true){
									gp.getStore().reload();
								}else{
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
			showWarningMsg('ArcheType', 'Debe seleccionar un elemento a eliminar');
		}
	}
	
	function showErrorMsg(msgTitle, msgTxt){
		 Ext.MessageBox.show({  
		  title: msgTitle,  
		  msg: msgTxt,  
		  buttons: Ext.MessageBox.OK,  
		  icon: Ext.MessageBox.ERROR  
	     });	
      }
	
	function showInfoMsg(msgTitle, msgTxt){
		 Ext.MessageBox.show({  
		  title: msgTitle,  
		  msg: msgTxt,  
		  buttons: Ext.MessageBox.OK,  
		  icon: Ext.MessageBox.INFO  
	     });	
     }
	
	function showWarningMsg(msgTitle, msgTxt){
		 Ext.MessageBox.show({  
		  title: msgTitle,  
		  msg: msgTxt,  
		  buttons: Ext.MessageBox.OK,  
		  icon: Ext.MessageBox.WARNING   
	     });	
     }
	
	function descargar(url, params){
		var form = Ext.getDom('dform');
		form.query.value = Ext.encode(params);
		form.action = url;
		form.submit();
	}
	
	function confirmar(gp,urlparam,This){
		This.disable();
		 if(gp.selModel.hasSelection()){
			Ext.MessageBox.show({
   		       title:'Confirmación',
		           msg: 'Compruebe que la fecha de operacion este actualizada antes de confirmar.¿Desea Confirmar de todos modos?',
		           buttons: Ext.MessageBox.YESNO,
		           fn: function (btn){
				       	 if (btn == 'yes'){
				       	 	Ext.Ajax.request({
						url: urlparam,
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
						waitMsg : 'Confirmando datos...'
					});
						}	
				       },
		           icon: Ext.MessageBox.QUESTION
		       });
		       }
	else{
		Ext.MessageBox.show({  
			msg: 'Debe seleccionar un Registro .',  
			buttons: Ext.MessageBox.OK,  
			icon: Ext.MessageBox.ERROR  
		});
	}
	This.enable();
	}
