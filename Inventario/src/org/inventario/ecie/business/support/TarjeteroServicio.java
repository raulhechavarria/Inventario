package org.inventario.ecie.business.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.archetype.common.business.support.CommonService;
import org.archetype.common.dao.ICommonDao;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.hibernate.Hibernate;
import org.inventario.ecie.business.IEcieServicio;
import org.inventario.ecie.business.ITarjeteroServicio;
import org.inventario.ecie.dao.ITarjeteroDao;
import org.inventario.ecie.domain.Almacen;
import org.inventario.ecie.domain.CentroCosto;
import org.inventario.ecie.domain.ClasificadorProducto;
import org.inventario.ecie.domain.Ecie;
import org.inventario.ecie.domain.Empresa;
import org.inventario.ecie.domain.Producto;
import org.inventario.ecie.domain.Responsable;
import org.inventario.ecie.domain.Tarjetero;
import org.inventario.ecie.domain.TipoVale;
import org.inventario.ecie.domain.Vale;
import org.inventario.ecie.domain.ValeProducto;
import org.inventario.ecie.web.utils.CalculoEco;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TarjeteroServicio extends CommonService implements
		ITarjeteroServicio {

	private ICommonDao daoAlmacen;
	private IEcieServicio ecieServicio;

	public ICommonDao getDaoAlmacen() {
		return daoAlmacen;
	}

	public void setDaoAlmacen(ICommonDao daoAlmacen) {
		this.daoAlmacen = daoAlmacen;
	}

	public void adicionarTarjetero(Object object) throws Exception {
		Tarjetero tarjetero = new Tarjetero();
		tarjetero = (Tarjetero) object;

		if (CalculoEco.SiEntrada(tarjetero.getValeProducto().getVale())) {
			entrada(tarjetero);
		} else {
			salida(tarjetero);
		}
		Ecie ecie = getEcieServicio().obtenerEcie();
		tarjetero.setFechaCambio(ecie.getFechaOperacion());

		getDao().adicionar(tarjetero);

	}

	@SuppressWarnings("unchecked")
	public BaseSearchResult listarExistencia(Serializable idclasificador,ExtGridRequest egr) {

		ClasificadorProducto Claproduct = (ClasificadorProducto) getDao()
				.obtener(ClasificadorProducto.class, idclasificador);
		BaseSearchResult bsr = ((ITarjeteroDao) getDao())
				.listarExistencia(Claproduct.getId(),egr);
		BaseSearchResult bsrResult = new BaseSearchResult();
		Collection<Object> lista = bsr.getResults();
		Set<Tarjetero> tarjetero = new HashSet<Tarjetero>();
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			Tarjetero object = (Tarjetero) iterator.next();
			if (object.getCantExist() != 0) {
				tarjetero.add(object);

			}
		}
		bsrResult.setResults(tarjetero);
		return bsr;
	}
 
	@SuppressWarnings("unchecked")
	public BaseSearchResult listarOciosos(Serializable idclasificador,ExtGridRequest egr) {

		ClasificadorProducto Claproduct = (ClasificadorProducto) getDao()
				.obtener(ClasificadorProducto.class, idclasificador);
		BaseSearchResult bsr = ((ITarjeteroDao) getDao())
				.listarOciosos(Claproduct.getId());
		BaseSearchResult bsrResult = new BaseSearchResult();
		Collection<Object> lista = bsr.getResults();
		Set<Tarjetero> tarjetero = new HashSet<Tarjetero>();
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			Tarjetero object = (Tarjetero) iterator.next();
			if (object.getCantExist() != 0) {
				tarjetero.add(object);

			}
		}
		bsrResult.setResults(tarjetero);
		return bsr;
	}	
	
	public BaseSearchResult listarTarjetero(Serializable idproducto,ExtGridRequest egr) {
		// BaseSearchResult bsr = getDao().listar(Tarjetero.class, egr);
		BaseSearchResult bsr = ((ITarjeteroDao) getDao())
				.listarTarjetero(idproducto,egr);
		Collection<Object> lista = bsr.getResults();

		for (Object object : lista) {
			Tarjetero tarjetero = (Tarjetero) object;

			Hibernate.initialize(tarjetero.getFechaCambio());
			Hibernate.initialize(tarjetero.getAlmacen());
			Hibernate.initialize(tarjetero.getProducto());
			Hibernate.initialize(tarjetero.getValeProducto());
		}

		return bsr;
	}

	public BaseSearchResult listarTarjetero(Date fecha) {
		// BaseSearchResult bsr = getDao().listar(Tarjetero.class, egr);
		BaseSearchResult bsr = ((ITarjeteroDao) getDao())
				.listarTarjetero(fecha);
		Collection<Object> lista = bsr.getResults();
		Collection<Tarjetero> tarjeterolist = new ArrayList<Tarjetero>();
		if (bsr.getTotalCount() > 0) {
			Tarjetero tarjetero = new Tarjetero();
			Long temp = new Long(-2133432);
			tarjetero.setId(temp);
			tarjetero.setProducto(((Tarjetero) lista.toArray()[0])
					.getProducto());
			for (Iterator<Object> iterator = lista.iterator(); iterator
					.hasNext();) {
				Tarjetero item = (Tarjetero) iterator.next();
				if ((item.getId() > tarjetero.getId())
						&& (item.getProducto() == tarjetero.getProducto())) {
					tarjetero = item;
				} else {
					tarjeterolist.add(tarjetero);
					tarjetero = item;
				}

			}
			tarjeterolist.add(tarjetero);
		}
		BaseSearchResult bsrResult = new BaseSearchResult();
		bsrResult.setResults(tarjeterolist);
		bsrResult.setTotalCount(new java.lang.Long(new Integer(tarjeterolist
				.size()).toString()));
		return bsrResult;
	}

	public void actualizarAlmacen(Serializable id) {
		Almacen almacen = (Almacen) getDao().obtener(Almacen.class, id);
		Boolean cargaInicial = false;
		almacen.setCargaInicial(cargaInicial);
	}

	public void addCargaInicial(Object object) {
		Vale vale = new Vale();
		vale = (Vale) object;

		Empresa empresa = (Empresa) getDao().obtener(Empresa.class,
				vale.getEmpresa().getId());
		vale.setEmpresa(empresa);
		Almacen almacenOrigen = (Almacen) getDao().obtener(Almacen.class,
				vale.getAlmacenOrigen().getId());
		vale.setAlmacenOrigen(almacenOrigen);
		Almacen almacenDestino = (Almacen) getDao().obtener(Almacen.class,
				vale.getAlmacenDestino().getId());
		vale.setAlmacenDestino(almacenDestino);
		Responsable responsAutoriza = (Responsable) getDao().obtener(
				Responsable.class, vale.getResponsAutoriza().getId());
		vale.setResponsAutoriza(responsAutoriza);
		Responsable solicElab = (Responsable) getDao().obtener(
				Responsable.class, vale.getSolicElab().getId());
		vale.setSolicElab(solicElab);
		CentroCosto centroCosto = (CentroCosto) getDao().obtener(Almacen.class,
				vale.getCentroCosto().getId());
		vale.setCentroCosto(centroCosto);

		getDao().adicionar(vale);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void PasarATarjetero(Serializable idVale) throws Exception {
		BaseSearchResult bsr = ((ITarjeteroDao) getDao()).listarValeProducto(idVale);
		if (bsr.getTotalCount() == 0) {
			throw new Exception("Este Vale no tiene productos");
		}
		Collection<Object> lista = bsr.getResults();

		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			ValeProducto vp = (ValeProducto) iterator.next();
			Tarjetero tarjetero = new Tarjetero();
			tarjetero.setValeProducto(vp);
			tarjetero.setProducto(vp.getProducto());
			if (tarjetero.getValeProducto().getVale().getTipoVale() == TipoVale.TransferenciaEntreAlmacenes) {
				this.adicionarTarjeteroTrans(tarjetero);
			} else {
				this.adicionarTarjetero(tarjetero);
			}

		}

	}

	public void adicionarTarjeteroTrans(Tarjetero obj) throws Exception {
		Tarjetero tarjetaEntrada = new Tarjetero(obj.getFechaCambio(), obj
				.getAlmacen(), obj.getProducto(), obj.getValeProducto(), obj
				.getCantEntradaVale(), obj.getCantSalidaVale(), obj
				.getCantExist(), obj.getPrecioMNExist(), obj
				.getPrecioMLCExist(), obj.getImpMNExist(), obj.getImpMLCExist());
		Tarjetero tarjetoSalida = new Tarjetero(obj.getFechaCambio(), obj
				.getAlmacen(), obj.getProducto(), obj.getValeProducto(), obj
				.getCantEntradaVale(), obj.getCantSalidaVale(), obj
				.getCantExist(), obj.getPrecioMNExist(), obj
				.getPrecioMLCExist(), obj.getImpMNExist(), obj.getImpMLCExist());

		entrada(tarjetaEntrada);
		salida(tarjetoSalida);
		// advertencia aqui se toma que simpre el unico registro Ecie su codigo
		// es 1
		Ecie ecie = getEcieServicio().obtenerEcie();
		tarjetaEntrada.setFechaCambio(ecie.getFechaOperacion());
		tarjetoSalida.setFechaCambio(ecie.getFechaOperacion());

		getDao().adicionar(tarjetaEntrada);
		getDao().adicionar(tarjetoSalida);
	}

	private Tarjetero salida(Tarjetero tarjetero) throws Exception {
		tarjetero.setAlmacen(tarjetero.getValeProducto().getVale()
				.getAlmacenOrigen());
		Tarjetero anterior = (Tarjetero) ((ITarjeteroDao) getDao())
				.ObtenerTarjetaAnterior(tarjetero);
		if (anterior.getId() == null) {
			throw new Exception(
					"No puede efectuar una salida porque en el almacen no existe el producto "
							+ tarjetero.getProducto().getNombre());
		}
		if (anterior.getCantExist() < tarjetero.getValeProducto()
				.getCantProdVale()) {
			throw new Exception(
					"No puede efectuar una salida porque la cantidad en existencia del producto "
							+ tarjetero.getProducto().getNombre()
							+ " es menor que la cantidad que se le quiere dar salida");
		}
		tarjetero.setCantSalidaVale(tarjetero.getValeProducto()
				.getCantProdVale());
		tarjetero.setCantEntradaVale(0.00);
		// cantidad
		Double cantExist = anterior.getCantExist()
				- tarjetero.getCantSalidaVale();
		tarjetero.setCantExist(cantExist);
		// importes
		if (tarjetero.getValeProducto().getVale().getTipoVale().equals(TipoVale.SalidaPorAjuste)) {
			if (tarjetero.getValeProducto().getImporteMLCVale() != 0.00) {
				tarjetero.setImpMLCExist(anterior.getImpMLCExist() - tarjetero.getValeProducto().getImporteMLCVale());
			}else{
				tarjetero.setImpMLCExist(anterior.getImpMLCExist());
			}
			if (tarjetero.getValeProducto().getImporteMNVale() != 0.00) {
				tarjetero.setImpMNExist(anterior.getImpMNExist() - tarjetero.getValeProducto().getImporteMNVale());
			}else{
				tarjetero.setImpMNExist(anterior.getImpMNExist());
			}
			
		} else {
			Double impMNExist = anterior.getPrecioMNExist() * cantExist;
			tarjetero.setImpMNExist(impMNExist);
			Double impMLCExist = anterior.getPrecioMLCExist() * cantExist;
			tarjetero.setImpMLCExist(impMLCExist);
			// precios
			
		}
		tarjetero.setPrecioMLCExist(tarjetero.getImpMLCExist()/tarjetero.getCantExist());
		tarjetero.setPrecioMNExist(tarjetero.getImpMNExist()/tarjetero.getCantExist());
		return tarjetero;
	}

	private Tarjetero entrada(Tarjetero tarjetero) {
		tarjetero.setAlmacen(tarjetero.getValeProducto().getVale()
				.getAlmacenDestino());
		Tarjetero anterior = (Tarjetero) ((ITarjeteroDao) getDao())
				.ObtenerTarjetaAnterior(tarjetero);
		if (anterior.getId() == null) {
			tarjetero.setCantExist(tarjetero.getValeProducto()
					.getCantProdVale());
			tarjetero.setPrecioMNExist(tarjetero.getValeProducto()
					.getPrecioMNVale());
			tarjetero.setImpMNExist(tarjetero.getValeProducto()
					.getImporteMNVale());
			tarjetero.setPrecioMLCExist(tarjetero.getValeProducto()
					.getPrecioMLCVale());
			tarjetero.setImpMLCExist(tarjetero.getValeProducto()
					.getImporteMLCVale());
		} else {
			// cantidad
			Double cantExist = anterior.getCantExist()
					+ tarjetero.getValeProducto().getCantProdVale();
			tarjetero.setCantExist(cantExist);
			// importes
			Double impMNExist = anterior.getImpMNExist()
					+ tarjetero.getValeProducto().getTotalMN();
			tarjetero.setImpMNExist(impMNExist);
			Double impMLCExist = anterior.getImpMLCExist()
					+ tarjetero.getValeProducto().getTotalMLC();
			tarjetero.setImpMLCExist(impMLCExist);
			// precios
			if ((tarjetero.getValeProducto().getPrecioMNVale() != anterior
					.getPrecioMNExist())) {
				Double precioMNExist = tarjetero.getImpMNExist()
						/ tarjetero.getCantExist();
				tarjetero.setPrecioMNExist(precioMNExist);
			} else {
				tarjetero.setPrecioMNExist(anterior.getPrecioMNExist());
			}
			if ((tarjetero.getValeProducto().getPrecioMLCVale() != anterior
					.getPrecioMLCExist())) {
				Double precioMLCExist = tarjetero.getImpMLCExist()
						/ tarjetero.getCantExist();
				tarjetero.setPrecioMLCExist(precioMLCExist);
			} else {
				tarjetero.setPrecioMLCExist(anterior.getPrecioMLCExist());
			}
		}
		tarjetero.setCantSalidaVale(0.00);
		tarjetero.setCantEntradaVale(tarjetero.getValeProducto()
				.getCantProdVale());
		return tarjetero;

	}

	public BaseSearchResult listarTarjeteroImprimir() {
		BaseSearchResult bsr = ((ITarjeteroDao) getDao())
				.listarTarjeteroImprimir();
		Collection<Object> lista = bsr.getResults();

		for (Object object : lista) {
			Tarjetero tarjetero = (Tarjetero) object;

			Hibernate.initialize(tarjetero.getFechaCambio());
			Hibernate.initialize(tarjetero.getAlmacen());
			Hibernate.initialize(tarjetero.getProducto());
			Hibernate.initialize(tarjetero.getValeProducto());
			// Hibernate.initialize(tarjetero.getVale().getId());

		}
		return bsr;
	}
	
	public BaseSearchResult listarTarjeteroAsc(Serializable idproducto) {
		BaseSearchResult bsr = ((ITarjeteroDao) getDao()).listarTarjetero(idproducto);
				
		Collection<Object> lista = bsr.getResults();

		for (Object object : lista) {
			Tarjetero tarjetero = (Tarjetero) object;

			Hibernate.initialize(tarjetero.getFechaCambio());
			Hibernate.initialize(tarjetero.getAlmacen());
			Hibernate.initialize(tarjetero.getProducto());
			Hibernate.initialize(tarjetero.getValeProducto());
			// Hibernate.initialize(tarjetero.getVale().getId());

		}
		return bsr;
	}

	public BaseSearchResult listarTarjeteroFecha(Serializable idproducto, 
			                                     Date fechaIni,
			                                     Date fechaFin) {
		BaseSearchResult bsr = ((ITarjeteroDao) getDao()).listarTarjeteroFecha(idproducto, fechaIni, fechaFin);
				
		Collection<Object> lista = bsr.getResults();

		for (Object object : lista) {
			Tarjetero tarjetero = (Tarjetero) object;

			Hibernate.initialize(tarjetero.getFechaCambio());
			Hibernate.initialize(tarjetero.getAlmacen());
			Hibernate.initialize(tarjetero.getProducto());
			Hibernate.initialize(tarjetero.getValeProducto());
			// Hibernate.initialize(tarjetero.getVale().getId());

		}
		return bsr;
	}
	
	public BaseSearchResult listarExistenciaImprimir() {
		return ((ITarjeteroDao) getDao()).listarTarjeteroExistenciaImprimir();
	}

	public ClasificadorProducto obtenerFamilia(
			Serializable idClasificadorProducto) {
		return (ClasificadorProducto) getDao().obtener(
				ClasificadorProducto.class, idClasificadorProducto);
	}

	public void setEcieServicio(IEcieServicio ecieServicio) {
		this.ecieServicio = ecieServicio;
	}

	public IEcieServicio getEcieServicio() {
		return ecieServicio;
	}
	public Tarjetero obtenerProductoTarjetero(Serializable id) {
		return (Tarjetero) getDao().obtener(Tarjetero.class, id);
	}
	
	public Producto obtenerProducto(Serializable id) {
		return (Producto) getDao().obtener(Producto.class, id);
	}
}
