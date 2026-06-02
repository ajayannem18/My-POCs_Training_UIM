/*==============================================================================================================
 * Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
 *
 * This sample file, which has been provided by Oracle Corporation as part of an Oracle® product for use
 * ONLY by licensed users of the product, includes CONFIDENTIAL and PROPRIETARY information of Oracle
 * Corporation.
 *
 * This material is the confidential property of Oracle Corporation or its licensors and may be used,
 * reproduced, stored, or transmitted only in accordance with a valid Oracle license or sublicense
 * agreement.
 * 
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS OF THE LICENSE
 * AGREEMENT AND LIMITED WARRANTY FURNISHED WITH THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD ORACLE CORPORATION, ITS RELATED COMPANIES AND ITS SUPPLIERS,
 * HARMLESS FROM AND AGAINST ANY CLAIMS OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF OR RESULTING FROM THE USE,
 * MODIFICATION, OR DISTRIBUTION OF PROGRAMS OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS
 * SAMPLE SOURCE CODE FILE.
 * =============================================================================================================*/

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import oracle.communications.inventory.api.common.container.ImportInventoryContainer;
import oracle.communications.inventory.api.common.container.ImportInventoryResult;
import oracle.communications.inventory.api.entity.ExtensionLanguageType;
import oracle.communications.inventory.api.entity.LocalizationInfo;
import oracle.communications.inventory.api.entity.RuleSetEntity;
import oracle.communications.inventory.api.exception.RuleExecutionException;
import oracle.communications.inventory.api.framework.logging.Log;
import oracle.communications.inventory.api.framework.logging.LogFactory;
import oracle.communications.inventory.extensibility.rules.GroovyRulesExecutor;
import oracle.communications.inventory.extensibility.rules.RulesAdministrator;
import oracle.communications.inventory.extensibility.rules.RulesExecutor;
import oracle.communications.inventory.extensibility.rules.RulesSearchCriteria;
import oracle.communications.platform.persistence.Finder;
import oracle.communications.platform.persistence.PersistenceHelper;
import oracle.communications.platform.util.Utils;

import oracle.communications.inventory.api.framework.logging.Log;
import oracle.communications.inventory.api.framework.logging.impl.FeedbackProviderImpl;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

/**------------------------------------------------------------------------------------------
Exports Specification, ConnectivityFunction displayNames for UI localization.
Fetches this information from LocalizationInfoDAO and generates .js for each language
Export is in ImportInventoryResult format
------------------------------------------------------------------------------------------**/

log.debug("", "Export specification display names a JSON");
return exportLocalizationInfoForSpecs();


def ImportInventoryResult exportLocalizationInfoForSpecs() {
    ImportInventoryResult iir = new ImportInventoryResult();
    iir.setDownloadAvailable(false);
    String jsFormatPrefix = "define(";
    String jsFormatSuffix = ");";
    String tempDir = System.getProperty("java.io.tmpdir");
    Finder finder = (Finder) PersistenceHelper.makeFinder();
    //language here is descriptive like 'Spanish (Mexico) [es-mx]', we only need 'es-mx' 

    try {
        //count the number of locales
        String query =
            "select * from LocalizationInfo where CHARACTERISTICSPECIFICATION IS NULL  AND (SPECIFICATION IS NOT NULL OR LABEL like 'ConnectivityFunction_%')";
        Collection < LocalizationInfo > localeInfos = finder.findByNativeSQL(LocalizationInfo.class, query);
        if (localeInfos == null || localeInfos.size() == 0)
            return iir;
        Map < String, Map < String, String >> langRowMap = new HashMap < String, Map < String, String >> ();
        Iterator i = localeInfos.iterator();
        LocalizationInfo li = null;
        int rowCount = localeInfos.size();
        int rowProcessed = 0;

        //required to populate nls app-string.js with language-boolean values
        Map < String, Boolean > langDirectories = new HashMap < String, Boolean > ();

        while (i.hasNext()) {
            li = (LocalizationInfo) i.next();
            if (!langRowMap.containsKey(li.getLanguage())) {
                langRowMap.put(li.getLanguage(), new HashMap < String, String > ());
            }
            String specType = null;
            if (li.getSpecification() != null)
                specType = li.getSpecification().getEntityClass();
            if (specType != null) {
                specType = specType.replace("SpecDAO", "");
                specType = specType.replace("SpecificationDAO", "");
            }
            String prefix = Utils.isEmpty(specType) ? "" : specType + "_";
            langRowMap.get(li.getLanguage()).put(prefix + li.getLabel(), li.getDisplayLabel());
        }
        int countLanguages = langRowMap.size();
        int errors = 0;
        String fileName = "appBundle-strings.js";
        for (Map.Entry < String, Map < String, String >> lang: langRowMap.entrySet()) {
            if (lang.getKey() == null)
                continue;
            String json = null;
            try {
                json = new ObjectMapper().writeValueAsString(lang.getValue());
                rowProcessed += lang.getValue().size();
            } catch (Exception e) {
                log.error("", e, "JSON parsing failed for " + lang.getKey());
                //e.printStackTrace();
                json = null;
                ++errors;
            }
            if (json != null) {
                //save to a <lang code folder>/file

                String langDir = lang.getKey();
                if (langDir.contains("[")) {
                    langDir = langDir.substring(langDir.indexOf('[') + 1, langDir.lastIndexOf(']'));
                }
                if (langDir.equalsIgnoreCase("default"))
                    langDir = "root";
                //Make later part of language as Capital as required by VBCS UI
                if (langDir.contains("-")) {
                    langDir = langDir.substring(0, langDir.indexOf('-') + 1) + langDir.substring(langDir.indexOf('-') + 1, langDir.length()).toUpperCase();
                }
                //create the directories first
                File file = null;
                try {
                    file = new File(FilenameUtils.normalize(tempDir + "/uimAppBundle_temp/nls/" + langDir));
                    file.mkdirs();
                } catch (IOException e) {
                    log.error("import.saveError", e, "Error");
                }
                BufferedWriter writer = null;
                //write json
                try {
                    writer = new BufferedWriter(new FileWriter(FilenameUtils.normalize(tempDir + "/uimAppBundle_temp/nls/" + langDir + "/" + fileName)));
                    writer.write(jsFormatPrefix + json + jsFormatSuffix);
                    langDirectories.put(langDir, true);
                } catch (IOException e) {
                    log.error("import.saveError", e, "nls/" + langDir + "/" + fileName);
                    //e.printStackTrace();
                } finally {
                    if (writer != null)
                        writer.close();
                }
            }
        }

        //create nls appBundle-strings.js
        createNlsJson(langDirectories);

        String zipFileName = "uimAppBundle.tar.gz";

        try {
            zipFolder(tempDir + "/uimAppBundle_temp", tempDir + "/" + zipFileName);
        } catch (IOException ioe) {
            ++errors;
            ioe.printStackTrace();
            log.error("import.saveError", ioe, zipFileName);
        }

        iir.setOutputFilePath(tempDir + "/" + zipFileName);
        iir.setOutputFileName(zipFileName);
        iir.setDownloadAvailable(true);
        iir.setImportedCount(countLanguages - errors);
        iir.setFailedCount(errors);
    } finally {
        finder.close();
        deleteFolder(new File(FilenameUtils.normalize(tempDir + "/uimAppBundle_temp")));
    }
    return iir;
}

