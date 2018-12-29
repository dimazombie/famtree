package com.dimazombie.famtree.model;

public interface FileEntryRepository {
    public FileEntry getById(String fileEntryId);
    public FileEntry persist(FileEntry fileEntry);
}
