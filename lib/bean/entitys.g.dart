// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'entitys.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

GroupEntity _$GroupEntityFromJson(Map<String, dynamic> json) => GroupEntity(
      json['userId'] as int,
      json['groupId'] as int,
      json['groupName'] as String,
    )..accounts = (json['accounts'] as List<dynamic>?)
        ?.map((e) => AccountEntity.fromJson(e as Map<String, dynamic>))
        .toList();

Map<String, dynamic> _$GroupEntityToJson(GroupEntity instance) =>
    <String, dynamic>{
      'userId': instance.userId,
      'groupId': instance.groupId,
      'groupName': instance.groupName,
      'accounts': instance.accounts,
    };

AccountEntity _$AccountEntityFromJson(Map<String, dynamic> json) =>
    AccountEntity(
      json['accountId'] as int,
      json['groupId'] as int,
      json['accountName'] as String,
      json['passport'] as String,
      json['password'] as String,
      json['remarks'] as String,
    )..isEncryption = json['isEncryption'] as bool;

Map<String, dynamic> _$AccountEntityToJson(AccountEntity instance) =>
    <String, dynamic>{
      'accountId': instance.accountId,
      'groupId': instance.groupId,
      'accountName': instance.accountName,
      'passport': instance.passport,
      'password': instance.password,
      'remarks': instance.remarks,
      'isEncryption': instance.isEncryption,
    };

UserEntity _$UserEntityFromJson(Map<String, dynamic> json) => UserEntity(
      json['userId'] as int,
      json['passport'] as String,
      json['nickName'] as String?,
    );

Map<String, dynamic> _$UserEntityToJson(UserEntity instance) =>
    <String, dynamic>{
      'userId': instance.userId,
      'passport': instance.passport,
      'nickName': instance.nickName,
    };
