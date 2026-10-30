package com.umuganda_ms.rw.domain;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity 
@Table(name = "activity_report")
public class ActivityReport {
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String workDone;
    private String remarks;
    @ManyToOne
    @JoinColumn(name = "activity_id") 
    private Activity activity;
    private int attendance;
    private LocalDate  submittedDate;
    public ActivityReport(){}
    
    public ActivityReport(String workDone, String remarks, Activity activity, int attendance) {
        this.workDone = workDone;
        this.remarks = remarks;
        this.activity = activity;
        this.attendance = attendance;
        
    }

    public UUID getId(){
           return id;
    }
    public String getWorkDone(){
        return workDone;
    }
    public void setWorkDone(String workDone) {
        this.workDone = workDone;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public Activity getActivity() {
        return activity;
    }
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public LocalDate getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDate submittedDate) {
        this.submittedDate = submittedDate;
    }
    
    

    


}
