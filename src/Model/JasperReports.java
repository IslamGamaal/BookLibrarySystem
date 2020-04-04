package Model;

import java.sql.Connection;
//import net.sf.jasperreports.engine.*;
//import net.sf.jasperreports.view.JasperViewer;

public class JasperReports implements IReports{

    private void createReport(String reportSource, String reportDest) {
        try {
            Connection con = ConnectionHandler.getInstance().getConnection();
//            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);
//            JasperExportManager.exportReportToPdfFile(jasperPrint, reportDest);
//            JasperViewer.viewReport(jasperPrint, false);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void getTotalSales() {
        String reportSource = System.getProperty("user.dir") + "/src/reports/TotalSales.jrxml";
        String reportDest = System.getProperty("user.dir") + "/src/reports/TotalSales.pdf";

        createReport(reportSource, reportDest);
    }

    @Override
    public void getTop5Customers() {
        String reportSource = System.getProperty("user.dir") + "/src/reports/Top5Customers.jrxml";
        String reportDest = System.getProperty("user.dir") + "/src/reports/Top5Customers.pdf";

        createReport(reportSource, reportDest);
    }

    @Override
    public void getTop10SellingBooks() {
        String reportSource = System.getProperty("user.dir") + "/src/reports/Top10SellingBooks.jrxml";
        String reportDest = System.getProperty("user.dir") + "/src/reports/Top10SellingBooks.pdf";

        createReport(reportSource, reportDest);
    }
}