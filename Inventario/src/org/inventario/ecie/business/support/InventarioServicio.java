package org.inventario.ecie.business.support;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.lang.Exception;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.record.Record;
import org.archetype.common.business.support.CommonService;
import org.archetype.common.domain.BaseSearchResult;
import org.inventario.ecie.business.IEcieServicio;
import org.inventario.ecie.business.IInventarioServicio;
import org.inventario.ecie.business.IValeServicio;
import org.inventario.ecie.dao.IInventarioDao;
import org.inventario.ecie.domain.Almacen;
import org.inventario.ecie.domain.CompraVentas;
import org.inventario.ecie.domain.Ecie;
import org.inventario.ecie.domain.Inventario;
import org.inventario.ecie.domain.Producto;
import org.inventario.ecie.domain.TipoVale;
import org.inventario.ecie.domain.Vale;
import org.inventario.ecie.web.utils.CalculoEco;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InventarioServicio extends CommonService implements
		IInventarioServicio {

	private IEcieServicio ecieServicio;
	private IValeServicio valeServicio;

	@SuppressWarnings("deprecation")
	public JSONObject informacion(String categoria, String mes)
			throws JSONException {
		Ecie ecie = getEcieServicio().obtenerEcie();
		Date date = new Date(ecie.getFechaOperacion().getTime());
		/*date.setYear(ecie.getFechaOperacion().getYear());
		date.setMonth(ecie.getFechaOperacion().getMonth());
		date.setDate(ecie.getFechaOperacion().getDate());*/
		if ((mes != null) && (mes != "")) {
			date.setDate(15);
			date.setMonth(java.lang.Integer.valueOf(mes) - 1);
		}
		if (categoria == "mes") {			
			return entradasSalidas(getValeServicio().listarValeMesAnno(date));
		} else {
			return entradasSalidas(getValeServicio().listarVale(date));
		}
	}

	@SuppressWarnings("deprecation")
	public BaseSearchResult ComprasVentas(String anno)throws JSONException {
		Collection<Object> listaResult = new LinkedList<Object>();
		Ecie ecie = getEcieServicio().obtenerEcie();
		Date date = new Date();
		if (anno == null) {
			date.setYear(ecie.getFechaOperacion().getYear());	
		} else {
			date.setYear(java.lang.Integer.valueOf(anno)-1900);
		}
		
		date.setDate(15);

		String meses[] = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
		for (int i = 0; i < 11; i++) {
			date.setMonth(i);
			BaseSearchResult bsrVales = ((IInventarioDao)getDao()).ValesMes(date);
			Collection<Object> lista = bsrVales.getResults();
			Double SumCompras = new Double(0.00);
			Double SumVentas  = new Double(0.00);
			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
				Vale vale = (Vale) iterator.next();
				if (CalculoEco.SiEntrada(vale)) {
					if (vale.getId()!= 15) {
						//esta comprobacion es para  saltar el vale de la carga inicial 
						SumCompras += vale.getImporteNeto(); ///aclarar aqui	
					}
					
				}else{
					SumVentas += vale.getImporteMLC() + vale.getImporteMN();
				}
			}
			if (SumCompras != 0.00) {
				CompraVentas compra = new CompraVentas(meses[i], SumCompras,SumVentas);
				listaResult.add(compra);	
			}
		}
		BaseSearchResult baseSearchResult = new BaseSearchResult();
		baseSearchResult.setResults(listaResult);
		baseSearchResult.setTotalCount((long)listaResult.size());
		return baseSearchResult;
	}

	public JSONObject entradasSalidas(BaseSearchResult bsrVales)
			throws JSONException {
		Collection<Object> lista = bsrVales.getResults();
		Double sumEntradasMNMLC = new Double(0.00);
		Double sumSalidasMNMLC = new Double(0.00);
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			Vale vale = (Vale) iterator.next();
			if (CalculoEco.SiEntrada(vale)) {
				sumEntradasMNMLC += vale.getImporteNeto();
			} else {
				sumSalidasMNMLC += vale.getImporteNeto();
			}
		}
		JSONObject json = new JSONObject();

		JSONArray data = new JSONArray();
		JSONObject minijson = new JSONObject();
		minijson.put("season", "Entradas");
		minijson.put("total", CalculoEco.redondeo2(sumEntradasMNMLC));
		data.put(minijson);
		JSONObject minijson1 = new JSONObject();
		minijson1.put("season", "Salidas");
		minijson1.put("total", CalculoEco.redondeo2(sumSalidasMNMLC));
		data.put(minijson1);
		json.put("data", data);
		return json;
	}

	public IValeServicio getValeServicio() {
		return valeServicio;
	}

	public void setValeServicio(IValeServicio valeServicio) {
		this.valeServicio = valeServicio;
	}

	@SuppressWarnings("deprecation")
	public BaseSearchResult listarInventario(String mes) {
		Date date = new Date();
		Ecie ecie = getEcieServicio().obtenerEcie();
		if (mes == null) {
			date = ecie.getFechaOperacion();
		} else {
			date.setMonth(java.lang.Integer.valueOf(mes) - 1);
			date.setYear(ecie.getFechaOperacion().getYear());// aqui se toma
			// como por
			// defecto el
			// anno de
			// operaciona
		}
		return ((IInventarioDao) getDao()).listarInventario(date);
	}

	public Inventario obtenerDadoFechaAlmacen(Date date, Serializable idalmacen) {
		return ((IInventarioDao) getDao()).obtenerDadoFechaAlmacen(date,
				idalmacen);
	}

	public BaseSearchResult listarMovimientoMes() {

		BaseSearchResult bsr = ((IInventarioDao) getDao())
				.listarMovimientoMes();
		Collection<Object> lista = bsr.getResults();
		Collection<Object> listaCollection = new ArrayList<Object>();
		listaCollection.add(lista.toArray()[0]);
		listaCollection.add(lista.toArray()[1]);
		listaCollection.add(lista.toArray()[2]);
		listaCollection.add(lista.toArray()[3]);
		listaCollection.add(lista.toArray()[4]);

		for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			// Integer minimo = listaCollection.add(lista.toArray()[0])
			for (Iterator<Object> iterator1 = listaCollection.iterator(); iterator1
					.hasNext();) {
				Object[] obj1 = (Object[]) iterator1.next();
				// obj1[0]
			}
			;

			// obj[0];
		}
		BaseSearchResult baseSearchResult = new BaseSearchResult();
		baseSearchResult.setResults(listaCollection);
		baseSearchResult.setTotalCount((long) listaCollection.size());
		return baseSearchResult;
	}

	public void adicionar_actualizar(Double sumInvFinalMLC,Double sumInvFinalMN, Almacen almacen) throws Exception {
		Inventario inventario = new Inventario();
		Ecie ecie = getEcieServicio().obtenerEcie();
		Inventario inventarioBD = obtenerDadoFechaAlmacen(ecie.getFechaOperacion(), almacen.getId());
		Inventario inventarioAnterior = null;
		Date fechaAnterior = null;
		for (int i = 1; i < 90; i++) {
			fechaAnterior = DateUtils.addDays(ecie.getFechaOperacion(), -i);
			inventarioAnterior = obtenerDadoFechaAlmacen(fechaAnterior, almacen.getId());
			if (inventarioAnterior != null) {
				break;
			}
		}
		if (inventarioAnterior == null) {
			throw new Exception("el almacen :" +almacen.getNombre() +" no tiene carga inicial");
		}
		if (inventarioBD != null) {
			inventarioBD.setInvFinalMLC(inventarioAnterior.getInvFinalMLC()
					+ sumInvFinalMLC);
			inventarioBD.setInvFinalMN(inventarioAnterior.getInvFinalMN()
					+ sumInvFinalMN);
		} else {
			inventario.setInvFinalMLC(inventarioAnterior.getInvFinalMLC()
					+ sumInvFinalMLC);
			inventario.setInvFinalMN(inventarioAnterior.getInvFinalMN()
					+ sumInvFinalMN);
			inventario.setAlmacen(almacen);
			inventario.setFecha(ecie.getFechaOperacion());
			inventarioBD = inventario;
		}
		getDao().adicionar_actualizar(inventarioBD);
	}

	public void setEcieServicio(IEcieServicio ecieServicio) {
		this.ecieServicio = ecieServicio;
	}

	public IEcieServicio getEcieServicio() {
		return ecieServicio;
	}

}
