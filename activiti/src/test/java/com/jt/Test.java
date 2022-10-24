package com.jt;

import com.jt.entity.Person;
import com.jt.utils.ActivitiUtil;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.*;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jiangtao
 * @create 2022/9/10 11:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;

    /**
     * 查询流程定义（查询所有的已部署流程）
     */
    @org.junit.Test
    public void query() {
        List<ProcessDefinition> list = repositoryService
                .createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion()
                .desc()
                .list();
        for (ProcessDefinition item :
                list) {
            System.out.println("========");
            System.out.println(item.getDeploymentId());
            System.out.println(item.getId());
            System.out.println(item.getKey());
            System.out.println(item.getName());
        }
    }

    /**
     * 查询流程实例
     */
    @org.junit.Test
    public void queryPro() {
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery()
//                .processInstanceId("05600760-32a7-11ed-a0e8-005056c00008")
                .processDefinitionKey("leaveDemo")
                .list();

        list.forEach(item -> {
            System.out.println("============");
            System.out.println(item.getBusinessKey());
            System.out.println(item.getDeploymentId());
            System.out.println(item.getStartTime());
            System.out.println(item.getProcessDefinitionKey());
            System.out.println(item.getName());
            System.out.println(item.getId());
            System.out.println(item.getProcessDefinitionId());
        });
    }

    /**
     * 启动流程实例
     */
    @org.junit.Test
    public void startProcess() {
        String processDefinitionKey = "candidateUser";
//        Map<String, Object> map = new HashMap<>();
//        map.put("depart", "张三");
//        map.put("user1", "李四1");
//        map.put("user2", "李四2");
//        Person person = Person.builder().name("测试人云").age(20).build();
//        map.put("person", person);

        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey(processDefinitionKey);
        if (processInstance != null) {
            System.out.println("=============");
            System.out.println(processInstance.getStartUserId());
            System.out.println(processInstance.getName());
            System.out.println(processInstance.getStartUserId());
            System.out.println(processInstance.getProcessDefinitionKey());
            System.out.println(processInstance.getId());
            System.out.println(processInstance.getProcessDefinitionId());
            System.out.println(processInstance.getProcessDefinitionKey());
        }

    }

    /**
     * 删除流程实例
     */
    @org.junit.Test
    public void deleteProcess() {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId("0e67372d-38d8-11ed-895f-005056c00008")
                .singleResult();
        if (processInstance != null) {
            runtimeService.deleteProcessInstance(processInstance.getId(), "测试");
            System.out.println("任务结束成功！");
        }
    }

    /**
     * 获取下一个节点
     */
    @org.junit.Test
    public void getNext() throws Exception {
        Task task = taskService.createTaskQuery().processInstanceId("651b9944-3432-11ed-8b62-005056c00008").singleResult();
//        FlowElement nextUserFlowElement = ActivitiUtil.getNextUserFlowElement(task);
//        if (nextUserFlowElement == null) {
//            System.out.println("结束节点");
//        } else if (nextUserFlowElement instanceof UserTask) {
//            System.out.println("userTask节点");
//            System.out.println(nextUserFlowElement.getName());
//            System.out.println();
//        } else if (nextUserFlowElement instanceof EndEvent) {
//            System.out.println("结束节点");
//        }

        System.out.println(task.getName());
        System.out.println(task.getId());
        System.out.println(task.getTaskDefinitionKey());
        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        List<EndEvent> flowElementsOfType = bpmnModel.getMainProcess().findFlowElementsOfType(EndEvent.class);
        EndEvent endEvent = flowElementsOfType.get(0);
        System.out.println(endEvent.getName());
        FlowElement flowElement = bpmnModel.getMainProcess().getFlowElement(task.getTaskDefinitionKey());
        SequenceFlow newSequenceFlow = new SequenceFlow();
        newSequenceFlow.setSourceFlowElement(flowElement);
    }

    /**
     * 结束任务
     */
    @org.junit.Test
    public void endTask() {
        String taskId = "643cb77f-38da-11ed-aebe-005056c00008";
        //  当前任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        List<EndEvent> endEventList = bpmnModel.getMainProcess().findFlowElementsOfType(EndEvent.class);

        FlowNode endFlowNode = endEventList.get(0);
        FlowNode currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(task.getTaskDefinitionKey());

        //  临时保存当前活动的原始方向
        List originalSequenceFlowList = new ArrayList<>();
        originalSequenceFlowList.addAll(currentFlowNode.getOutgoingFlows());
        //  清理活动方向
        currentFlowNode.getOutgoingFlows().clear();

        //  建立新方向
        SequenceFlow newSequenceFlow = new SequenceFlow();
        newSequenceFlow.setId("newSequenceFlowId");
        newSequenceFlow.setSourceFlowElement(currentFlowNode);
        newSequenceFlow.setTargetFlowElement(endFlowNode);
        List newSequenceFlowList = new ArrayList<>();
        newSequenceFlowList.add(newSequenceFlow);
        //  当前节点指向新的方向
        currentFlowNode.setOutgoingFlows(newSequenceFlowList);

        //  完成当前任务
        taskService.complete(task.getId());

        //  可以不用恢复原始方向，不影响其它的流程
//        currentFlowNode.setOutgoingFlows(originalSequenceFlowList);
    }

    /**
     * 根据流程key和用户查询任务
     */
    @org.junit.Test
    public void queryTask() {

        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("candidateUser")
//                .taskAssignee("张三")
//                .taskId("d9cc9362-3124-11ed-8715-005056c00008")
//                .processInstanceId("cca885a4-31a3-11ed-a478-005056c00008")
                .list();
        for (int i = 0; i < list.size(); i++) {
            Task task = list.get(i);
            System.out.println("===========");
            System.out.println(task.getProcessDefinitionId());
            System.out.println(task.getProcessInstanceId());
            System.out.println(task.getAssignee());
            System.out.println(task.getName());
            System.out.println(task.getId());
            System.out.println(task.getTaskDefinitionKey());
        }
    }

    /**
     * 根据用户查询任务
     */
    @org.junit.Test
    public void queryTaskByUser() {
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee("[测试人员1, 测试人员2]")
                .list();
        for (int i = 0; i < list.size(); i++) {
            Task task = list.get(i);
            System.out.println("=============");
            System.out.println(task.getProcessDefinitionId());
            System.out.println(task.getProcessInstanceId());
            System.out.println(task.getAssignee());
            System.out.println(task.getName());
            System.out.println(task.getId());
        }
    }

    /**
     * 根据候选人查询任务
     */
    @org.junit.Test
    public void candidateUser() {
        String candidateUser = "小四";
        List<Task> list = taskService.createTaskQuery()
                .taskCandidateUser(candidateUser)
                .list();
        for (int i = 0; i < list.size(); i++) {
            Task task = list.get(i);
            System.out.println("=============");
            System.out.println(task.getId());
            System.out.println(task.getAssignee());
            System.out.println(task.getName());
            System.out.println(task.getProcessInstanceId());
        }
    }

    /**
     * 领取任务
     */
    @org.junit.Test
    public void getTask() {
        String candidateUser = "小三";
        Task task = taskService.createTaskQuery()
                .taskCandidateUser(candidateUser)
                .singleResult();
        if (task != null) {
            taskService.claim(task.getId(), candidateUser);
            System.out.println(candidateUser + "申领到了任务！");
        }
    }

    /**
     * 放弃任务
     */
    @org.junit.Test
    public void rejectTask() {
        String candidateUser = "小三";
        Task task = taskService.createTaskQuery()
                .taskAssignee(candidateUser)
                .singleResult();
        if (task != null) {
            taskService.setAssignee(task.getId(), null);
            System.out.println(candidateUser + "放弃了任务！");
        }
    }

    /**
     * 完成个人任务
     */
    @org.junit.Test
    public void complete() throws Exception {

        String assignee = "张三";
        String processInstanceId = "3235bb46-3e3e-11ed-a6d9-005056c00008";
        String processDefinitionKey = "gateway1";
        List<Task> list = taskService.createTaskQuery()
//                .processDefinitionKey(processDefinitionKey)
//                .taskAssignee(assignee)
                .processInstanceId(processInstanceId)
                .list();
        HashMap<String, Object> map = new HashMap<>();
//        map.put("input", "1");
//        map.put("input", "0");
//        Task task1 = list.get(0);
        // setVariable
//        taskService.setVariableLocal("862e5029-31e4-11ed-ab1a-005056c00008", "num", "4");
        //        taskService.setVariable();
        // executionId 就当是processInstanceId
//        runtimeService.setVariableLocal("651b9944-3432-11ed-8b62-005056c00008", "input", "1");
//        runtimeService.setVariable();
//        taskService.complete(task1.getId());11
        for (int i = 0; i < list.size(); i++) {
            Task task = list.get(i);
//            taskService.setVariableLocal(task.getId(), "result", "同意");
//            taskService.setVariableLocal(task.getId(), "result", "拒绝");
//            taskService.addComment(task.getId(), processInstanceId, "同意了");
//            taskService.addComment(task.getId(), processInstanceId, "测试1内容");
//            taskService.addComment(task.getId(), processInstanceId, "测试2内容");
//            taskService.complete(task.getId());
            System.out.println("==========");
//            FlowElement element = ActivitiUtil.getNextUserFlowElement(task);
//            if (element instanceof UserTask) {
//                System.out.println("为userTask");
//            }

            System.out.println(task.getId());
            System.out.println(task.getName());
            System.out.println(task.getAssignee());
            System.out.println(task.getProcessDefinitionId());
            System.out.println(task.getProcessInstanceId());
            System.out.println(task.getTaskDefinitionKey());
            System.out.println(task.getExecutionId());
        }

    }

    /**
     * 根据businessKey获取任务
     */
    @org.junit.Test
    public void getTaskByBusinessKey() {
        String businessKey = "测试key";
        Task task = taskService.createTaskQuery()
                .processInstanceBusinessKey(businessKey)
                .singleResult();
        System.out.println(task.getId());
        System.out.println(task.getName());
        System.out.println(task.getAssignee());
    }


    /**
     * 流程是否结束  如果流程存在且endTime为null  则未结束，若endTime不为null  则结束了
     */
    @org.junit.Test
    public void select() {
        HistoricProcessInstance historicProcessInstance = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceId("8470a4ad-3e00-11ed-814c-005056c00008")
                .singleResult();
        if (historicProcessInstance != null) {
            if (historicProcessInstance.getEndTime() != null) {
                System.out.println("流程已结束！");
            } else {
                System.out.println("流程未结束！");
            }
        } else {
            System.out.println("流程不存在！");
        }
        System.out.println(historicProcessInstance.getId());
        System.out.println(historicProcessInstance.getEndTime());
        System.out.println(historicProcessInstance.getProcessDefinitionId());
        System.out.println(historicProcessInstance.getProcessDefinitionKey());
    }

    /**
     * 认领任务
     */
    @org.junit.Test
    public void claim() {
        List<Task> list = taskService.createTaskQuery()
                .taskCandidateUser("赵六")
                .list();
        Task task = list.get(0);
        if (task != null) {
            taskService.claim(task.getId(), "赵六");
        }
        System.out.println(task.getAssignee());
    }

    /**
     * 查询流程变量
     */
    @org.junit.Test
    public void queryVar() {
        Object num = taskService.getVariable("862e5029-31e4-11ed-ab1a-005056c00008", "num");
        Object num1 = taskService.getVariableLocal("c203bb22-335e-11ed-b8a6-005056c00008", "result");
        // c20038ae-335e-11ed-b8a6-005056c00008    c20086cf-335e-11ed-b8a6-005056c00008   ebe3f1ac-335e-11ed-918a-005056c00008
        Object user1 = runtimeService.getVariable("e012d470-3364-11ed-a28e-005056c00008", "test");
        Object user2 = runtimeService.getVariableLocal("d8c1137a-31bf-11ed-aee8-005056c00008", "user2");
//        System.out.println(num);
//        System.out.println(user2);
        System.out.println(user1);
//        System.out.println(num1);
    }

    /**
     * 历史审批过程
     */
    @org.junit.Test
    public void queryHistory() {
//        // 根据业务key查找processInstanceId  一定要保证businessKey是唯一  不然结果不唯一
//        historyService.createHistoricProcessInstanceQuery()
//                .processInstanceBusinessKey("")
//                .singleResult();

        // 根据taskId 查询processInstanceId
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
//                .taskId("b50b5961-399a-11ed-9b1f-005056c00008")
                .processInstanceId("8470a4ad-3e00-11ed-814c-005056c00008")
                .singleResult();
        // 历史节点
        List<HistoricActivityInstance> list = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(historicTaskInstance.getProcessInstanceId())
//                .orderByActivityId()
//                .desc()
                .orderByHistoricActivityInstanceEndTime()
                .asc()
//                .finished()
//                .unfinished()
                .list()
                .stream()
                .filter(item -> !StringUtils.containsAny(item.getActivityType(), "inclusiveGateway", "parallelGateway"))
                .collect(Collectors.toList());


        // 历史变量
        for (int i = 0; i < list.size(); i++) {
            System.out.println("========");
            HistoricActivityInstance historicActivityInstance = list.get(i);
//            System.out.println(historicActivityInstance.getActivityId());  // _2
            System.out.println(historicActivityInstance.getActivityName());
            String taskId = historicActivityInstance.getTaskId();
            System.out.println(taskId);
            String result = "";
            if (StringUtils.isNotEmpty(taskId)) {
                HistoricVariableInstance historicVariableInstance = historyService.createHistoricVariableInstanceQuery().taskId(taskId).variableName("result").singleResult();
                if (historicVariableInstance != null) {
                    result = (String) historicVariableInstance.getValue();
                }
                // 已经经过的节点不适用该方法查询变量
//                result = (String) taskService.getVariableLocal(taskId, "result");
            }
            List<Comment> taskComments = taskService.getTaskComments(taskId, "测试1");
            taskComments.forEach(item -> System.out.println(item.getType() + "==" + item.getFullMessage()));
            System.out.println(historicActivityInstance.getAssignee() + "----" + result);
            System.out.println(historicActivityInstance.getStartTime());
            System.out.println(historicActivityInstance.getEndTime());
//            System.out.println("ExecutionId:" + historicActivityInstance.getExecutionId());
//            System.out.println("ProcessInstanceId:" + historicActivityInstance.getProcessInstanceId());
//            System.out.println(historicActivityInstance.getActivityType()); // userTask
        }
    }

    /**
     * 历史流程实例
     */
    @org.junit.Test
    public void queryHistory1() {
        ProcessInstanceHistoryLog historicProcessInstance = historyService
                .createProcessInstanceHistoryLogQuery("bd13ee23-3502-11ed-b74e-005056c00008")
                .singleResult();
        System.out.println("========");
        System.out.println(historicProcessInstance.getId());
        System.out.println(historicProcessInstance.getEndTime()); // userTask
        System.out.println(historicProcessInstance.getStartTime());
//        new User()
        AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
    }

    /**
     * 查询一个流程中所有execution
     */
    @org.junit.Test
    public void allExecution() {
        String processInstanceId = "";
        List<Execution> list = runtimeService
                .createExecutionQuery()
                .processInstanceId("723cff39-3807-11ed-9b8e-005056c00008")
                .list();
        for (int i = 0; i < list.size(); i++) {
            Execution execution = list.get(i);
            System.out.println(execution.getId());
        }

    }

    @org.junit.Test
    public void process() {
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery()
//                .processInstanceId("05600760-32a7-11ed-a0e8-005056c00008")
                .processDefinitionKey("leaveDemo")
                .list();
        ProcessInstance processInstance = list.get(0);
        //获取流程模型
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        if (null == bpmnModel) {
            throw new RuntimeException("撤回失败，失败原因：未查到流程信息！");
        }
        List<Process> processes = bpmnModel.getProcesses();

        for (int i = 0; i < processes.size(); i++) {
            Process process = processes.get(i);
            Collection<FlowElement> flowElements = process.getFlowElements();
            for (int j = 0; j < flowElements.size(); j++) {
                System.out.println("=============");
                for (Iterator<FlowElement> iterator = flowElements.iterator(); iterator.hasNext(); ) {
                    FlowElement next = iterator.next();
                    System.out.println(next.getName());
                }

            }
        }
    }
}

