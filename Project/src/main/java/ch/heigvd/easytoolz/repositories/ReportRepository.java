package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Report;
import ch.heigvd.easytoolz.models.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Integer>, JpaSpecificationExecutor<Report>{
    List<Report> findByReportType(@NotNull ReportType reportType);
}

