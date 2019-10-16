<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
		 var ProductoStore = new Ext.data.JsonStore({
            url: '../${modulo}/listarProducto.json',
            autoLoad: true,
       	    remoteSort: true,
			baseParams: {limit:20},
  	        root: 'data',
    	    totalProperty: 'totalCount',
    	    id: 'id',
   	    	fields:['id','codigo','nombre','descripcion','unidad','ClasificadorProducto']
		});
		var datosForm = new Ext.form.FormPanel({
  			id:'datosForm',
  			layout:'form',
  			frame: true,
  			border:false,
  			monitorValid: true,
			bodyStyle:'padding:5px 5px 0',
			defaults: {width: 300},
			items:[			
				new Ext.form.DateField({
					width: 100,
					id: 'fecha1DateField',
					allowBlank: false,
					invalidClass : '',
					blankText:'Este campo es requerido',
					labelStyle: 'font-weight:bold',
					name: 'fecha1',
					format:'Y-m-d',
					value: '${command.fecha}',
					fieldLabel: 'Fecha Inicial',
					emptyText:'Seleccione fecha inicial...',
					autoComplete: 'off',
					editable: false
				}),
				new Ext.form.DateField({
					width: 100,
					id: 'fecha2DateField',
					allowBlank: false,
					invalidClass : '',
					blankText:'Este campo es requerido',
					labelStyle: 'font-weight:bold',
					name: 'fecha2',
					format:'Y-m-d',
					value: '${command.fecha }',
					fieldLabel: 'Fecha Final',
					emptyText:'Seleccione fecha final...',
					autoComplete: 'off',
					editable: false,
					listeners:{
						select: function(field, date){ //se le pasa la fecha que se seleccione y el field this
							if (!validarFecha(date)){
								field.reset();
							}
						}
					}
				})

			],

			buttons:[				
				new Ext.Button({
					text:'Aceptar',
					formBind: true,
					listeners:{
						click:function(){
							if(datosForm.getForm().isValid()){
								var params = {
	  									fechaI: Ext.getCmp('fecha1DateField').getRawValue(),
	  									fechaF: Ext.getCmp('fecha2DateField').getRawValue(),
	  									idproducto:${producto.id}
	  								};
							 		descargar('../ecie/rangoReporteTarjetero.htm', params);
							 		win.close();
								win.close();
	      	        		}else{
								showErrorMsg('Error','Introduzca bien los datos');
							}
						}
					}
				}),
				new Ext.Button({
					text:'Cancelar',
					type:'button',
					listeners:{
						click:function(){
					 		win.close();
	 					}			  			
					}
			   })
			]
		});

		function validarFecha(date){
			 var test = true;
			 datosForm = Ext.getCmp('datosForm'); 
			 var fechaInicio = datosForm.getForm().findField('fecha1DateField').getValue()
			 if (fechaInicio!= ''){
			 	if (date.getTime() < new Date(fechaInicio).getTime()){
		          	test = false;
			        alert('La fecha inicio no puede ser mayor que la fecha fin, verifique el rango');
			       return test
		          }
			 }
        	return test;
	    };
		
		var win = new Ext.Window({
			title: 'Reporte Tarjetero',
		    resizable: false,                
            closable: true,
            modal: true,                   
            constrain: true,
            width:450,
            items:[datosForm]
		});
		return win;			
	}
}
</aek:jsmin>