package com.zt.repository;

import com.zt.entity.Student;

public interface StudentRepository {
    public Student findById(long id);
}
