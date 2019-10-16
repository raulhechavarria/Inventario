package org.archetype.common.web.utils;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import org.springframework.web.servlet.view.jasperreports.AbstractJasperReportsSingleFormatView;

public class JasperReportsJExcelApiView extends AbstractJasperReportsSingleFormatView {

	public JasperReportsJExcelApiView() {
		setContentType("application/vnd.ms-excel");
	}
	
	protected JRExporter createExporter() {
		return new JExcelApiExporter();
	}

	protected boolean useWriter() {
		return false;
	}


}
