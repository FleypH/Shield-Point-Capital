package com.shieldpointcapital.lrms.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
@Table(name = "id_sequence")
public class IdSequence {

    @Id
    @Column(name = "seq_name", length = 50)
    private String seqName;

    @Column(name = "next_val", nullable = false)
    private Long nextVal;

    public String getSeqName() { return seqName; }
    public Long getNextVal() { return nextVal; }
    public void setNextVal(Long nextVal) { this.nextVal = nextVal; }
}