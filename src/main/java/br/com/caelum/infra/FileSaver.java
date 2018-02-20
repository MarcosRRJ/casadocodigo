package br.com.caelum.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

//essa anotation serve para identificar que essa class a gente que injetar
@Component
public class FileSaver {

	@Autowired
	private HttpServletRequest request;
	
	public String write(String baseFolder, MultipartFile file) {

		try {
			String realPath = request.getServletContext().getRealPath("/"+baseFolder);
			String path = realPath + "/" + file.getOriginalFilename();
			file.transferTo(new File(path));
			
			return baseFolder + "/" + file.getOriginalFilename();

		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e);
		}
		

	}
}
