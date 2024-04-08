package com.board.pds.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.board.domain.BoardVo;
import com.board.menus.domain.MenuVo;
import com.board.pds.vo.FilesVo;
import com.board.pds.vo.PdsVo;

@Mapper
public interface PdsMapper {

	List<PdsVo> getPdsList(MenuVo menuVo);

	void setWrite(HashMap<String, Object> map);

	PdsVo getView(HashMap<String, Object> map);

	List<FilesVo> getFileList(HashMap<String, Object> map);

	void setDelete(HashMap<String, Object> map);

	void deleteUploadFile(HashMap<String, Object> map);

	void setUpdate(HashMap<String, Object> map);

	void setReadcountUpdate(HashMap<String, Object> map);

	void setFileWrite(HashMap<String, Object> map);

}
