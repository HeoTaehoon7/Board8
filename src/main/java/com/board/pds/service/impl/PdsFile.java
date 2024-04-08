package com.board.pds.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.board.pds.vo.FilesVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PdsFile {

	public static void save(
			HashMap<String, Object> map, 
			MultipartFile[] uploadFiles) {
      
	  String uploadPath = String.valueOf( map.get("uploadPath") );
	  System.out.println("uploadPath:" + uploadPath);
	  
	  List<FilesVo>  fileList      = new ArrayList<>(); 	
	  
	  for(MultipartFile uploadFile : uploadFiles){	
	     
         String originalName = uploadFile.getOriginalFilename();
         String fileName     = originalName.substring(originalName.lastIndexOf("//")+1);
         String fileExt      = originalName.substring( fileName.lastIndexOf('.') );
        
         log.info("fileName" + fileName);
	    
        //날짜 폴더 생성
        String folderPath = makeFolder(uploadPath);
        //UUID
        String uuid = UUID.randomUUID().toString();
        //저장할 파일 이름 중간에 "_"를 이용하여 구분
        String saveName = uploadPath + File.separator
        		        + folderPath + File.separator
        		        + uuid + "_" + fileName;
        String saveName2 = folderPath + File.separator
		        + uuid + "_" + fileName;
        
        Path savePath = Paths.get(saveName);
        //Paths.get() 메서드는 특정 경로의 파일 정보를 가져옵니다.(경로 정의하기)
        
        FilesVo  vo   = new FilesVo(0, 0, fileName, fileExt, saveName2);
		fileList.add( vo );
        
        try{
        	uploadFile.transferTo(savePath);            
        	System.out.println("saved");
        } catch (IOException e) {
             e.printStackTrace();
        }
        
        map.put("fileList", fileList );
        
      } //end for
    }

	private static  String makeFolder(String uploadPath) {
		      
      	String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);
             
        File uploadPathFolder = new File(uploadPath, folderPath);
                
        if(uploadPathFolder.exists() == false){
        	uploadPathFolder.mkdirs();
            //mkdir(): 디렉토리에 상위 디렉토리가 존재하지 않을경우에는 생성이 불가능한 함수
			//mkdirs(): 디렉토리의 상위 디렉토리가 존재하지 않을 경우에는 상위 디렉토리까지 모두 생성하는 함수
        }
       return folderPath;
	}

	public static void delete(
			String uploadPath, List<FilesVo> fileList) {
		
		String path       = uploadPath ;
		
		fileList.forEach( ( f ) -> {
			String sfile = f.getSfilename();
			File   file  = new File(path + sfile);
			if(file.exists())
				file.delete();
		});
		
	}
}


