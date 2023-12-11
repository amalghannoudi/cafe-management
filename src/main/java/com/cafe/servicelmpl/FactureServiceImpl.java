package com.cafe.servicelmpl;

import com.cafe.JWT.JwtFilter;
import com.cafe.constantes.CafeConstants;
import com.cafe.dao.FactureDao;
import com.cafe.models.Facture;
import com.cafe.services.FactureService;
import com.cafe.utils.cafeUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


@Slf4j
@Service
public class FactureServiceImpl implements FactureService {

    @Autowired
    JwtFilter jwtFilter ;

    @Autowired
    FactureDao factureDao ;

    /* generer facture sous forme pdf dans un format bien precis */
    @Override
    public ResponseEntity<String> generateFacture(Map<String, Object> requestMap) {
      log.info("inside generateFacture ");
        try{
            log.info("inside try ");

            String fileName;
            if (validateRequest(requestMap)){
                log.info("inside validateRequest ");
                /* cette partie concerne la methode generatePdf*/
                 if (requestMap.containsKey("isGenerate")&& !(Boolean)requestMap.get("isGenerate")){
                     fileName =(String) requestMap.get("uuid");
                     log.info("fileName {}:",fileName);
                 }else {
                     fileName = cafeUtils.getUUID() ;
                     requestMap.put("uuid",fileName) ;
                     insertFacture(requestMap) ;
                 }

                File directory = new File(CafeConstants.locationFiles);
                if (!directory.exists()) {
                    if (directory.mkdirs()) {
                        log.info("Directory created successfully: {}", directory.getAbsolutePath());
                    } else {
                        log.error("Failed to create directory: {}", directory.getAbsolutePath());
                    }
                }
                    /* generer un pdf avc la location donnée */
                Document document = new Document();
                String filePath = CafeConstants.locationFiles + fileName + ".pdf";
                log.info("Generating PDF at path: {}", filePath);

                PdfWriter.getInstance(document,new FileOutputStream(CafeConstants.locationFiles+fileName+".pdf")) ;


                document.open() ;
                /* inserer cadre dans pdf*/
                setCadreInPdf(document) ;

                /* inserer titre dans pdf avc quelques styles*/
                Paragraph titre = new Paragraph("Cafe Management System", getFont("Header"));
                titre.setAlignment(Element.ALIGN_CENTER);
                document.add(titre) ;

                /* inserer  qlq données */
                String data = "Name : "+requestMap.get("name")+"\n"+"Contact Number : "+requestMap.get("number")+"\n"+
                        "Email : "+requestMap.get("email")+"\n"+"Methode de payement : "+requestMap.get("methodePayement") ;

                Paragraph para = new Paragraph(data + "\n \n",getFont("Data"));
                document.add(para) ;

                /* intialisation du tableau */
                PdfPTable table = new PdfPTable(5) ;
                table.setWidthPercentage(100);
                addTableHeader(table) ;
                 /* remplir le tableau avec les donnes get it from request map apres les convertir en json */
                JSONArray jsonArray = cafeUtils.getJsonFromString((String) requestMap.get("productDetails")) ;
                for (int i=0 ; i< jsonArray.length(); i++){
                    /* chaque ligne on doit la convertir en map */
                    addRow(table,cafeUtils.getMapFromJson(jsonArray.getString(i)));
                }
                document.add(table) ;

                /* ajouter foorer*/
                Paragraph footer = new Paragraph("Total = "+requestMap.get("totale")+"\n"+
                        "Thank you for visiting ! ",getFont("Data")) ;
                document.add(footer) ;
                document.close();
                log.info("PDF generation successful");

                return  new ResponseEntity<>("{\"uuid\":\""+ fileName +"/}",HttpStatus.OK) ;
             }
            return cafeUtils.getResponseEntity("required data invalid",HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            log.info("erreur");
            e.printStackTrace();
        }
        return cafeUtils.getResponseEntity(CafeConstants.erreur, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    /* l ajout des colones pour chaque ligne */
    private void addRow(PdfPTable table, Map<String, Object> data) {
      table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double) data.get("price")));
        table.addCell(Double.toString((Double) data.get("total")));

    }

