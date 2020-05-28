package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Report;
import ch.heigvd.easytoolz.models.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report,String>, JpaSpecificationExecutor<Report>{
    /**
     * @param reportType type of the report
     * @return sort the reports by a report type
     */
    List<Report> findReportByReportType_OrderByEZObject(@NotNull ReportType reportType);

    /**
     * @param owner the username of the report
     * @return sort the reports by the username of the owner of the object
     */
    List<Report> findReportByEZObject_Owner_UserName(String owner);

    /**
     * @param reported
     * @return sorts the reports by the username of reporter
     */
    List<Report> findReportByReporter_UserName(String reported);
}

