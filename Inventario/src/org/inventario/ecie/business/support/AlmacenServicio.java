package org.inventario.ecie.business.support;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.time.DateUtils;
import org.archetype.common.business.ICommonService;
import org.archetype.common.business.support.CommonService;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.inventario.ecie.business.IAlmacenServicio;
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

public class AlmacenServicio extends CommonService implements IAlmacenServicio {

	private IInventarioServicio inventarioServicio;
	private IEcieServicio ecieServicio;

	public IEcieServicio getEcieServicio() {
		return ecieServicio;
	}

	public void setEcieServicio(IEcieServicio ecieServicio) {
		this.ecieServicio = ecieServicio;
	}

	public IInventarioServicio getInventarioServicio() {
		return inventarioServicio;
	}

	public void setInventarioServicio(IInventarioServicio inventarioServicio) {
		this.inventarioServicio = inventarioServicio;
	}

	@Override
	public void adicionar(Object object) {
		
		super.adicionar(object);
		Almacen almacen = (Almacen) object;
		Ecie ecie = ecieServicio.obtenerEcie();
		Inventario inventario = new Inventario();
		inventario.setAlmacen(almacen);
		Long long1 = ecie.getFechaOperacion().getTime() - 86400000;
		Date fecha = new Date(long1);
		inventario.setFecha(fecha);
		inventario.setInvFinalMLC(0.00);
		inventario.setInvFinalMN(0.00);
		inventarioServicio.adicionar(inventario);
		}
}
