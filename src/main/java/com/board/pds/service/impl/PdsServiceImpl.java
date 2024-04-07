package com.board.pds.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.board.pds.mapper.PdsMapper;
import com.board.pds.service.PdsService;
import com.board.pds.vo.FilesVo;
import com.board.pds.vo.PdsVo;


@Service("pdsService")
public class PdsServiceImpl implements PdsService {
	
	//application.properties의 변수(import org.springframework.beans.factory.annotation.Value) 
	@Value("${part.upload.path}")
    private String uploadPath;

	@Autowired
	private   PdsMapper  pdsMapper;
	
	@Override
	public List<PdsVo> getPdsList(String menu_id) {
		
		List<PdsVo>  pdsList  =  pdsMapper.getPdsList( menu_id );  
		
		return pdsList;
	}
	
	@Override
	public void setWrite(
			HashMap<String, Object> map, 
			MultipartFile[] uploadfiles) {
        
		System.out.println("1:" + map);
		
		// 자료실 글쓰기 + 파일 저장
		// 1. 파일 저장 
		//  request  처리 - 넘어온 파일들을  d:\dev\data\ 에 저장
		PdsFile.save( map, uploadfiles );  // map + fileList
		// 자바에서 파라미터가 객체일 경우 함수안에서 변경된 파라미터 인자는
		//  함수 종료후 돌아와도 변경된 값을 유지한다 - call by reference
		
		System.out.println("2:" + map);
				
		// 2. 자료실 글쓰기 
		// Board( <- map), Files( <- map 안의 fileList )
		// FILES db 에 FILE 정보 저장
		/*
		2:{lvl=, nref=, bnum=0, step=, writer=aaa, title=aaa, cont=aaa, menu_id=MENU01,
		  fileList=[
			 FilesVo [file_num=0, idx=0, filename=history-flower.jpg, fileext=.jpg, sfilename=history-flower.jpg], 
			 FilesVo [file_num=0, idx=0, filename=색상영역.jpg, fileext=.jpg, sfilename=색상영역.jpg], 
			 FilesVo [file_num=0, idx=0, filename=Apple.jpg, fileext=.jpg, sfilename=Apple.jpg]
		  ]
	    }
	    */
		List<FilesVo>  fileList =  (List<FilesVo>) map.get("fileList");
		if( fileList.size() != 0 )
			pdsMapper.setFileWrite( map );	
		
		// Board 정보저장
		pdsMapper.setWrite( map );
		
	}

	@Override
	public PdsVo getView(HashMap<String, Object> map) {
		
		PdsVo   pdsVo  =  pdsMapper.getView( map );
		
		return  pdsVo;
	}

	@Override
	public List<FilesVo> getFileList(HashMap<String, Object> map) {
		
		List<FilesVo>  fileList  =  pdsMapper.getFileList( map );
		
		return   fileList;
	}

	@Override
	public void setDelete(HashMap<String, Object> map) {
		
		System.out.println("map1:" + map);

		pdsMapper.setDelete( map ); // BOARD, FILES 의 IDX 번째 자료를 삭제
		
		System.out.println("map2:" + map);
		
		// idx 에 해당하는 파일 정보들
		List<FilesVo>  fileList  =  (List<FilesVo>) map.get("fileList"); 
		// IDX 에 해당 파일을 삭제
		PdsFile.delete( fileList );
		
		// files db  삭제
		pdsMapper.deleteUploadFile(map);
		// board 게시글 삭제
		pdsMapper.setDelete(map);
		
	}

	@Override
	public void setUpdate(HashMap<String, Object> map, 
			MultipartFile[] uploadFiles) {
		
		// 1. request 넘어온 파일 저장
		List<FilesVo>  fileList =  (List<FilesVo>) map.get("fileList");
		if( fileList.size() != 0 )
			PdsFile.save(map, uploadFiles);
		
		// 2. db 에 정보를 저장(Board, Files)
		pdsMapper.setUpdate( map );
		
	}

	@Override
	public void setReadcountUpdate(HashMap<String, Object> map) {
		
		pdsMapper.setReadcountUpdate( map );
		
	}



}