def void deleteFolder(File file) {
    if (file.exists()) {
        File[] subFiles = file.listFiles();
        if (subFiles != null) {
            for (File subFile: subFiles) {
                if (subFile.isDirectory()) {
                    deleteFolder(subFile);
                } else {
                    subFile.delete();
                }
            }
        }
        file.delete();
    }
}

def void createNlsJson(Map < String, Boolean > langDirectories) {
    String json = null;
    String jsFormatPrefix = "define(";
    String jsFormatSuffix = ");";
    String tempDir = System.getProperty("java.io.tmpdir");
    try {
        json = new ObjectMapper().writeValueAsString(langDirectories);
    } catch (Exception e) {
        log.error("", e, "JSON parsing failed for NLS");
    }
    if (json != null) {
        //save to a <lang code folder>/file
        String fileName = "appBundle-strings.js";

        //create the directories first
        File file = null;
        try {
            file = new File(FilenameUtils.normalize(tempDir + "/uimAppBundle_temp/nls"));
            file.mkdirs();
        } catch (IOException e) {
            log.error("import.saveError", e, "nls/" + fileName);
        }
        BufferedWriter writer = null;
        //write json
        try {
            writer = new BufferedWriter(new FileWriter(FilenameUtils.normalize(tempDir + "/uimAppBundle_temp/nls" + "/" + fileName)));
            writer.write(jsFormatPrefix + json + jsFormatSuffix);
        } catch (IOException e) {
            log.error("import.saveError", e, "nls/" + fileName);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}

def void zipFolder(String srcFolder, String destZipFile) throws IOException {
    FileOutputStream fos = new FileOutputStream(FilenameUtils.normalize(destZipFile));
    GzipCompressorOutputStream gzipOut = new GzipCompressorOutputStream(fos);
    TarArchiveOutputStream tarOut = new TarArchiveOutputStream(gzipOut);
    try {
        File dir = new File(FilenameUtils.normalize(srcFolder));

        for (File file: dir.listFiles()) {
            addFileToZip(file, file.getName(), tarOut);
        }
    } catch (IOException e) {
        log.error("import.saveError", e, destZipFile);
    } finally {

        if (tarOut != null)
            tarOut.close();
        if (gzipOut != null)
            gzipOut.close();
        if (fos != null)
            fos.close();
    }
}

def void addFileToZip(File file, String fileName, TarArchiveOutputStream tarOut) throws IOException {
    if (file.isDirectory()) {
        File[] files = file.listFiles();
        for (File childFile: files) {
            addFileToZip(childFile, fileName + "/" + childFile.getName(), tarOut);
        }
        return;
    }

    FileInputStream fis = new FileInputStream(file);
    try {
        TarArchiveEntry tarEntry = new TarArchiveEntry(FilenameUtils.normalize(fileName));
        tarEntry.setSize(file.length());
        tarOut.putArchiveEntry(tarEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) > 0) {
            tarOut.write(bytes, 0, length);
        }
    } catch (IOException e) {
        e.printStackTrace();
        log.error("import.saveError", e, file.getName());
    } finally {
        if (fis != null)
            fis.close();
        tarOut.closeArchiveEntry();
    }
}