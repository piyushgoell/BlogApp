package com.piyushgoel.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.piyushgoel.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
 
		String fileName = UUID.randomUUID().toString().concat(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));	
		
		String filePath = path + File.separator + fileName;
		
		File f = new File(path);
		if(!f.exists()) f.mkdir();
		
		Files.copy(file.getInputStream(), Paths.get(filePath));
		return fileName;
	}

	@Override
	public InputStream reteriveImage(String path, String fileName) throws FileNotFoundException {		
		return new FileInputStream(path + File.separator + fileName);
	}

}
