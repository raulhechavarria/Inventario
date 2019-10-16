package org.inventario.ecie.business.support;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.time.DateUtils;
import org.archetype.common.business.ICommonService;
import org.archetype.common.business.support.CommonService;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.inventario.ecie.business.IEcieServicio;
import org.inventario.ecie.business.IInventarioServicio;
import org.inventario.ecie.business.ITarjeteroServicio;
import org.inventario.ecie.business.IValeServicio;
import org.inventario.ecie.dao.IEcieDao;
import org.inventario.ecie.domain.Almacen;
import org.inventario.ecie.domain.Ecie;
import org.inventario.ecie.domain.Inventario;
import org.inventario.ecie.domain.Tarjetero;
import org.inventario.ecie.domain.TipoVale;
import org.inventario.ecie.domain.Vale;
import org.inventario.ecie.web.utils.CalculoEco;

import com.sun.org.apache.bcel.internal.generic.IALOAD;

import java.io.Serializable;
import java.lang.Exception;

public class EcieServicio extends CommonService implements IEcieServicio {

	private IInventarioServicio inventarioServicio;
	private ITarjeteroServicio tarjeteroServicio;
	private IValeServicio valeServicio;
	private ICommonService almaceServicio;

	public ICommonService getAlmaceServicio() {
		return almaceServicio;
	}

	public void setAlmaceServicio(ICommonService almaceServicio) {
		this.almaceServicio = almaceServicio;
	}

	public IValeServicio getValeServicio() {
		return valeServicio;
	}

	public void setValeServicio(IValeServicio valeServicio) {
		this.valeServicio = valeServicio;
	}

	public IInventarioServicio getInventarioServicio() {
		return inventarioServicio;
	}

	public void setInventarioServicio(IInventarioServicio inventarioServicio) {
		this.inventarioServicio = inventarioServicio;
	}

	public ITarjeteroServicio getTarjeteroServicio() {
		return tarjeteroServicio;
	}

	public void setTarjeteroServicio(ITarjeteroServicio tarjeteroServicio) {
		this.tarjeteroServicio = tarjeteroServicio;
	}

	public void Inventariar() throws Exception {
		Ecie ecie = obtenerEcie();
		BaseSearchResult bsrVales = getValeServicio().listarVale(
				ecie.getFechaOperacion());
		BaseSearchResult bsrAlmacen = ((IEcieDao)getDao()).listarAlmacen();
		Collection<Object> lista = bsrVales.getResults();
		Collection<Object> listaAlmacen = bsrAlmacen.getResults();
		for (Iterator iterator = listaAlmacen.iterator(); iterator.hasNext();) {
			Almacen almacen = (Almacen) iterator.next();
			Double sumMN = new Double(0.00);
			Double sumMLC = new Double(0.00);
			for (Iterator iterator2 = lista.iterator(); iterator2.hasNext();) {
				Vale vale = (Vale) iterator2.next();
				if (!vale.getTipoVale().equals(TipoVale.Reclamacion)) {
					if (almacen == vale.getAlmacenDestino()) {
						sumMLC += vale.getTotalMLC();
						sumMN += vale.getTotalMN();
					}
					if (almacen == vale.getAlmacenOrigen()) {
						sumMLC -= vale.getTotalMLC();
						sumMN -= vale.getTotalMN();
					}
				}
				
			}
			if (!(sumMLC == 0)||!(sumMN == 0)) {
				getInventarioServicio()
				.adicionar_actualizar(sumMLC, sumMN, almacen);
			}		
		}

	}

	public Boolean ExisteFechaEnVale(Date date) {
		BaseSearchResult bsr = ((IEcieDao) getDao()).listarValeDadoFecha(date);
		if (bsr.getTotalCount() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean ExisteValeSinConfirmar() throws Exception {
		Ecie ecie = obtenerEcie();
		BaseSearchResult bsr = ((IEcieDao) getDao()).listarValeSinConfirmar(ecie.getFechaOperacion());
		if (bsr.getTotalCount() == 0) {
			return false;
		} else {
			return true;
		}

	}

	public BaseSearchResult listarCuadreDiarioImprimir(Date fecha) {
		BaseSearchResult bsr = ((IEcieDao) getDao()).listarValeDadoFecha(fecha);

		return bsr;
	}

	public Ecie obtenerEcie() {
		return (Ecie) getDao().obtener(Ecie.class, java.lang.Long.valueOf(1));
	}

	public Inventario ObtenerInvInicial(Date fecha, Serializable idAlmacen) {
		return (Inventario) ((IEcieDao) getDao()).ObtenerInventario(fecha,
				idAlmacen);
	}

}
