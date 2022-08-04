package com.panda.activiti.demo01;


import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class HelloWorld {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();


    /**
     * 部署流程定义
     * 部署的时候，会生成数据
     * 1. ACT_GE_BYTEARRAY 流程定义和流程资源
     * 2. ACT_RE_DEPLOYMENT 部署单元信息
     * 3. ACT_RE_PROCDEF 已部署的流程定义
     * 每部署一次，都会在以上表中插入数据
     *
     */
    @Test
    public void deploymentProcessDefinition() {
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name("helloworld入门程序")//添加部署的名称
                .addClasspathResource("bpmn/helloworld.bpmn20.xml")//从classpath的资源中加载，一次只能加载一个文件
                .deploy();//完成部署
        System.out.println("部署ID：" + deployment.getId());//1
        System.out.println("部署名称：" + deployment.getName());//helloworld入门程序
    }

    /**
     *  删除部署的文件
     */
    @Test
    public void deleteProcessDefinition() {
        String deploymentId = "1";
        processEngine.getRepositoryService().deleteDeployment(deploymentId);
    }


    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstance() {
        //流程定义的key
        String processDefinitionKey = "helloworld";
        ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        System.out.println("流程实例ID:" + pi.getId());//流程实例ID    101
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());//流程定义ID   helloworld:1:4
    }

    /**
     *  删除流程实例
     */
    @Test
    public void deleteProcessInstance() {

        String processInstanceId = "17501";
        processEngine.getRuntimeService().deleteProcessInstance(processInstanceId, "null");


    }


    /**
     * 查询当前人的个人任务
     */
    @Test
    public void findMyPersonalTask() {
        // 张三、李四、王五
        String assignee = "李四";
        List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                .taskAssignee(assignee)//指定个人任务查询，指定办理人
                .list();
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                System.out.println("任务ID:" + task.getId());
                System.out.println("任务名称:" + task.getName());
                System.out.println("任务的创建时间:" + task.getCreateTime());
                System.out.println("任务的办理人:" + task.getAssignee());
                System.out.println("流程实例ID：" + task.getProcessInstanceId());
                System.out.println("执行对象ID:" + task.getExecutionId());
                System.out.println("流程定义ID:" + task.getProcessDefinitionId());
                System.out.println("########################################################");
            }
        }
    }

    /**
     * 完成我的任务
     */
    @Test
    public void completeMyPersonalTask() {
        //任务ID
        String taskId = "17505";
        processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .complete(taskId);
        System.out.println("完成任务：任务ID：" + taskId);
    }

}