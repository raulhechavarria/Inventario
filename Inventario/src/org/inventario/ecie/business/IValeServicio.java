package org.inventario.ecie.business;

import java.io.Serializable;
import java.util.Date;

import javax.jws.WebService;
import org.archetype.common.business.ICommonService;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.inventario.ecie.domain.ClasificadorProducto;
import org.inventario.ecie.domain.Ecie;
import org.inventario.ecie.domain.Vale;
import org.inventario.ecie.domain.ValeProducto;

@WebService
public interface IValeServicio extends ICommonService
{	
	public BaseSearchResult listarValeMesAnno(Date fecha);
	public BaseSearchResult listarVale(Date fecha);
	public BaseSearchResult listarValeDadoTipoVale(String tipo,String fechaIni, String fechaFin, ExtGridRequest egr);
	public BaseSearchResult listarValeProducto(Serializable idvale,String tip);
	public Object obtener(Serializable id);	
	public void pasarARecepcion(Serializable id);
	public void confirmar(Serializable id) throws Exception;
	public Vale obtenerUltimo();
	public Double comprobarImporteNeto(Vale vale);
	public void adicionar(Vale vale);
	public void actualizar(Vale vale);
	public String imprimir(Serializable id);
	public BaseSearchResult listarFecha(String tip);
	public ClasificadorProducto obtenerFamilia(Serializable idClasificadorProducto);
	public BaseSearchResult listarSolicitudesConfirmados();
	public Vale obtenerNoDoc(String noDoc);
	public Vale ValeSinConfirmar();
	public BaseSearchResult listarSoliAnno(ExtGridRequest egr,String subCodigo);
}
