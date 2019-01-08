package com.dimazombie.famtree.rest;

import com.dimazombie.famtree.model.FileEntry;
import com.dimazombie.famtree.model.FileEntryRepository;
import com.dimazombie.famtree.web.Secured;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

@Path("files")
public class FileResource {
    Logger logger = LoggerFactory.getLogger(FileResource.class);

    @Inject
    FileEntryRepository repo;

    String UPLOAD_DIR = "upload";

    private String getFilePath(FileEntry fileEntry) {
        String uploadDirPath = System.getProperty("user.dir") + File.separator + UPLOAD_DIR + File.separator + fileEntry.getId();
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String filePathName = uploadDirPath + File.separator + fileEntry.getName();
        return filePathName;
    }

    @POST
    @Secured
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Long uploadFile(@FormDataParam("file") InputStream inputStream,
                           @FormDataParam("file") FormDataContentDisposition fileDisposition) {

        FileEntry fileEntry = new FileEntry();
        fileEntry.setName(fileDisposition.getFileName());
        fileEntry = repo.persist(fileEntry);

        logger.debug("create fileEntry: " + fileEntry);

        String filePathName = getFilePath(fileEntry);
        logger.debug("with filePathName: " + filePathName);

        File file = new File(filePathName);

        try {
            int read = 0;
            byte[] bytes = new byte[1024];

            OutputStream out = new FileOutputStream(file);
            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error("",e);
            throw new WebApplicationException(500);
        }

        return fileEntry.getId();
    }

    @GET
    @Path("/{fileEntryId}")
    public Response getFile(@PathParam("fileEntryId") String fileEntryId) {

        FileEntry fileEntry = repo.findById(fileEntryId);
        logger.debug("found fileEntry:" + fileEntry);

        String filePathName = getFilePath(fileEntry);
        logger.debug("with filePathName: " + filePathName);

        File file = new File(filePathName);
        if (!file.exists()) {
            throw new WebApplicationException(404);
        }

        String mt = new MimetypesFileTypeMap().getContentType(file);
        return Response.ok(file, mt).build();
    }
}
