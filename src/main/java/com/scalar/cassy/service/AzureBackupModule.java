package com.scalar.cassy.service;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.BlobAsyncClient;
import com.azure.storage.blob.BlobServiceAsyncClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.scalar.cassy.config.BackupType;
import com.scalar.cassy.transferer.AzureFileUploader;
import com.scalar.cassy.transferer.FileUploader;
import com.scalar.cassy.traverser.FileTraverser;
import com.scalar.cassy.traverser.IncrementalBackupTraverser;
import com.scalar.cassy.traverser.SnapshotTraverser;
import java.nio.file.Paths;

public class AzureBackupModule extends AbstractModule {
  private final BackupType type;
  private final String dataDir;
  private final String snapshotId;

  public AzureBackupModule(BackupType type, String dataDir, String snapshotId) {
    this.type = type;
    this.dataDir = dataDir;
    this.snapshotId = snapshotId;
  }

  @Override
  protected void configure() {
    bind(FileUploader.class).to(AzureFileUploader.class).in(Singleton.class);
  }

  @Provides
  @Singleton
  FileTraverser provideFileTraverser() {
    if (type.equals(BackupType.NODE_INCREMENTAL)) {
      return new IncrementalBackupTraverser(Paths.get(dataDir));
    }
    return new SnapshotTraverser(Paths.get(dataDir), snapshotId);
  }

  @Provides
  @Singleton
  BlobServiceAsyncClient provideBlobServiceClient() {
    return new BlobServiceClientBuilder()
        .endpoint("https://cassydev.blob.core.windows.net/indetail-cassy-test") // input string directly for testing
        .credential(new DefaultAzureCredentialBuilder().build())
        .buildAsyncClient();
  }

  @Provides
  @Singleton
  BlobAsyncClient provideBlobClient(BlobServiceAsyncClient serviceAsyncClient) {
    return serviceAsyncClient
        .getBlobContainerAsyncClient("indetail-cassy-test") // input string directly for testing
        .getBlobAsyncClient("myblob"); // input string directly for testing
  }
}
