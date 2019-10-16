package org.archetype.common.web.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ModuleInstaller {

	private String module = "personas"; 
	private String root;
	
	public static void main(String[] args) {
		ModuleInstaller installer = new ModuleInstaller();
		try {
			if(args[0].equals("-root")){
				installer.setRoot(args[1]);
			} else
				throw new IOException("No se pudo instalar el modulo");
			
			if(args[2].equals("-module")){
				installer.setModule(args[3]);
			}else
				throw new IOException("No se pudo instalar el modulo");
			
			installer.createStructure();
			
		} catch (IOException e) {
			System.out.println("No se pudo instalar el modulo "+args[3]+" en la url "+args[1]);
			System.out.println();
			System.out.println("Ejemplo:");
			System.out.println("java org.archetype.common.web.utils.ModuleInstaller -root /home/user/workspace/Proyecto -module modulo");
		}
	}
	
	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	private void createStructure() throws IOException{
		String [] folders = {"business","config/mappings","dao","domain","web/controllers","web/validators"};
		String [] files = {"config/controller.xml","config/servlet.xml","config/dao.xml","config/business.xml"};
		
		for (int i = 0; i < folders.length; i++) {
			String path = root+"/"+module+"/"+folders[i];
			File file = new File(path);
			if(!file.mkdirs()){
				throw new IOException("No se pudo instalar el modulo");
			}
		}
		for (int i = 0; i < files.length; i++) {
			String path = root+"/"+module+"/"+files[i];
			File file = new File(path);
			file.createNewFile();
			StringBuffer buffer = new StringBuffer();
			buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			buffer.append("<beans xmlns=\"http://www.springframework.org/schema/beans\"\n");
			buffer.append("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
			buffer.append("	xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd\">\n\n");
			buffer.append("</beans>");
			
			FileUtils.writeStringToFile(file, buffer.toString());
		}
	}
}
