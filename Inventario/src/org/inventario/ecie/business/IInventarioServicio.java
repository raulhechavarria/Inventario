package org.inventario.ecie.business;

import java.io.Serializable;
import java.util.Date;

import javax.jws.WebService;
import org.archetype.common.business.ICommonService;
import org.archetype.common.domain.BaseSearchResult;
import org.inventario.ecie.domain.Almacen;
import org.inventario.ecie.domain.ClasificadorProducto;
import org.inventario.ecie.domain.Ecie;
import org.inventario.ecie.domain.Inventario;
import org.inventario.ecie.domain.Vale;
import org.inventario.ecie.domain.ValeProducto;
import org.json.JSONException;
import org.json.JSONObject;

@WebService
public interface IInventarioServicio extends ICommonService
{	
	public BaseSearchResult listarInventario(String fecha);
	public Inventario obtenerDadoFechaAlmacen(Date date, Serializable idalmacen);
	public void adicionar_actualizar(Double sumInvFinalMLC,Double sumInvFinalMN,Almacen almacen) throws Exception;
	//public JSONObject entradasSalidas() throws JSONException;
	public JSONObject informacion(String categoria, String mes) throws JSONException ;
	public BaseSearchResult listarMovimientoMes();
	public BaseSearchResult ComprasVentas(String anno)throws JSONException ;
}
