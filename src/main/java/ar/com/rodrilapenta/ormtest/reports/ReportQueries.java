package ar.com.rodrilapenta.ormtest.reports;

import java.util.Map;

public class ReportQueries {

	public static String armarReporteFarmacia(Map<String, Object> params) {
		/*Integer idFarmacia = null, idProducto = null, trimestre = null;
		String fechaDesde = null, fechaHasta = null;
		
		if(params.get("farmacia") != null) {
			idFarmacia = ((FarmaciasDTO) params.get("farmacia")).getIdFarmacias();
		}
		
		if(params.get("producto") != null) {
			idProducto = ((ProductosDTO) params.get("producto")).getIdProductos();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		if(params.get("fechaDesde") != null) {
			fechaDesde = sdf.format(params.get("fechaDesde"));
		}
		
		if(params.get("fechaHasta") != null) {
			fechaHasta = sdf.format(params.get("fechaHasta"));
		}
		
		trimestre = (Integer) params.get("trimestre");
		
		
		String query = "SELECT * FROM InformesFarmacias i left join Productos p on p.idProductos = i.idProductos left join Farmacias f on f.idFarmacias = i.idFarmacias ";
		
		if(trimestre != -1) {
			query = query + " where i.InformesFarmaciasTrimestre = " + trimestre;
			
			if(idProducto != null) {
				query = query + " and i.idProductos = " + idProducto;
			}
			
			if(idFarmacia != null) {
				query = query + " and i.idFarmacias = " + idFarmacia;
			}
			
			if(fechaDesde != null && fechaHasta != null) {
				query = query + " and i.InformeFarmaciaFecha between '" + fechaDesde + "' and '" + fechaHasta + "' ";
			}
			else if(fechaHasta != null) {
				query = query + " and i.InformeFarmaciaFecha <= '" + fechaHasta + "' ";
			}
			else if(fechaDesde != null) {
				query = query + " and i.InformeFarmaciaFecha >= '" + fechaDesde + "' ";
			}
		}
		else {
			if(fechaDesde != null && fechaHasta != null) {
				query = query + " where i.InformeFarmaciaFecha between '" + fechaDesde + "' and '" + fechaHasta + "' ";
				
				if(idProducto != null) {
					query = query + " and i.idProductos = " + idProducto;
				}
				
				if(idFarmacia != null) {
					query = query + " and i.idFarmacias = " + idFarmacia;
				}

			}
			else if(fechaHasta != null) {
				query = query + " where i.InformeFarmaciaFecha <= '" + fechaHasta + "' ";
				
				if(idProducto != null) {
					query = query + " and i.idProductos = " + idProducto;
				}
				
				if(idFarmacia != null) {
					query = query + " and i.idFarmacias = " + idFarmacia;
				}

			}
			else if(fechaDesde != null){
				query = query + " where i.InformeFarmaciaFecha >= '" + fechaDesde + "' ";
				
				if(idProducto != null) {
					query = query + " and i.idProductos = " + idProducto;
				}
				
				if(idFarmacia != null) {
					query = query + " and i.idFarmacias = " + idFarmacia;
				}

			}
			else {
				if(idProducto != null) {
					query = query + " where i.idProductos = " + idProducto;
					
					if(idFarmacia != null) {
						query = query + " and i.idFarmacias = " + idFarmacia;
					}
				}
				else {
					if(idFarmacia != null) {
						query = query + " where i.idFarmacias = " + idFarmacia;
					}
				}
			}
		}*/
		return "";
	}
	
	public static String armarReporteFarmaciaObraSocial(Map<String, Object> params) {
		/*Integer idPlanFarmacia = null, idProducto = null, trimestre = null;
		String fechaDesde = null, fechaHasta = null;
		
		if(params.get("planFarmacia") != null) {
			idPlanFarmacia = ((PlanesFarmaciasDTO) params.get("planFarmacia")).getIdPlanesFarmacias();
		}
		
		if(params.get("producto") != null) {
			idProducto = ((ProductosDTO) params.get("producto")).getIdProductos();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		if(params.get("fechaDesde") != null) {
			fechaDesde = sdf.format(params.get("fechaDesde"));
		}
		
		if(params.get("fechaHasta") != null) {
			fechaHasta = sdf.format(params.get("fechaHasta"));
		}
		
		trimestre = (Integer) params.get("trimestre");
		
		
		String query = "select * from InformesVentasFarmaciasOS ifvo inner join PlanesFarmacias pf on pf.idPlanesFarmacias = ifvo.idPlanesFarmacias " + 
					   "inner join ObrasSociales os on os.idObrasSociales = ifvo.idObrasocial inner join Productos p on p.idProductos = ifvo.idProductos " +
					   "inner join Farmacias f on f.idFarmacias = pf.idFarmacias inner join Planes pl on pl.idPlanes = pf.idPlanes and pl.idObrasSociales = ifvo.idObraSocial";
		
		if(trimestre != -1) {
			query = query + " where ifvo.InformesVentasFarmaciasOSTrimestre = " + trimestre;
			
			if(idProducto != null) {
				query = query + " and ifvo.idProductos = " + idProducto;
			}
			
			if(idPlanFarmacia != null) {
				query = query + " and ifvo.idPlanesFarmacias = " + idPlanFarmacia;
			}
			
			if(fechaDesde != null && fechaHasta != null) {
				query = query + " and ifvo.InformesVentasFarmaciasOSFecha between '" + fechaDesde + "' and '" + fechaHasta + "' ";
			}
			else if(fechaHasta != null) {
				query = query + " and ifvo.InformesVentasFarmaciasOSFecha <= '" + fechaHasta + "' ";
			}
			else if(fechaDesde != null) {
				query = query + " and ifvo.InformesVentasFarmaciasOSFecha >= '" + fechaDesde + "' ";
			}
		}
		else {
			if(fechaDesde != null && fechaHasta != null) {
				query = query + " where ifvo.InformesVentasFarmaciasOSFecha between '" + fechaDesde + "' and '" + fechaHasta + "' ";
				
				if(idProducto != null) {
					query = query + " and ifvo.idProductos = " + idProducto;
				}
				
				if(idPlanFarmacia != null) {
					query = query + " and ifvo.idPlanesFarmacias = " + idPlanFarmacia;
				}

			}
			else if(fechaHasta != null) {
				query = query + " where ifvo.InformesVentasFarmaciasOSFecha <= '" + fechaHasta + "' ";
				
				if(idProducto != null) {
					query = query + " and ifvo.idProductos = " + idProducto;
				}
				
				if(idPlanFarmacia != null) {
					query = query + " and ifvo.idPlanesFarmacias = " + idPlanFarmacia;
				}

			}
			else if(fechaDesde != null){
				query = query + " where ifvo.InformesVentasFarmaciasOSFecha >= '" + fechaDesde + "' ";
				
				if(idProducto != null) {
					query = query + " and ifvo.idProductos = " + idProducto;
				}
				
				if(idPlanFarmacia != null) {
					query = query + " and ifvo.idPlanesFarmacias = " + idPlanFarmacia;
				}

			}
			else {
				if(idProducto != null) {
					query = query + " where ifvo.idProductos = " + idProducto;
					
					if(idPlanFarmacia != null) {
						query = query + " and ifvo.idPlanesFarmacias = " + idPlanFarmacia;
					}
				}
				else {
					if(idPlanFarmacia != null) {
						query = query + " where ifvo.idPlanesFarmacias = " + idPlanFarmacia;
					}
				}
			}
		}*/
		return "";
	}
}
