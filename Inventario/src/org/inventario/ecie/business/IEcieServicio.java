package org.inventario.ecie.business;

import java.io.Serializable;
import java.util.Date;

import javax.jws.WebService;
import org.archetype.common.business.ICommonService;
import org.archetype.common.domain.BaseSearchResult;
import org.inventario.ecie.domain.Ecie;
import org.inventario.ecie.domain.Inventario;

@WebService
public interface IEcieServicio extends ICommonService
{	
	public boolean ExisteValeSinConfirmar() throws Exception ;
	public Boolean ExisteFechaEnVale(Date date);
	public BaseSearchResult listarCuadreDiarioImprimir(Date fecha);
	public Ecie obtenerEcie();
	public Inventario ObtenerInvInicial(Date fecha, Serializable idAlmacen);
	public void Inventariar() throws Exception;
}
