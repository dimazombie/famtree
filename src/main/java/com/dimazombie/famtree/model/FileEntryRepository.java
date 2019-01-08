package com.dimazombie.famtree.model;

public interface FileEntryRepository {
    public FileEntry findById(String fileEntryId);
    public FileEntry persist(FileEntry fileEntry);
}
