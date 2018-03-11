package com.prashanth.sunvalley.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class StudentIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object)
            throws HibernateException {

        String prefix = "SV";
        Connection connection = session.connection();

        try {
            Statement statement=connection.createStatement();

            ResultSet rs=statement.executeQuery("select count(student_id) as Id from student_id_keeper");

            if(rs.next())
            {
                int id=rs.getInt(1)+1001;
                return prefix + Integer.toString(id);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }
}