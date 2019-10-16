package org.inventario.ecie.domain;

public enum ConceptoAjuste {
	Mermas, //Mermas
	RoturasAlmacen, //Roturas en almacén
	MermasRoturasTransport, //Mermas y roturas transportación
	MermasRoturasDistrib, //Mermas y roturas Distribución
	MermasAveriasMuelle, //Mermas y averías Muelle
	MalEstado, // Mal estado 
	Vencimiento, //Vencimiento
	FaltantesReenvase, //Faltantes en reenvase
	SobrantesReenvase, // Sobrantes en Reenvase
	FaltanteConteoFisico, // Faltante en Conteo Físico
	SobranteConteoFisico, // Sobrante en Conteo Físico
	BajaUtensiliosHerramientas, // Baja Utensilios y Herramientas
	Otro, //se deja el concepto vacío para que ellos lo pongan en el vale

}
