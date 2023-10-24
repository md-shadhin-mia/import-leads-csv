package com.shadhin.importleads.csvFiles;


import com.shadhin.importleads.customer.Customer;
import com.shadhin.importleads.customer.CustomerRepository;
import com.shadhin.importleads.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileParseService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private CustomerRepository customerRepository;
    @Async
    protected CompletableFuture<ImportCsv> saveImportCsv(ImportCsv importCsv) {
        try {
            Path CsvFile = fileStorageService.load(importCsv.getFilename());
            try(Stream<String> lines = Files.lines(CsvFile).skip(1)) {
                List<Customer> customers = lines.parallel().map(this::parseLine).toList();          //file parse parallel processing
                customerRepository.saveAll(customers);
            }
            importCsv.setStatus(ImportState.SUCCESS);
            //calculate duration
            Date createTime = importCsv.getCreatedAt();
            Date currentTime = new Date();
            importCsv.setDuration(currentTime.getTime()-createTime.getTime()+" ms" );
            fileStorageService.deleteFile(importCsv.getFilename());
        }catch (Exception e){
            importCsv.setStatus(ImportState.FAILURE);
        }
        return CompletableFuture.completedFuture(importCsv);
    }

    private Customer parseLine(String line) {
        String[] collumns = line.split(",");
        Customer customer = new Customer();
        customer.setId(Long.parseLong(collumns[0]));
        customer.setFirstName(collumns[1]);
        customer.setLastName(collumns[2]);
        customer.setEmail(collumns[3]);
        customer.setPhoneNumber(collumns[4]);
        customer.setGender(collumns[5]);
        customer.setIpAddress(collumns[6]);
        return customer;
    }


}