package com.lblog.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "l_user_visit_record")
public class UserVisitRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "visit_module")
    private Long visitModule;

    @Column(name = "visit_ip")
    private String visitIp;

    @Column(name = "visit_time")
    private Long visitTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVisitModule() {
        return visitModule;
    }

    public void setVisitModule(Long visitModule) {
        this.visitModule = visitModule;
    }

    public String getVisitIp() {
        return visitIp;
    }

    public void setVisitIp(String visitIp) {
        this.visitIp = visitIp;
    }

    public Long getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Long visitTime) {
        this.visitTime = visitTime;
    }
}
