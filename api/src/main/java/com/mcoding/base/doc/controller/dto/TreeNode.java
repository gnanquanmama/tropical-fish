package com.mcoding.base.doc.controller.dto;

import com.mcoding.base.doc.EventNode;
import lombok.Data;

import java.util.List;


/**
 * @author wzt on 2020/4/5.
 * @version 1.0
 */
@Data
public class TreeNode {

    private EventNode eventNode;

    private List<TreeNode> childEventNode;

}
