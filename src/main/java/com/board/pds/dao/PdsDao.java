package com.board.pds.dao;

import java.util.HashMap;
import java.util.List;

import com.board.pds.vo.FilesVo;
import com.board.pds.vo.PdsVo;

public interface PdsDao {

	List<PdsVo> getPdsList(String menu_id);

	void setWrite(HashMap<String, Object> map);

	PdsVo getView(HashMap<String, Object> map);

	List<FilesVo> getFileList(HashMap<String, Object> map);

	void setDelete(HashMap<String, Object> map);

	void deleteUploadFile(HashMap<String, Object> map);

	void setUpdate(HashMap<String, Object> map);

	void setReadcountUpdate(HashMap<String, Object> map);

}
