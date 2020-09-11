package se.callista.springboot.rest.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.callista.springboot.rest.api.v1.Hospital;
import se.callista.springboot.rest.domain.HospitalJPA;
import se.callista.springboot.rest.domain.HospitalRepository;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class HospitalServiceTest {

    @Autowired
    HospitalService hospitalService;

    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    HospitalMapper hospitalMapper;

    private static String NAME_ONE = "SU";
    private static String NAME_TWO = "SÄS";
    private static String ADDRESS_ONE = "Gbg";

    @BeforeEach
    public void emptyRepository() {
        hospitalRepository.deleteAll();
    }

    @Test
    public void insertOne_findAll() {
        HospitalJPA initHospital = insertOneHospital();

        List<Hospital> readHospitals = hospitalService.findAll();

        assertEquals(1, readHospitals.size());
        assertEquals(NAME_ONE, readHospitals.get(0).getName());
    }

    @Test
    public void insertOne_findOne() {
        HospitalJPA initHospital = insertOneHospital();

        Hospital readHospital = hospitalService.findOne(initHospital.getId());

        assertEquals(NAME_ONE, readHospital.getName());
    }


    @Test
    public void insertOne_deleteIt() {
        HospitalJPA initHospital = insertOneHospital();

        hospitalService.delete(initHospital.getId());

        Hospital readHospital = hospitalService.findOne(initHospital.getId());
        assertEquals(null, readHospital);
     }

    @Test
    public void insertOne_updateIt() {
        HospitalJPA initHospital = insertOneHospital();

        // Create updated hospital object
        Hospital hospitalToUpdate = hospitalMapper.toDTO(initHospital);

        hospitalToUpdate.setName(NAME_TWO);
        hospitalService.update(hospitalToUpdate);

        Hospital readHospital = hospitalService.findOne(initHospital.getId());

        assertEquals(NAME_TWO, readHospital.getName());
    }

    /**
     * Helper method
     * @return
     */
    private HospitalJPA insertOneHospital() {
        HospitalJPA hospital = new HospitalJPA(NAME_ONE, ADDRESS_ONE);
        return hospitalRepository.save(hospital);
    }

}
