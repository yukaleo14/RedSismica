package com.example.RedSismica.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RedSismica.Model.RevisionManual;

@Repository
public interface RevisionManualRepository extends JpaRepository<RevisionManual, Long> {

    
}
