package org.inventario.ecie.business.support;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import org.archetype.common.business.support.CommonService;
import org.archetype.common.domain.BaseSearchResult;
import org.hibernate.Hibernate;
import org.inventario.ecie.business.IValeProductoServicio;
import org.inventario.ecie.business.IValeServicio;
import org.inventario.ecie.dao.IValeDao;
import org.inventario.ecie.dao.IValeProductoDao;
import org.inventario.ecie.domain.Ecie;
import org.inventario.ecie.domain.EstadoVale;
import org.inventario.ecie.domain.Producto;
import org.inventario.ecie.domain.Tarjetero;
import org.inventario.ecie.domain.TipoVale;
import org.inventario.ecie.domain.ValeProducto;
import org.inventario.ecie.domain.Vale;
import org.inventario.ecie.web.utils.CalculoEco;

import java.lang.Exception;

public class ValeProductoServicio extends CommonService implements
		IValeProductoServicio {

	private IValeDao valeDao;

	public IValeDao getValeDao() {
		return valeDao;
	}

	public void setValeDao(IValeDao valeDao) {
		this.valeDao = valeDao;
	}

	public BaseSearchResult listarValeProducto(Serializable idvale) {
		BaseSearchResult bsr = new BaseSearchResult();
		bsr = ((IValeProductoDao) getDao()).listarValeProducto(idvale);
		return bsr;
	}

	public void eliminarVPDadoVale(Vale vale) {
		BaseSearchResult bsr = listarValeProducto(vale.getId(), vale
				.getTipoVale().toString());
		Collection<Object> lista = bsr.getResults();
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			ValeProducto object = (ValeProducto) iterator.next();
			eliminarValeProducto(object);
		}
	}

	public Object obtenerV(Serializable id) {
		Vale vale = (Vale) getDao().obtener(Vale.class, id);
		Hibernate.initialize(vale.getAlmacenDestino());
		Hibernate.initialize(vale.getAlmacenOrigen());
		Hibernate.initialize(vale.getCentroCosto());
		Hibernate.initialize(vale.getEmpresa());
		Hibernate.initialize(vale.getResponsAutoriza());
		Hibernate.initialize(vale.getRecibido());
		Hibernate.initialize(vale.getSolicElab());
		Hibernate.initialize(vale.getValeProducto());
		return vale;
	}

	public BaseSearchResult listarValeProducto(Serializable idvale, String tip) {
		BaseSearchResult bsr = ((IValeProductoDao) getDao())
				.listarValeProducto(idvale, tip);
		return bsr;
	}

	public void actualizar(ValeProducto valeProducto) throws Exception {
		Vale vale = (Vale) getDao().obtener(Vale.class,
				valeProducto.getVale().getId());
		valeProducto.setVale(vale);
		Producto p = (Producto) getDao().obtener(Producto.class,
				valeProducto.getProducto().getId());
		valeProducto.setProducto(p);
		if (CalculoEco.SiEntrada(valeProducto.getVale())) {
			entrada(valeProducto);
		} else {
			if (valeProducto.getVale().getTipoVale().equals(
					TipoVale.Reclamacion)) {
				reclamacion(valeProducto);
			} else {
				if (valeProducto.getVale().getTipoVale().equals(
						TipoVale.TransferenciaEntreAlmacenes)) {
					salida(valeProducto);
					entrada(valeProducto);
				} else {
					salida(valeProducto);
				}
			}
			
		}
		this.actualizarVale(vale, valeProducto);
		getDao().actualizar(valeProducto);

	}

	private void salida(ValeProducto valeProducto) throws Exception {
		Tarjetero tarAnt = (Tarjetero) ((IValeProductoDao) getDao())
				.ObtUltimoRegTarjeta(valeProducto, valeProducto.getVale()
						.getAlmacenOrigen());
		if (tarAnt.getId() == null) {
			throw new Exception("No exite el producto "
					+ valeProducto.getProducto().getNombre()
					+ " a sacar en el almacen");
		}
		if (valeProducto.getCantProdVale() > tarAnt.getCantExist()) {
			throw new Exception(
					"No puede efectuar una salida porque la cantidad en existencia del producto "
							+ tarAnt.getProducto().getNombre()
							+ " es menor que la cantidad que se le quiere dar salida");
		}
		valeProducto.setExistenciaOrigen(tarAnt.getCantExist()
				- valeProducto.getCantProdVale());
		if (valeProducto.getVale().getTipoVale()
				.equals(TipoVale.Factura_MN_MLC)
				|| valeProducto.getVale().getTipoVale().equals(
						TipoVale.FacturaMN)
				|| valeProducto.getVale().getTipoVale().equals(
						TipoVale.FacturaMLC)) {
			if (valeProducto.getVale().getRecargoDescuentoMLC() != null) {
				Double recargoMLC = (valeProducto.getCantProdVale()*tarAnt.getPrecioMLCExist()*valeProducto.getVale().getRecargoDescuentoMLC())/100;
				valeProducto.setRecargoDescuentoMLC(recargoMLC);
			}
			if (valeProducto.getVale().getRecargoDescuentoMN() != null) {
				Double recargoMN = (valeProducto.getCantProdVale()*tarAnt.getPrecioMNExist()*valeProducto.getVale().getRecargoDescuentoMN())/100;
				valeProducto.setRecargoDescuentoMN(recargoMN);			
			}
		} else {
			valeProducto.setRecargoDescuentoMLC(0.00);
			valeProducto.setRecargoDescuentoMN(0.00);			
		}
		if (valeProducto.getVale().getTipoVale().equals(TipoVale.SalidaPorAjuste)) {
			if (valeProducto.getCantProdVale() == 0.00) {
				valeProducto.setPrecioMLCVale(0.00);
				valeProducto.setPrecioMNVale(0.00);
			} else {
				valeProducto.setPrecioMLCVale(CalculoEco.calculoPrecio(valeProducto.getImporteMLCVale(), valeProducto.getCantProdVale()));
				valeProducto.setPrecioMNVale(CalculoEco.calculoPrecio(valeProducto.getImporteMNVale(), valeProducto.getCantProdVale()));
			}
			if (valeProducto.getImporteMLCVale() > tarAnt.getImpMLCExist()) {
				throw new Exception("Importe en moneda libremente convertible no valida");
			}
			if (valeProducto.getImporteMNVale() > tarAnt.getImpMNExist()) {
				throw new Exception("Importe en moneda nacional no valida");
			}
			
		} else {
			valeProducto.setPrecioMLCVale(tarAnt.getPrecioMLCExist());
			valeProducto.setPrecioMNVale(tarAnt.getPrecioMNExist());
			valeProducto.setImporteMLCVale(tarAnt.getPrecioMLCExist()* valeProducto.getCantProdVale());
			valeProducto.setImporteMNVale(tarAnt.getPrecioMNExist()* valeProducto.getCantProdVale());			
		}
		valeProducto.setTotalMLC(valeProducto.getImporteMLCVale());
		valeProducto.setTotalMN(valeProducto.getImporteMNVale());

	}
	
	private void reclamacion(ValeProducto valeProducto) throws Exception {	
		valeProducto.setTotalMLC(valeProducto.getImporteMLCVale());
		valeProducto.setTotalMN(valeProducto.getImporteMNVale());
	}

	private void entrada(ValeProducto valeProducto) throws Exception {
		Tarjetero tarAnt = (Tarjetero) ((IValeProductoDao) getDao())
				.ObtUltimoRegTarjeta(valeProducto, valeProducto.getVale()
						.getAlmacenDestino());
		if (valeProducto.getVale().getTipoVale().equals(TipoVale.Devolucion)) {
			CalculoEco.llenarValeProductoConCero(valeProducto);
			ValeProducto vPBD = (ValeProducto) ((IValeProductoDao) getDao())
					.obtener(ValeProducto.class, valeProducto.getId());
			valeProducto.setPrecioMLCVale(vPBD.getPrecioMLCVale());
			valeProducto.setPrecioMNVale(vPBD.getPrecioMNVale());
			valeProducto.setImporteMLCVale(valeProducto.getCantProdVale()
					* vPBD.getPrecioMLCVale());
			valeProducto.setImporteMNVale(valeProducto.getCantProdVale()
					* vPBD.getPrecioMNVale());
			valeProducto.setTotalMLC(valeProducto.getImporteMLCVale());
			valeProducto.setTotalMN(valeProducto.getImporteMNVale());
			if (tarAnt.getId() == null) {
				valeProducto.setExistenciaDestino(valeProducto
						.getCantProdVale());
			} else {
				valeProducto.setExistenciaDestino(tarAnt.getCantExist()
						+ valeProducto.getCantProdVale());
			}
		}
		
		if ((valeProducto.getVale().getTipoVale().equals(TipoVale.TransferenciaEntrada))
				|| (valeProducto.getVale().getTipoVale().equals(TipoVale.EntradaPorAjuste))
				|| (valeProducto.getVale().getTipoVale().equals(TipoVale.TransferenciaEntreAlmacenes))) {
			if (tarAnt.getId() == null) {
				valeProducto.setExistenciaDestino(valeProducto
						.getCantProdVale());
				if (valeProducto.getVale().getTipoVale().equals(TipoVale.EntradaPorAjuste)) {
					valeProducto.CalculoPrecios();	
				} 
			} else {
				valeProducto.setExistenciaDestino(tarAnt.getCantExist()
						+ valeProducto.getCantProdVale());
			}
			valeProducto.setRecargoDescuentoMLC(0.00);
			valeProducto.setRecargoDescuentoMN(0.00);
			valeProducto.CalculoTotalesConRecargo();
			if (!valeProducto.getVale().getTipoVale().equals(TipoVale.EntradaPorAjuste)) {
				valeProducto.CalculoPrecios();	
			} 		
		}
		if ((valeProducto.getVale().getTipoVale().equals(TipoVale.Recepcion)) ){
			if (tarAnt.getId() == null) {
				valeProducto.setExistenciaDestino(valeProducto
						.getCantProdVale());
			} else {
				valeProducto.setExistenciaDestino(tarAnt.getCantExist()
						+ valeProducto.getCantProdVale());
			}
			valeProducto.calculoRecargoDescuentoMLC();
			valeProducto.calculoRecargoDescuentoMN();
			valeProducto.CalculoTotalesConRecargo();
			valeProducto.CalculoPrecios();
		}

		if ((valeProducto.getVale().getTipoVale().equals(TipoVale.RecepcionACiegas)) ){
			CalculoEco.llenarValeProductoConCero(valeProducto);
		}
	}

	public void adicionar(ValeProducto valeProducto) throws Exception {
		Vale vale = (Vale) getDao().obtener(Vale.class,
				valeProducto.getVale().getId());
		valeProducto.setVale(vale);
		Producto p = (Producto) getDao().obtener(Producto.class,
				valeProducto.getProducto().getId());
		valeProducto.setProducto(p);
		if (CalculoEco.SiEntrada(valeProducto.getVale())) {
			entrada(valeProducto);
		} else {
			if (valeProducto.getVale().getTipoVale().equals(TipoVale.Reclamacion)) {
				reclamacion(valeProducto);
			} else {
			if (valeProducto.getVale().getTipoVale().equals(
					TipoVale.TransferenciaEntreAlmacenes)) {
				salida(valeProducto);
				entrada(valeProducto);
			} else {
				salida(valeProducto);
			}}
		}
		this.actualizarVale(vale, valeProducto);
		getDao().adicionar(valeProducto);

	}

	@SuppressWarnings("unchecked")
	private void actualizarVale(Vale vale, ValeProducto valepro)
			throws Exception {
		BaseSearchResult bsr = ((IValeProductoDao) getDao())
				.listarValeProducto(vale.getId(), vale.getTipoVale().toString());
		Double totalMN = new Double(0.00);
		Double totalMLC = new Double(0.00);
		Double importMN = new Double(0.00);
		Double importMLC = new Double(0.00);
		Collection<Object> lista = bsr.getResults();
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			ValeProducto vp = (ValeProducto) iterator.next();
			if ((vp.getProducto().getCodigo() == valepro.getProducto().getCodigo())&& (valepro.getId() == null)) {
				throw new Exception(
				"Producto duplicado");
			}
			if (!vp.getId().equals(valepro.getId())) {
				totalMN += vp.getTotalMN();
				totalMLC += vp.getTotalMLC();
				importMLC += vp.getImporteMLCVale();
				importMN += vp.getImporteMNVale();
			}
		}
		 Double tempTotal = CalculoEco.redondeo2(totalMLC + totalMN + valepro.getTotalMLC()	+ valepro.getTotalMN());
		if ((vale.getTipoVale().equals(TipoVale.Reclamacion))) {
			if (vale.getImporteNeto() < tempTotal) {
				throw new Exception(
						"Este producto se sobrepasa el importe neto");
			}
		} else{
		if ((CalculoEco.SiEntrada(vale))& (!vale.getTipoVale().equals(TipoVale.RecepcionACiegas))) {
			if (vale.getImporteNeto() < tempTotal) {
				throw new Exception(
						"Este producto se sobrepasa el importe neto");
			}
			Double tempcommlc = CalculoEco.redondeo2(valepro.getImporteMLCVale() + totalMLC);
			if (vale.getTotalMLC() < tempcommlc) {
				throw new Exception(
						"El importe del producto en Moneda Libremente convertible no puede ser mayor que el del vale");
			}
			Double tempcommn = CalculoEco.redondeo2(valepro.getImporteMNVale()+ totalMN);
			if (vale.getTotalMN() < tempcommn) {
				throw new Exception(
						"El importe del producto en Moneda Nacional no puede ser mayor que el del vale");
			}
		} else {
				vale.setTotalMLC(totalMLC + valepro.getTotalMLC());
				vale.setTotalMN(totalMN + valepro.getTotalMN());
				vale.setImporteMLC(importMLC + valepro.getImporteMLCVale());
				vale.setImporteMN(importMN + valepro.getImporteMNVale());
				vale.setImporteNeto(Double.valueOf(tempTotal));
				getDao().actualizar(vale);
		}}
	}

	public void eliminarValeProducto(ValeProducto valeProducto) {
		getDao().eliminar(valeProducto);

	}

	public void adicionarValeProductoACiegas(ValeProducto valeProducto) {
		Hibernate.initialize(valeProducto.getVale());
		Hibernate.initialize(valeProducto.getProducto());
		getDao().adicionar(valeProducto);
	}

	public void pasarARecepcion(Serializable id) {
		Vale vale = (Vale) getDao().obtener(Vale.class, id);
		if ((vale.getTipoVale() == TipoVale.RecepcionACiegas)
				&& (vale.getEstadoVale() == EstadoVale.Confirmado)) {
			vale.setTipoVale(TipoVale.Recepcion);
			getDao().actualizar(vale);
		}
	}

	public void confirmar(Serializable id) {
		Vale vale = (Vale) getDao().obtener(Vale.class, id);
		if (vale.getEstadoVale() == EstadoVale.Confirmado) {
			// throw new Exception("Este Vale no ya existe");
		}
		vale.setEstadoVale(EstadoVale.Confirmado);
		getDao().actualizar(vale);
	}

	public ValeProducto asignarValores(ValeProducto valeProducto) {
		ValeProducto resultValeProducto = new ValeProducto();
		resultValeProducto.setCantProdVale(valeProducto.getCantProdVale());
		resultValeProducto.setImporteMLCVale(valeProducto.getImporteMLCVale());
		resultValeProducto.setImporteMNVale(valeProducto.getImporteMNVale());
		resultValeProducto.setTotalMLC(valeProducto.getTotalMLC());
		resultValeProducto.setTotalMN(valeProducto.getTotalMN());
		resultValeProducto.setPrecioMLCVale(valeProducto.getPrecioMLCVale());
		resultValeProducto.setPrecioMNVale(valeProducto.getPrecioMNVale());
		resultValeProducto.setProducto(valeProducto.getProducto());
		return resultValeProducto;
	}

	@SuppressWarnings("unchecked")
	public Float comprobarImporteNeto(Vale vale) {
		Float Total = new Float(0);
		for (Iterator iterator = vale.getValeProducto().iterator(); iterator
				.hasNext();) {
			ValeProducto vp = (ValeProducto) iterator.next();
			Total += vp.getTotalMLC().floatValue()
					+ vp.getTotalMN().floatValue();

		}
		return Total;// confirma aqui si hay que redondear aqui
	}

	public String imprimir(Serializable id) {
		Ecie e = (Ecie) getDao().obtener(Ecie.class, id);
		return e.getReup() + "-" + e.getNombre();
	}
}
