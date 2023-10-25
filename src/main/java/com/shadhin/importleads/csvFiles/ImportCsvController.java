package com.shadhin.importleads.csvFiles;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/import-csv")
public class ImportCsvController {

    public final ImportCsvService importCsvService;

    public ImportCsvController(ImportCsvService importCsvService) {
        this.importCsvService = importCsvService;
    }

    //uploading csv file
    @PostMapping(consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    public ImportCsv uploadCsvFile(@RequestParam("file") MultipartFile file) {
        return importCsvService.uploadCsvFile(file);
    }

    @GetMapping("/{id}")
    public ImportCsv getImportCsv(@PathVariable Long id) {
        return importCsvService.getImportCsv(id);
    }
}
