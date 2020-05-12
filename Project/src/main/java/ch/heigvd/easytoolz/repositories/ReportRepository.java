package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Report;
import ch.heigvd.easytoolz.models.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report,String>, JpaSpecificationExecutor<Report>{
    List<Report> findReportByReportType_OrderByEZObject(@NotNull ReportType reportType);
    List<Report> findReportByEZObject_Owner_UserName(String reporter);
    List<Report> findReportByReporter_UserName(String reported);
    List<Report> findReportByValidTrue();



    /*
    @Query("SELECT ezobject.id, report.type" +
            " FROM ezobject" +
            " INNER JOIN report ON report.ezobject = ezobject.id" +
            " GROUP BY ezobject.id, report.type")   // get all loans with dateStart during the new loan period
    List<Report> findAll();
     */

/*
    @Query("UPDATE report" +
    " SET valid = false" +
    " WHERE report.ezobject = :idEZobject")
   void reportObject1(int idEZobject);

    @Query("UPDATE ezObject" +
            " SET is_active = false" +
            " WHERE id = :idEZobject")
    void reportObject2(int idEZobject);
    */

}

