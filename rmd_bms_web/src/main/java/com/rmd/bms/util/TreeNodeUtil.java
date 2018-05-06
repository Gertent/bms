package com.rmd.bms.util;

import java.util.ArrayList;
import java.util.List;

import com.rmd.bms.bean.JsonTreeData;

/**
 * @Description: 获取树节点集合
 * @author lc
 * @date 2017年2月22日
 */
public class TreeNodeUtil {
	
	/**
	 * @Description: 父节点
	 * @author lc
	 * @param treeDataList
	 * @return
	 * List<JsonTreeData>
	 */
	public final static List<JsonTreeData> getfatherNode(List<JsonTreeData> treeDataList) {
        List<JsonTreeData> newTreeDataList = new ArrayList<JsonTreeData>();
        for (JsonTreeData jsonTreeData : treeDataList) {
            if(jsonTreeData.getPid() == 0) {
                //获取父节点下的子节点
                jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId(),treeDataList));
                jsonTreeData.setState("closed");
                newTreeDataList.add(jsonTreeData);
            }
        }
        return newTreeDataList;
    }
	
	/**
	 * @Description: 子节点
	 * @author lc
	 * @param pid
	 * @param treeDataList
	 * @return
	 * List<JsonTreeData>
	 */
	private final static List<JsonTreeData> getChildrenNode(Integer pid , List<JsonTreeData> treeDataList) {
        List<JsonTreeData> newTreeDataList = new ArrayList<JsonTreeData>();
        for (JsonTreeData jsonTreeData : treeDataList) {
            if(jsonTreeData.getPid() == null)  continue;
            //这是一个子节点
            if(jsonTreeData.getPid().equals(pid)){
                //递归获取子节点下的子节点
                jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId() , treeDataList));
                newTreeDataList.add(jsonTreeData);
            }
        }
        return newTreeDataList;
    }
}
