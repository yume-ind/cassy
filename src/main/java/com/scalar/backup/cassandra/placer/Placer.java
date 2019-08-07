package com.scalar.backup.cassandra.placer;

import com.scalar.backup.cassandra.config.RestoreConfig;
import com.scalar.backup.cassandra.exception.PlacementException;
import com.scalar.backup.cassandra.transferer.BackupPath;
import com.scalar.backup.cassandra.traverser.IncrementalBackupTraverser;
import com.scalar.backup.cassandra.traverser.SnapshotTraverser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Placer {

  public void place(RestoreConfig config) {
    String key = BackupPath.create(config, "");
    Path fromDir = Paths.get(config.getDataDir(), key);
    Path toDir = Paths.get(config.getDataDir());

    place(
        new SnapshotTraverser(fromDir, config.getSnapshotId()).traverse(config.getKeyspace()),
        fromDir,
        toDir,
        SnapshotTraverser.DIR_TO_FILE_DISTANCE);

    place(
        new IncrementalBackupTraverser(fromDir).traverse(config.getKeyspace()),
        fromDir,
        toDir,
        IncrementalBackupTraverser.DIR_TO_FILE_DISTANCE);
  }

  private void place(List<Path> files, Path fromDir, Path toDir, int dirToFileDistance) {
    files.forEach(
        f -> {
          try {
            Path from = Paths.get(fromDir.toString(), f.toString());
            Path tmp = Paths.get(toDir.toString(), f.getParent().toString());
            for (int i = 0; i < dirToFileDistance; ++i) {
              tmp = tmp.getParent();
            }
            Path to = Paths.get(tmp.toString(), f.getFileName().toString());
            Files.createDirectories(to.getParent());
            Files.move(from, to);
          } catch (IOException e) {
            throw new PlacementException(e);
          }
        });
  }
}