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
}

