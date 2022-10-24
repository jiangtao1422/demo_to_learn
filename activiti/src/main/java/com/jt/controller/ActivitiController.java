package com.jt.controller;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jiangtao
 * @create 2022/9/10 10:22
 */
@RestController
@RequestMapping("activiti")
public class ActivitiController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;

    @RequestMapping("query")
    public void query() {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = query.orderByProcessDefinitionVersion().desc().list();
        ProcessDefinition processDefinition = query.orderByProcessDefinitionName().desc().singleResult();
        System.out.println(processDefinition.getDeploymentId());
        System.out.println(processDefinition.getId());
        System.out.println(processDefinition.getKey());
        System.out.println(processDefinition.getName());
        for (ProcessDefinition item :
                list) {
            System.out.println("========");
            System.out.println(item.getDeploymentId());
            System.out.println(item.getId());
            System.out.println(item.getKey());
            System.out.println(item.getName());
        }
    }

    @RequestMapping("queryPro")
    public void queryPro() {
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
        list.forEach(item -> {
            System.out.println(item.getBusinessKey());
            System.out.println(item.getDeploymentId());
            System.out.println(item.getStartTime());
            System.out.println(item.getStartUserId());
        });
    }
}
