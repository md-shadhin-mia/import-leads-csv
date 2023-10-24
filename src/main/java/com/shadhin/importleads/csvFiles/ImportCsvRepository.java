package com.shadhin.importleads.csvFiles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportCsvRepository extends JpaRepository<ImportCsv, Long> {
}
