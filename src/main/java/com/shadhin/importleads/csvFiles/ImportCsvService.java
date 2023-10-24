package com.shadhin.importleads.csvFiles;

import com.shadhin.importleads.storage.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImportCsvService {
    private final FileStorageService fileStorageService;
    private final ImportCsvRepository importCsvRepository;
    private final FileParseService fileParseService;

    public ImportCsvService(FileStorageService fileStorageService, ImportCsvRepository importCsvRepository, FileParseService fileParseService) {
        this.fileStorageService = fileStorageService;
        this.importCsvRepository = importCsvRepository;
        this.fileParseService = fileParseService;
    }

    public ImportCsv uploadCsvFile(MultipartFile file) {
        String filename = fileStorageService.store(file);

        ImportCsv importCsv = new ImportCsv();
        importCsv.setFilename(filename);
        importCsv.setStatus(ImportState.PROCESSING);
        //Todo : add to Csv Saving queue
        ImportCsv importCsvRes = importCsvRepository.save(importCsv);

        fileParseService.saveImportCsv(importCsv).thenAccept(importCsvRepository::save);
        return importCsvRes;
    }



    public ImportCsv getImportCsv(Long id) {
        return importCsvRepository.findById(id).orElseThrow();
    }
}
