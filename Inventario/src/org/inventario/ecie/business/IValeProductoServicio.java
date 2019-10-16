package org.inventario.ecie.business;

import java.io.Serializable;
import java.lang.Exception;
import javax.jws.WebService;

import org.archetype.common.business.ICommonService;
import org.archetype.common.domain.BaseSearchResult;
import org.inventario.ecie.domain.Ecie;
import org.inventario.ecie.domain.Vale;
import org.inventario.ecie.domain.ValeProducto;

@WebService
public interface IValeProductoServicio extends ICommonService
{	
	public BaseSearchResult listarValeProducto(Serializable idvale,String tip);
	public void eliminarValeProducto(ValeProducto valeProducto);
	public Object obtenerV(Serializable id);	
	public void adicionarValeProductoACiegas(ValeProducto valeProducto);
	public void adicionar(ValeProducto valeProducto)throws Exception;
	public void pasarARecepcion(Serializable id);
	public void confirmar(Serializable id);
	public void actualizar(ValeProducto valeproducto)throws Exception;
	public Float comprobarImporteNeto(Vale vale);
	public String imprimir(Serializable id);
	public  void eliminarVPDadoVale(Vale vale);
	public ValeProducto asignarValores(ValeProducto valeProducto);
	public BaseSearchResult listarValeProducto(Serializable idvale);
}
