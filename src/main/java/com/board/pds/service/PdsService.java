package com.board.pds.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.board.pds.vo.FilesVo;
import com.board.pds.vo.PdsVo;

import jakarta.servlet.http.HttpServletRequest;

public interface PdsService {

	List<PdsVo> getPdsList(String menu_id);

	void setWrite(HashMap<String, Object> map, MultipartFile[] uploadFiles);

	PdsVo getView(HashMap<String, Object> map);

	List<FilesVo> getFileList(HashMap<String, Object> map);

	void setDelete(HashMap<String, Object> map);

	void setUpdate(HashMap<String, Object> map, MultipartFile[] uploadFiles);

	void setReadcountUpdate(HashMap<String, Object> map);

}
