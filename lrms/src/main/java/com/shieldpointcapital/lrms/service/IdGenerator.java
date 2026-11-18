package com.shieldpointcapital.lrms.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.shieldpointcapital.lrms.repository.IdSequenceRepository;
import com.shieldpointcapital.lrms.domain.entity.IdSequence;

@Component
public class IdGenerator {

    private final IdSequenceRepository idSequenceRepository;

    public IdGenerator(IdSequenceRepository idSequenceRepository) {
        this.idSequenceRepository = idSequenceRepository;
    }

    @Transactional
    public String nextStaffId() {
        IdSequence seq = idSequenceRepository.findByNameForUpdate("STAFF_ID")
                .orElseThrow(() -> new IllegalStateException("STAFF_ID sequence is missing"));

        long value = seq.getNextVal();
        seq.setNextVal(value + 1);
        idSequenceRepository.save(seq);

        return String.format("STF-%04d", value);
    }
}
