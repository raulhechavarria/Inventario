package org.inventario.ecie.web.controllers;
 
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.archetype.common.web.controllers.CommonController;
import org.archetype.common.web.utils.ArcheTypeUtils;
import org.inventario.ecie.business.IEcieServicio;
import org.inventario.ecie.domain.Ecie;
import org.inventario.ecie.domain.Inventario;
import org.inventario.ecie.web.utils.MetComunes;
import org.json.JSONObject;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

public class EcieController extends CommonController {

	public void initialize(Object obj,HttpServletRequest request, BindException errors) throws Exception{
		String fecha = request.getParameter("fechaOperacion");
		Date date = java.sql.Date.valueOf(fecha);
		((Ecie)obj).setFechaOperacion(date);
	}

	@SuppressWarnings("deprecation")
	public ModelAndView guardarEcie(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		Serializable id = ArcheTypeUtils.getIdParameter(request, "id");
		Ecie ecie = (Ecie)getServicio().obtener(id);// las fecha esto no lo puedo coger de aqui sino del vale
		String fecha = request.getParameter("fechaOperacion");
		Date date = java.sql.Date.valueOf(fecha);
		BindException errors = new BindException(ecie,"command");		
		if(errors.hasErrors()){
			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors",errors.getFieldErrors());
			return mv;
		}				
		try {
			Boolean existeFecha = ((IEcieServicio)getServicio()).ExisteFechaEnVale(date);
				if ((ecie.getFechaOperacion().after(date))& (existeFecha)) {
				throw new Exception("No se puede introducir  una fecha anterior a la de las operaciones");
			}
			if (((IEcieServicio)getServicio()).ExisteValeSinConfirmar()) {
				throw new Exception("Aun hay Vales sin confirmar");
			}
			if (!ecie.getFechaOperacion().equals(date)) {
				((IEcieServicio)getServicio()).Inventariar();	
			}
		} catch (Exception ee) {
			String codes[]={ee.getMessage()};
			FieldError fieldError = new FieldError("Ecie","fecha",null,false,codes,null,ee.getMessage());
			errors.addError(fieldError);
	        ModelAndView mv = new ModelAndView("common/error");
	        mv.addObject("errors",errors.getFieldErrors());
	        return mv;
		}
		//aqui inventariomamos el dias
		return super.guardar(request, response);
	}
	

	public ModelAndView imprimirDetalles(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Ecie e = ((IEcieServicio)getServicio()).obtenerEcie();

		Set<Ecie> list = new HashSet<Ecie>();
		list.add(e);
		ModelAndView mv = new ModelAndView("EcieReport");
		
		mv.addObject("Logo",getLogo().getFile());
		mv.addObject("dataSource",list);
		response.setHeader("Content-Disposition", "attachment; filename=Detalles_Ecie.xls;");
		return mv;
	}
	}