    /* l entete du tableau avec qlq style */
    private void addTableHeader(PdfPTable table) {
        Stream.of("Name","Category","Quantity","Price","Sub Total")
                .forEach(columnTitle->{
                    PdfPCell header = new PdfPCell() ;
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBackgroundColor(BaseColor.BLUE);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    private void setCadreInPdf(Document document) throws DocumentException {
        log.info("Inside setTableauInPdf ");

        Rectangle rect = new Rectangle(577,825,18,15) ;
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
        document.add(rect) ;

    }
    /* creer font pour chaque data avec un styme different */
    private Font getFont(String type){
        log.info("inside getFont ");
        switch (type){
            case "Header" :
                Font headerFont=FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18,BaseColor.BLACK) ;
                headerFont.setStyle(Font.BOLD);
                return headerFont ;

            case "Data" :
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont ;

            default:
                return new Font() ;
        }
    }
/* la insertion du facture dans DB a l'aide du FactureDao */
    private void insertFacture(Map<String, Object> requestMap) {
        log.info("inside insertFacture ", requestMap);

        try{
        Facture facture = new Facture() ;
        facture.setUuid((String) requestMap.get("uuid"));
        facture.setName((String) requestMap.get("name")) ;
        facture.setEmail((String) requestMap.get("email")) ;
        facture.setNumber((String) requestMap.get("number")) ;
        facture.setMethodePayement((String) requestMap.get("methodePayement"));
            Object totaleAmountObject = requestMap.get("totale");
            int totaleValue = Integer.parseInt(totaleAmountObject.toString());
            facture.setTotale(totaleValue);
            facture.setProductDetails((String) requestMap.get("productDetails"));
           facture.setCreatedBy(jwtFilter.getCurretUser());
            log.info("facture: {}", facture);

            factureDao.save(facture) ;


    }catch(Exception e){
        e.printStackTrace();
    }
    }

    private boolean validateRequest(Map<String, Object> requestMap) {
        if (requestMap != null
                && requestMap.containsKey("name")
                && requestMap.containsKey("number")
                && requestMap.containsKey("email")
                && requestMap.containsKey("methodePayement")
                && requestMap.containsKey("productDetails")
                && requestMap.containsKey("totale")) {
            log.info("Validation successful");
            return true;
        } else {
            log.info("Values in the requestMap:");
            requestMap.forEach((key, value) -> log.info("{}: {}", key, value));
                    return false;
        }
    }



    /*Methode retourne all facture to admin and for specific user it return the facture that he does**/


    @Override
    public ResponseEntity<List<Facture>> getFacture() {
           List<Facture> list = new ArrayList<>() ;
           if (jwtFilter.isAdmin()){
                 list = factureDao.getAllFacture() ;
           }else {
               list = factureDao.getFactureByUserName(jwtFilter.getCurretUser()) ;
           }
        return new ResponseEntity<>(list,HttpStatus.OK);


    }

    /* retourner le pdf a partir du requestMap*/
    /* si le fichier deja exist juste en le convertir et le retourner sinon on generer un noveau fichier a partir du requestMap */

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        log.info("inside getPdf : requestMap {} :", requestMap);
        try {
            byte[] byteArray = new byte[0] ;
            if (!requestMap.containsKey("uuid") && validateRequest(requestMap)){
                log.error("Invalid request for PDF retrieval.");

                return new ResponseEntity<>(byteArray,HttpStatus.BAD_REQUEST) ;
            }
            /* construire filePath*/
            String filePath= CafeConstants.locationFiles+(String) requestMap.get("uuid")+".pdf" ;
            log.info("File path: {}", filePath);

            if (cafeUtils.isFileExist(filePath)){
                byteArray= getByteArray(filePath) ;
               return new ResponseEntity<>(byteArray,HttpStatus.OK) ;
            } else {
                log.info("File does not exist. Generating invoice...");

                requestMap.put("isGenerate",false) ;
                generateFacture(requestMap) ;
                log.info("After generating Facture. File Path: {}", filePath);
                byteArray= getByteArray(filePath) ;
                return new ResponseEntity<>(byteArray,HttpStatus.OK) ;
            }

         }   catch (Exception e){
            log.error("Erreur lors de la récupération du fichier PDF : {}", e.getMessage());

            e.printStackTrace();
         }
        return null ;
    }


    /* convertir le fichier à lire en byteArray */
    /* IOUtils class simplifies the process of converting an InputStream */

    private byte[] getByteArray(String filePath) throws Exception {
        try {
            File initFile = new File(filePath);
            if (initFile.exists()) {
                InputStream targetStream = new FileInputStream(initFile);
                byte[] byteArray = IOUtils.toByteArray(targetStream);
                targetStream.close();
                return byteArray;
            } else {
                log.error("File does not exist at path: {}", filePath);
                return new byte[0];
            }
        } catch (Exception e) {
            log.error("Error reading file: {}", e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /* methode delete by id */

    @Override
    public ResponseEntity<String> deleteById(Integer id) {
        try {
            Optional optional = factureDao.findById(id);
            if (!optional.isEmpty()){
            factureDao.deleteById(id);
            return cafeUtils.getResponseEntity("facture deleted successuly",HttpStatus.OK);
            }
            return cafeUtils.getResponseEntity("facture n existe pas",HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            e.printStackTrace();
        }
    return cafeUtils.getResponseEntity(CafeConstants.erreur,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
