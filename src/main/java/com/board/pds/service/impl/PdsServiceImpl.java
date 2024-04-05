package com.board.pds.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.pds.dao.PdsDao;
import com.board.pds.service.PdsService;
import com.board.pds.vo.FilesVo;
import com.board.pds.vo.PdsVo;

import jakarta.servlet.http.HttpServletRequest;


@Service("pdsService")
public class PdsServiceImpl implements PdsService {

	@Autowired
	private   PdsDao  pdsDao;
	
	@Override
	public List<PdsVo> getPdsList(String menu_id) {
		
		List<PdsVo>  pdsList  =  pdsDao.getPdsList( menu_id );  
		
		return pdsList;
	}
	
	@Override
	public void setWrite(
			HashMap<String, Object> map, 
			HttpServletRequest request) {
        
		System.out.println("1:" + map);
		
		// 자료실 글쓰기 + 파일 저장
		// 1. 파일 저장 
		//  request  처리 - 넘어온 파일들을  d:\dev\data\ 에 저장
		PdsFile.save( map, request );  // map + fileList
		// 자바에서 파라미터가 객체일 경우 함수안에서 변경된 파라미터 인자는
		//  함수 종료후 돌아와도 변경된 값을 유지한다 - call by reference
		
		System.out.println("2:" + map);
				
		// 2. 자료실 글쓰기 
		// Board( <- map), Files( <- map 안의 fileList )
		pdsDao.setWrite( map );
		
	}

	@Override
	public PdsVo getView(HashMap<String, Object> map) {
		
		PdsVo   pdsVo  =  pdsDao.getView( map );
		
		return  pdsVo;
	}

	@Override
	public List<FilesVo> getFileList(HashMap<String, Object> map) {
		
		List<FilesVo>  fileList  =  pdsDao.getFileList( map );
		
		return   fileList;
	}

	@Override
	public void setDelete(HashMap<String, Object> map) {
		
		System.out.println("map1:" + map);

		pdsDao.setDelete( map ); // BOARD, FILES 의 IDX 번째 자료를 삭제
		
		System.out.println("map2:" + map);
		
		// idx 에 해당하는 파일 정보들
		List<FilesVo>  fileList  =  (List<FilesVo>) map.get("fileList"); 
		// IDX 에 해당 파일을 삭제
		PdsFile.delete( fileList );
		
	}

	// /deleteFile?file_num=12&sfile=flower_1.jpg
	@Override
	public void deleteUploadFile(HashMap<String, Object> map) {
		
		// Files 에서 삭제
		pdsDao.deleteUploadFile( map );
		
		// d:\dev\data\flower_1.jpg 삭제
		String  filepath   =  "d:\\dev\\data\\";
		String  sfilename  =  String.valueOf( map.get("sfile"));
		
		File    file       =  new File( filepath + sfilename ); 
		if ( file.exists() )
			file.delete();
		
	}

	@Override
	public void setUpdate(HashMap<String, Object> map, HttpServletRequest request) {
		
		// 1. request 넘어온 파일 저장
		PdsFile.save(map, request);
		
		// 2. db 에 정보를 저장(Board, Files)
		pdsDao.setUpdate( map );
		
	}

	@Override
	public void setReadcountUpdate(HashMap<String, Object> map) {
		
		pdsDao.setReadcountUpdate( map );
		
	}

}





