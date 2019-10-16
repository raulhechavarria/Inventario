package org.inventario.ecie.dao;

import org.archetype.common.dao.ICommonDao;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.inventario.ecie.domain.Almacen;
import org.inventario.ecie.domain.Vale;
import org.inventario.ecie.domain.ValeProducto;

import java.io.Serializable;
import java.util.Date;


public interface IValeDao extends ICommonDao {
	//public BaseSearchResult listarValeDadoTipoVale(String tip, Date fecha);
	public Object ObtUltimoRegTarjeta(ValeProducto vp, Almacen almacen);
	public Object ObtUltimoVale();
	public BaseSearchResult listarFecha(String tip);
	public BaseSearchResult listarSolicitudesConfirmados(Date fecha);
	public Object obtenerSolicitudDadoNoControl(String nocontrol);
	public BaseSearchResult listarValeDadoTipoVale(String tip, Date dateIni, ExtGridRequest egr);
	public BaseSearchResult listarVale(Date fecha);
	public BaseSearchResult listarValeMesAnno(Date fecha);
	public Object obtenerReclamacionDadoNoControl(Vale vale);
	public Vale ObtenerNoDoc(String noDoc);
	public Vale ValeSinConfirmar();
	public BaseSearchResult listarSoliAnno(ExtGridRequest egr,String subCodigo);
	public BaseSearchResult listarEntreFecha(String tipo, Date dateIni,
			Date dateFin, ExtGridRequest egr);
	public BaseSearchResult listarValeDadoTipoVale(String string);
}

