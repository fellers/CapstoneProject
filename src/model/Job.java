/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static dao.DBClients.getAllClients;
import static dao.DBJobInvoice.getAllJobItems;
import static dao.DBUsers.getAllProjectManagers;
import static dao.DBUsers.getAllSalesmen;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author indya
 */
public class Job {
    private int jobID;
    private String jobName;
    private String progress;
    private String jobNotes;
    private Timestamp createDate;
    private Timestamp lastEdit;
    private String lastEditBy;
    private Date finalInstallDate;
    private String address;
    private String city;
    private Long zip;
    private int clientID;
    private String salesmanID;
    private String projectManagerID;
    private ObservableList<Product> associatedProducts = FXCollections.observableArrayList();

    public Job(int jobID, String jobName, String progress, String jobNotes, Timestamp createDate, Timestamp lastEdit, String lastEditBy, Date finalInstallDate, String address, String city, Long zip, int clientID, String salesmanID, String projectManagerID) {
        this.jobID = jobID;
        this.jobName = jobName;
        this.progress = progress;
        this.jobNotes = jobNotes;
        this.createDate = createDate;
        this.lastEdit = lastEdit;
        this.lastEditBy = lastEditBy;
        this.finalInstallDate = finalInstallDate;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.clientID = clientID;
        this.salesmanID = salesmanID;
        this.projectManagerID = projectManagerID;
    }
    
    public Job(String jobName, String progress, String jobNotes, Timestamp createDate, Timestamp lastEdit, String lastEditBy, Date finalInstallDate, String address, String city, Long zip, int clientID, String salesmanID, String projectManagerID) {
        this.jobName = jobName;
        this.progress = progress;
        this.jobNotes = jobNotes;
        this.createDate = createDate;
        this.lastEdit = lastEdit;
        this.lastEditBy = lastEditBy;
        this.finalInstallDate = finalInstallDate;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.clientID = clientID;
        this.salesmanID = salesmanID;
        this.projectManagerID = projectManagerID;
    }

    @Override
    public String toString() {
        return jobName;
    }
    
    

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public String getLastEditBy() {
        return lastEditBy;
    }

    public void setLastEditBy(String lastEditBy) {
        this.lastEditBy = lastEditBy;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    
    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getJobNotes() {
        return jobNotes;
    }

    public void setJobNotes(String jobNotes) {
        this.jobNotes = jobNotes;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(Timestamp lastEdit) {
        this.lastEdit = lastEdit;
    }

    public Date getFinalInstallDate() {
        return finalInstallDate;
    }

    public void setFinalInstallDate(Date finalInstallDate) {
        this.finalInstallDate = finalInstallDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getSalesmanID() {
        return salesmanID;
    }

    public void setSalesmanID(String salesmanID) {
        this.salesmanID = salesmanID;
    }

    public String getProjectManagerID() {
        return projectManagerID;
    }

    public void setProjectManagerID(String projectManagerID) {
        this.projectManagerID = projectManagerID;
    }

    public ObservableList<Product> getAssociatedProducts() {
        return associatedProducts;
    }

    public void setAssociatedProducts(ObservableList<Product> associatedProducts) {
        this.associatedProducts = associatedProducts;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getZip() {
        return zip;
    }

    public void setZip(Long zip) {
        this.zip = zip;
    }

    public static final LocalDate extractDate(Date d) {
        String date = d.toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date.substring(0, 10), formatter);
        return localDate;
    }
    
    public static String getSalesmanName(String id) {
        String salesmanName = null;
        for (Salesman s : getAllSalesmen()) {
            if (s.getSalesmanID().equals(id)) {
                salesmanName = s.getName();
            }
        }
        return salesmanName;
    }
    
    public static String getProjectManagerName(String id) {
        String pmName = null;
        for (ProjectManager p: getAllProjectManagers()) {
            if (p.getProjectManagerID().equals(id)) {
                pmName = p.getName();
            }
        }
        return pmName;
    }
    
    public static String getClientName(int id) {
        String cName = null;
        for (Client c: getAllClients()) {
            if (c.getClientID() == id) {
                cName = c.getFirstName() + " " + c.getLastName();
            }
        }
        return cName;
    }
    
    public static BigDecimal getTotalJobCost(int id) {
        BigDecimal totalCost = new BigDecimal(0);
        ObservableList<JobInvoice> jobItems = FXCollections.observableArrayList();
        
        for (JobInvoice ji : getAllJobItems()) {
            if (ji.getJobID() == id) {
                jobItems.add(ji);
            }
        }
        
        for(JobInvoice ji : jobItems) {
            BigDecimal cost = ji.getPrice().multiply(BigDecimal.valueOf(ji.getQuantity()));
            totalCost = totalCost.add(cost);
        }
        return totalCost.setScale(2);
    }
    
    
}
