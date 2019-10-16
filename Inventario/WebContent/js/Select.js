/*
 * Ext JS Library 2.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

/**
 * @class Ext.form.SelectBox
 * @extends Ext.form.ComboBox
 * A SelectBox control with support for autocomplete, remote-loading, paging and many other features.
 * @constructor
 * Create a new SelectBox.
 * @param {Object} config Configuration options
 */
Ext.form.SelectBox = Ext.extend(Ext.form.ComboBox, {

	// private
    beforeBlur : function(){
        Ext.form.SelectBox.superclass.beforeBlur.call();
   	    if(this.el.dom.value.length == 0){
       	 this.clearValue();
	  	}
    }
});
Ext.reg('select', Ext.form.SelectBox);