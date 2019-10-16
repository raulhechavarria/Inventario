<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='aek' uri="http://www.aek.org/jsmin-tag" %>    
<aek:jsmin>
{
	success: true,
	view: function(){
		
		var panel = new Ext.Panel({
			frame: true,
			border: false,
			bodyStyle: 'padding:5px 5px 0',
			layout: 'fit',
			autoScroll:true,
			autoLoad:'../documents/<%=request.getParameter("page")%>.htm'
		});
		
		return panel;
	}
}
</aek:jsmin>