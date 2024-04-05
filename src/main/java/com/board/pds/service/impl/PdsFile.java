package com.board.pds.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.board.pds.vo.FilesVo;

import jakarta.servlet.http.HttpServletRequest;

public class PdsFile {

	public static void save(
			HashMap<String, Object> map, 
			HttpServletRequest request) {
		
		// 자료실에 파일이 저장될 경로(디렉토리) 지정 없으면 생성
		String  filePath   =  "d:\\dev\\data\\";
		File    dir        =  new File(filePath);
		if( !dir.exists() ) {
			dir.mkdir();    // make directory   
		}
		
		// 넘어온 파일 저장( d:\\upload\\ ) 처리 -  중복파일 처리
		CheckFileName   checkFile = new CheckFileName();
		
		// 파일 저장
		MultipartHttpServletRequest  multipartHttpServletRequest
		  =  (MultipartHttpServletRequest) request;
				
		// write.jsp 보내준 여러파일의 이름을 추출
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
				
		MultipartFile  multipartFile = null;
		
		List<FilesVo>  fileList      = new ArrayList<>();
		
		String         fileName      = null;
		String         orgFileName   = null;
		String         fileExt       = null;
		String         sFileName     = null;   // 저장된 실제 파일명
		
		// 넘어온 파일을 한개씩 반복 처리한다
		while( iterator.hasNext() ) {
			multipartFile = multipartHttpServletRequest.getFile( iterator.next() );
			
			if( !multipartFile.isEmpty() ) {
				fileName     =  multipartFile.getOriginalFilename(); // 손.흥민.jpg
				orgFileName  =  fileName.substring(0, fileName.lastIndexOf('.'));   // 손.흥민  
				fileExt      =  fileName.substring( fileName.lastIndexOf('.') );   // .jpg  
				
				// 손.흥민.jpg 있으면   손.흥민_1.jpg 리턴
				// 중복파일이 존재하면  파일명을 변경하여 리턴
				sFileName    =  checkFile.getCheckFileName(
					filePath, orgFileName, fileExt	);   
				
				FilesVo  vo   = new FilesVo(0, 0, fileName, fileExt, sFileName);
				fileList.add( vo );
				
				//  파일 저장
				File     file = new File( filePath + sFileName ); 
				try {
					multipartFile.transferTo( file );     // 실제 파일 저장
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}  
				
			}  // if end
			
		}  // while end
		
		map.put("fileList", fileList );
		
	}

	public static void delete(List<FilesVo> fileList) {
		
		String path = "d:\\dev\\data\\";
		
		fileList.forEach( ( f ) -> {
			String sfile = f.getSfilename();
			File   file  = new File(path + sfile);
			if(file.exists())
				file.delete();
		});
		
	}

}














