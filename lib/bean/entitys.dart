import 'dart:convert';
import 'dart:ui';

import 'package:json_annotation/json_annotation.dart';

part 'entitys.g.dart';

@JsonSerializable()
class GroupEntity {
  int userId = 0;
  int groupId = 0;
  String groupName = "";
  List<AccountEntity>? accounts = [];

  GroupEntity(this.userId, this.groupId, this.groupName);

  factory GroupEntity.fromJson(Map<String, dynamic> json) =>
      _$GroupEntityFromJson(json);

  Map<String, dynamic> toJson() => _$GroupEntityToJson(this);
}

@JsonSerializable()
class AccountEntity {
  int accountId = 0;
  int groupId = 0;
  String accountName = "";
  String passport = "";
  String password = "";
  String remarks = "";

  //是否加密 进行base64转码；用户设置加盐码；对加盐码进行md5；用MD5首字母取模-模参数为盐长度（最大不超过10）；
  //base64中在取模位置插入原始加盐码；AES加密后；上传到服务器
  bool isEncryption = true;

  //解密后的数据,内容为******时，认为解密失败
  String decodePwd = "";

  AccountEntity(this.accountId, this.groupId, this.accountName, this.passport,
      this.password, this.remarks);

  factory AccountEntity.fromJson(Map<String, dynamic> json) =>
      _$AccountEntityFromJson(json);

  Map<String, dynamic> toJson() => _$AccountEntityToJson(this);

//解码：服务器数据进行AES解密； 用户输入加盐码；对加盐码进行md5；用MD5首字母取模-模参数为盐长度（最大不超过10）；
//base64中在取模位置删除原始加盐码，进行base64解码
}

@JsonSerializable()
class UserEntity {
  int userId = 0;
  String passport = "";
  String? nickName;

  UserEntity(this.userId, this.passport, this.nickName);

  factory UserEntity.fromJson(Map<String, dynamic> json) =>
      _$UserEntityFromJson(json);

  Map<String, dynamic> toJson() => _$UserEntityToJson(this);
//factory UserEntity.fromJson()

}
