package com.panda;


import com.panda.dao.StudentMapper;
import com.panda.domain.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LearnMybatisMain {

    private SqlSession session;

    @BeforeAll
    public void getSqlSession(){

        String resource = "mybatisConfig.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        session = sqlSessionFactory.openSession();
    }

    @AfterAll
    public void closeSqlSession(){
        session.close();
    }


    @Test
    public void queryAll() {
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        List<Student> students = studentMapper.queryAll();

        students.forEach(System.out::println);
    }

    @Test
    public void queryById() {

        Configuration configuration = session.getConfiguration();

        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        Student student = studentMapper.queryById(1);
        System.out.println(student);
    }
}
