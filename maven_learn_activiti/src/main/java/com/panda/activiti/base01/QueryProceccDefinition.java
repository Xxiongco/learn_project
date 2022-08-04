package com.panda.activiti.base01;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;

import java.util.List;

/**
 * 流程定义查询
 *
 * @author zrj
 * @date 2020/12/29
 * @since V1.0
 **/
public class QueryProceccDefinition {

    @Test
    public void queryProceccDefinition() {
        // 流程定义key
        String processDefinitionKey = "first-learn";
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 获取repositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 查询流程定义
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        //遍历查询结果
        List<ProcessDefinition> list = processDefinitionQuery
                .processDefinitionKey( processDefinitionKey )
                .orderByProcessDefinitionVersion().desc().list();

        for (ProcessDefinition processDefinition : list) {
            System.out.println( "------------------------" );
            System.out.println( " 流 程 部 署 id ： " + processDefinition.getDeploymentId() );
            System.out.println( "流程定义id： " + processDefinition.getId() );
            System.out.println( "流程定义名称： " + processDefinition.getName() );
            System.out.println( "流程定义key： " + processDefinition.getKey() );
            System.out.println( "流程定义版本： " + processDefinition.getVersion() );
        }
    }
}
