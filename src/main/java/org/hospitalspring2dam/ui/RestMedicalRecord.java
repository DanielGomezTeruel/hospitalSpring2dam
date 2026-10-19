package org.hospitalspring2dam.ui;

import org.hospitalspring2dam.domain.model.MedRecordDTO;
import org.hospitalspring2dam.domain.service.MedRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class RestMedicalRecord {
    private final MedRecordService medicalRecordService;

    public RestMedicalRecord(MedRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }


    @GetMapping("/patients/{patientId}/medRecords")
    public List<MedRecordDTO> getMedicalRecords(@PathVariable int patientId) {
        return medicalRecordService.getAllbyPatientId(patientId);
    }

    @PostMapping("/patients/medRecords")
    public int addMedRecord(@RequestBody MedRecordDTO medicalRecordUI) {
        return medicalRecordService.add(medicalRecordUI);

    }

    @DeleteMapping("/patients/medRecords/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMedRecord(@PathVariable int id) {
        medicalRecordService.delete(id);
    }

    @PutMapping("/patients/medRecords")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMedRecord(@RequestBody MedRecordDTO medicalRecordUI) {
        medicalRecordService.update(medicalRecordUI);
    }

}
