// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cassandra-backup.proto

package com.scalar.backup.cassandra.rpc;

public interface StatusUpdateRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:rpc.StatusUpdateRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string cluster_id = 1;</code>
   */
  java.lang.String getClusterId();
  /**
   * <code>string cluster_id = 1;</code>
   */
  com.google.protobuf.ByteString
      getClusterIdBytes();

  /**
   * <code>string target_ip = 2;</code>
   */
  java.lang.String getTargetIp();
  /**
   * <code>string target_ip = 2;</code>
   */
  com.google.protobuf.ByteString
      getTargetIpBytes();

  /**
   * <code>string backup_id = 3;</code>
   */
  java.lang.String getBackupId();
  /**
   * <code>string backup_id = 3;</code>
   */
  com.google.protobuf.ByteString
      getBackupIdBytes();

  /**
   * <code>.rpc.OperationStatus status = 4;</code>
   */
  int getStatusValue();
  /**
   * <code>.rpc.OperationStatus status = 4;</code>
   */
  com.scalar.backup.cassandra.rpc.OperationStatus getStatus();
}
