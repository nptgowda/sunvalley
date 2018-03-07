package com.prashanth.sunvalley.repository;

import com.prashanth.sunvalley.domain.StudentIdKeeper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentIdKeeperRepository extends JpaRepository<StudentIdKeeper,String> {
}
